package model;

/**
 * Classe contenant les données d'une musique
 * @author Derek
 *
 */
public class AudioCollector extends MetaDataCollector{
	private static final long serialVersionUID = -7220269338111519233L;
	private String artist;
	private int album;
	private String albumName;
	private String filename;

	public AudioCollector(String title, String filename, String artist, int album, String albumName) {
		super(title);
		this.artist = artist;
		this.filename = filename;
		this.album = album;
		this.albumName = albumName;
	}
	
	public AudioCollector(int id, String title, String filename, String artist, int album, String albumName) {
		super(id, title);
		this.artist = artist;
		this.filename = filename;
		this.album = album;
		this.albumName = albumName;
	}

	/**
	 * Permet d'obtenir l'artiste de la musique
	 * @return L'artiste de la musique
	 */
	public String getArtist() {
		return artist;
	}
	/**
	 * Permet d'obtenir l'album auquel appartient la musique
	 * @return L'identifiant de l'album de la musique
	 */
	public int getAlbum() {
		return album;
	}
	
	/**
	 * Permet de modifier l'album de la musique
	 * @param id : L'identifiant de l'album de la musique
	 */
	public void setAlbum(int album) {
		this.album = album;
	}
	/**
	 * Permet d'obtenir le nom du fichier 
	 * @return Le nom du fichier
	 */
	public String getFilename() {
		return filename;
	}
	/**
	 * Permet de modifier le nom du fichier de la musique
	 * @param filename : le nom de fichier de la musique
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
	/**
	 * Permet d'obtenir le nom de l'album auquel appartient la musique
	 * @return Le nom de l'album de la musique
	 */
	public String getAlbumName() {
		return albumName;
	}
	/**
	 * Permet de modifier le nom de l'album de la musique
	 * @param albumName : Le nom de l'album à remplacer
	 */
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	/**
	 * @throws
	 */
	public String toString() {
		// TODO Auto-generated method stub
		return (this.title + " - " + this.artist + " - " + this.albumName);
	}
	
}