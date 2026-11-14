package vn.iotstar.configs;

import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import vn.iotstar.utils.ValidationUtil;

@WebListener
public class JPAConfig implements ServletContextListener {

	private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("learn-jpa",
			Map.of("jakarta.persistence.validation.factory", ValidationUtil.factory));

	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		factory.close();
	}
}
