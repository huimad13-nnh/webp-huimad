package vn.iotstar.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import vn.iotstar.entity.User;

/**
 * Một yêu cầu đang chờ nhập OTP gửi qua email, chỉ nằm trong session.
 * Dùng cho: đăng ký (user mới, CHƯA lưu DB) và quên mật khẩu (user có sẵn trong DB).
 */
@SuppressWarnings("serial")
public class PendingOtp implements Serializable {
	private final User user;
	private final String purpose; // ghi trong email, vd "kích hoạt tài khoản"
	private String otp; // null = mã đã dùng/bị huỷ, phải gửi lại
	private LocalDateTime sentAt; // lúc gửi mã (tính hạn dùng và thời gian chờ gửi lại)
	private int attempts; // số lần nhập sai mã hiện tại

	public PendingOtp(User user, String purpose) {
		this.user = user;
		this.purpose = purpose;
	}

	public User getUser() { return user; }
	public String getPurpose() { return purpose; }
	public String getOtp() { return otp; }
	public void setOtp(String otp) { this.otp = otp; }
	public LocalDateTime getSentAt() { return sentAt; }
	public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }
	public int getAttempts() { return attempts; }
	public void setAttempts(int attempts) { this.attempts = attempts; }
}
