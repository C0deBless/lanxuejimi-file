package com.trnnn.osgi.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.trnnn.osgi.lancher.EquinoxLancher;

public class FrameworkConfigListener implements ServletContextListener {

	protected EquinoxLancher equinox;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		equinox = new EquinoxLancher(event.getServletContext());
		equinox.start();
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		if (equinox != null)
			equinox.stop();
	}
}
