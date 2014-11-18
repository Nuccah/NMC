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

import model.AudioCollector;
import model.EpisodeCollector;
import model.MetaDataCollector;

import com.jcraft.jsch.SftpException;

import controller.Converter;
import controller.SocketManager;
import controller.TransferManager;

/**
 * Classe de gestion de la vue lors d'un upload
 * @author Derek
 *
 */

public class UploadTask extends SwingWorker<Void, Void> {
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
	public UploadTask(String directory, File selectedFile, MetaDataCollector mdc) {
		this.uploadFile = selectedFile;
		this.directory = directory;
		this.mdc = mdc;
	}

	@Override
	public Void doInBackground(){
		File resetName = null;
		try {
			if(directory == "Movies" || directory == "Series"){
				String filepath = Converter.getInstance().convertToMP4(mdc, uploadFile);
				uploadFile = new File(filepath);
			}
			else if(directory == "Music"){
				String filepath = Converter.getInstance().convertToMP3((AudioCollector) mdc, uploadFile);
				uploadFile = new File(filepath);
			}
			if (!SocketManager.getInstance().sendMeta(mdc))
				cancel(true);
			if(!(mdc instanceof AudioCollector) && !(mdc instanceof EpisodeCollector)){
				if (!SocketManager.getInstance().lastID(mdc))
					cancel(true);
				try {
					resetName = uploadFile;
					uploadFile = TransferManager.getInstance().setFilename(mdc, uploadFile);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			int percentCompleted = 0;
			setProgress(percentCompleted);
			
			TransferManager.getInstance().connect();
			try {
				TransferManager.getInstance().sendFile(directory, uploadFile, mdc);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,  "[ERROR] sendFile: " + e.getMessage(),
						"Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				setProgress(0);
				cancel(true);
			}
			FileInputStream inputStream = null;
			try {
				inputStream = new FileInputStream(uploadFile);
			} catch (IOException e){
				JOptionPane.showMessageDialog(null,  "[ERROR] creating inputstream: " + e.getMessage(),
						"Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				setProgress(0);
				cancel(true);
			}
			byte[] buffer = new byte[BUFFER_SIZE]; 
			int bytesRead = -1;
			long totalBytesRead = 0;
			long fileSize = uploadFile.length();
			try{
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					TransferManager.getInstance().writeFile(buffer, 0, bytesRead);
					totalBytesRead += bytesRead;
					percentCompleted = (int) (totalBytesRead * 100 / fileSize);
					setProgress(percentCompleted);
				}
			}catch (IOException e){
				JOptionPane.showMessageDialog(null,  "[ERROR] while read/write to FTP: " + e.getMessage(),
						"Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				setProgress(0);
				cancel(true);
			}
			try {
				inputStream.close();
				TransferManager.getInstance().finish();
			} catch (IOException e) {
					JOptionPane.showMessageDialog(null,  "[ERROR] while closing FTP: " + e.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
					setProgress(0);
					cancel(true);
			}
			try {
				TransferManager.getInstance().resetFilename(uploadFile, resetName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SftpException e){
			JOptionPane.showMessageDialog(null,  "SFTP Error: " + e.getMessage(),
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
					"File has been converted & uploaded successfully!", "Message",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
