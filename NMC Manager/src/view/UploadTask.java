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

import com.xuggle.mediatool.IMediaReader;

import controller.Converter;
import controller.TransferManager;

/**
 * @author Derek
 *
 */

public class UploadTask extends SwingWorker<Void, Void> {
	/*
	 * Main task. Executed in background thread.
	 */
	private File uploadFile;
	private String directory;
	private static final int BUFFER_SIZE = 4096;

	/** Task Constructor
	 * @param selectedFile file to be uploaded
	 */
	public UploadTask(String directory, File selectedFile) {
		this.uploadFile = selectedFile;
		this.directory = directory;
	}

	@Override
	public Void doInBackground() throws Exception {
		try {
			System.out.println("0");
			Converter.getInstance().convertToMP4(uploadFile);
			System.out.println("1");
			TransferManager.getInstance().connect();
			TransferManager.getInstance().sendFile(directory, uploadFile);
			FileInputStream inputStream = new FileInputStream(uploadFile);
			byte[] buffer = new byte[BUFFER_SIZE]; 
			int bytesRead = -1;
			long totalBytesRead = 0;
			int percentCompleted = 0;
			setProgress(percentCompleted);
			System.out.println("2");
			long fileSize = uploadFile.length();
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				TransferManager.getInstance().writeFile(buffer, 0, bytesRead);
				totalBytesRead += bytesRead;
				percentCompleted = (int) (totalBytesRead * 100 / fileSize);
				setProgress(percentCompleted);
			}
			inputStream.close();
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
