package io.github.huimad13.utils;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

/**
 * Kiểm tra các annotation @NotBlank, @Email, @Size... trên entity (Jakarta Validation + hibernate-validator).
 */
public class ValidationUtil {
	// ParameterMessageInterpolator: không cần thư viện EL (jakarta.el) để dựng câu thông báo lỗi
	public static final ValidatorFactory factory = Validation.byDefaultProvider()
			.configure()
			.messageInterpolator(new ParameterMessageInterpolator())
			.buildValidatorFactory();

	/** @return các thông báo lỗi nối bằng &lt;br&gt;, hoặc null nếu hợp lệ. */
	public static <T> String validate(T object) {
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<T>> violations = validator.validate(object);
		if (violations.isEmpty()) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (ConstraintViolation<T> v : violations) {
			if (sb.length() > 0) {
				sb.append("<br>");
			}
			sb.append(v.getMessage());
		}
		return sb.toString();
	}
}
