package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import model.Config;
import model.MetaDataCollector;
/**
 * Permet l'envoi des meta données au serveur
 * @author Antoine Ceyssens
 * @version 1.0
 */
public class MetaSender extends Socket {
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	public MetaSender() throws NumberFormatException, UnknownHostException, IOException{
		super(Config.getInstance().getProp("srv_url"), Integer.valueOf(Config.getInstance().getProp("srv_port")));
		oos = new ObjectOutputStream(this.getOutputStream());
		ois = new ObjectInputStream(this.getInputStream());
	}
	
	public void sendMeta(MetaDataCollector mdc){
		try {
			do {
				oos.writeObject(mdc);
			} while(!ois.readBoolean());			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Unable to send meta data for: "+mdc.getTitle());
			e.printStackTrace();
		}
	}
}
