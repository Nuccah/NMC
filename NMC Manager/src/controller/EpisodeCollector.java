package controller;

public class EpisodeCollector extends MetaDataCollector{
	private String director;
	private String screenwriter;
	private int season;
	private int chrono;
	private int duration;
	
	public EpisodeCollector(String title, int year, int modId, int visId, String filename, int season, int chrono){
		super(title, year, modId, visId);
		this.season = season;
		this.chrono = chrono;
		this.duration = 0;
	}
	
	public EpisodeCollector(String title, int year, int modId, int visId, String filename, String director, int season, int chrono){
		super(title, year, modId, visId);
		this.director = director;
		this.season = season;
		this.chrono = chrono;
		this.duration = 0;
	}
	
	public EpisodeCollector(String title, int year, int modId, int visId, String filename, String director, String screenwriter, int season, int chrono){
		super(title, year, modId, visId);
		this.director = director;
		this.screenwriter = screenwriter;
		this.season = season;
		this.chrono = chrono;
		this.duration = 0;
	}
}