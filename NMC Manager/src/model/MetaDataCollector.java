package model;

import java.util.Date;

/**
 * Classe de gestion des méta données des fichiers à envoyer au serveur
 * 
 * Permet de créer une instance pour chaque fichier à uploader
 * @author Antoine
 *
 */
public class MetaDataCollector {
	//TODO: Implémenter cette classe et tous ses composants
	private String title;
	private Date date;

	public MetaDataCollector(){
		title = null;
		date = null;
	}

	public MetaDataCollector audioExtraction(){
		return null;

	}

	public MetaDataCollector bookExtraction(){
		return null;

	}

	public MetaDataCollector imageExtraction(){
		return null;

	}

	public MetaDataCollector videoExtraction(){
		return null;

	}

	class AudioCollector extends MetaDataCollector{

		private String artist;
		private String album;
		private int duration;

		public AudioCollector() {
			super();
			artist = null;
			album = null;
			duration = 0;
			// TODO Auto-generated constructor stub
		}

		@Override
		public MetaDataCollector audioExtraction() {
			// TODO Auto-generated method stub
			return super.audioExtraction();
		}

	}

	class BookCollector extends MetaDataCollector{
		private String author;
		private String genre;
		private int length;

		public BookCollector() {
			super();
			author = null;
			genre = null;
			length = 0;
			// TODO Auto-generated constructor stub
		}

		@Override
		public MetaDataCollector bookExtraction() {
			// TODO Auto-generated method stub
			return super.bookExtraction();
		}

	}

	class ImageCollector extends MetaDataCollector{
		private String photographer;
		
		public ImageCollector() {
			super();
			photographer = null;
			// TODO Auto-generated constructor stub
		}

		@Override
		public MetaDataCollector imageExtraction() {
			// TODO Auto-generated method stub
			return super.imageExtraction();
		}

	}

	class VideoCollector extends MetaDataCollector{
		private String director;
		private String screenwriter;
		private String genre;
		private int duration;
		
		public VideoCollector() {
			super();
			director = null;
			screenwriter = null;
			genre = null;
			duration = 0;
			// TODO Auto-generated constructor stub
		}

		@Override
		public MetaDataCollector videoExtraction() {
			// TODO Auto-generated method stub
			return super.videoExtraction();
		}

	}
}
