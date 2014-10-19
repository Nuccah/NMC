package controller;

public class BookCollector extends MetaDataCollector{
	private String author;
	private String genre;
	private int length;
	private String filename;
	private String synopsis;
	private int year;
	
	public BookCollector(String title, int year, int modId, int visId, String filename, String author, String genre, String synopsis) {
		super(title, modId,visId);
		this.author = author;
		this.filename = filename;
		this.year = year;
		this.genre = genre;
		this.synopsis = synopsis;
		// TODO Auto-generated constructor stub
	}

	@Override
	public MetaDataCollector bookExtraction() {
		// TODO Auto-generated method stub
		return super.bookExtraction();
	}

}