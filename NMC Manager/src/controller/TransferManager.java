package controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.JOptionPane;

import model.AudioCollector;
import model.Config;
import model.EpisodeCollector;
import model.FTPException;
import model.MetaDataCollector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * Classe de gestion des téléversements et téléchargements de fichiers médias sur/depuis le serveur
 * @author Antoine
 *
 */
public class TransferManager {
	private static TransferManager instance = null;
	private static final String encryptoPass = "4YnB8e4p";
	private Config conf;
	private Session session;
	private Channel channel;
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
		JSch jsch = new JSch();
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		try {
			jsch.addIdentity(new File(".config/security/private.pem").getAbsolutePath(), encryptoPass);
		} catch (JSchException e1) {
			JOptionPane.showMessageDialog(null, "Unable to find the private key");
			e1.printStackTrace();
		}

		try {
			session = jsch.getSession(conf.getProp("ftp_user"), conf.getProp("srv_url"), Integer.valueOf(conf.getProp("ftp_port")));
			session.setConfig(config);
		} catch (NumberFormatException | JSchException e) {
			JOptionPane.showMessageDialog(null, "Unable to create the SFTP Session");
			e.printStackTrace();
		}
		try {
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			sftpChannel = (ChannelSftp) channel;
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
			// [TODO] TEST
			else { 
				if (mdc instanceof AudioCollector){
					AudioCollector cdc = (AudioCollector)mdc;
					mdc.setRelPath(directory+"/"+cdc.getAlbum()+"/");
					filename = directory+"/"+cdc.getAlbum()+"/"+fToSend.getName();
					String slash = Parser.getInstance().getSlash();
					relPath = directory+slash+cdc.getAlbum()+slash+fToSend.getName();
					System.out.println(filename);
				}
				else if (mdc instanceof EpisodeCollector){
					EpisodeCollector edc = (EpisodeCollector)mdc;
					mdc.setRelPath(directory+"/"+edc.getSeries()+"/");
					filename = directory+"/"+edc.getSeries()+"/"+fToSend.getName();
					String slash = Parser.getInstance().getSlash();
					relPath = directory+slash+edc.getSeries()+slash+fToSend.getName();
				}
				else{
					mdc.setRelPath(directory);
					filename = directory+"/"+fToSend.getName();
					String slash = Parser.getInstance().getSlash();
					relPath = directory+slash+fToSend.getName();
				}	
			}
			if(mdc != null) mdc.setRelPath(relPath);
			String root_path = null;
			if(Parser.getInstance().isWindows()) {
				root_path = "/";
				int i = 0;
				String tmp = conf.getProp("root_dir");
				while((i = tmp.indexOf("\\")) != -1){
					root_path = root_path.concat(tmp.substring(0, i)+"/");
					System.out.println("root_path with i = "+i+": "+root_path);
					tmp = tmp.substring(i + 1);
				}
				root_path = root_path.concat(tmp);
				if((i = root_path.indexOf(':')) != -1) root_path = root_path.substring(0, i)+root_path.substring(i+1);
				System.out.println("root_path generated: "+root_path);
			} else {
				root_path = conf.getProp("root_dir");
			}
			sftpChannel.cd(root_path);
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

	public static String chooseDirectory(String node) {
		switch(node){
			case "Add New Albums": return "Music";
			case "Add New Series": return "Series";
			case "Add New Episodes": return "Series";
			case "Add New Music": return "Music";
			case "Books": return "Books";
			case "Movies": return "Movies";
			case "Images": return "Images";
		}
		return null;
	}
}

