package model;
/**
 * Classe contenant les données d'une image
 * @author Derek
 *
 */
public class ImageCollector extends MetaDataCollector{
	private static final long serialVersionUID = -6557025443275058628L;
	private String photographer;
	private String filename;
	private String year;
	
	public ImageCollector(String title, String year, int modId, int visId, String filename, String photographer, String relPath) {
		super(title, modId,visId, relPath);
		this.photographer = photographer;
		this.year = year;
		this.filename = filename;
	}
	
	public ImageCollector(String title, String year, int modId, int visId, String filename, String photographer, String relPath, int adder) {
		super(title, modId,visId, relPath, adder);
		this.photographer = photographer;
		this.year = year;
		this.filename = filename;
	}
	
	public ImageCollector(int id, String title, String year, int modId, int visId, String filename, String photographer) {
		super(id, title, modId,visId);
		this.photographer = photographer;
		this.year = year;
		this.filename = filename;
	}
	/**
	 * Permet d'obtenir le nom du photographe/dessinateur
	 * @return Le nom du photographe/dessinateur
	 */
	public String getPhotographer() {
		return photographer;
	}
	/**
	 * Permet d'obtenir le nom du fichier
	 * @return Le nom du fichier
	 */
	public String getFilename() {
		return filename;
	}
	/**
	 * Permet d'obtenir l'année de création de l'image
	 * @return L'année de création de l'image
	 */
	public String getYear() {
		return year;
	}
	/**
	 * @throws
	 */
	public String toString() {
		return (this.title + " - " + this.year);
	}
}