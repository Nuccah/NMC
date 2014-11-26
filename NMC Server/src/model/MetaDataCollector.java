package model;

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
	protected int id;
	protected String title;
	protected int modificiationID;
	protected int visibilityID;
	protected String absPath;
	protected String relPath;
	protected int adder;

	public MetaDataCollector(String title){
		this.title = title;
		this.absPath = null;
		this.relPath = null;
	}
	
	public MetaDataCollector(String title, int adder){
		this.title = title;
		this.adder = adder;
		this.absPath = null;
		this.relPath = null;
	}
	
	public MetaDataCollector(int id, String title){
		this.id = id;
		this.title = title;
		this.absPath = null;
		this.relPath = null;
	}
	
	public MetaDataCollector(String title, int modId, int visId){
		this.title = title;
		this.modificiationID = modId;
		this.visibilityID = visId;
		this.absPath = null;
		this.relPath = null;
	}
	
	public MetaDataCollector(String title, int modId, int visId, String relPath, int adder){
		this.title = title;
		this.modificiationID = modId;
		this.visibilityID = visId;
		this.absPath = null;
		this.relPath = relPath;
		this.adder = adder;
	}
	
	public MetaDataCollector(String title, int modId, int visId, String relPath){
		this.title = title;
		this.modificiationID = modId;
		this.visibilityID = visId;
		this.absPath = null;
		this.relPath = relPath;
	}
	
	public MetaDataCollector(String title, int modId, int visId, int adder){
		this.title = title;
		this.modificiationID = modId;
		this.visibilityID = visId;
		this.absPath = null;
		this.relPath = null;
		this.adder = adder;
	}
	
	public MetaDataCollector(int id, String title, int modId, int visId){
		this.id = id;
		this.title = title;
		this.modificiationID = modId;
		this.visibilityID = visId;
		this.absPath = null;
		this.relPath = null;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle(){
		return title;
	}

	public String getAbsPath() {
		return absPath;
	}

	public void setAbsPath(String absPath) {
		this.absPath = absPath;
	}

	public String getRelPath() {
		return relPath;
	}

	public void setRelPath(String relPath) {
		this.relPath = relPath;
	}

	public int getModificiationID() {
		return modificiationID;
	}

	public void setModificiationID(int modificiationID) {
		this.modificiationID = modificiationID;
	}

	public int getVisibilityID() {
		return visibilityID;
	}

	public void setVisibilityID(int visibilityID) {
		this.visibilityID = visibilityID;
	}

	public String toString() {
		// TODO Auto-generated method stub
		return this.title;
	}
	
	public int getAdder() {
		return adder;
	}

	public void setAdder(int adder) {
		this.adder = adder;
	}
}
