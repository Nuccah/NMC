package model;
/**
 * Classe contenant les données d'une vidéo
 * @author Antoine
 *
 */
public class VideoCollector extends MetaDataCollector{
	private static final long serialVersionUID = -2416569935908691795L;
	private String director;
	private String genre;
	private String filename;
	private String synopsis;
	private String year;

	public VideoCollector(String title, String year, int modId, int visId, String filename, String director, String genre, String synopsis) {
		super(title, modId,visId);
		this.director = director;
		this.filename = filename;
		this.year = year;
		this.genre = genre;
		this.synopsis = synopsis;
	}
	
	public VideoCollector(int id, String title, String year, int modId, int visId, String filename, String director, String genre, String synopsis) {
		super(id, title, modId,visId);
		this.director = director;
		this.filename = filename;
		this.year = year;
		this.genre = genre;
		this.synopsis = synopsis;
	}
	/**
	 * Permet d'obtenir le réalisateur de la vidéo
	 * @return Le réalisateur de la vidéo
	 */
	public String getDirector() {
		return director;
	}
	/**
	 * Permet d'obtenir le genre de la vidéo
	 * @return Le genre de la vidéo
	 */
	public String getGenre() {
		return genre;
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
	 * Permet d'obtenir le synopsis 
	 * @return Le synopsis
	 */
	public String getSynopsis() {
		return synopsis;
	}
	/**
	 * Permet d'obtenir l'année de parution de la vidéo
	 * @return L'année de parution de la vidéo
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