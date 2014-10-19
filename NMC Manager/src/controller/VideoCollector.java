package controller;

public class VideoCollector extends MetaDataCollector{
	private String director;
	private String screenwriter;
	private String genre;
	private int duration;
	private String filename;
	private String synopsis;

	public VideoCollector(String title, int year, int modId, int visId, String filename, String director, String genre, String synopsis) {
		super(title, year, modId,visId);
		this.director = director;
		this.filename = filename;
		this.genre = genre;
		this.synopsis = synopsis;
		this.duration = 0;
		// TODO Auto-generated constructor stub
	}

	@Override
	public MetaDataCollector videoExtraction() {
		// TODO Auto-generated method stub
		return super.videoExtraction();
	}
}