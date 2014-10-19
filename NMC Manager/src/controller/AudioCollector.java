package controller;

public class AudioCollector extends MetaDataCollector{
	private String artist;
	private String album;
	private String filename;
	
	public AudioCollector(String title, String filename, String artist, String album) {
		super(title);
		this.artist = artist;
		this.filename = filename;
		this.album = album;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public MetaDataCollector audioExtraction() {
		// TODO Auto-generated method stub
		return super.audioExtraction();
	}
}