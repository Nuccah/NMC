package model;

public class ImageCollector extends MetaDataCollector{
	private static final long serialVersionUID = -6557025443275058628L;
	private String photographer;
	private String filename;
	private int year;
	
	public ImageCollector(String title, int year, int modId, int visId, String filename, String photographer) {
		super(title, modId,visId);
		this.photographer = photographer;
		this.year = year;
		this.filename = filename;
	}

	@Override
	public MetaDataCollector imageExtraction() {
		return super.imageExtraction();
	}

	public String getPhotographer() {
		return photographer;
	}

	public String getFilename() {
		return filename;
	}

	public int getYear() {
		return year;
	}
	
	
}