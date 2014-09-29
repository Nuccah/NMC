package test;

import model.Config;
import model.ConnectorDB;

import org.junit.Test;

import controller.SessionManager;

public class TestLogin {

	@Test
	public void testLogin() {
		Config.getInstance();
		SessionManager.getInstance().login("admin", "admin");
	}
	
	@Test
	public void testLogout(){
		SessionManager sm = SessionManager.getInstance();
		sm.login("admin", "admin");
		ConnectorDB.getInstance().closeConnection();
		sm.logout();
	}

}
