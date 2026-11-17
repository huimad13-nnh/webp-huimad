package vn.iotstar.services.impl;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import vn.iotstar.dao.UserDao;
import vn.iotstar.dao.impl.UserDaoImpl;
import vn.iotstar.entity.User;
import vn.iotstar.models.PendingRegistration;
import vn.iotstar.services.UserService;
import vn.iotstar.utils.MailUtil;
import vn.iotstar.utils.ValidationUtil;

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

	@Override
	public String checkRegister(User user) {
		// Kiểm tra dữ liệu theo annotation trên entity (@NotBlank, @Email, @Size, @Pattern)
		String error = ValidationUtil.validate(user);
		return error != null ? error : checkDuplicate(user);
	}

	@Override
	public PendingRegistration sendOtp(User user) {
		PendingRegistration pending = new PendingRegistration(user);
		newOtp(pending);
		sendOtpMail(pending);
		return pending;
	}

	@Override
	public String activate(PendingRegistration pending, String otp) {
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
			pending.setOtp(null); // mỗi mã chỉ dùng 1 lần
		}
		User user = pending.getUser();
		// Trong lúc chờ OTP có thể người khác đã đăng ký trùng -> kiểm tra lại trước khi lưu
		String error = checkDuplicate(user);
		if (error != null) {
			return error;
		}
		user.setRoleid(5);
		user.setCreatedDate(LocalDate.now());
		userDao.insert(user);
		return null;
	}

	@Override
	public String resendOtp(PendingRegistration pending) {
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
	private void newOtp(PendingRegistration pending) {
		pending.setOtp(String.format("%06d", RANDOM.nextInt(1_000_000)));
		pending.setSentAt(LocalDateTime.now());
		pending.setAttempts(0);
	}

	private void sendOtpMail(PendingRegistration pending) {
		User user = pending.getUser();
		MailUtil.send(user.getEmail(), "Mã kích hoạt tài khoản",
				"Xin chào " + user.getUserName() + ",\n\n"
						+ "Mã OTP kích hoạt tài khoản của bạn là: " + pending.getOtp() + "\n"
						+ "Mã có hiệu lực trong " + OTP_TTL.toMinutes() + " phút.\n\n"
						+ "Nếu bạn không đăng ký tài khoản, hãy bỏ qua email này.");
	}
}
