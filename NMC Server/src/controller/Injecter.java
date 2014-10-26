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
			query5 = "INSERT INTO nmc_tracks_albums VALUES ((SELECT id FROM nmc_tracks ORDER BY id DESC LIMIT 1),(SELECT nmc_media.id FROM nmc_media INNER JOIN nmc_media_info ON nmc_media.id = nmc_media_info.id INNER JOIN nmc_media_info_bands ON nmc_media.id = media_info_id INNER JOIN nmc_bands ON band_id = nmc_bands.id WHERE title = 'Singles' AND nmc_bands.name = 'Various'));";
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
		String query2 = "INSERT INTO nmc_media values (DEFAULT, '"+album.getTitle()+"', '"+album.getRelPath()+"', 'album', '"+album.getVisibilityID()+"', '"+album.getModificiationID()+"', (SELECT id FROM nmc_additions ORDER BY id DESC LIMIT 1));";
		String query3;
		if(album.getDescription() == null)
			query3 = "INSERT INTO nmc_media_info(id, release_date) values ((SELECT id FROM nmc_media ORDER BY id DESC LIMIT 1), '"+Integer.parseInt(album.getYear())+"';";
		else 
			query3 = "INSERT INTO nmc_media_info(id, description, release_date) values ((SELECT id FROM nmc_media ORDER BY id DESC LIMIT 1), '"+album.getDescription()+"', '"+Integer.parseInt(album.getYear())+"';";
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
		String query2 = "INSERT INTO nmc_media values (DEFAULT, '"+book.getTitle()+"', '"+book.getRelPath()+"', 'book', '"+book.getVisibilityID()+"', '"+book.getModificiationID()+"', (SELECT id FROM nmc_additions ORDER BY id DESC LIMIT 1));";
		String query3;
		if(book.getSynopsis() == null)
			query3 = "INSERT INTO nmc_media_info(id, release_date) values ((SELECT id FROM nmc_media ORDER BY id DESC LIMIT 1), '"+Integer.parseInt(book.getYear())+"';";
		else
			query3 = "INSERT INTO nmc_media_info(id, description, release_date) values ((SELECT id FROM nmc_media ORDER BY id DESC LIMIT 1), '"+book.getSynopsis()+"', '"+Integer.parseInt(book.getYear())+"';";
		String query4 =	"INSERT INTO nmc_persons(\"name\") SELECT '"+book.getAuthor()+"' WHERE NOT EXISTS (SELECT id FROM nmc_persons WHERE name = '"+book.getAuthor()+"');";
		String query5 = "INSERT INTO nmc_media_info_authors VALUES ((SELECT id FROM nmc_media ORDER BY id DESC LIMIT 1),(SELECT id FROM nmc_persons WHERE name = '"+book.getAuthor()+"'));";
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
		String query2 = "INSERT INTO nmc_media values (DEFAULT, '"+image.getTitle()+"', '"+image.getRelPath()+"', 'image', '"+image.getVisibilityID()+"', '"+image.getModificiationID()+"', (SELECT id FROM nmc_additions ORDER BY id DESC LIMIT 1));";
		String query3 =	"INSERT INTO nmc_persons(\"name\") SELECT '"+image.getPhotographer()+"' WHERE NOT EXISTS (SELECT id FROM nmc_persons WHERE name = '"+image.getPhotographer()+"');";
		String query4 = "INSERT INTO nmc_media_photographers VALUES ((SELECT id FROM nmc_media ORDER BY id DESC LIMIT 1),(SELECT id FROM nmc_persons WHERE name = '"+image.getPhotographer()+"'));";
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
		String query1 = "INSERT INTO nmc_additions VALUES (DEFAULT, NOW(), '"+Profil.getInstance().getId()+"');";
		String query2 = "INSERT INTO nmc_media VALUES (DEFAULT, '"+video.getTitle()+"', '"+video.getRelPath()+"', 'video', '"+video.getVisibilityID()+"', '"+video.getModificiationID()+"', (SELECT id FROM nmc_additions ORDER BY id DESC LIMIT 1));";
		String query3;
		if(video.getSynopsis() == null)
			query3 = "INSERT INTO nmc_media_info(id, release_date) values ((SELECT id FROM nmc_media ORDER BY id DESC LIMIT 1), '"+Integer.parseInt(video.getYear())+"';";
		else
			query3 = "INSERT INTO nmc_media_info VALUES ((SELECT id FROM nmc_media ORDER BY id DESC LIMIT 1), '"+video.getSynopsis()+"', '"+Integer.parseInt(video.getYear())+"';";
		String query4 = "INSERT INTO nmc_videos(id, filename) VALUES (DEFAULT, '"+video.getFilename()+"');";
		String query5 = "INSERT INTO nmc_media_info_films VALUES ((SELECT id FROM nmc_videos ORDER BY id DESC LIMIT 1), (SELECT id FROM nmc_media ORDER BY id DESC LIMIT 1));";
		String query6 = "INSERT INTO nmc_categories(\"category\") SELECT '"+video.getGenre()+"' WHERE NOT EXISTS (SELECT id FROM nmc_categories WHERE category = '"+video.getGenre()+"');";
		String query7 = "INSERT INTO nmc_films_categories VALUES ((SELECT id FROM nmc_media ORDER BY id DESC LIMIT 1),(SELECT id FROM nmc_categories WHERE category = '"+video.getGenre()+"');";
		String query8 = null;
		String query9 = null;
		if (video.getDirector() != null){
			query8 = "INSERT INTO nmc_persons(\"name\") SELECT '"+video.getDirector()+"' WHERE NOT EXISTS (SELECT id FROM nmc_persons WHERE name = '"+video.getDirector()+"');";
			query9 = "INSERT INTO nmc_directors_persons VALUES ((SELECT id FROM nmc_videos ORDER BY id DESC LIMIT 1),(SELECT id FROM nmc_persons WHERE name = '"+video.getDirector()+"'));";
		}
		String query10 = "INSERT INTO nmc_users_watched_videos VALUES ((SELECT id FROM nmc_videos ORDER BY id DESC LIMIT 1), '"+Profil.getInstance().getId()+"', 'false');";
		try {
			db.modify(query1);
			db.modify(query2);
			db.modify(query3);
			db.modify(query4);
			db.modify(query5);
			db.modify(query6);
			db.modify(query7);
			if(video.getDirector() != null){
				db.modify(query8);
				db.modify(query9);
			}
			db.modify(query10);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.closeConnection();
	}

	public void injector(SeriesCollector series){
		db.openConnection();
		String query1 = "INSERT INTO nmc_additions VALUES (DEFAULT, NOW(), '"+Profil.getInstance().getId()+"');";
		String query2 = "INSERT INTO nmc_media VALUES (DEFAULT, '"+series.getTitle()+"', '"+series.getRelPath()+"', 'series', '"+series.getVisibilityID()+"', '"+series.getModificiationID()+"', (SELECT id FROM nmc_additions ORDER BY id DESC LIMIT 1));";
		String query3;
		if(series.getSynopsis() == null)
			query3 = "INSERT INTO nmc_media_info(id, release_date) values ((SELECT id FROM nmc_media ORDER BY id DESC LIMIT 1), '"+Integer.parseInt(series.getYear())+"';";
		else
			query3 = "INSERT INTO nmc_media_info VALUES ((SELECT id FROM nmc_media ORDER BY id DESC LIMIT 1), '"+series.getSynopsis()+"', '"+Integer.parseInt(series.getYear())+"';";
		String query4 = "INSERT INTO nmc_categories(\"category\") SELECT '"+series.getGenre()+"' WHERE NOT EXISTS (SELECT id FROM nmc_categories WHERE category = '"+series.getGenre()+"');";
		String query5 = "INSERT INTO nmc_series_categories VALUES ((SELECT id FROM nmc_media ORDER BY id DESC LIMIT 1),(SELECT id FROM nmc_categories WHERE category = '"+series.getGenre()+"');";
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

	public void injector(EpisodeCollector episode){
		db.openConnection();
		String query1 = "INSERT INTO nmc_additions VALUES (DEFAULT, NOW(), '"+Profil.getInstance().getId()+"');";
		String query2 = "INSERT INTO nmc_videos(id, filename) VALUES (DEFAULT, '"+episode.getFilename()+"');";
		String query3 = "INSERT INTO nmc_users_watched_videos VALUES ((SELECT id FROM nmc_videos ORDER BY id DESC LIMIT 1), '"+Profil.getInstance().getId()+"', 'false');";
		//TODO: [Derek] DANGER - NEAR CERTAINTY OF MULTIPLE RETURNS - NEEDS ADDITIONAL PARAMETERS
		String query4;
		if (episode.getSeason() == null && episode.getChrono() == null)
			query4 = "INSERT INTO nmc_media_info_series(nmc_episodes_id, nmc_media_info_id, title, nmc_addition_id) VALUES ((SELECT id FROM nmc_videos ORDER BY id DESC LIMIT 1), (SELECT id FROM nmc_media WHERE title = '"+episode.getSeries()+"' AND type = 'series'), '"+episode.getTitle()+"', (SELECT id FROM nmc_additions ORDER BY id DESC LIMIT 1));";
		else if (episode.getSeason() == null)
			query4 = "INSERT INTO nmc_media_info_series(nmc_episodes_id, nmc_media_info_id, title, chrono, nmc_addition_id) VALUES ((SELECT id FROM nmc_videos ORDER BY id DESC LIMIT 1), (SELECT id FROM nmc_media WHERE title = '"+episode.getSeries()+"' AND type = 'series'), '"+episode.getTitle()+"', '"+episode.getChrono()+"', (SELECT id FROM nmc_additions ORDER BY id DESC LIMIT 1));";
		else if(episode.getChrono() == null)
			query4 = "INSERT INTO nmc_media_info_series(nmc_episodes_id, nmc_media_info_id, title, season, nmc_addition_id) VALUES ((SELECT id FROM nmc_videos ORDER BY id DESC LIMIT 1), (SELECT id FROM nmc_media WHERE title = '"+episode.getSeries()+"' AND type = 'series'), '"+episode.getTitle()+"', '"+episode.getSeason()+"', (SELECT id FROM nmc_additions ORDER BY id DESC LIMIT 1));";
		else
			query4 = "INSERT INTO nmc_media_info_series(nmc_episodes_id, nmc_media_info_id, title, season, chrono, nmc_addition_id) VALUES ((SELECT id FROM nmc_videos ORDER BY id DESC LIMIT 1), (SELECT id FROM nmc_media WHERE title = '"+episode.getSeries()+"' AND type = 'series'), '"+episode.getTitle()+"', '"+episode.getSeason()+"', '"+episode.getChrono()+"', (SELECT id FROM nmc_additions ORDER BY id DESC LIMIT 1));";
		String query5 = null;
		String query6 = null;
		if (episode.getDirector() != null){
			query5 = "INSERT INTO nmc_persons(\"name\") SELECT '"+episode.getDirector()+"' WHERE NOT EXISTS (SELECT id FROM nmc_persons WHERE name = '"+episode.getDirector()+"');";
			query6 = "INSERT INTO nmc_directors_persons VALUES ((SELECT id FROM nmc_videos ORDER BY id DESC LIMIT 1),(SELECT id FROM nmc_persons WHERE name = '"+episode.getDirector()+"'));";
		}
		try {
			db.modify(query1);
			db.modify(query2);
			db.modify(query3);
			db.modify(query4);
			if (episode.getDirector() != null){
				db.modify(query5);
				db.modify(query6);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.closeConnection();		
	}
}
