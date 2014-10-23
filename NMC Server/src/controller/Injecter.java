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
import model.Profil;
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
		String query1 = "INSERT INTO nmc_additions values (DEFAULT, NOW(), '"+Profil.getInstance().getId()+"');";
		String query2 = "INSERT INTO nmc_tracks values (DEFAULT, '"+audio.getTitle()+"', '"+audio.getFilename()+"', (SELECT id FROM nmc_additions ORDER BY id DESC LIMIT 1));";
		String query3 =	"INSERT INTO nmc_bands(\"name\") SELECT '"+audio.getArtist()+"' WHERE NOT EXISTS (SELECT id FROM nmc_bands WHERE name = '"+audio.getArtist()+"');";
		String query4 = "INSERT INTO nmc_tracks_bands VALUES ((SELECT id FROM nmc_tracks ORDER BY id DESC LIMIT 1),(SELECT id FROM nmc_bands ORDER BY id DESC LIMIT 1));";
		try {
			db.modify(query1);
			db.modify(query2);
			db.modify(query3);
			db.modify(query4);
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
