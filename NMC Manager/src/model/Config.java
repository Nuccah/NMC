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
 * @version 1.0
 */
public class Config {
	private File cfg_file;
	private FileInputStream cfg_in;
	private FileOutputStream cfg_out;
	private Properties prop;
	/**
	 * Constructeur config
	 * Met en place le fichier de config
	 */
	public Config(){
		this.cfg_file = new File(".properties");
		this.prop = new Properties();
		if(!this.cfg_file.exists()){
			try {
				this.cfg_file.createNewFile();
			} catch (IOException e) {
				JOptionPane.showConfirmDialog(null, "Le fichier n'a pas pu être créé!\n"
						+ "Veuillez essayer de lancer le programme en tant qu'Administrateur.");
				e.printStackTrace();
			}
		}
		this.loadProp();
	}
	
	/**
	 * Permet d'initialiser les propriétés de bases
	 */
	public void init(){
		this.setProp("program_title", "Nukama MediaCenter Manager");
		this.setProp("url_db", "//localhost/nmc_db");
		this.setProp("user_db", "nmc_admin");
		this.setProp("pass_db", "ephec2014");
		this.saveProp();
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
	public void loadProp(){
		try {
			this.cfg_in = new FileInputStream(this.cfg_file);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Le fichier de config n'a pas pu être trouvé!");
			e.printStackTrace();
		}
		try {
			this.prop.loadFromXML(cfg_in);
			this.cfg_in.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erreur lors du chargement du fichier de config!");
			e.printStackTrace();
		}
	}

}
