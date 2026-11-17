package vn.iotstar.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import vn.iotstar.entity.User;

/**
 * Thông tin đăng ký đang chờ nhập OTP. Chỉ nằm trong session, CHƯA lưu DB.
 * Nhập đúng OTP thì user mới được insert vào DB.
 */
@SuppressWarnings("serial")
public class PendingRegistration implements Serializable {
	private final User user;
	private String otp; // null = mã đã dùng/bị huỷ, phải gửi lại
	private LocalDateTime sentAt; // lúc gửi mã (tính hạn dùng và thời gian chờ gửi lại)
	private int attempts; // số lần nhập sai mã hiện tại

	public PendingRegistration(User user) {
		this.user = user;
	}

	public User getUser() { return user; }
	public String getOtp() { return otp; }
	public void setOtp(String otp) { this.otp = otp; }
	public LocalDateTime getSentAt() { return sentAt; }
	public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }
	public int getAttempts() { return attempts; }
	public void setAttempts(int attempts) { this.attempts = attempts; }
}
