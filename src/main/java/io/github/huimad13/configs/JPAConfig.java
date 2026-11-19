package io.github.huimad13.configs;

import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import io.github.huimad13.utils.ValidationUtil;

@WebListener
public class JPAConfig implements ServletContextListener {

	private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("learn-jpa",
			Map.of("jakarta.persistence.validation.factory", ValidationUtil.factory,
					// Mật khẩu DB lấy từ biến môi trường, không ghi vào code/git
					"jakarta.persistence.jdbc.password", requireEnv("DB_PASSWORD")));

	private static String requireEnv(String name) {
		String value = System.getenv(name);
		if (value == null || value.isEmpty()) {
			throw new IllegalStateException("Chưa đặt biến môi trường " + name);
		}
		return value;
	}

	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		factory.close();
	}
}
