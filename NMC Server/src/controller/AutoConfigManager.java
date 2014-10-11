package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import model.Config;
/**
 * Classe permettant l'envoi des configurations automatiquement lorsqu'un client se connecte pour la première fois
 * @author Antoine
 *
 */
public class AutoConfigManager extends Thread {
	private static AutoConfigManager instance = null;
	private Config conf;

	@Override
	public void run(){
		try {
			ServerSocket srv = new ServerSocket(Integer.valueOf(conf.getProp("srv_port")));
			
			while(!srv.isClosed()){
				Socket client = srv.accept();
				sendConfig(client);
			}
			srv.close();
			
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
	protected AutoConfigManager(){
		conf = Config.getInstance();
	}
	
	public static AutoConfigManager getInstance(){
		if(instance == null){
			instance = new AutoConfigManager();
		}
		return instance;
	}

	private void sendConfig(Socket client) throws IOException{
		//ArrayList<String> listConf = new ArrayList<String>();
		//TODO: Définir les configs à envoyer + sécuriser
		
		//ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
		//ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
		
	}
	
}
