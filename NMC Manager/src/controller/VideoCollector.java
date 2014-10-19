package controller;

public class VideoCollector extends MetaDataCollector{
	private String director;
	private String genre;
	private String filename;
	private String synopsis;
	private int year;

	public VideoCollector(String title, int year, int modId, int visId, String filename, String director, String genre, String synopsis) {
		super(title, modId,visId);
		this.director = director;
		this.filename = filename;
		this.year = year;
		this.genre = genre;
		this.synopsis = synopsis;
		// TODO Auto-generated constructor stub
	}

	@Override
	public MetaDataCollector videoExtraction() {
		// TODO Auto-generated method stub
		return super.videoExtraction();
	}
}