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
	
	public ResultSet selectAudioList(String query){
		db.openConnection();
		//TODO: [Derek] SQL Select Audio
		ResultSet rs = db.select(query);
		db.closeConnection();
		return rs;
	}
	
	public ResultSet selectAlbumList(String query){
		db.openConnection();
		//TODO: [Derek] SQL Select Album
		ResultSet rs = db.select(query);
		db.closeConnection();
		return rs;
	}
	
	public ResultSet selectBookList(String query){
		db.openConnection();
		//TODO: [Derek] SQL Select Book
		ResultSet rs = db.select(query);
		db.closeConnection();
		return rs;
	}
	
	public ResultSet selectEpisodeList(String query){
		db.openConnection();
		//TODO: [Derek] SQL Select Episode
		ResultSet rs = db.select(query);
		db.closeConnection();
		return rs;
	}
	
	public ResultSet selectImageList(String query){
		db.openConnection();
		//TODO: [Derek] SQL Select Image
		ResultSet rs = db.select(query);
		db.closeConnection();
		return rs;
	}
	
	public ResultSet selectSeriesList(String query){
		db.openConnection();
		//TODO: [Derek] SQL Select Series
		ResultSet rs = db.select(query);
		db.closeConnection();
		return rs;
	}
	
	public ArrayList<VideoCollector> selectVideoList(String query){
		db.openConnection();
		//TODO: [Derek] SQL Select Video
		ResultSet rs = db.select(query);
		db.closeConnection();
		return null;
	}
}
