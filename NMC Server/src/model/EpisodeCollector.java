package model;

public class EpisodeCollector extends MetaDataCollector{
	private static final long serialVersionUID = 5898661708344764422L;
	private String director;
	private int series;
	private String seriesName;
	private String season;
	private String chrono;
	private String filename;
	
	public EpisodeCollector(String title, String filename, int series, String seriesName, String director, String season, String chrono){
		super(title);
		this.director = director;
		this.series = series;
		this.seriesName = seriesName;
		this.season = season;
		this.chrono = chrono;
		this.filename = filename;
	}
	
	public EpisodeCollector(String title, String filename, int series, String seriesName, String director, String season, String chrono, int adder){
		super(title, adder);
		this.director = director;
		this.series = series;
		this.seriesName = seriesName;
		this.season = season;
		this.chrono = chrono;
		this.filename = filename;
	}
	
	public EpisodeCollector(int id, String title, String filename, int series, String seriesName, String director, String season, String chrono){
		super(id, title);
		this.director = director;
		this.series = series;
		this.seriesName = seriesName;
		this.season = season;
		this.chrono = chrono;
		this.filename = filename;
	}

	public String getDirector() {
		return director;
	}

	public int getSeries() {
		return series;
	}
	public void setSeries(int series) {
		this.series = series;
	}
	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
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
	
	public String toString() {
		// TODO Auto-generated method stub
		return (this.title + " - " + this.seriesName);
	}
}