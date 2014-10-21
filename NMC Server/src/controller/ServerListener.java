package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.AlbumCollector;
import model.AudioCollector;
import model.BookCollector;
import model.Config;
import model.EpisodeCollector;
import model.ImageCollector;
import model.MetaDataCollector;
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
	}
	
	public void executeAction() throws IOException, ClassNotFoundException{
		ois = new ObjectInputStream(cl.getInputStream());
		oos = new ObjectOutputStream(cl.getOutputStream());
		
		switch(String.valueOf(ois.readObject())){
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
			default :
				oos.writeObject("NACK");
		}
	}
	
	private void sendAutoConfig(){
		// TODO: Créer les méthodes nécessaires à l'envoi auto des configs
	}
	
	private void connectCl(){
		
	}
	// Permet de recevoir les méta données
	private void recieveMeta() throws ClassNotFoundException, IOException{
		Object o = ois.readObject();
		System.out.println("Object recieved: "+o);
		MetaDataCollector mdc = (MetaDataCollector) o;
		mdc.setAbsPath(Config.getInstance().getProp("root_dir")+mdc.getRelPath());
		Injecter inj = Injecter.getInstance();
		System.out.println("Server will check Meta Collector Type!!!");
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
			System.out.println("Found!!!");
			oos.writeObject("ACK");
			inj.injector((VideoCollector) mdc);
		}
		else oos.writeObject("NACK");
	}
	
	private void delObject(){
		
	}

	private void sendList(){
		
	}
	
	@Override
	public void run() {
		try {
			executeAction();
		} catch (ClassNotFoundException | IOException e) {
			// TODO: control exceptions
			e.printStackTrace();
		}
	}

}
