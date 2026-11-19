package io.github.huimad13.services;

import io.github.huimad13.entity.User;
import io.github.huimad13.models.PendingOtp;

public interface UserService {
	User login(String username, String password);

	User get(String username);

	// ===== Đăng ký =====

	/** Bước 1: kiểm tra dữ liệu + trùng. @return thông báo lỗi, hoặc null nếu hợp lệ. */
	String checkRegister(User user);

	/** Bước 2: tạo OTP và gửi email. Chưa lưu DB. Ném RuntimeException nếu gửi mail lỗi. */
	PendingOtp sendRegisterOtp(User user);

	/** Bước 3: đúng OTP thì mới lưu user vào DB. @return thông báo lỗi, hoặc null nếu thành công. */
	String activate(PendingOtp pending, String otp);

	// ===== Quên mật khẩu =====

	/** Gửi OTP tới email. @return null nếu không có tài khoản nào dùng email này. Ném RuntimeException nếu gửi mail lỗi. */
	PendingOtp sendResetOtp(String email);

	/** Đúng OTP thì đổi mật khẩu. @return thông báo lỗi, hoặc null nếu thành công. */
	String resetPassword(PendingOtp pending, String otp, String newPassword);

	// ===== Dùng chung =====

	/** Tạo mã OTP mới và gửi lại. @return thông báo lỗi, hoặc null nếu đã gửi. */
	String resendOtp(PendingOtp pending);
}
