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
	protected String relPath;
	protected String absPath;
	protected int adder;


	public MetaDataCollector(String title){
		this.title = title;
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
	
	public MetaDataCollector(int id, String title, int modId, int visId){
		this.id = id;
		this.title = title;
		this.modificiationID = modId;
		this.visibilityID = visId;
		this.absPath = null;
		this.relPath = null;
	}
	/**
	 * Permet d'obtenir l'identifiant du média
	 * @return L'identifiant du média
	 */
	public int getId() {
		return id;
	}
	/**
	 * Permet de modifier l'id du média
	 * @param id : Identifiant à placer
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Permet d'obtenir le titre du média
	 * @return Le titre du média
	 */
	public String getTitle(){
		return title;
	}
	/**
	 * Permet d'obtenir le chemin relatif du fichier
	 * @return Le chemin relatif du fichier
	 */
	public String getRelPath() {
		return relPath;
	}
	/**
	 * Permet de modifier le chemin relatif du fichier
	 * @param relPath : Le chemin relatif du fichier
	 */
	public void setRelPath(String relPath) {
		this.relPath = relPath;
	}
	/**
	 * Permet d'obtenir le chemin absolu du fichier
	 * @return Le chemin absolu du fichier
	 */
	public String getAbsPath() {
		return absPath;
	}

	/**
	 * Permet de modifier le chemin absolu du fichier
	 * @param absPath : Le nouveau chemin absolu du fichier
	 */
	public void setAbsPath(String absPath) {
		this.absPath = absPath;
	}
	/**
	 * Permet d'obtenir le rang requis pour la modification
	 * @return L'identifiant du rang requis
	 */
	public int getModificiationID() {
		return modificiationID;
	}
	/**
	 * Permet de modifier le rang de modification requis
	 * @param modificiationID : Identifiant du nouveau rang
	 */
	public void setModificiationID(int modificiationID) {
		this.modificiationID = modificiationID;
	}
	/**
	 * Permet d'obtenir le rang requis pour accéder au média
	 * @return L'identifiant du rang requis
	 */
	public int getVisibilityID() {
		return visibilityID;
	}
	/**
	 * Permet de modifier le rang requis pour accéder au média
	 * @param visibilityID : Identifiant du nouveau rang
	 */
	public void setVisibilityID(int visibilityID) {
		this.visibilityID = visibilityID;
	}
	/**
	 * Permet d'obtenir l'identifiant de l'user qui à ajouté le media
	 * @return L'identifiant de l'user
	 */
	public int getAdder() {
		return adder;
	}
	/**
	 * Permet de modifier l'identifiant de l'user qui à ajouté le media
	 * @param visibilityID : Identifiant de l'user
	 */
	public void setAdder(int adder) {
		this.adder = adder;
	}
	/**
	 * @throws
	 */
	public String toString() {
		return this.title;
	}
}
