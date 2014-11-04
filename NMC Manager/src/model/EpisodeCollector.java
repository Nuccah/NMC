package model;

public class EpisodeCollector extends MetaDataCollector{
	private static final long serialVersionUID = 5898661708344764422L;
	private String director;
	private String series;
	private String season;
	private String chrono;
	private String filename;
	
	public EpisodeCollector(String title, String filename, String series, String director, String season, String chrono){
		super(title);
		this.director = director;
		this.series = series;
		this.season = season;
		this.chrono = chrono;
		this.filename = filename;
	}
	
	public EpisodeCollector(int id, String title, String filename, String series, String director, String season, String chrono){
		super(id, title);
		this.director = director;
		this.series = series;
		this.season = season;
		this.chrono = chrono;
		this.filename = filename;
	}

	public String getDirector() {
		return director;
	}

	public String getSeries() {
		return series;
	}

	public String getSeason() {
		return season;
	}

	public String getChrono() {
		return chrono;
	}

	public String getFilename() {
		return filename;
	}
}