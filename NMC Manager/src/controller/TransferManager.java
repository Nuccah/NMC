package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.ProgressMonitor;

import model.Config;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/**
 * Classe de gestion des téléversements et téléchargements de fichiers médias sur/depuis le serveur
 * @author Antoine
 *
 */
public class TransferManager extends Thread {
	private static TransferManager instance = null;
	private FTPClient client;
	private Config conf;
	private int read;
	
	protected TransferManager(){
		client = new FTPClient();
		conf = Config.getInstance();
	}
	/**
	 * Permet de récupérer l'instance de TransferManager
	 * Instancie l'obojet TransferManager si ce n'était pas déjà le cas
	 * @return L'instance TransferManager 
	 */
	public static TransferManager getInstance(){
		if(instance == null)
			instance = new TransferManager();
		return instance;
	}

	private void connect(){
		try {
			client.connect(conf.getProp("srv_url"), Integer.valueOf(conf.getProp("ftp_port")));
			if(!client.login(conf.getProp("ftp_user"), conf.getProp("ftp_pass")))
				JOptionPane.showMessageDialog(null, "Connexion refusée Login/Mot de passe FTP invalide!");
		} catch (NumberFormatException | IOException e) {
			JOptionPane.showMessageDialog(null, "Impossible de contacter le serveur média!");
			e.printStackTrace();
		}		
	}

	private void close(){
		try {
			if(client.isConnected()){
				client.logout();
				client.disconnect();
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Une erreur s'est produite lors de la clôture de la connexion FTP!");
			e.printStackTrace();
		}
	}
	/**
	 * Permet d'envoyer le fichier fToSend au serveur FTP
	 * @param fToSend : Fichier à téléverser sur le serveur
	 * @param uploadDataPane 
	 * @param progressMonitor 
	 */
	public void sendFile(File fToSend/*, JPanel uploadDataPane, JProgressBar progressMonitor*/){
		connect();
		try {
			client.enterLocalPassiveMode();
			client.setFileType(FTP.BINARY_FILE_TYPE);
			
			InputStream is = new FileInputStream(fToSend);
			
			String filename = fToSend.getName();			
			OutputStream os = client.storeUniqueFileStream(filename);
			
			byte[] bytesIn = new byte[4096];
			read = 0;
			while((read = is.read(bytesIn)) != -1){
				os.write(bytesIn, 0, read);
//				progressMonitor.setValue((int) (((read/fToSend.length())*100)));
//				uploadDataPane.repaint(); uploadDataPane.revalidate();
			}
			is.close();
			os.close();
			// The File has been correctly uploaded
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public int getRead() {
		return read;
	}
	public void setRead(int read) {
		this.read = read;
	}
}
