package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.Config;
import model.MetaDataCollector;
/**
 * Gère les actions sockets nécessaires
 * @author Antoine Ceyssens
 * @version 1.0
 */
public class SocketManager extends Socket {
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	public SocketManager() throws NumberFormatException, UnknownHostException, IOException{
		super(Config.getInstance().getProp("srv_url"), Integer.valueOf(Config.getInstance().getProp("srv_port")));
		oos = new ObjectOutputStream(this.getOutputStream());
		ois = new ObjectInputStream(this.getInputStream());
	}
	/**
	 * Permet d'envoyer les méta données liées à un fichier uploadé
	 * @param mdc : MetaDataCollector à envoyer
	 */
	public void sendMeta(MetaDataCollector mdc){
		try {
			oos.writeObject("meta");
			String ack = null;
			ack = String.valueOf(ois.readObject());
			while(ack.compareTo("ACK") != 0) {
				oos.writeObject("meta");
				ack = String.valueOf(ois.readObject());
			}
			ack = null;
			oos.writeObject(mdc);
			ack = String.valueOf(ois.readObject());
			while(ack.compareTo("ACK") != 0) {
				oos.writeObject(mdc);
				ack = String.valueOf(ois.readObject());
			}			
		} catch (IOException | ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Unable to send meta data for: "+mdc.getTitle());
			e.printStackTrace();
		}
	}
	
	public void connect(String login, String password){
		throw new UnsupportedOperationException("Method not yet implemented!");
	}
	
	public void getConfig(){
		throw new UnsupportedOperationException("Method not yet implemented!");
	}
	
	public void delObject(Object o){
		//TODO: Trouver comment envoyer la requête de suppression
		throw new UnsupportedOperationException("Method not yet implemented!");
	}
	
	public ArrayList<MetaDataCollector> getList(String type){
		//TODO: Implémenter cette méthode
		return null;
		
	}
	
}
