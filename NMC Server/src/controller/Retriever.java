package controller;

import java.sql.ResultSet;
import java.util.ArrayList;

import model.AlbumCollector;
import model.AudioCollector;
import model.BookCollector;
import model.ConnectorDB;
import model.EpisodeCollector;
import model.ImageCollector;
import model.SeriesCollector;
import model.VideoCollector;

/**
 * Classe permettant de récupérer les métadonnées des fichiers voulus
 * @author Antoine
 *
 */
public class Retriever {
	private static Retriever instance = null;
	private ConnectorDB db;
	
	protected Retriever(){
		this.db = ConnectorDB.getInstance();
	}
	
	public static Retriever getInstance(){
		if(instance == null){
			instance = new Retriever();
		}
		return instance;
	}
	
	public ArrayList<AudioCollector> selectAudioList(){
		db.openConnection();
		String query = null; //TODO: [Derek] SQL Select Audio
		ResultSet rs = db.select(query);
		db.closeConnection();
		return null;
	}
	
	public ArrayList<AlbumCollector> selectAlbumList(){
		db.openConnection();
		String query = null; //TODO: [Derek] SQL Select Album
		ResultSet rs = db.select(query);
		db.closeConnection();
		return null;
	}
	
	public ArrayList<BookCollector> selectBookList(){
		db.openConnection();
		String query = null; //TODO: [Derek] SQL Select Book
		ResultSet rs = db.select(query);
		db.closeConnection();
		return null;
	}
	
	public ArrayList<EpisodeCollector> selectEpisodeList(){
		db.openConnection();
		String query = null; //TODO: [Derek] SQL Select Episode
		ResultSet rs = db.select(query);
		db.closeConnection();
		return null;
	}
	
	public ArrayList<ImageCollector> selectImageList(){
		db.openConnection();
		String query = null; //TODO: [Derek] SQL Select Image
		ResultSet rs = db.select(query);
		db.closeConnection();
		return null;
	}
	
	public ArrayList<SeriesCollector> selectSeriesList(){
		db.openConnection();
		String query = null; //TODO: [Derek] SQL Select Series
		ResultSet rs = db.select(query);
		db.closeConnection();
		return null;
	}
	
	public ArrayList<VideoCollector> selectVideoList(){
		db.openConnection();
		String query = null; //TODO: [Derek] SQL Select Video
		ResultSet rs = db.select(query);
		db.closeConnection();
		return null;
	}
}
