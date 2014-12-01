package model;
/**
 * Classe contenant les données d'un épisode
 * @author Derek
 *
 */
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
	/**
	 * Permet d'obtenir le réalisateur de l'épisode
	 * @return Le nom du réalisateur de l'épisode
	 */
	public String getDirector() {
		return director;
	}
	/**
	 * Permet d'obtenir la série à laquelle appartient l'épisode
	 * @return L'identifiant de la série de l'épisode
	 */
	public int getSeries() {
		return series;
	}
	
	/**
	 * Permet de mettre la série d'appartenance de l'épisode
	 * @param series : Identifiant de la série à laquelle appartient l'épisode
	 */
	public void setSeries(int series) {
		this.series = series;
	}
	/**
	 * Permet d'obtenir le nom de la série
	 * @return Le nom de la série à laquelle appartient l'épisode
	 */
	public String getSeriesName() {
		return seriesName;
	}
	/**
	 * Permet de mettre le nom de la série à laquelle appartient l'épisode
	 * @param seriesName : Le nom de la série à placer
	 */
	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}
	/**
	 * Permet d'obtenir la saison à laquelle appartient l'épisode
	 * @return La nom de la saison à laquelle l'épisode appartient
	 */
	public String getSeason() {
		return season;
	}

	public String getChrono() {
		return chrono;
	}
	/**
	 * Permet d'obtenir le nom du fichier
	 * @return Le nom du fichier
	 */
	public String getFilename() {
		return filename;
	}
	/**
	 * @throws
	 */
	public String toString() {
		return (this.title + " - " + this.seriesName);
	}
}