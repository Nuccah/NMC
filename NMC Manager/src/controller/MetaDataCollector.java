package controller;

import java.io.Serializable;

/**
 * Classe de gestion des méta données des fichiers à envoyer au serveur
 * 
 * Permet de créer une instance pour chaque fichier à uploader
 * @author Antoine
 *
 */
public class MetaDataCollector implements Serializable{
	private static final long serialVersionUID = 349141030197537212L;
	protected String title;
	protected int modificiationID;
	protected int visibilityID;

	public MetaDataCollector(String title){
		this.title = title;
	}
	
	public MetaDataCollector(String title, int modId, int visId){
		this.title = title;
		this.modificiationID = modId;
		this.visibilityID = visId;
	}

	public MetaDataCollector audioExtraction(){
		return null;
	}

	public MetaDataCollector bookExtraction(){
		return null;
	}

	public MetaDataCollector imageExtraction(){
		return null;
	}

	public MetaDataCollector videoExtraction(){
		return null;
	}
	
	public String getTitle(){
		return title;
	}
}
