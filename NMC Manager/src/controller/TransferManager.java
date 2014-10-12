package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JOptionPane;

import model.Config;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import view.FTPException;

/**
 * Classe de gestion des téléversements et téléchargements de fichiers médias sur/depuis le serveur
 * @author Antoine
 *
 */
public class TransferManager extends Thread {
	private static TransferManager instance = null;
	private FTPClient client;
	private Config conf;
	private OutputStream os;
	
	protected TransferManager(){
		client = new FTPClient();
		conf = Config.getInstance();
	}
	
	/**
	 * Permet de récupérer l'instance de TransferManager
	 * Instancie l'obojet TransferManager si ce n'était pas déjà le cas
	 * @throws FTPException
	 * @return L'instance TransferManager 
	 */
	public static TransferManager getInstance(){
		if(instance == null)
			instance = new TransferManager();
		return instance;
	}

	public void connect() throws FTPException{
		try {
			client.connect(conf.getProp("srv_url"), Integer.valueOf(conf.getProp("ftp_port")));
			if(!client.login(conf.getProp("ftp_user"), conf.getProp("ftp_pass")))
				throw new FTPException("FTP serve refused connection.");
			client.enterLocalPassiveMode();
		} catch ( IOException e) {
			throw new FTPException("I/O error: " + e.getMessage());
		}		
	}

	/**
	 * Permet d'envoyer le fichier fToSend au serveur FTP
	 * @param fToSend : Fichier à téléverser sur le serveur 
	 * @throws FTPException if client-server communication error occurred
	 */
	public void sendFile(File fToSend) throws FTPException{
		try {
			if(!client.setFileType(FTP.BINARY_FILE_TYPE))
				throw new FTPException("Could not set binary file type.");;
			
//			InputStream is = new FileInputStream(fToSend);
//			
			String filename = fToSend.getName();			
			os = client.storeUniqueFileStream(filename);
			
//			byte[] bytesIn = new byte[4096];
//			read = 0;
//			while((read = is.read(bytesIn)) != -1){
//				
//			}
//			is.close();
			
			// The File has been correctly uploaded
		} catch (IOException e) {
			throw new FTPException("Error uploading file: " + e.getMessage());
		}
//		} finally {
//			close();
//			read = 0;
//		}
	}
	
	public void writeFile(byte[] bytes, int offset, int length) 
			throws IOException{
		os.write(bytes, offset, length);
	}
	
	public void finish() throws IOException{
		os.close();
	}
	
	public void close() throws FTPException{
		if (client.isConnected()){
			try {
				if(!client.logout()){
					throw new FTPException("Could not log out from the server");
                }
				client.disconnect();
			} catch (IOException e) {
				throw new FTPException("Error disconnect from the server: "
                        + e.getMessage());
			}
		};
		
	}
}
