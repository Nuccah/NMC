package controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import model.Config;
import model.MetaDataCollector;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import view.FTPException;

/**
 * Classe de gestion des téléversements et téléchargements de fichiers médias sur/depuis le serveur
 * @author Antoine
 *
 */
//TODO: Passer en SFTP!!!!
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
	 * @return L'instance TransferManager 
	 */
	public static TransferManager getInstance(){
		if(instance == null)
			instance = new TransferManager();
		return instance;
	}

	/**Connects client to FTP Server in passive mode
	 * @throws FTPException if login/pass is not correct
	 */
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
	 * @param directory : Dossier de placement du fichier sur le serveur <br />
	 * 			Ce paramètre est facultatif <br />
	 * 			Attention : Le dossier doit avoir été créé au préalable sur le serveur
	 * @param fToSend : Fichier à téléverser sur le serveur 
	 * @throws FTPException if client-server communication error occurred
	 */
	public void sendFile(String directory, File fToSend, MetaDataCollector mdc) throws FTPException{

		try {
			if(!client.setFileType(FTP.BINARY_FILE_TYPE))
				throw new FTPException("Could not set binary file type.");;
			client.setFileType(FTP.BINARY_FILE_TYPE);
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
			os = client.storeUniqueFileStream(filename);
		} catch (IOException e) {
			throw new FTPException("Error uploading file: " + e.getMessage());
		}
	}

	/**Write of file onto outputstream
	 * @param bytes bytes to read
	 * @param offset from which location in file
	 * @param length total length of file
	 * @throws IOException if error when writing to outputstream
	 */
	public void writeFile(byte[] bytes, int offset, int length) 
			throws IOException{
		os.write(bytes, offset, length);
	}

	/** Closes output stream
	 * @throws IOException
	 */
	public void finish() throws IOException{
		os.close();
	}

	/** Closes connection between client and server
	 * @throws FTPException if unable to logout or error when attempting to disconnect
	 */
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

