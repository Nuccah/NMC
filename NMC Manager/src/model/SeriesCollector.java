package model;

public class SeriesCollector extends MetaDataCollector{
	private static final long serialVersionUID = -7536243590487815526L;
	private String synopsis;
	private String genre;
	private int year;

	public SeriesCollector(String title, int year, int modId, int visId, String synopsis, String genre){
		super(title, modId,visId);
		this.synopsis = synopsis;
		this.genre = genre;
		this.year = year;
	}
}