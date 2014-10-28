/**
 * 
 */
package view;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import model.FTPException;
import model.MetaDataCollector;
import controller.SocketManager;
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
	private String directory;
	private MetaDataCollector mdc;
	private static final int BUFFER_SIZE = 4096;

	/** Task Constructor
	 * @param selectedFile file to be uploaded
	 */
	public Task(String directory, File selectedFile, MetaDataCollector mdc) {
		this.uploadFile = selectedFile;
		this.directory = directory;
		this.mdc = mdc;
	}

	@Override
	public Void doInBackground() throws Exception {
		try {
			TransferManager.getInstance().connect();
			TransferManager.getInstance().sendFile(directory, uploadFile, mdc);
			FileInputStream inputStream = new FileInputStream(uploadFile);
			byte[] buffer = new byte[BUFFER_SIZE]; 
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
			SocketManager.getInstance().sendMeta(mdc); 
			TransferManager.getInstance().finish();
		} catch (IOException | FTPException e){
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
		if (!isCancelled()) {
			JOptionPane.showMessageDialog(null,
					"File has been uploaded successfully!", "Message",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
