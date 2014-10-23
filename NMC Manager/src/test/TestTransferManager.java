package test;

import java.io.File;

import org.junit.Test;

import view.FTPException;
import controller.TransferManager;


public class TestTransferManager {

	@Test
	public void testSendFile() {
		// Launch Main NMCServer BEFORE launch this test
		try {
			TransferManager.getInstance().sendFile("Movies", new File("C:\\Users\\Antoine Ceyssens\\Downloads\\from_sqli_to_shell.iso"), null);
		} catch (FTPException e) {
			e.printStackTrace();
		}
		
	}

}
