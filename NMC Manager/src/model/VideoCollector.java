package model;

public class VideoCollector extends MetaDataCollector{
	private static final long serialVersionUID = -2416569935908691795L;
	private String director;
	private String genre;
	private String filename;
	private String synopsis;
	private String year;

	public VideoCollector(String title, String year, int modId, int visId, String filename, String director, String genre, String synopsis) {
		super(title, modId,visId);
		this.director = director;
		this.filename = filename;
		this.year = year;
		this.genre = genre;
		this.synopsis = synopsis;
	}
	
	public VideoCollector(int id, String title, String year, int modId, int visId, String filename, String director, String genre, String synopsis) {
		super(id, title, modId,visId);
		this.director = director;
		this.filename = filename;
		this.year = year;
		this.genre = genre;
		this.synopsis = synopsis;
	}

	@Override
	public MetaDataCollector videoExtraction() {
		// TODO Auto-generated method stub
		return super.videoExtraction();
	}

	public String getDirector() {
		return director;
	}

	public String getGenre() {
		return genre;
	}

	public String getFilename() {
		return filename;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public String getYear() {
		return year;
	}
}