package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.hamcrest.core.IsInstanceOf;

import model.AlbumCollector;
import model.AudioCollector;
import model.BookCollector;
import model.Config;
import model.EpisodeCollector;
import model.ImageCollector;
import model.MetaDataCollector;
import model.SeriesCollector;
import model.VideoCollector;

public class ServerListener implements Runnable {
	private Socket cl;
	
	protected ServerListener(Socket cl) {
		this.cl = cl;
	}
	
	public void executeAction() throws IOException, ClassNotFoundException{
		ObjectInputStream ois = new ObjectInputStream(cl.getInputStream());
		ObjectOutputStream oos = new ObjectOutputStream(cl.getOutputStream());
		
		switch(String.valueOf(ois.readObject())){
			case "init" : 
				oos.writeObject("ACK");
				sendAutoConfig(ois, oos);
				break;
			case "connec":
				oos.writeObject("ACK");
				connectCl(ois, oos);
				break;
			case "meta" :
				oos.writeObject("ACK");
				recieveMeta(ois, oos);
				break;
			case "del" :
				oos.writeObject("ACK");
				delObject(ois, oos);
				break;
			default :
				oos.writeObject("NACK");
		}
	}
	
	private void sendAutoConfig(ObjectInputStream ois, ObjectOutputStream oos){
		// TODO: Créer les méthodes nécessaires à l'envoi auto des configs
	}
	
	private void connectCl(ObjectInputStream ois, ObjectOutputStream oos){
		
	}
	
	private void recieveMeta(ObjectInputStream ois, ObjectOutputStream oos) throws ClassNotFoundException, IOException{
		MetaDataCollector mdc = (MetaDataCollector) ois.readObject();
		Injecter inj = Injecter.getInstance();
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
	}
	
	private void delObject(ObjectInputStream ois, ObjectOutputStream oos){
		
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
