package model;

public class AudioCollector extends MetaDataCollector{
	private static final long serialVersionUID = -7220269338111519233L;
	private String artist;
	private String album;
	private String filename;
	
	public AudioCollector(String title, String filename, String artist, String album) {
		super(title);
		this.artist = artist;
		this.filename = filename;
		this.album = album;
	}
	
	@Override
	public MetaDataCollector audioExtraction() {
		return super.audioExtraction();
	}

	public String getArtist() {
		return artist;
	}

	public String getAlbum() {
		return album;
	}

	public String getFilename() {
		return filename;
	}
	
	
	
	
}