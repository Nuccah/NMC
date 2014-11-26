package model;

public class ImageCollector extends MetaDataCollector{
	private static final long serialVersionUID = -6557025443275058628L;
	private String photographer;
	private String filename;
	private String year;
	
	public ImageCollector(String title, String year, int modId, int visId, String filename, String photographer, String relPath) {
		super(title, modId,visId, relPath);
		this.photographer = photographer;
		this.year = year;
		this.filename = filename;
	}
	
	public ImageCollector(String title, String year, int modId, int visId, String filename, String photographer, String relPath, int adder) {
		super(title, modId,visId, relPath, adder);
		this.photographer = photographer;
		this.year = year;
		this.filename = filename;
	}
	
	public ImageCollector(int id, String title, String year, int modId, int visId, String filename, String photographer) {
		super(id, title, modId,visId);
		this.photographer = photographer;
		this.year = year;
		this.filename = filename;
	}

	public String getPhotographer() {
		return photographer;
	}

	public String getFilename() {
		return filename;
	}

	public String getYear() {
		return year;
	}
	
	public String toString() {
		// TODO Auto-generated method stub
		return (this.title + " - " + this.year);
	}
}