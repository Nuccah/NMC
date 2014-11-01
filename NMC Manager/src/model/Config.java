/**
 * 
 */
package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

/**
 * Classe de gestion du fichier de config (.properties) de l'application
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
				JOptionPane.showConfirmDialog(null, "The config file couldn't be created\n"
						+ "Please try to run the application with administrator privileges.");
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
		setProp("program_title", "Nukama MediaCenter Manager");
		setProp("base_title", "NMC - ");
		//TODO: Modifier gui pour demander IP et port serveur (si différent de port défaut: 50002") lors du premier lancement
		setProp("srv_url", "localhost");
		setProp("srv_port", "50002");
		setProp("init", "0");
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
			JOptionPane.showMessageDialog(null, "The config file was not found!");
			e1.printStackTrace();
		}
		try {
			prop.storeToXML(cfg_out, null);
			cfg_out.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error while writing configurations!");
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
			JOptionPane.showMessageDialog(null, "The config file was not found!");
			e.printStackTrace();
		}
		try {
			prop.loadFromXML(cfg_in);
			cfg_in.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error while reading configurations!");
			e.printStackTrace();
		}
	}
	
	

}
