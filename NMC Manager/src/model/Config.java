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

import controller.Parser;

/**
 * Classe de gestion du fichier de config (.properties) de l'application
 * @author Antoine Ceyssens
 * @version 1.1
 */
public class Config {
	private static Config instance = null; 
	private File defaultDir;
	private File cfg_file;
	private FileInputStream cfg_in;
	private FileOutputStream cfg_out;
	private Properties prop;
	
	
	/**
	 * Initialise le fichier de config
	 */
	protected Config(){
		if(Parser.getInstance().isWindows()){
			defaultDir = new File(System.getProperty("user.home")+"\\AppData\\Local\\NMC");
		} else {
			defaultDir = new File(System.getProperty("user.home")+"/.config/NMC");
		}
		defaultDir.mkdirs();
		cfg_file = new File(defaultDir.getAbsolutePath()+Parser.getInstance().getSlash()+".properties");
		prop = new Properties();
		if(!cfg_file.exists()){
			try {
				cfg_file.createNewFile();
				defaultConf();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "The config file couldn't be created\n"
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
		setProp("program_title", "NMC Manager");
		setProp("base_title", "NMC - ");
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
	 * Permet d'ajouter les propriétés passées en paramètres aux propriétés existantes
	 * @param new_prop : Les nouvelles propriétés à ajouter
	 */
	public void saveProp(Properties new_prop){
		try {
			cfg_out = new FileOutputStream(cfg_file);
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null, "The config file was not found!");
			e1.printStackTrace();
		}
		try {
			Properties merged = new Properties();
			merged.putAll(prop);
			merged.putAll(new_prop);
			merged.storeToXML(cfg_out, null);
			cfg_out.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error while writing configurations!");
			e.printStackTrace();
		}
		loadProp();
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
	
	public void delCfg(){
		cfg_file.delete();
	}
	
	public String getDefaultDir(){
		return defaultDir.getAbsolutePath();
	}

}
