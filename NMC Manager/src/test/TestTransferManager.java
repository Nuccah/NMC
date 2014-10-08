package test;

import java.io.File;

import org.junit.Test;

import controller.TransferManager;

public class TestTransferManager {

	@Test
	public void testSendFile() {
		// Launch Main NMCServer BEFORE launch this test
		TransferManager.getInstance().sendFile(new File("C:\\Users\\Antoine Ceyssens\\Downloads\\from_sqli_to_shell.iso"));
		
	}

}
