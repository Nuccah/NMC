package test;

import model.Config;

import org.junit.Test;

import controller.Login;

public class TestLogin {

	@Test
	public void testLogin() {
		Config conf = new Config();
		conf.init();
		new Login("admin", "admin");
	}

}
