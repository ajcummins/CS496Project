package edu.ycp.cs496.eduapp.webapp.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import edu.ycp.cs496.eduapp.model.persist.DatabaseProvider;
import edu.ycp.cs496.eduapp.model.persist.FakeDatabase;

public class DatabaseInitListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent e) {
		// Webapp is starting
		DatabaseProvider.setInstance(new FakeDatabase());
		System.out.println("Initialized database!");
	}

	@Override
	public void contextDestroyed(ServletContextEvent e) {
		// Webapp is shutting down
	}

}
