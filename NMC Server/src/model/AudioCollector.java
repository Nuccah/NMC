package model;

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

	public String getArtist() {
		return artist;
	}

	public int getAlbum() {
		return album;
	}

	public void setAlbum(int album) {
		this.album = album;
	}

	public String getFilename() {
		return filename;
	}
	
	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	
	public String toString() {
		// TODO Auto-generated method stub
		return (this.title + " - " + this.artist + " - " + this.albumName);
	}
}