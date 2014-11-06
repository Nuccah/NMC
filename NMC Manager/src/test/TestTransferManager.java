package test;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.jcraft.jsch.SftpException;

import controller.TransferManager;


public class TestTransferManager {

	@Test
	public void testSendFile() {
		// Launch Main NMCServer BEFORE launch this test
		TransferManager.getInstance().connect();
		try {
			TransferManager.getInstance().sendFile("Movies", new File("F:\\Téléchargement\\Daybreaker.avi"), null);
		} catch (SftpException | IOException e) {
			e.printStackTrace();
		}
		TransferManager.getInstance().close();
		
	}

}
