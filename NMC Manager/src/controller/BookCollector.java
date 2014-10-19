package controller;

public class BookCollector extends MetaDataCollector{
	private String author;
	private String genre;
	private int length;
	private String filename;
	
	public BookCollector(String title, int year, int modId, int visId, String filename, String author, String genre) {
		super(title, year, modId,visId);
		this.author = author;
		this.filename = filename;
		this.genre = genre;
		this.length = 0;
		// TODO Auto-generated constructor stub
	}

	@Override
	public MetaDataCollector bookExtraction() {
		// TODO Auto-generated method stub
		return super.bookExtraction();
	}

}