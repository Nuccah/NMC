package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Properties;

import controller.Main;
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
				if(Main.getDebug()) e.printStackTrace();
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
		if(Main.getDev()) setProp("url_db", "localhost/nmc_db");
		if(Main.getDev()) setProp("user_db", "nmc_admin");
		if(Main.getDev()) setProp("pass_db", "ephec2014");
		setProp("ftp_port", "50001");
		setProp("ftp_user", new BigInteger(130, new SecureRandom()).toString(32));
		setProp("sock_port", "50002");
		saveProp();
		File keyStore = new File(".config/security/");
		if(!keyStore.exists()){
			keyStore.mkdirs();
			try {
				String privKey = "-----BEGIN RSA PRIVATE KEY-----\n"
						+"Proc-Type: 4,ENCRYPTED\n"
						+"DEK-Info: AES-128-CBC,820B3B95E2614F49E2B58E6FFA312BCB\n\n"
						+"QUmCPd5ZAbL7e7QJUwkAEmbYiGwt1X1OgZvDnOQsW89kLnqbyh+1thz7+lvk21RZ\n"
						+"0C5tWzUKoFOu8fqJGScHoCotkiGLt0wCnC9VAgAsc7gmkFRVKh3FGSyyKbeIrdh8\n"
						+"RlcrdtuohTTDE7Mb5SRPX+CYM5b8jKFJOIkd4/7xhvNbsko5PIczIrKz1XyFypMv\n"
						+"OkW58YJUaRhXy8XLCdvFlrJLGokAJIYeZcXoD/SSpSa4qxD4mLASv4loN62LGtKV\n"
						+"XQUSXFHzbFWPg3whbm9XOOpieRduf/HA0vBTa6ypMBRHNcMQ3QGrK1Fmab1TkOy3\n"
						+"3MfK+3fbpGekORONc6qnaBVHe9PvJxj8ijQJQDexVElmCf+52kRDwVIX9dzr9fzP\n"
						+"8z/3e3Cj5W+JYQLXekfaBDb18Rh4vsVWJpJudPQE/KwqnOugUUhoJPlwCjDHHFqq\n"
						+"+nB31BdKCcLdfltljPmISCXJmxntOvhKsGQrEEAPZnRqk0Q3svA4DeTqUWlp3e8y\n"
						+"cHluzXtaW0IUPzGNP2hHk4zcaBUDhqGlIffLex+N/t4xun9NvMOF1mbWBT+tRN8Y\n"
						+"gsXQ2SFf/souiGMX6/h2TSnwkOAYOecLn75qJbrLaIXVV2puoGLF4SbSwmlRvjQO\n"
						+"YQExMlU97wp+IWloiQteSEQ2ScmpZDCk82rv3DNAJh9vBUNWfw7SjN5xy+W9vKPU\n"
						+"NmHVqy56jwVLOaq1Tvc3x8J73NKSox1Hdsx8qdPvrf/dB9j6KxKadn3sjyH/o6CY\n"
						+"r5CQ07lPXGQQKPoHY4uTKv2vtXXRcSrcofEf7lW/mTn0+l3i2m9kwUXIq80uiecq\n"
						+"ZSS2XdPie+7hCPMfHm5hrqHYBSEbVScVzDG3bUQh+q0j88KJtaOMJf0L/httGbMP\n"
						+"WeRen2MamjG0hHsoKQFh+OA9VTqEfkdMkzWsJri39e1f3Y5FO1eLne8SIQzftlJh\n"
						+"2DH/K23pmZIQYaSeDxLDl4ptcxdiwSFBQ+NLu3nn/QVb/oyno6q+4nElDtTMnUpy\n"
						+"qKG+vrXmIouDOJxbEujwnO/2Esr3zStYeDu/hXdtBXKYKOEenXcT3c2vU2U2V1vZ\n"
						+"A+HsM1BUkFYzOj2COkUfsQbK19QX3AyXyaa2ETl/EBpGHXDIUwK++qdEV7ANFCvI\n"
						+"cNOH3MA7NB1HsC68/YBZmONE765+bCwIPcP5mjVrpYkNqaL2c31WE9zA/cWky8tX\n"
						+"jSWtYrYbUh/6pkn2LPXLajBWDAoMHDTmhlyPJr9QuGV3MoE8T2wkavChs1Tk0AMl\n"
						+"xc92RcmoSM0t+4dP1i6oQGxM4nyv+OwrVZrSxAghiMxXwGJYzd7u+9RhvjYQRBya\n"
						+"W9Yh6rx6BO7h+yNkahNxxkaKnODZm0b8nm2onbCwJ8b8nZJ6YbJ5s6vGYErTmNEa\n"
						+"aoXRcrJB7Zwdu+k9OF8rHgx+UbYmWjUCWWVIZEBBGOgLzxVfXi67Mv5qlnNjmF1g\n"
						+"tfdPcixJJSo1YdwQ/w9sxuc2hjkc0Kw8ZA0TM1EJexAsYTIOQbSvosJ5oMYGxT1z\n"
						+"uPpsBOzBnAX8go1A7HeAWm3ilWni+2Gz77lh4JLQwn2/dJuaGZaCdY1Ea86eBLJO\n"
						+"-----END RSA PRIVATE KEY-----";
				File privKeyFile = new File(".config/security/private.pem");
				privKeyFile.createNewFile();
				FileOutputStream fos = new FileOutputStream(privKeyFile);
				fos.write(privKey.getBytes());
				fos.close();
				
				String pubKey = "-----BEGIN PUBLIC KEY-----\n"
						+"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0U+YFNEmTq8CCFZjqduy\n"
						+"JOfnYS9k3zAD4M3BwjP/Z/tQo7uAVWhHBIMpWssQigHsWGO1QdN1eyWTkWynQwu3\n"
						+"zDxSYpCY7WQTvl5+rBOYe7pX0u8DmMvm8sK9gDVWhfPvjJePSq7nTeVsS6FB7Xmg\n"
						+"k2smtranQU2KBdIhFDdj7X63eYSuOccJy5hOZ2t0hV77kO/gYdSnJ7WIpyosuiUU\n"
						+"PqfD0A5tnaFeA4TBKDRu6sElqtbiQvZyoxRBDalPlY6j3FBA2tnVneuZAkHALM+l\n"
						+"oe44mdr3+l3Qcpee99LyX4jmBiWKKvlNDULBaZI/Wm2Kwhxey+LGCZweKtt4PzFk\n"
						+"WwIDAQAB\n"
						+"-----END PUBLIC KEY-----";
				
				File pubKeyFile = new File(".config/security/public.pem");
				pubKeyFile.createNewFile();
				fos = new FileOutputStream(pubKeyFile);
				fos.write(pubKey.getBytes());
				fos.close();
			} catch (IOException e) {
				System.out.println("[Error] - Unable to open key files");
				if(Main.getDebug()) e.printStackTrace();
			}
		}
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
			if(Main.getDebug()) e1.printStackTrace();
		}
		try {
			prop.storeToXML(cfg_out, null);
			cfg_out.close();
		} catch (IOException e) {
			System.out.println("Error while writing configurations!");
			if(Main.getDebug()) e.printStackTrace();
		}
	}
	/**
	 * Permet d'ajouter les propriétés passées en paramètres aux propriétés existantes
	 * @param new_prop
	 */
	public void saveProp(Properties new_prop){
		try {
			cfg_out = new FileOutputStream(cfg_file);
		} catch (FileNotFoundException e1) {
			System.out.println("The config file was not found!");
			if(Main.getDebug()) e1.printStackTrace();
		}
		try {
			Properties merged = new Properties();
			merged.putAll(prop);
			merged.putAll(new_prop);
			merged.storeToXML(cfg_out, null);
			cfg_out.close();
		} catch (IOException e) {
			System.out.println("Error while writing configurations!");
			if(Main.getDebug()) e.printStackTrace();
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
			System.out.println("The config file was not found!");
			if(Main.getDebug()) e.printStackTrace();
		}
		try {
			prop.loadFromXML(cfg_in);
			cfg_in.close();
		} catch (IOException e) {
			System.out.println("Error while reading configurations!");
			if(Main.getDebug()) e.printStackTrace();
		}
	}
	

}
