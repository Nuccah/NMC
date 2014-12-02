package model;

/**
 * Classe contenant les données d'un album
 * @author Antoine
 *
 */
public class AlbumCollector extends MetaDataCollector{
	private static final long serialVersionUID = -7524508120485511030L;
	private String description;
	private String artist;
	private String genre;
	private String year;

	public AlbumCollector(String title, String year, int modId, int visId, String artist, String description, String genre) {
		super(title, modId,visId);
		this.artist = artist;
		this.year = year;
		this.description = description;
		this.genre = genre;
	}
	
	public AlbumCollector(int id, String title, String year, int modId, int visId, String artist, String description, String genre) {
		super(id, title, modId,visId);
		this.artist = artist;
		this.year = year;
		this.description = description;
		this.genre = genre;
	}

	/**
	 * Permet d'obtenir la description d'un album
	 * @return La description d'un album
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Permet d'obtenir l'artiste de l'album
	 * @return L'artiste de l'album
	 */
	public String getArtist() {
		return artist;
	}
	
	/**
	 * Permet d'obtenir le genre de l'album
	 * @return Le genre de l'album
	 */
	public String getGenre() {
		return genre;
	}
	/**
	 * Permet d'obtenir l'année de parution de l'album
	 * @return L'année de parution de l'album
	 */
	public String getYear() {
		return year;
	}
	/**
	 * @throws
	 */
	public String toString() {
		// TODO Auto-generated method stub
		return (this.title + " ("+this.year+")");
	}
	
}
