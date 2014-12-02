package model;
/**
 * Classe contenant les données d'une série
 * @author Antoine
 *
 */
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
	
	public SeriesCollector(String title, String year, int modId, int visId, String synopsis, String genre, int adder){
		super(title, modId,visId, adder);
		this.synopsis = synopsis;
		this.genre = genre;
		this.year = year;
	}
	
	public SeriesCollector(int id, String title, String year, int modId, int visId, String synopsis, String genre){
		super(id, title, modId,visId);
		this.synopsis = synopsis;
		this.genre = genre;
		this.year = year;
	}
	/**
	 * Permet d'obtenir le synopsis d'une série
	 * @return Le synopsis de la série
	 */
	public String getSynopsis() {
		return synopsis;
	}
	/**
	 * Permet d'obtenir le genre de la série
	 * @return Le genre de la série
	 */
	public String getGenre() {
		return genre;
	}
	/**
	 * Permet d'obtenir l'année de parution de la série
	 * @return L'année de parution de la série
	 */
	public String getYear() {
		return year;
	}
	/**
	 * @throws
	 */
	public String toString() {
		return (this.id+this.title);
	}
}