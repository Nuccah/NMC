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
	private static File cfg_file;
	private static FileInputStream cfg_in;
	private static FileOutputStream cfg_out;
	private static Properties prop;
	/**
	 * Initialise le fichier de config
	 */
	public static void init(){
		cfg_file = new File(".properties");
		prop = new Properties();
		if(!cfg_file.exists()){
			try {
				cfg_file.createNewFile();
				Config.defaultConf();
			} catch (IOException e) {
				JOptionPane.showConfirmDialog(null, "Le fichier n'a pas pu être créé!\n"
						+ "Veuillez essayer de lancer le programme en tant qu'Administrateur.");
				e.printStackTrace();
			}
		}
		Config.loadProp();
	}
	
	/**
	 * Permet d'initialiser les propriétés de bases
	 */
	public static void defaultConf(){
		Config.setProp("program_title", "Nukama MediaCenter Manager");
		Config.setProp("base_title", "NMC - ");
		Config.setProp("url_db", "//localhost/nmc_db");
		Config.setProp("user_db", "nmc_admin");
		Config.setProp("pass_db", "ephec2014");
		Config.saveProp();
	}
	
	public static void setProp(String key, String value){
		prop.setProperty(key, value);
	}
	
	public static String getProp(String key){
		return prop.getProperty(key);
	}
	
	public static void removeProp(String key){
		prop.remove(key);
	}
	
	/**
	 * Ecris le contenu de la hashtable dans le fichier de config
	 */
	public static void saveProp(){
		try {
			cfg_out = new FileOutputStream(cfg_file);
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null, "Le fichier de config n'a pas pu être trouvé!");
			e1.printStackTrace();
		}
		try {
			prop.storeToXML(cfg_out, null);
			cfg_out.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erreur lors de l'écriture des configurations!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Charge les propriétés dans la hashtable prop
	 */
	public static void loadProp(){
		try {
			Config.cfg_in = new FileInputStream(Config.cfg_file);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Le fichier de config n'a pas pu être trouvé!");
			e.printStackTrace();
		}
		try {
			Config.prop.loadFromXML(cfg_in);
			Config.cfg_in.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erreur lors du chargement du fichier de config!");
			e.printStackTrace();
		}
	}

}
