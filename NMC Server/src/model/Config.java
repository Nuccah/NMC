package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Classe de gestion du fichier de config (.properties) du serveur
 * @author Antoine Ceyssens
 * @version 1.1
 */
public class Config {
	private static Config instance = null; 
	private File cfg_file;
	private FileInputStream cfg_in;
	private FileOutputStream cfg_out;
	private Properties prop;
	
	
	/**
	 * Initialise le fichier de config
	 */
	protected Config(){
		cfg_file = new File(".properties");
		prop = new Properties();
		if(!cfg_file.exists()){
			try {
				cfg_file.createNewFile();
				defaultConf();
			} catch (IOException e) {
				System.out.println("Le fichier n'a pas pu être créé!\n"
						+ "Veuillez essayer de lancer le programme en tant qu'Administrateur.");
				e.printStackTrace();
			}
		}
		loadProp();
	}
	
	public static Config getInstance(){
		if(instance == null){
			instance = new Config();
		}
		return instance;
	}
	
	/**
	 * Permet d'initialiser les propriétés de bases
	 */
	public void defaultConf(){
		//TODO: Définir les variables nécessaires au lancement
		saveProp();
	}
	
	public void setProp(String key, String value){
		prop.setProperty(key, value);
	}
	
	public String getProp(String key){
		return prop.getProperty(key);
	}
	
	public void removeProp(String key){
		prop.remove(key);
	}
	
	/**
	 * Ecris le contenu de la hashtable dans le fichier de config
	 */
	public void saveProp(){
		try {
			cfg_out = new FileOutputStream(cfg_file);
		} catch (FileNotFoundException e1) {
			System.out.println("Le fichier de config n'a pas pu être trouvé!");
			e1.printStackTrace();
		}
		try {
			prop.storeToXML(cfg_out, null);
			cfg_out.close();
		} catch (IOException e) {
			System.out.println("Erreur lors de l'écriture des configurations!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Charge les propriétés dans la hashtable prop
	 */
	public void loadProp(){
		try {
			cfg_in = new FileInputStream(cfg_file);
		} catch (FileNotFoundException e) {
			System.out.println("Le fichier de config n'a pas pu être trouvé!");
			e.printStackTrace();
		}
		try {
			prop.loadFromXML(cfg_in);
			cfg_in.close();
		} catch (IOException e) {
			System.out.println("Erreur lors du chargement du fichier de config!");
			e.printStackTrace();
		}
	}
}
