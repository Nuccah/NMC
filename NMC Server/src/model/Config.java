package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import controller.Parser;

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
				System.out.println("The config file couldn't be created\n"
						+ "Please try to run the application with administrator privileges.");
				e.printStackTrace();
			}
		}
		loadProp();
		File fl = new File(getProp("root_dir"));
		if(!fl.exists()) fl.mkdirs();
		String slash = Parser.getInstance().getSlash();
		File vid = new File(getProp("root_dir")+slash+"Movies");
		if(!vid.exists()) vid.mkdir();
		File music = new File(getProp("root_dir")+slash+"Music");
		if(!music.exists()) music.mkdir();
		File pic = new File(getProp("root_dir")+slash+"Images");
		if(!pic.exists()) pic.mkdir();
		File series = new File(getProp("root_dir")+slash+"Series");
		if(!series.exists()) series.mkdir();
		File ebooks = new File(getProp("root_dir")+slash+"Books");
		if(!ebooks.exists()) ebooks.mkdir();
		
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
		setProp("root_dir", System.getProperty("user.home")+Parser.getInstance().getSlash()+"NMC_STOCK");
		setProp("url_db", "//localhost/nmc_db");
		setProp("user_db", "nmc_admin");
		setProp("pass_db", "ephec2014");
		setProp("ftp_port", "50001");
		setProp("ftp_user", "ftpadmin");
		setProp("ftp_pass", "ephec2014");
		setProp("sock_port", "50002");
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
			System.out.println("The config file was not found!");
			e1.printStackTrace();
		}
		try {
			prop.storeToXML(cfg_out, null);
			cfg_out.close();
		} catch (IOException e) {
			System.out.println("Error while writing configurations!");
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
			System.out.println("The config file was not found!");
			e.printStackTrace();
		}
		try {
			prop.loadFromXML(cfg_in);
			cfg_in.close();
		} catch (IOException e) {
			System.out.println("Error while reading configurations!");
			e.printStackTrace();
		}
	}
}
