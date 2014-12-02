package model;
/**
 * Classe contenant les données d'un livre
 * @author Derek
 *
 */
public class BookCollector extends MetaDataCollector{
	private static final long serialVersionUID = 1138699093003320810L;
	private String author;
	private String genre;
	private int length;
	private String filename;
	private String synopsis;
	private String year;
	
	public BookCollector(String title, String year, int modId, int visId, String filename, String author, String genre, String synopsis) {
		super(title, modId,visId);
		this.author = author;
		this.filename = filename;
		this.year = year;
		this.genre = genre;
		this.synopsis = synopsis;
	}
	
	public BookCollector(int id, String title, String year, int modId, int visId, String filename, String author, String genre, String synopsis) {
		super(id, title, modId,visId);
		this.author = author;
		this.filename = filename;
		this.year = year;
		this.genre = genre;
		this.synopsis = synopsis;
	}
	/**
	 * Permet d'obtenir le nom de l'auteur
	 * @return Le nom de l'auteur
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * Permet d'obtenir le genre
	 * @return Le genre du livre
	 */
	public String getGenre() {
		return genre;
	}
	/**
	 * Permet d'obtenir la longueur du livre
	 * @return La longueur du livre (en nombre de pages)
	 */
	public int getLength() {
		return length;
	}
	/**
	 * Permet d'obtenir le nom du fichier
	 * @return Le nom du fichier 
	 */
	public String getFilename() {
		return filename;
	}
	/**
	 * Permet de modifier le nom du fichier de la video
	 * @param filename : le nom de fichier de la video
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
	/**
	 * Permet d'obtenir le synopsis du livre
	 * @return Le synopsis du livre
	 */
	public String getSynopsis() {
		return synopsis;
	}
	/**
	 * Permet d'obtenir l'année de parution du livre
	 * @return L'année de parution du livre
	 */
	public String getYear() {
		return year;
	}
	/**
	 * @throws
	 */
	public String toString() {
		// TODO Auto-generated method stub
		return (this.title + " - " + this.year);
	}

}