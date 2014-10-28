package test;

import java.io.File;

import model.FTPException;

import org.junit.Test;

import controller.TransferManager;


public class TestTransferManager {

	@Test
	public void testSendFile() {
		// Launch Main NMCServer BEFORE launch this test
		TransferManager.getInstance().connect();
		try {
			TransferManager.getInstance().sendFile("Movies", new File("F:\\Téléchargement\\Daybreaker.avi"), null);
		} catch (FTPException e) {
			e.printStackTrace();
		}
		TransferManager.getInstance().close();
		
	}

}
