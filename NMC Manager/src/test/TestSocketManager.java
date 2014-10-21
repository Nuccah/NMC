package test;

import java.io.IOException;

import model.VideoCollector;

import org.junit.Test;

import controller.SocketManager;

public class TestSocketManager {

	@Test
	public void testSendMeta() {
		VideoCollector vidC = new VideoCollector(
				"Title", 
				1000, 
				0, 
				1, 
				"Test.avi", 
				"Moi", 
				"Ta gueule", 
				"c'est de la merde ce film ne le reagardez surtout pas!!!!");
		vidC.setRelPath("\\Test\\Test.avi");
		try {
			SocketManager sm = new SocketManager();
			sm.sendMeta(vidC);
			sm.close();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}

}
