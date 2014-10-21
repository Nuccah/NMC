/**
 * 
 */
package controller;

import java.sql.SQLException;

import model.AlbumCollector;
import model.AudioCollector;
import model.BookCollector;
import model.ConnectorDB;
import model.EpisodeCollector;
import model.ImageCollector;
import model.SeriesCollector;
import model.VideoCollector;

/**
 * Class permitting to inject metadata into database
 * @author Derek
 *
 */
public class Injecter {
	private static Injecter instance = null;
	private ConnectorDB db;

	protected Injecter(){
		this.db = ConnectorDB.getInstance();
	}

	public static Injecter getInstance(){
		if(instance == null) instance = new Injecter();
		return instance;
	}
	
	public void injector(AudioCollector audio){
		db.openConnection();
		String query = null; //TODO: [Derek] SQL Insert Audio
		try {
			db.modify(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.closeConnection();
	}
	
	public void injector(AlbumCollector album){
		db.openConnection();
		String query = null; //TODO: [Derek] SQL Insert Album
		try {
			db.modify(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.closeConnection();		
	}
	
	public void injector(BookCollector book){
		db.openConnection();
		String query = null; //TODO: [Derek] SQL Insert Book
		try {
			db.modify(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.closeConnection();		
	}
	
	public void injector(ImageCollector image){
		db.openConnection();
		String query = null; //TODO: [Derek] SQL Insert Image
		try {
			db.modify(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.closeConnection();		
	}
	
	public void injector(VideoCollector video){
		db.openConnection();
		String query = null; //TODO: [Derek] SQL Insert Video
		try {
			db.modify(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.closeConnection();
	}
	
	public void injector(SeriesCollector series){
		db.openConnection();
		String query = null; //TODO: [Derek] SQL Insert Series
		try {
			db.modify(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.closeConnection();		
	}
	
	public void injector(EpisodeCollector episode){
		db.openConnection();
		String query = null; //TODO: [Derek] SQL Insert Episode
		try {
			db.modify(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.closeConnection();		
	}
}
