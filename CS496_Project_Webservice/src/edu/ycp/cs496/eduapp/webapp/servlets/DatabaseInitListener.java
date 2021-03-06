package edu.ycp.cs496.eduapp.webapp.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import edu.ycp.cs496.eduapp.model.persist.DatabaseProvider;
import edu.ycp.cs496.eduapp.model.persist.DerbyDatabase;
import edu.ycp.cs496.eduapp.model.persist.FakeDatabase;

public class DatabaseInitListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent e) {
		DatabaseProvider.setInstance(new DerbyDatabase()); //FakeDatabase()
		System.out.println("Database initialized!");
	}

	@Override
	public void contextDestroyed(ServletContextEvent e) {
		// Nothing to do
	}

}
