package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JOptionPane;

import model.Config;
import model.MetaDataCollector;

import org.vngx.jsch.ChannelSftp;
import org.vngx.jsch.ChannelType;
import org.vngx.jsch.JSch;
import org.vngx.jsch.Session;
import org.vngx.jsch.config.SessionConfig;
import org.vngx.jsch.exception.JSchException;
import org.vngx.jsch.exception.SftpException;

import view.FTPException;

/**
 * Classe de gestion des téléversements et téléchargements de fichiers médias sur/depuis le serveur
 * @author Antoine
 *
 */
public class TransferManager {
	private static TransferManager instance = null;
	private Config conf;
	private Session session;
	private ChannelSftp sftpChannel;
	private OutputStream os;


	protected TransferManager(){
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

	/**
	 * Connects client to FTP Server in passive mode
	 * 
	 */
	public void connect(){
		SessionConfig config = new SessionConfig();
		//config.setProperty(SessionConfig.STRICT_HOST_KEY_CHECKING, "no");
		File hostKey = new File("hostkey.ser");
		FileInputStream in = null;
		try {
			in = new FileInputStream(hostKey);
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null, "Host Key file couldn't be found");
		}
		byte[] buffer = new byte[4096];
		String key = new String();
		try{
			while(in.read(buffer) != -1) key = key.concat(String.valueOf(buffer));
		} catch(IOException e){
			JOptionPane.showMessageDialog(null, "Error while reading host key");
		}
		config.setProperty(SessionConfig.KEX_SERVER_HOST_KEY, key);
		config.setProperty(SessionConfig.KEX_ALGORITHMS, "1");
		try {
			session = JSch.getInstance().createSession(conf.getProp("ftp_user"), conf.getProp("srv_url"), Integer.valueOf(conf.getProp("ftp_port")), config);
		} catch (NumberFormatException | JSchException e) {
			JOptionPane.showMessageDialog(null, "Unable to create the SFTP Session");
			e.printStackTrace();
		}
		try {
			session.connect(conf.getProp("ftp_pass").getBytes());
			sftpChannel = session.openChannel(ChannelType.SFTP);
			sftpChannel.connect();
		} catch (JSchException e) {
			JOptionPane.showMessageDialog(null, "Unable to connect to the SFTP Server");
			e.printStackTrace();
		}
	}

	/**
	 * Permet d'envoyer le fichier fToSend au serveur FTP
	 * @param directory : Dossier de placement du fichier sur le serveur <br />
	 * 			Ce paramètre est facultatif <br />
	 * 			Attention : Le dossier doit avoir été créé au préalable sur le serveur
	 * @param fToSend : Fichier à téléverser sur le serveur 
	 * @throws FTPException if client-server communication error occurred
	 */
	public void sendFile(String directory, File fToSend, MetaDataCollector mdc) throws FTPException{

		try {
			String filename = null;
			String relPath = null;
			if(directory == null){
				filename = fToSend.getName();
				relPath = filename;
			}
			else {
				filename = "/"+directory+"/"+fToSend.getName();
				String slash = Parser.getInstance().getSlash();
				relPath = slash+directory+slash+fToSend.getName();
			}
			if(mdc != null) mdc.setRelPath(relPath);
			os = sftpChannel.put(filename);
		} catch (SftpException e) {
			throw new FTPException("Error uploading file: " + e.getMessage());
		}
	}

	/**
	 * Write of file onto outputstream
	 * @param bytes bytes to read
	 * @param offset from which location in file
	 * @param length total length of file
	 * @throws IOException if error when writing to outputstream
	 */
	public void writeFile(byte[] bytes, int offset, int length) 
			throws IOException{
		os.write(bytes, offset, length);
	}

	/**
	 * Closes output stream
	 * @throws IOException
	 */
	public void finish() throws IOException{
		os.close();
	}

	/** 
	 * Closes connection between client and server
	 */
	public void close() {
		if (sftpChannel.isConnected()) sftpChannel.disconnect();
		if (session.isConnected()) session.disconnect();
	}
}

