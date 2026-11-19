package io.github.huimad13.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

public class UploadUtil {
	// /image trả Content-Type theo đuôi file -> chỉ nhận đuôi an toàn (chặn upload .html/.svg chứa script)
	public static final List<String> IMAGE_EXTS = List.of("jpg", "jpeg", "png", "gif", "webp");
	public static final List<String> VIDEO_EXTS = List.of("mp4", "webm", "ogg", "mov");

	/**
	 * Kiểm tra file upload: dung lượng (theo @MultipartConfig) và đuôi file.
	 * Gọi TRƯỚC khi đọc các ô khác: request quá lớn thì Tomcat không đọc được form, getParameter trả null.
	 * @return thông báo lỗi, hoặc null nếu hợp lệ / không chọn file.
	 */
	public static String check(HttpServletRequest req, String name, List<String> allowedExts) {
		Part part;
		try {
			part = req.getPart(name);
		} catch (IllegalStateException e) { // vượt maxFileSize / maxRequestSize
			return "File vượt quá dung lượng cho phép";
		} catch (IOException | ServletException e) {
			return "Không đọc được file upload";
		}
		if (part == null || part.getSize() == 0) {
			return null;
		}
		if (!allowedExts.contains(extension(part))) {
			return "Chỉ chấp nhận file: " + String.join(", ", allowedExts);
		}
		return null;
	}

	private static String extension(Part part) {
		String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
		int index = filename.lastIndexOf(".");
		return index >= 0 ? filename.substring(index + 1).toLowerCase(Locale.ROOT) : "";
	}

	/**
	 * Lưu file upload vào Constant.DIR với tên mới = thời gian hiện tại + đuôi file.
	 * @return tên file đã lưu, hoặc null nếu người dùng không chọn file.
	 */
	public static String save(Part part) throws IOException {
		if (part == null || part.getSize() == 0) {
			return null;
		}
		File uploadDir = new File(Constant.DIR);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		String fname = System.currentTimeMillis() + "." + extension(part);
		part.write(new File(uploadDir, fname).getAbsolutePath());
		return fname;
	}

	/** Xoá file đã upload (bỏ qua nếu null/rỗng/không tồn tại). */
	public static void delete(String fileName) {
		if (fileName != null && !fileName.isBlank()) {
			new File(Constant.DIR, fileName).delete();
		}
	}
}
