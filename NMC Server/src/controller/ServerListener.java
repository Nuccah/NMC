package controller;

import java.awt.HeadlessException;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import model.AlbumCollector;
import model.AudioCollector;
import model.BookCollector;
import model.Config;
import model.ConnectorDB;
import model.EpisodeCollector;
import model.ImageCollector;
import model.MetaDataCollector;
import model.Permissions;
import model.Profil;
import model.SeriesCollector;
import model.VideoCollector;
/**
 * Classe d'écoute Socket et gestion des différentes actions réseau du serveur (hors FTP)
 * @author Antoine
 * @version 1.0
 */
public class ServerListener implements Runnable {
	private Socket cl;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private ArrayList<AudioCollector> audioList;
	private ArrayList<AlbumCollector> albumList;
	private ArrayList<BookCollector> bookList;
	private ArrayList<ImageCollector> imageList;
	private ArrayList<EpisodeCollector> episodeList;
	private ArrayList<SeriesCollector> seriesList;
	private ArrayList<VideoCollector> videoList;
	private ArrayList<Profil> usersList;
	private ArrayList<Permissions> permissionsList;

	protected ServerListener(Socket cl) {
		this.cl = cl;
		try{
			this.ois = new ObjectInputStream(cl.getInputStream());
			this.oos = new ObjectOutputStream(cl.getOutputStream());
		} catch(IOException e){
			System.out.println("[Error] - Unable to create socket streams for: "+cl.getInetAddress());
			if(Main.getDebug()) e.printStackTrace();
		}		
	}
	/**
	 * Permet de définir l'action à exécuter au niveau du socket
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void executeAction() throws IOException, ClassNotFoundException{
		String action = String.valueOf(ois.readObject());
		System.out.println(action+" recieved from: "+cl.getInetAddress());
		switch(action){
		case "init" : 
			oos.writeObject("ACK");
			sendAutoConfig();
			break;
		case "connec":
			oos.writeObject("ACK");
			connectCl();
			break;
		case "meta" :
			oos.writeObject("ACK");
			recieveMeta();
			break;
		case "create" :
			oos.writeObject("ACK");
			createUser();
			break;
		case "delete" :
			oos.writeObject("ACK");
			delObject();
			break;
		case "list":
			oos.writeObject("ACK");
			sendList();
			break;
		case "last":
			oos.writeObject("ACK");
			lastID();
			break;
		case "modify":
			oos.writeObject("ACK");
			modify();
			break;
		case "logout":
			oos.writeObject("ACK");
			logout();
			break;
		default :
			oos.writeObject("NACK");
		}
	}

	/**
	 * Permet d'envoyer les configurations de bases au client
	 */
	private void sendAutoConfig(){
		Config conf = Config.getInstance();
		try{
			ois.readObject();
			Properties prop = new Properties();
			prop.setProperty("ftp_port", conf.getProp("ftp_port"));
			prop.setProperty("ftp_user", conf.getProp("ftp_user"));
			prop.setProperty("root_dir", conf.getProp("root_dir"));
			oos.writeObject(prop);
			ois.readObject();
			File privF = new File(".config/security/private.pem");
			FileInputStream fis = new FileInputStream(privF);
			byte[] privKey = new byte[(int)privF.length()];
			fis.read(privKey);
			fis.close();
			oos.writeObject(privKey);
		} catch(ClassNotFoundException | IOException e){
			System.out.println("[Error] - Unable to send basic configurations to: "+cl.getInetAddress());
			if(Main.getDebug()) e.printStackTrace();
		}
	}
	/**
	 * Permet de connecter un client avec les données reçues.
	 * Le client récupère un objet Profil dans le cas d'une connexion réussie
	 * @param action 
	 */
	private void connectCl(){
		String[] credentials = new String[2];
		try{
			credentials = (String[]) ois.readObject();
			oos.writeObject("CRED ACK");
			ois.readObject();	
		} catch (ClassNotFoundException | IOException e){
			System.out.println("[Error] Couldn't retrieve credentials from: "+cl.getInetAddress());
			if(Main.getDebug()) e.printStackTrace();
		}
		String username = credentials[0];
		String password = credentials[1];
		ConnectorDB connDB = ConnectorDB.getInstance();
		connDB.openConnection();
		ResultSet res = connDB.select("SELECT * FROM nmc_users WHERE login LIKE '"+username+"'");
		try {
			res.first();
		} catch (HeadlessException | SQLException e1) {
			try {
				oos.writeObject("[Error] - Wrong login/password");
			} catch (IOException e) {
				if(Main.getDebug()) e.printStackTrace();
			}
			if(Main.getDebug()) e1.printStackTrace();
		}
		try {
			if(password.compareTo(res.getString("password")) == 0){
				Profil pf = Profil.getInstance();
				pf.setUsername(username);
				pf.setId(res.getInt("id"));
				pf.setPassword(res.getString("password"));
				pf.setMail(res.getString("mail"));
				pf.setFirstName(res.getString("first_name"));
				pf.setLastName(res.getString("last_name"));
				pf.setBirthdate(res.getDate("birthdate"));
				pf.setRegDate(res.getTimestamp("reg_date"));
				pf.setPermissions_id(res.getInt("permissions_id"));
				try{
					oos.writeObject(pf);
					sendList();
				} catch (IOException e){
					System.out.println("[Error] - Profil couldn't be sent to: "+cl.getInetAddress());
					if(Main.getDebug()) e.printStackTrace();
				}
			} else {
				try {
					oos.writeObject("[Error] - Wrong login/password");
				} catch (IOException e) {
					if(Main.getDebug()) e.printStackTrace();
				}			
			}
		} catch (SQLException e) {
			System.out.println("[Error] - Connection failed! Please check connection settings");
			try {
				oos.writeObject("[Error] - Connection failed! Please check if server is up");
			} catch (IOException e1) {
				if(Main.getDebug()) e1.printStackTrace();
			}
			if(Main.getDebug()) e.printStackTrace();
		}
	}
	/**
	 * Permet de recevoir les méta données et de les injecter dans la base de données
	 */
	private void recieveMeta(){
		MetaDataCollector mdc = null;
		try {
			mdc = (MetaDataCollector) ois.readObject();
		} catch (ClassNotFoundException | IOException e1) {
			try {
				oos.writeObject("NACK");
			} catch (IOException e) {
				System.out.println("[Error] - Couldn't send NACK to: "+cl.getInetAddress());
				if(Main.getDebug()) e1.printStackTrace();
			}
			System.out.println("[Error] - Couldn't retrieve meta type from: "+cl.getInetAddress());
			if(Main.getDebug()) e1.printStackTrace();
		}
		mdc.setAbsPath(Config.getInstance().getProp("root_dir")+mdc.getRelPath());
		Injecter inj = Injecter.getInstance();
		try {
			if(mdc instanceof AudioCollector){
				System.out.println("Received: "+((AudioCollector)mdc).toString());
				inj.injector((AudioCollector) mdc);
				oos.writeObject("ACK");
			}
			else if(mdc instanceof AlbumCollector){
				System.out.println("Received: "+((AlbumCollector)mdc).toString());
				inj.injector((AlbumCollector)mdc);
				mdc.setId(Retriever.getInstance().selectLastEntry());
				Config.getInstance().createAlbumFolders((AlbumCollector)mdc);
				oos.writeObject("ACK");
			}
			else if(mdc instanceof BookCollector){
				System.out.println("Received: "+((BookCollector)mdc).toString());
				inj.injector((BookCollector) mdc);
				oos.writeObject("ACK");
			}
			else if(mdc instanceof EpisodeCollector){
				System.out.println("Received: "+((EpisodeCollector)mdc).toString());
				inj.injector((EpisodeCollector) mdc);
				oos.writeObject("ACK");
			}
			else if(mdc instanceof ImageCollector){
				System.out.println("Received: "+((ImageCollector)mdc).toString());
				inj.injector((ImageCollector) mdc);
				oos.writeObject("ACK");
			}
			else if(mdc instanceof SeriesCollector){
				System.out.println("Received: "+((SeriesCollector)mdc).toString());
				inj.injector((SeriesCollector) mdc);
				mdc.setId(Retriever.getInstance().selectLastEntry());
				Config.getInstance().createSeriesFolders((SeriesCollector)mdc);
				oos.writeObject("ACK");
			}
			else if(mdc instanceof VideoCollector){
				System.out.println("Received: "+((VideoCollector)mdc).toString());
				inj.injector((VideoCollector) mdc);
				oos.writeObject("ACK");
			}
			else oos.writeObject("NACK");
		} catch (IOException e){
			System.out.println("[Error] - ACK couldn't be sent to: "+cl.getInetAddress());
			if(Main.getDebug()) e.printStackTrace();
		} catch (SQLException e){
			System.out.println("[Error] - SQL ERROR");
			if(Main.getDebug()) e.printStackTrace();
		}
	}

	private void createUser() {
		Profil profil = null;
		Injecter inj = new Injecter();
		boolean succeeded = true;
		try {
			profil = (Profil) ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("[Error] - Reception of New User from: "+cl.getInetAddress()+" failed");
			if(Main.getDebug()) e.printStackTrace();
		}
		try {
			inj.injector(profil);
		} catch (SQLException e) {
			succeeded = false;
			System.out.println("[SQL Error] - Could not create new user!");
			try {
				oos.writeObject("NACK");
			} catch (IOException e1) {
				System.out.println("[Error] - NACK couldn't be sent to: "+cl.getInetAddress());
				if(Main.getDebug()) e.printStackTrace();
			}
			if(Main.getDebug()) e.printStackTrace();
		}
		if (succeeded){
			try {
				oos.writeObject("ACK");
			} catch (IOException e){
				System.out.println("[Error] - ACK couldn't be sent to: "+cl.getInetAddress());
				if(Main.getDebug()) e.printStackTrace();
			}
		}
	}

	private void sendList(){
		try {
			String action = String.valueOf(ois.readObject());
			System.out.println(action+" recieved from: "+cl.getInetAddress());
			if (action.equals("startup"))
				createArrayLists(action, null);
			else
				oos.writeObject(createArrayLists(action, null));
		} catch (IOException e) {
			System.out.println("[Error] - List could not be sent to: "+cl.getInetAddress());
			if(Main.getDebug()) e.printStackTrace();
		} catch (SQLException s){
			System.out.println("[Error] - SQL Exception");
			if(Main.getDebug()) s.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("[Error] Couldn't retrieve case from: "+cl.getInetAddress());
			if(Main.getDebug()) e.printStackTrace();
		}
	}

	private void lastID() {
		try {
			oos.writeObject(Retriever.getInstance().selectLastEntry());
		} catch (IOException e) {
			System.out.println("[Error] Couldn't write ID to: "+cl.getInetAddress());
			if(Main.getDebug()) e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("[Error] SQL Error");
			e.printStackTrace();
		}
	}

	private void modify() {
		Profil profil = null;
		ResultSet rs = null;
		Modifier mod = Modifier.getInstance();
		Retriever retr = Retriever.getInstance();
		try {
			profil = (Profil) ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("[Error] - Reception of Profil from: "+cl.getInetAddress()+" failed");
			if(Main.getDebug()) e.printStackTrace();
		}
		try {
			rs = retr.selectUsers("WHERE id = '"+profil.getId()+"'");
			if (rs.next())
				if (!rs.getString("password").equals(profil.getPassword()))
					mod.modifyUser(profil);
		} catch (SQLException e) {
			System.out.println("[SQL Error] - Could not update profile!");
			if(Main.getDebug()) e.printStackTrace();
		}
		try {
			oos.writeObject("ACK");
		} catch (IOException e){
			System.out.println("[Error] - ACK couldn't be sent to: "+cl.getInetAddress());
			if(Main.getDebug()) e.printStackTrace();
		}
	}

	private void delObject(){
		MetaDataCollector mdc = null;
		Boolean success = true;
		Deleter del = Deleter.getInstance();
		try {
			mdc = (MetaDataCollector) ois.readObject();
		} catch (ClassNotFoundException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			if(mdc instanceof AudioCollector){
				if (!del.deleteAudio(mdc.getId())) success = false;
			}
			else if(mdc instanceof AlbumCollector){
				if (!del.deleteAlbum(mdc.getId())) success = false;
			}
			else if(mdc instanceof BookCollector){
				if (!del.deleteBook(mdc.getId())) success = false;
			}
			else if(mdc instanceof EpisodeCollector){
				if (!del.deleteEpisode(mdc.getId())) success = false;
			}
			else if(mdc instanceof ImageCollector){
				if (!del.deleteImage(mdc.getId())) success = false;
			}
			else if(mdc instanceof SeriesCollector){
				if (!del.deleteSeries(mdc.getId())) success = false;
			}
			else if(mdc instanceof VideoCollector){
				if (!del.deleteVideo(mdc.getId())) success = false;
			}
			else{
				success = false;
				System.out.println("[Error] - Could not cast MetaDataCollector");
			}
		}
		catch (SQLException e) {
			System.out.println("[SQL] - SQL Exception");
			if(Main.getDebug()) e.printStackTrace();
		}
		if (!success)
			try {
				oos.writeObject("NACK");
			} catch (IOException e) {
				System.out.println("[Error] - NACK couldn't be sent to: "+cl.getInetAddress());
				if(Main.getDebug()) e.printStackTrace();
			}
		else{
			try {
				oos.writeObject("ACK");
			} catch (IOException e) {
				System.out.println("[Error] - ACK couldn't be sent to: "+cl.getInetAddress());
				if(Main.getDebug()) e.printStackTrace();
			}
		}
	}

	private void logout() {
		try{
			ois.close();
			oos.close();
			if(!cl.isClosed()) cl.close();
		} catch(IOException e){
			System.out.println("[Warning] - Streams are already closed");
			if(Main.getDebug()) e.printStackTrace();
		}		
	}

	private ArrayList<?> createArrayLists(String action, String query) throws IOException, SQLException{
		ResultSet rs = null;
		switch (action){
		case "startup":
			oos.writeObject(createArrayLists("users", null));
			oos.writeObject(createArrayLists("permissions", null));
			oos.writeObject(createArrayLists("albums", null));
			oos.writeObject(createArrayLists("series", null));
			return null;
		case "albums":
			albumList = new ArrayList<AlbumCollector>();
			rs = Retriever.getInstance().selectAlbumList(query);
			while(rs.next()){
				albumList.add(new AlbumCollector(rs.getInt("id"), rs.getString("title"), String.valueOf(rs.getInt("release_date")), rs.getInt("modification"), rs.getInt("visibility"), rs.getString("name"), rs.getString("description"), rs.getString("category")));
			}
			return albumList;
		case "audio":
			audioList = new ArrayList<AudioCollector>();
			rs = Retriever.getInstance().selectAudioList(query);
			while(rs.next()){
				audioList.add(new AudioCollector(rs.getInt("id"), rs.getString("title"), rs.getString("filename"), rs.getString("name"), rs.getInt("album"), rs.getString("albumName")));
			}
			return audioList;
		case "books": 
			bookList = new ArrayList<BookCollector>();
			rs = Retriever.getInstance().selectBookList(query);
			while(rs.next()){
				bookList.add(new BookCollector(rs.getInt("id"), rs.getString("title"), String.valueOf(rs.getInt("release_date")), rs.getInt("modification"), rs.getInt("visibility"), rs.getString("path"), rs.getString("name"), rs.getString("category"), rs.getString("description")));
			}
			return bookList;
		case "episodes": 
			episodeList = new ArrayList<EpisodeCollector>();
			rs = Retriever.getInstance().selectEpisodeList(query);
			while(rs.next()){
				episodeList.add(new EpisodeCollector(rs.getInt("id"), rs.getString("title"), rs.getString("filename"), rs.getInt("series"), rs.getString("seriesName"), rs.getString("name"), String.valueOf(rs.getInt("season")), String.valueOf(rs.getInt("chrono"))));
			}
			return episodeList;
		case "images": 
			imageList = new ArrayList<ImageCollector>();
			rs = Retriever.getInstance().selectImageList(query);
			while(rs.next()){
				imageList.add(new ImageCollector(rs.getInt("id"), rs.getString("title"), String.valueOf(rs.getInt("release_date")), rs.getInt("modification"), rs.getInt("visibility"), rs.getString("path"), rs.getString("name")));
			}
			return imageList;
		case "permissions": 
			permissionsList = new ArrayList<Permissions>();
			rs = Retriever.getInstance().selectPermissions(query);
			while(rs.next()){
				permissionsList.add(new Permissions(rs.getInt("id"), rs.getString("label"), rs.getInt("level")));
			}
			return permissionsList;
		case "series": 
			seriesList = new ArrayList<SeriesCollector>();
			rs = Retriever.getInstance().selectSeriesList(query);
			while(rs.next()){
				seriesList.add(new SeriesCollector(rs.getInt("id"), rs.getString("title"), String.valueOf(rs.getInt("release_date")), rs.getInt("modification"), rs.getInt("visibility"), rs.getString("description"), rs.getString("category")));
			}
			return seriesList;
		case "users": 
			usersList = new ArrayList<Profil>();
			rs = Retriever.getInstance().selectUsers(query);
			while(rs.next()){
				usersList.add(new Profil(rs.getInt("id"), rs.getString("login"), rs.getString("mail"), rs.getString("first_name"), rs.getString("last_name"), rs.getDate("birthdate"), rs.getTimestamp("reg_date"), rs.getInt("permissions_id")));
			}
			return usersList;
		case "videos": 
			videoList = new ArrayList<VideoCollector>();
			rs = Retriever.getInstance().selectVideoList(query);
			while(rs.next()){
				videoList.add(new VideoCollector(rs.getInt("id"), rs.getString("title"), String.valueOf(rs.getInt("release_date")), rs.getInt("modification"), rs.getInt("visibility"), rs.getString("filename"), rs.getString("name"), rs.getString("category"), rs.getString("description")));
			}
			return videoList;
		}
		return null;
	}

	@Override
	public void run() {
		try {
			while(!cl.isClosed()){
				executeAction();
			}
		} catch (ClassNotFoundException | IOException e) {
			if(e instanceof EOFException) System.out.println("[Info] - Socket "+cl.getInetAddress()+" closed");
			else System.out.println("[Error] - Couldn't define action to execute.");
			if(Main.getDebug()) e.printStackTrace();
		} 
	}
}
