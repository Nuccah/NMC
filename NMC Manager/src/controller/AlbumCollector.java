package controller;

public class AlbumCollector extends MetaDataCollector{
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
		// TODO Auto-generated constructor stub
	}
}
