package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JOptionPane;

import model.AlbumCollector;
import model.AudioCollector;
import model.BookCollector;
import model.Config;
import model.EpisodeCollector;
import model.ImageCollector;
import model.Lists;
import model.MetaDataCollector;
import model.Permissions;
import model.Profil;
import model.SeriesCollector;
import model.VideoCollector;
/**
 * Gère les actions sockets nécessaires
 * @author Antoine Ceyssens
 * @version 1.2
 */
public class SocketManager extends Socket {
	private static SocketManager instance = null;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	protected SocketManager() throws NumberFormatException, UnknownHostException, IOException{
		super(Config.getInstance().getProp("srv_url"), Integer.valueOf(Config.getInstance().getProp("srv_port")));
		oos = new ObjectOutputStream(this.getOutputStream());
		ois = new ObjectInputStream(this.getInputStream());
	}

	public static SocketManager getInstance(){
		if(instance == null){
			try {
				instance = new SocketManager();
			} catch (NumberFormatException | IOException e) {
				JOptionPane.showMessageDialog(null, "Server unreachable.\nPlease verify your connection and the server status.", null, JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				if(Config.getInstance().getProp("init").equals("0")){
					Config.getInstance().delCfg();
				}
			}
		}
		return instance;
	}

	/**
	 * Permet de créer une session avec le serveur
	 * @param login : nom d'utilisateur
	 * @param password : mot de passe
	 * @return Vrai si la connexion a été effectuée avec succès
	 */
	public boolean connect(String login, String password){
		String[] credentials = new String[2];
		credentials[0] = login;
		credentials[1] = Crypter.encrypt(password);
		String ack = null;

		try{	
			do{
				oos.writeObject("connec");
				ack = (String) ois.readObject();
			} while(ack.compareTo("ACK") != 0);
			do{
				oos.writeObject(credentials);
				ack = (String) ois.readObject();
			} while(ack.compareTo("CRED ACK") != 0);
			oos.writeObject("Ready");
		} catch(ClassNotFoundException | IOException e){
			JOptionPane.showMessageDialog(null, "Unable to send credentials to the server.\nPlease verify the server is up.");
			e.printStackTrace();
		}
		try {
			Object tmp = ois.readObject();
			if(tmp instanceof String){
				JOptionPane.showMessageDialog(null, (String)tmp);
				return false;
			} else {
				Profil.setInstance((Profil)tmp);
				getList("startup");
				return true;
			}
		} catch(ClassNotFoundException | IOException e){
			JOptionPane.showMessageDialog(null, "Unable to retrieve the profil from server.\nPlease verify the server is up.");
		}
		return false;		
	}
	/**
	 * Permet de récupérer les configurations de bases automatiquement via le serveur
	 */
	public void getConfig(){
		try{
			String ack = null;
			do{
				oos.writeObject("init");
				ack = (String) ois.readObject();
			} while(ack.compareTo("ACK") != 0);
			oos.writeObject("Ready");
			Properties prop = (Properties) ois.readObject();
			Config conf = Config.getInstance();
			conf.setProp("ftp_port", prop.getProperty("ftp_port"));
			conf.setProp("ftp_user", prop.getProperty("ftp_user"));
			conf.setProp("root_dir", prop.getProperty("root_dir"));
			conf.setProp("init", "1");
			conf.saveProp();
			oos.writeObject("ACK CONF");

			File keyStore = new File(Config.getInstance().getDefaultDir()+Parser.getInstance().getSlash()+"security");
			keyStore.mkdirs();
			File privF = new File(keyStore.getAbsolutePath()+Parser.getInstance().getSlash()+"private.pem");
			privF.createNewFile();
			byte[] privKey = (byte[]) ois.readObject();
			FileOutputStream fos = new FileOutputStream(privF);
			fos.write(privKey);
			fos.close();

		} catch(ClassNotFoundException | IOException e){
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void getList(String type){
		try {
			Lists lists = Lists.getInstance();
			if (type != "startup"){
				String ack = null;
				do{
					oos.writeObject("list");
					ack = (String) ois.readObject();
				} while(ack.compareTo("ACK") != 0);
				oos.writeObject(type);
			}
			else
				oos.writeObject(type);
			switch(type){
			case "startup":
				Lists.setInstance(new Lists((ArrayList<Profil>)ois.readObject(),
						(ArrayList<Permissions>)ois.readObject(),
						(ArrayList<AlbumCollector>)ois.readObject(),
						(ArrayList<SeriesCollector>)ois.readObject()));
				break;
			case "albums": 
				lists.setAlbumList((ArrayList<AlbumCollector>)ois.readObject());
				break;
			case "series":
				lists.setSeriesList((ArrayList<SeriesCollector>)ois.readObject());
				break;
			case "audio": 
				lists.setAudioList((ArrayList<AudioCollector>) ois.readObject());
				break;
			case "books": 
				lists.setBookList((ArrayList<BookCollector>) ois.readObject());
				break;
			case "episodes": 
				lists.setEpisodeList((ArrayList<EpisodeCollector>) ois.readObject());
				break;
			case "images": 
				lists.setImageList((ArrayList<ImageCollector>) ois.readObject());
				break;
			case "permissions": 
				lists.setPermissionsList((ArrayList<Permissions>)ois.readObject());
				break;
			case "users": 
				lists.setUsersList((ArrayList<Profil>)ois.readObject());
				break;
			case "videos": 
				lists.setVideoList((ArrayList<VideoCollector>) ois.readObject());
				break;
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Permet d'envoyer les méta données liées à un fichier uploadé
	 * @param mdc : MetaDataCollector à envoyer
	 */
	public boolean sendMeta(MetaDataCollector mdc){
		System.out.println("Sending :" +mdc.toString());
		try {
			String ack = null;
			do {
				oos.writeObject("meta");
				ack = String.valueOf(ois.readObject());
			} while(ack.compareTo("ACK") != 0);
			ack = null;
			do {
				oos.writeObject(mdc);
				ack = String.valueOf(ois.readObject());
				if(ack.compareTo("NACK") == 0){
					JOptionPane.showMessageDialog(null, "Unable to send meta data for: "+mdc.getTitle());
					return false;
				}
			} while(ack.compareTo("ACK") != 0);			
		} catch (IOException | ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Unable to send meta data for: "+mdc.getTitle());
			e.printStackTrace();
			return false;
		}
		System.out.println("Metadata Successfully Sent");
		return true;
	}

	public boolean lastID(MetaDataCollector mdc){
		int id = 0;
		String ack = null;
		try {
			do {
				oos.writeObject("last");
				ack = String.valueOf(ois.readObject());
			} while(ack.compareTo("ACK") != 0);
			do{
				id = (int) ois.readObject();
			}while(id == -1) ;
			mdc.setId(id);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		System.out.println("Return ID: "+id);
		return true;
	}

	public void delObject(Object o){
		//TODO: Trouver comment envoyer la requête de suppression
		throw new UnsupportedOperationException("Method not yet implemented!");
	}

	public void logout(){
		Profil.getInstance().reset();
		try {
			oos.close();
			ois.close();
			if(!this.isClosed()) this.close();
		} catch (IOException e) {
			System.out.println("[Warning] - Socket and Streams are already closed!");
			e.printStackTrace();
		}
	}

}
