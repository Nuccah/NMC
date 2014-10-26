package model;

public class SeriesCollector extends MetaDataCollector{
	private static final long serialVersionUID = -7536243590487815526L;
	private String synopsis;
	private String genre;
	private String year;

	public SeriesCollector(String title, String year, int modId, int visId, String synopsis, String genre){
		super(title, modId,visId);
		this.synopsis = synopsis;
		this.genre = genre;
		this.year = year;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public String getGenre() {
		return genre;
	}

	public String getYear() {
		return year;
	}
	
	
}