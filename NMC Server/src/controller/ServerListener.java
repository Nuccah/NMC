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
import java.util.Properties;

import model.AlbumCollector;
import model.AudioCollector;
import model.BookCollector;
import model.Config;
import model.ConnectorDB;
import model.EpisodeCollector;
import model.ImageCollector;
import model.MetaDataCollector;
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
	
	protected ServerListener(Socket cl) {
		this.cl = cl;
		try{
			this.ois = new ObjectInputStream(cl.getInputStream());
			this.oos = new ObjectOutputStream(cl.getOutputStream());
		} catch(IOException e){
			System.out.println("[Error] - Unable to create socket streams for: "+cl.getInetAddress());
			e.printStackTrace();
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
			case "del" :
				oos.writeObject("ACK");
				delObject();
				break;
			case "list":
				oos.writeObject("ACK");
				sendList();
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
			e.printStackTrace();
		}
	}
	/**
	 * Permet de connecter un client avec les données reçues.
	 * Le client récupère un objet Profil dans le cas d'une connexion réussie
	 */
	private void connectCl(){
		String[] credentials = new String[2];
		try{
			credentials = (String[]) ois.readObject();
			oos.writeObject("CRED ACK");
			ois.readObject();	
		} catch (ClassNotFoundException | IOException e){
			System.out.println("[Error] Couldn't retrieve credentials from: "+cl.getInetAddress());
			e.printStackTrace();
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
				e.printStackTrace();
			}
			e1.printStackTrace();
		}
		try {
			if(password.compareTo(res.getString("password")) == 0){
				Profil pf = Profil.getInstance();
				pf.setUsername(username);
				pf.setId(res.getInt("id"));
				pf.setMail(res.getString("mail"));
				pf.setFirstName(res.getString("first_name"));
				pf.setLastName(res.getString("last_name"));
				pf.setBirthdate(res.getDate("birthdate"));
				pf.setRegDate(res.getDate("reg_date"));
				try{
					oos.writeObject(pf);
				} catch (IOException e){
					System.out.println("[Error] - Profil couldn't be sent to: "+cl.getInetAddress());
					e.printStackTrace();
				}
			} else {
				try {
					oos.writeObject("[Error] - Wrong login/password");
				} catch (IOException e) {
					e.printStackTrace();
				}			
			}
		} catch (SQLException e) {
			System.out.println("[Error] - Connection failed! Please check connection settings");
			try {
				oos.writeObject("[Error] - Connection failed! Please check if server is up");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			//e.printStackTrace();
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
			System.out.println("[Error] - Couldn't retrieve meta type from: "+cl.getInetAddress());
			//e1.printStackTrace();
		}
		mdc.setAbsPath(Config.getInstance().getProp("root_dir")+mdc.getRelPath());
		Injecter inj = Injecter.getInstance();
		try {
			if(mdc instanceof AudioCollector){
				oos.writeObject("ACK");
				inj.injector((AudioCollector) mdc);
			}
			else if(mdc instanceof AlbumCollector){
				oos.writeObject("ACK");
				inj.injector((AlbumCollector)mdc);
			}
			else if(mdc instanceof BookCollector){
				oos.writeObject("ACK");
				inj.injector((BookCollector) mdc);
			}
			else if(mdc instanceof EpisodeCollector){
				oos.writeObject("ACK");
				inj.injector((EpisodeCollector) mdc);
			}
			else if(mdc instanceof ImageCollector){
				oos.writeObject("ACK");
				inj.injector((ImageCollector) mdc);
			}
			else if(mdc instanceof SeriesCollector){
				oos.writeObject("ACK");
				inj.injector((SeriesCollector) mdc);
			}
			else if(mdc instanceof VideoCollector){
				oos.writeObject("ACK");
				inj.injector((VideoCollector) mdc);
			}
			else oos.writeObject("NACK");
		} catch (IOException e){
			System.out.println("[Error] - Meta Type ACK couldn't be sent to: "+cl.getInetAddress());
			//e.printStackTrace();
		}
	}
	
	private void delObject(){
		
	}

	private void sendList(){
		
	}
	
	private void logout() {
		try{
			ois.close();
			oos.close();
			if(!cl.isClosed()) cl.close();
		} catch(IOException e){
			System.out.println("[Warning] - Streams are already closed");
		}		
	}
	
	@Override
	public void run() {
		try {
			while(!cl.isClosed()) executeAction();
		} catch (ClassNotFoundException | IOException e) {
			if(e instanceof EOFException) System.out.println("[Info] - Socket "+cl.getInetAddress()+" closed");
			else System.out.println("[Error] - Couldn't define action to execute.");
		}
	}

}
