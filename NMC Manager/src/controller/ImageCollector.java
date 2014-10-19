package controller;

public class ImageCollector extends MetaDataCollector{
	private String photographer;
	private String filename;
	
	public ImageCollector(String title, int year, int modId, int visId, String filename, String photographer) {
		super(title, year, modId,visId);
		this.photographer = photographer;
		this.filename = filename;
		// TODO Auto-generated constructor stub
	}

	@Override
	public MetaDataCollector imageExtraction() {
		// TODO Auto-generated method stub
		return super.imageExtraction();
	}
}