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
		String query4 = "INSERT INTO nmc_tracks_bands VALUES ((SELECT id FROM nmc_tracks ORDER BY id DESC LIMIT 1),(SELECT id FROM nmc_bands WHERE name = '"+audio.getArtist()+"'));";
		String query5;
		//TODO: [Derek] DANGER - NEAR CERTAINTY OF MULTIPLE RETURNS - NEEDS ADDITIONAL PARAMETERS
		if (audio.getAlbum() != null)
			query5 = "INSERT INTO nmc_tracks_albums VALUES ((SELECT id FROM nmc_tracks ORDER BY id DESC LIMIT 1),(SELECT id FROM nmc_media WHERE title = '"+audio.getAlbum()+"'));";
		else
			query5 = "INSERT INTO nmc_tracks_albums VALUES ((SELECT id FROM nmc_tracks ORDER BY id DESC LIMIT 1),(SELECT id FROM nmc_media WHERE title = 'Singles'));";
		try {
			db.modify(query1);
			db.modify(query2);
			db.modify(query3);
			db.modify(query4);
			db.modify(query5);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.closeConnection();
	}
	
	public void injector(AlbumCollector album){
		db.openConnection();
		String query1 = "INSERT INTO nmc_additions values (DEFAULT, NOW(), '"+Profil.getInstance().getId()+"');";
		String query2 = "INSERT INTO nmc_media values (DEFAULT, '"+album.getTitle()+"', '"+album.getRelPath()+"', '"+album.getVisibilityID()+"', '"+album.getModificiationID()+"', (SELECT id FROM nmc_additions ORDER BY id DESC LIMIT 1));";
		String query3 = "INSERT INTO nmc_media_info values ((SELECT id FROM nmc_media ORDER BY id DESC LIMIT 1), '"+album.getDescription()+"', '"+album.getYear()+"';";
		String query4 =	"INSERT INTO nmc_bands(\"name\") SELECT '"+album.getArtist()+"' WHERE NOT EXISTS (SELECT id FROM nmc_bands WHERE name = '"+album.getArtist()+"');";
		String query5 = "INSERT INTO nmc_media_info_bands VALUES ((SELECT id FROM nmc_media ORDER BY id DESC LIMIT 1),(SELECT id FROM nmc_bands WHERE name = '"+album.getArtist()+"'));";
		String query6 = "INSERT INTO nmc_categories(\"category\") SELECT '"+album.getGenre()+"' WHERE NOT EXISTS (SELECT id FROM nmc_categories WHERE category = '"+album.getGenre()+"');";
		String query7 = "INSERT INTO nmc_albums_categories VALUES ((SELECT id FROM nmc_media ORDER BY id DESC LIMIT 1),(SELECT id FROM nmc_categories WHERE category = '"+album.getGenre()+"');";
		try {
			db.modify(query1);
			db.modify(query2);
			db.modify(query3);
			db.modify(query4);
			db.modify(query5);
			db.modify(query6);
			db.modify(query7);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.closeConnection();		
	}
	
	public void injector(BookCollector book){
		db.openConnection();
		String query1 = "INSERT INTO nmc_additions values (DEFAULT, NOW(), '"+Profil.getInstance().getId()+"');";
		String query2 = "INSERT INTO nmc_media values (DEFAULT, '"+book.getTitle()+"', '"+book.getRelPath()+"', '"+book.getVisibilityID()+"', '"+book.getModificiationID()+"', (SELECT id FROM nmc_additions ORDER BY id DESC LIMIT 1));";
		String query3 = "INSERT INTO nmc_media_info values ((SELECT id FROM nmc_media ORDER BY id DESC LIMIT 1), '"+book.getSynopsis()+"', '"+book.getYear()+"';";
		String query4 =	"INSERT INTO nmc_persons(\"name\") SELECT '"+book.getAuthor()+"' WHERE NOT EXISTS (SELECT id FROM nmc_persons WHERE name = '"+book.getAuthor()+"');";
		String query5 = "INSERT INTO nmc_media_info_persons VALUES ((SELECT id FROM nmc_media ORDER BY id DESC LIMIT 1),(SELECT id FROM nmc_persons WHERE name = '"+book.getAuthor()+"'));";
		String query6 = "INSERT INTO nmc_categories(\"category\") SELECT '"+book.getGenre()+"' WHERE NOT EXISTS (SELECT id FROM nmc_categories WHERE category = '"+book.getGenre()+"');";
		String query7 = "INSERT INTO nmc_books_categories VALUES ((SELECT id FROM nmc_media ORDER BY id DESC LIMIT 1),(SELECT id FROM nmc_categories WHERE category = '"+book.getGenre()+"');";
		try {
			db.modify(query1);
			db.modify(query2);
			db.modify(query3);
			db.modify(query4);
			db.modify(query5);
			db.modify(query6);
			db.modify(query7);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.closeConnection();		
	}
	
	public void injector(ImageCollector image){
		db.openConnection();
		String query1 = "INSERT INTO nmc_additions values (DEFAULT, NOW(), '"+Profil.getInstance().getId()+"');";
		String query2 = "INSERT INTO nmc_media values (DEFAULT, '"+image.getTitle()+"', '"+image.getRelPath()+"', '"+image.getVisibilityID()+"', '"+image.getModificiationID()+"', (SELECT id FROM nmc_additions ORDER BY id DESC LIMIT 1));";
		String query3 =	"INSERT INTO nmc_persons(\"name\") SELECT '"+image.getPhotographer()+"' WHERE NOT EXISTS (SELECT id FROM nmc_persons WHERE name = '"+image.getPhotographer()+"');";
		String query4 = "INSERT INTO nmc_media_info_persons VALUES ((SELECT id FROM nmc_media ORDER BY id DESC LIMIT 1),(SELECT id FROM nmc_persons WHERE name = '"+image.getPhotographer()+"'));";
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
