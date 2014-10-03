package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import controller.TransferManager;

public class TestReceiver {

	@Test
	public void testRun() {
		TransferManager test = TransferManager.getInstance();
		test.start();
        String server = "localhost";
        int port = 50001;
        String user = "ftpadmin";
        String pass = "ephec2014";
 
        FTPClient ftpClient = new FTPClient();
        try{
        	ftpClient.connect(server, port);
        	ftpClient.login(user, pass);
        	ftpClient.enterLocalPassiveMode();
        	ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        	
        	File testF = new File("C:\\Users\\Antoine Ceyssens\\Downloads\\Rouge Rubis.mkv");
        	InputStream is = new FileInputStream(testF);
        	String fileName = "Rouge Rubis.mkv";
        	
        	OutputStream os = ftpClient.storeFileStream(fileName);
        	
            byte[] bytesIn = new byte[4096];
            int read = 0;
 
            while ((read = is.read(bytesIn)) != -1) {
                os.write(bytesIn, 0, read);
            }
            is.close();
            os.close();
 
            boolean completed = ftpClient.completePendingCommand();
            if (completed) {
                System.out.println("The file is uploaded successfully.");
            }
        	
        	is.close();
        	
        } catch (IOException e){
        	e.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
	}

}
