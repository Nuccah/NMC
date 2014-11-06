package model;

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

	public String getDescription() {
		return description;
	}

	public String getArtist() {
		return artist;
	}

	public String getGenre() {
		return genre;
	}

	public String getYear() {
		return year;
	}
	public String toString() {
		// TODO Auto-generated method stub
		return (this.id+this.title);
	}
	
}
