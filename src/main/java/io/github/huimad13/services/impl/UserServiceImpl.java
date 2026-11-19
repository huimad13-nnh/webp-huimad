package io.github.huimad13.services.impl;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import io.github.huimad13.dao.UserDao;
import io.github.huimad13.dao.impl.UserDaoImpl;
import io.github.huimad13.entity.User;
import io.github.huimad13.models.PendingOtp;
import io.github.huimad13.services.UserService;
import io.github.huimad13.utils.MailUtil;
import io.github.huimad13.utils.ValidationUtil;

public class UserServiceImpl implements UserService {
	private static final Duration OTP_TTL = Duration.ofMinutes(5); // mã có hiệu lực 5 phút
	private static final Duration RESEND_WAIT = Duration.ofSeconds(60); // chờ 60s mới được gửi lại
	private static final int MAX_ATTEMPTS = 5; // sai 5 lần thì huỷ mã, phải gửi lại
	private static final SecureRandom RANDOM = new SecureRandom();

	UserDao userDao = new UserDaoImpl();

	@Override
	public User login(String username, String password) {
		User user = userDao.findByUsername(username);
		if (user != null && password.equals(user.getPassWord())) {
			return user;
		}
		return null;
	}

	@Override
	public User get(String username) {
		return userDao.findByUsername(username);
	}

	// ===== Đăng ký =====

	@Override
	public String checkRegister(User user) {
		// Kiểm tra dữ liệu theo annotation trên entity (@NotBlank, @Email, @Size, @Pattern)
		String error = ValidationUtil.validate(user);
		return error != null ? error : checkDuplicate(user);
	}

	@Override
	public PendingOtp sendRegisterOtp(User user) {
		return startOtp(new PendingOtp(user, "kích hoạt tài khoản"));
	}

	@Override
	public String activate(PendingOtp pending, String otp) {
		String error = checkOtp(pending, otp);
		if (error != null) {
			return error;
		}
		User user = pending.getUser();
		// Trong lúc chờ OTP có thể người khác đã đăng ký trùng -> kiểm tra lại trước khi lưu
		error = checkDuplicate(user);
		if (error != null) {
			return error;
		}
		user.setRoleid(5);
		user.setCreatedDate(LocalDate.now());
		userDao.insert(user);
		return null;
	}

	// ===== Quên mật khẩu =====

	@Override
	public PendingOtp sendResetOtp(String email) {
		User user = userDao.findByEmail(email);
		return user == null ? null : startOtp(new PendingOtp(user, "đặt lại mật khẩu"));
	}

	@Override
	public String resetPassword(PendingOtp pending, String otp, String newPassword) {
		// Kiểm tra mật khẩu mới TRƯỚC, để mật khẩu không hợp lệ không làm mất lượt nhập OTP
		String error = ValidationUtil.validateValue(User.class, "passWord", newPassword);
		if (error != null) {
			return error;
		}
		error = checkOtp(pending, otp);
		if (error != null) {
			return error;
		}
		userDao.updatePassword(pending.getUser().getId(), newPassword);
		return null;
	}

	// ===== Dùng chung =====

	@Override
	public String resendOtp(PendingOtp pending) {
		synchronized (pending) {
			// Chặn bấm gửi liên tục (spam mail, đoán mò mã)
			if (LocalDateTime.now().isBefore(pending.getSentAt().plus(RESEND_WAIT))) {
				return "Vui lòng đợi " + RESEND_WAIT.toSeconds() + " giây rồi mới gửi lại mã.";
			}
			newOtp(pending);
		}
		sendOtpMail(pending);
		return null;
	}

	private PendingOtp startOtp(PendingOtp pending) {
		newOtp(pending);
		sendOtpMail(pending);
		return pending;
	}

	/** @return thông báo lỗi, hoặc null nếu mã đúng (mã bị huỷ ngay, mỗi mã chỉ dùng 1 lần). */
	private String checkOtp(PendingOtp pending, String otp) {
		// synchronized: 2 request cùng session gửi song song không vượt được giới hạn số lần sai
		synchronized (pending) {
			if (pending.getOtp() == null || LocalDateTime.now().isAfter(pending.getSentAt().plus(OTP_TTL))) {
				return "Mã OTP đã hết hiệu lực, hãy bấm \"Gửi lại mã\".";
			}
			if (!pending.getOtp().equals(otp)) {
				pending.setAttempts(pending.getAttempts() + 1);
				if (pending.getAttempts() >= MAX_ATTEMPTS) {
					pending.setOtp(null);
					return "Nhập sai quá " + MAX_ATTEMPTS + " lần, mã đã bị huỷ. Hãy bấm \"Gửi lại mã\".";
				}
				return "Mã OTP không đúng!";
			}
			pending.setOtp(null);
			return null;
		}
	}

	private String checkDuplicate(User user) {
		if (userDao.checkExistUsername(user.getUserName())) {
			return "Tài khoản đã tồn tại!";
		}
		if (userDao.checkExistEmail(user.getEmail())) {
			return "Email đã tồn tại!";
		}
		if (user.getPhone() != null && !user.getPhone().isEmpty() && userDao.checkExistPhone(user.getPhone())) {
			return "Số điện thoại đã tồn tại!";
		}
		return null;
	}

	// Mã 6 chữ số ngẫu nhiên (SecureRandom: không đoán trước được), đặt lại số lần sai
	private void newOtp(PendingOtp pending) {
		pending.setOtp(String.format("%06d", RANDOM.nextInt(1_000_000)));
		pending.setSentAt(LocalDateTime.now());
		pending.setAttempts(0);
	}

	private void sendOtpMail(PendingOtp pending) {
		User user = pending.getUser();
		MailUtil.send(user.getEmail(), "Mã OTP " + pending.getPurpose(),
				"Xin chào " + user.getUserName() + ",\n\n"
						+ "Mã OTP " + pending.getPurpose() + " của bạn là: " + pending.getOtp() + "\n"
						+ "Mã có hiệu lực trong " + OTP_TTL.toMinutes() + " phút.\n\n"
						+ "Nếu không phải bạn yêu cầu, hãy bỏ qua email này.");
	}
}
