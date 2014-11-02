package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import model.Config;

/**
 * Permet de gérer l'initialisation du serveur
 * @author Antoine
 *
 */
public class Initializer {
	private static Initializer instance = null;
	
	protected Initializer(){
		
	}
	
	public static Initializer getInstance(){
		if(instance == null){
			instance = new Initializer();
		}
		return instance;
	}
	
	public boolean importDefaultConf(String path){
		File fConf = new File(path);
		if(fConf.exists()){
			Properties props = new Properties();
			try {
				props.loadFromXML(new FileInputStream(fConf));
			} catch (IOException e) {
				System.out.println("[Error] - Unable to retrieve properties from "+fConf.getAbsolutePath());
				if(Main.getDebug()) e.printStackTrace();
			}
			Config.getInstance().saveProp(props);
			fConf.delete();
			return true;
		}
		return false;		
	}
}
