package vn.iotstar.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import jakarta.servlet.http.Part;

public class UploadUtil {
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
		String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
		int index = filename.lastIndexOf(".");
		String ext = index >= 0 ? filename.substring(index + 1) : "png";
		String fname = System.currentTimeMillis() + "." + ext;
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
