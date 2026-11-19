package io.github.huimad13.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.github.huimad13.entity.User;

import lombok.Getter;
import lombok.Setter;

/**
 * Một yêu cầu đang chờ nhập OTP gửi qua email, chỉ nằm trong session.
 * Dùng cho: đăng ký (user mới, CHƯA lưu DB) và quên mật khẩu (user có sẵn trong DB).
 */
@SuppressWarnings("serial")
@Getter
public class PendingOtp implements Serializable {
	private final User user;
	private final String purpose; // ghi trong email, vd "kích hoạt tài khoản"
	@Setter
	private String otp; // null = mã đã dùng/bị huỷ, phải gửi lại
	@Setter
	private LocalDateTime sentAt; // lúc gửi mã (tính hạn dùng và thời gian chờ gửi lại)
	@Setter
	private int attempts; // số lần nhập sai mã hiện tại

	public PendingOtp(User user, String purpose) {
		this.user = user;
		this.purpose = purpose;
	}
}
