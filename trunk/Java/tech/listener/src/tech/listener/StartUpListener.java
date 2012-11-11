package tech.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartUpListener implements ServletContextListener {

	static Logger logger = LoggerFactory.getLogger(StartUpListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info("StartUpListener.contextDestorted");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("StartUpListener.contextInitialized");
	}

}
