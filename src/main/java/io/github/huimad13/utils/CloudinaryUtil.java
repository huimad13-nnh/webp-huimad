package io.github.huimad13.utils;

import java.io.IOException;
import java.util.Map;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import jakarta.servlet.http.Part;

/**
 * Upload ảnh lên Cloudinary.
 * Cấu hình lấy từ biến môi trường (không ghi secret vào code/git):
 *   CLOUDINARY_URL = cloudinary://<api_key>:<api_secret>@<cloud_name>  (copy ở Dashboard của Cloudinary)
 */
public class CloudinaryUtil {
	private static final Cloudinary CLOUDINARY = new Cloudinary(); // tự đọc CLOUDINARY_URL

	/**
	 * Upload file multipart lên Cloudinary.
	 * @return URL https của ảnh, hoặc null nếu người dùng không chọn file.
	 */
	public static String upload(Part part, String folder) throws IOException {
		if (part == null || part.getSize() == 0) {
			return null;
		}
		if (CLOUDINARY.config.cloudName == null) {
			throw new IllegalStateException("Chưa cấu hình biến môi trường CLOUDINARY_URL");
		}
		Map<?, ?> result = CLOUDINARY.uploader().upload(part.getInputStream().readAllBytes(),
				ObjectUtils.asMap("folder", folder, "resource_type", "image"));
		return (String) result.get("secure_url");
	}
}
