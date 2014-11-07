package controller;

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
	
	public void importDefaultConf(String[] args, int initInd){
		Properties newProps = new Properties();
		for(int i = 1; i <= 3; i++)
			newProps.setProperty(args[initInd+i].substring(0, args[initInd+i].lastIndexOf('=')), 
							args[initInd+i].substring(args[initInd+i].lastIndexOf('=') + 1));
		Config.getInstance().saveProp(newProps);		
	}
}
