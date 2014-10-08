package model;

import java.util.Date;

/**
 * Classe de gestion des méta données des fichiers à envoyer au serveur
 * 
 * Permet de créer une instance pour chaque fichier à uploader
 * @author Antoine
 *
 */
public class MetaDataCollector {
	//TODO: Implémenter cette classe et tous ses composants
	private String title;
	private String author;
	private String director;
	private String scenarist;
	private Date release_date;
	
	
	public MetaDataCollector(){
		title = null;
		author = null;
		director = null;
		scenarist = null;
		release_date = null;
	}
}
