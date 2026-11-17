package vn.iotstar.services;

import vn.iotstar.entity.User;
import vn.iotstar.models.PendingRegistration;

public interface UserService {
	User login(String username, String password);

	User get(String username);

	/** Bước 1: kiểm tra dữ liệu + trùng. @return thông báo lỗi, hoặc null nếu hợp lệ. */
	String checkRegister(User user);

	/** Bước 2: tạo OTP và gửi email. Chưa lưu DB. Ném RuntimeException nếu gửi mail lỗi. */
	PendingRegistration sendOtp(User user);

	/** Bước 3: đúng OTP thì mới lưu user vào DB. @return thông báo lỗi, hoặc null nếu thành công. */
	String activate(PendingRegistration pending, String otp);

	/** Tạo mã OTP mới và gửi lại. @return thông báo lỗi, hoặc null nếu đã gửi. */
	String resendOtp(PendingRegistration pending);
}
