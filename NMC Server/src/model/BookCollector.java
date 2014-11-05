package model;

public class BookCollector extends MetaDataCollector{
	private static final long serialVersionUID = 1138699093003320810L;
	private String author;
	private String genre;
	private int length;
	private String filename;
	private String synopsis;
	private String year;
	
	public BookCollector(String title, String year, int modId, int visId, String filename, String author, String genre, String synopsis) {
		super(title, modId,visId);
		this.author = author;
		this.filename = filename;
		this.year = year;
		this.genre = genre;
		this.synopsis = synopsis;
	}
	
	public BookCollector(int id, String title, String year, int modId, int visId, String filename, String author, String genre, String synopsis) {
		super(id, title, modId,visId);
		this.author = author;
		this.filename = filename;
		this.year = year;
		this.genre = genre;
		this.synopsis = synopsis;
	}

	public String getAuthor() {
		return author;
	}

	public String getGenre() {
		return genre;
	}

	public int getLength() {
		return length;
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