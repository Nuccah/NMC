package controller;

public class AudioCollector extends MetaDataCollector{
	private String artist;
	private String album;
	private int duration;
	private String filename;
	
	public AudioCollector(String title, int year, int modId, int visId, String filename, String artist, String album) {
		super(title, year, modId,visId);
		this.artist = artist;
		this.filename = filename;
		this.album = album;
		this.duration = 0;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public MetaDataCollector audioExtraction() {
		// TODO Auto-generated method stub
		return super.audioExtraction();
	}
}