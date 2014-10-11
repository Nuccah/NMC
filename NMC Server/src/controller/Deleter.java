package controller;

import java.io.File;
import java.util.ArrayList;

/**
 * Classe de suppression définitive de fichiers sur le serveur
 * @author Antoine Ceyssens
 *
 */
public class Deleter {
	private static Deleter instance = null;
	private ArrayList<File> listFileToDelete;
	
	protected Deleter(){
		listFileToDelete = new ArrayList<File>();
	}
	
	public static Deleter getInstance(){
		if(instance == null){
			instance = new Deleter();
		}
		return instance;
	}
	/**
	 * Supprime les fichiers contenus dans l'arraylist listToDelete
	 */
	public void deleteFile(){
		while(!listFileToDelete.isEmpty()){
			listFileToDelete.get(listFileToDelete.size() - 1).delete();
			listFileToDelete.remove(listFileToDelete.size() - 1);
		}
	}
	
	/**
	 * Permet de récupérer la liste des fichiers à supprimer
	 * @return L'ArrayList des fichiers à supprimer
	 */
	public ArrayList<File> getFilesToConvert(){
		return listFileToDelete;
	}
}
