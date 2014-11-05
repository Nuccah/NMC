package model;

public class ImageCollector extends MetaDataCollector{
	private static final long serialVersionUID = -6557025443275058628L;
	private String photographer;
	private String filename;
	private String year;
	
	public ImageCollector(String title, String year, int modId, int visId, String filename, String photographer) {
		super(title, modId,visId);
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
	
	
}