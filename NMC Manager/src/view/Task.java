/**
 * 
 */
package view;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import controller.TransferManager;

/**
 * @author Derek
 *
 */


public class Task extends SwingWorker<Void, Void> {
	/*
	 * Main task. Executed in background thread.
	 */
	private File uploadFile;
	
	public Task(File selectedFile) {
		this.uploadFile = selectedFile;
	}

	@Override
	public Void doInBackground() throws Exception {
		try {
			TransferManager.getInstance().connect();
			TransferManager.getInstance().sendFile(uploadFile);
			FileInputStream inputStream = new FileInputStream(uploadFile);
			byte[] buffer = new byte[4096];
			int bytesRead = -1;
			long totalBytesRead = 0;
			int percentCompleted = 0;
			long fileSize = uploadFile.length();
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				TransferManager.getInstance().writeFile(buffer, 0, bytesRead);
				totalBytesRead += bytesRead;
				percentCompleted = (int) (totalBytesRead * 100 / fileSize);
				setProgress(percentCompleted);
			}
			inputStream.close();
			TransferManager.getInstance().finish();
		} catch (FTPException e){
			JOptionPane.showMessageDialog(null,  "Error upload file: " + e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			setProgress(0);
			cancel(true);
		} finally{
			TransferManager.getInstance().close();
		}
		return null;
	}

	/*
	 * Executed in event dispatching thread
	 */
	@Override
	public void done() {
		Toolkit.getDefaultToolkit().beep();

	}
}
