package model;

public class EpisodeCollector extends MetaDataCollector{
	private static final long serialVersionUID = 5898661708344764422L;
	private String director;
	private String series;
	private int season;
	private int chrono;
	private String filename;
	
	public EpisodeCollector(String title, String filename, String series, String director, int season, int chrono){
		super(title);
		this.director = director;
		this.series = series;
		this.season = season;
		this.chrono = chrono;
		this.filename = filename;
	}
}