package io.github.huimad13.utils;

import java.util.Properties;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

/**
 * Gửi email qua SMTP của Gmail.
 * Tài khoản gửi lấy từ biến môi trường (không ghi mật khẩu vào code/git):
 *   MAIL_USERNAME = địa chỉ gmail gửi đi
 *   MAIL_PASSWORD = "App password" 16 ký tự của gmail đó (không phải mật khẩu đăng nhập)
 */
public class MailUtil {
	private static final String USERNAME = System.getenv("MAIL_USERNAME");
	private static final String PASSWORD = System.getenv("MAIL_PASSWORD");

	public static void send(String to, String subject, String text) {
		if (USERNAME == null || PASSWORD == null) {
			// shortcut: chưa cấu hình SMTP -> in mail ra console để vẫn test được trên máy mình
			System.out.println("[MAIL - chưa cấu hình SMTP] to=" + to + " | " + subject + "\n" + text);
			return;
		}
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		try {
			MimeMessage msg = new MimeMessage(Session.getInstance(props));
			msg.setFrom(new InternetAddress(USERNAME));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			msg.setSubject(subject, "UTF-8");
			msg.setText(text, "UTF-8");
			Transport.send(msg, USERNAME, PASSWORD);
		} catch (MessagingException e) {
			throw new RuntimeException("Gửi email thất bại", e);
		}
	}
}
