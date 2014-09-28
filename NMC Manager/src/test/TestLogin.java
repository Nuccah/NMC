package test;

import model.Config;
import model.ConnectorDB;

import org.junit.Test;

import controller.SessionManager;

public class TestLogin {

	@Test
	public void testLogin() {
		Config.init();
		SessionManager.login("admin", "admin");
	}
	
	@Test
	public void testLogout(){
		SessionManager.login("admin", "admin");
		ConnectorDB.closeConnection();
		SessionManager.logout();
	}

}
