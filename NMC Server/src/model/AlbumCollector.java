package model;

public class AlbumCollector extends MetaDataCollector{
	private static final long serialVersionUID = -7524508120485511030L;
	private String description;
	private String artist;
	private String genre;
	private int year;

	public AlbumCollector(String title, int year, int modId, int visId, String artist, String description, String genre) {
		super(title, modId,visId);
		this.artist = artist;
		this.year = year;
		this.description = description;
		this.genre = genre;
	}
}
