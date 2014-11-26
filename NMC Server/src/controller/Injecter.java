/**
 * 
 */
package controller;

import java.sql.SQLException;

import model.AlbumCollector;
import model.AudioCollector;
import model.BookCollector;
import model.Config;
import model.ConnectorDB;
import model.EpisodeCollector;
import model.ImageCollector;
import model.Permissions;
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

	public void injector(Profil user) throws SQLException{
		db.openConnection();
		String query1 = "INSERT INTO nmc_users VALUES (DEFAULT, '"+user.getUsername()+"', '"+user.getPassword()+"', '"+user.getMail()+"', '"+user.getFirstName()+"', '"+user.getLastName()+"', '"+user.getBirthdate()+"', NOW(), '"+user.getPermissions_id()+"');";

		db.modify(query1);

		db.closeConnection();
	}

	public void injector(Permissions permissions) throws SQLException{
		db.openConnection();
		String query1 = "INSERT INTO nmc_permissions VALUES (DEFAULT, '"+permissions.getLabel()+"', '"+permissions.getLevel()+"');";

		db.modify(query1);

		db.closeConnection();
	}

	public void injector(AudioCollector audio) throws SQLException{
		db.openConnection();
		String query1 = "INSERT INTO nmc_additions values (DEFAULT, NOW(), '"+audio.getAdder()+"');";
		String query2 = "INSERT INTO nmc_tracks values (DEFAULT, '"+audio.getTitle()+"', '"+audio.getFilename()+"', (SELECT id FROM nmc_additions ORDER BY id DESC LIMIT 1));";
		String query3 =	"INSERT INTO nmc_bands(\"name\") SELECT '"+audio.getArtist()+"' WHERE NOT EXISTS (SELECT id FROM nmc_bands WHERE name = '"+audio.getArtist()+"');";
		String query4 = "INSERT INTO nmc_tracks_bands VALUES ((SELECT id FROM nmc_tracks ORDER BY id DESC LIMIT 1),(SELECT id FROM nmc_bands WHERE name = '"+audio.getArtist()+"'));";
		String query5 = "INSERT INTO nmc_tracks_albums VALUES ((SELECT id FROM nmc_tracks ORDER BY id DESC LIMIT 1),(SELECT id FROM nmc_media WHERE id = '"+audio.getAlbum()+"'));";

		db.modify(query1);
		db.modify(query2);
		db.modify(query3);
		db.modify(query4);
		db.modify(query5);

		db.closeConnection();
	}

	public void injector(AlbumCollector album) throws SQLException{
		album.setAbsPath(Config.getInstance().getProp("root_dir")+Parser.getInstance().getSlash()+"Music"+Parser.getInstance().getSlash()+(Retriever.getInstance().selectLastEntry()+1)+album.getTitle()+Parser.getInstance().getSlash());
		db.openConnection();
		String query1 = "INSERT INTO nmc_additions values (DEFAULT, NOW(), '"+album.getAdder()+"');";
		String query2;
		if(album.getDescription().isEmpty())
			query2 = "INSERT INTO nmc_media values (DEFAULT, '"+album.getTitle()+"', '"+album.getAbsPath()+"', 'album', null, '"+Integer.parseInt(album.getYear())+"',  0, (SELECT id FROM nmc_permissions WHERE level = '"+album.getVisibilityID()+"'), (SELECT id FROM nmc_permissions WHERE level = '"+album.getModificiationID()+"'), (SELECT id FROM nmc_additions ORDER BY id DESC LIMIT 1));";
		else 
			query2 = "INSERT INTO nmc_media values (DEFAULT, '"+album.getTitle()+"', '"+album.getAbsPath()+"', 'album', '"+album.getDescription()+"', '"+Integer.parseInt(album.getYear())+"', 0, (SELECT id FROM nmc_permissions WHERE level = '"+album.getVisibilityID()+"'), (SELECT id FROM nmc_permissions WHERE level = '"+album.getModificiationID()+"'), (SELECT id FROM nmc_additions ORDER BY id DESC LIMIT 1));";
		String query3 =	"INSERT INTO nmc_bands(\"name\") SELECT '"+album.getArtist()+"' WHERE NOT EXISTS (SELECT id FROM nmc_bands WHERE name = '"+album.getArtist()+"');";
		String query4 = "INSERT INTO nmc_media_bands VALUES ((SELECT id FROM nmc_media ORDER BY id DESC LIMIT 1),(SELECT id FROM nmc_bands WHERE name = '"+album.getArtist()+"'));";
		String query5 = "INSERT INTO nmc_categories(\"category\") SELECT '"+album.getGenre()+"' WHERE NOT EXISTS (SELECT id FROM nmc_categories WHERE category = '"+album.getGenre()+"');";
		String query6 = "INSERT INTO nmc_albums_categories VALUES ((SELECT id FROM nmc_media ORDER BY id DESC LIMIT 1),(SELECT id FROM nmc_categories WHERE category = '"+album.getGenre()+"'));";

		db.modify(query1);
		db.modify(query2);
		db.modify(query3);
		db.modify(query4);
		db.modify(query5);
		db.modify(query6);

		db.closeConnection();		
	}

	public void injector(BookCollector book) throws SQLException{
		book.setAbsPath(Config.getInstance().getProp("root_dir")+Parser.getInstance().getSlash()+"Books"+Parser.getInstance().getSlash()+(Retriever.getInstance().selectLastEntry()+1)+book.getFilename());
		db.openConnection();
		String query1 = "INSERT INTO nmc_additions values (DEFAULT, NOW(), '"+book.getAdder()+"');";
		String query2;
		String query4 = null;
		String query5 = null;
		String query6 = null;
		String query7 = null;
		if(book.getSynopsis().isEmpty())
			query2 = "INSERT INTO nmc_media values (DEFAULT, '"+book.getTitle()+"', '"+book.getAbsPath()+"', 'book', null, '"+Integer.parseInt(book.getYear())+"', 0, (SELECT id FROM nmc_permissions WHERE level = '"+book.getVisibilityID()+"'), (SELECT id FROM nmc_permissions WHERE level = '"+book.getModificiationID()+"'), (SELECT id FROM nmc_additions ORDER BY id DESC LIMIT 1));";
		else
			query2 = "INSERT INTO nmc_media values (DEFAULT, '"+book.getTitle()+"', '"+book.getAbsPath()+"', 'book', '"+book.getSynopsis()+"', '"+Integer.parseInt(book.getYear())+"', 0, (SELECT id FROM nmc_permissions WHERE level = '"+book.getVisibilityID()+"'), (SELECT id FROM nmc_permissions WHERE level = '"+book.getModificiationID()+"'), (SELECT id FROM nmc_additions ORDER BY id DESC LIMIT 1));";
		if(!book.getAuthor().isEmpty()){
			query4 =	"INSERT INTO nmc_persons(\"name\") SELECT '"+book.getAuthor()+"' WHERE NOT EXISTS (SELECT id FROM nmc_persons WHERE name = '"+book.getAuthor()+"');";
			query5 = "INSERT INTO nmc_media_authors VALUES ((SELECT id FROM nmc_media ORDER BY id DESC LIMIT 1),(SELECT id FROM nmc_persons WHERE name = '"+book.getAuthor()+"'));";
		}
		if(!book.getGenre().isEmpty()){
			query6 = "INSERT INTO nmc_categories(\"category\") SELECT '"+book.getGenre()+"' WHERE NOT EXISTS (SELECT id FROM nmc_categories WHERE category = '"+book.getGenre()+"');";
			query7 = "INSERT INTO nmc_books_categories VALUES ((SELECT id FROM nmc_media ORDER BY id DESC LIMIT 1),(SELECT id FROM nmc_categories WHERE category = '"+book.getGenre()+"'));";
		}
		db.modify(query1);
		db.modify(query2);
		if(!book.getAuthor().isEmpty()){
			db.modify(query4);
			db.modify(query5);
		}
		if(!book.getGenre().isEmpty()){
			db.modify(query6);
			db.modify(query7);
		}
		db.closeConnection();		
	}

	public void injector(ImageCollector image) throws SQLException{
		image.setAbsPath(Config.getInstance().getProp("root_dir")+Parser.getInstance().getSlash()+"Images"+Parser.getInstance().getSlash()+(Retriever.getInstance().selectLastEntry()+1)+image.getFilename());
		db.openConnection();
		String query1 = "INSERT INTO nmc_additions values (DEFAULT, NOW(), '"+image.getAdder()+"');"; 
		String query2 = null;
		if (!image.getYear().isEmpty())
			query2 = "INSERT INTO nmc_media values (DEFAULT, '"+image.getTitle()+"', '"+image.getAbsPath()+"', 'image', null, '"+Integer.parseInt(image.getYear())+"', 0, (SELECT id FROM nmc_permissions WHERE level = '"+image.getVisibilityID()+"'), (SELECT id FROM nmc_permissions WHERE level = '"+image.getModificiationID()+"'), (SELECT id FROM nmc_additions ORDER BY id DESC LIMIT 1));";
		else
			query2 = "INSERT INTO nmc_media(id, title, path, type, visibility, modification, addition_id) values (DEFAULT, '"+image.getTitle()+"', '"+image.getAbsPath()+"', 'image', (SELECT id FROM nmc_permissions WHERE level = '"+image.getVisibilityID()+"'), (SELECT id FROM nmc_permissions WHERE level = '"+image.getModificiationID()+"'), (SELECT id FROM nmc_additions ORDER BY id DESC LIMIT 1));";
		String query3 = null;
		String query4 = null;
		if (!image.getPhotographer().isEmpty()){
			query3 =	"INSERT INTO nmc_persons(\"name\") SELECT '"+image.getPhotographer()+"' WHERE NOT EXISTS (SELECT id FROM nmc_persons WHERE name = '"+image.getPhotographer()+"');";
			query4 = "INSERT INTO nmc_media_photographers VALUES ((SELECT id FROM nmc_media ORDER BY id DESC LIMIT 1),(SELECT id FROM nmc_persons WHERE name = '"+image.getPhotographer()+"'));";
		}

		db.modify(query1);
		db.modify(query2);
		if (!image.getPhotographer().isEmpty()){
			db.modify(query3);
			db.modify(query4);
		}

		db.closeConnection();		
	}

	public void injector(VideoCollector video) throws SQLException{
		video.setAbsPath(Config.getInstance().getProp("root_dir")+Parser.getInstance().getSlash()+"Movies"+Parser.getInstance().getSlash());
		String temp = (Retriever.getInstance().selectLastEntry()+1)+video.getFilename();
		db.openConnection();
		String query1 = "INSERT INTO nmc_additions VALUES (DEFAULT, NOW(), '"+video.getAdder()+"');";
		String query2;
		String query6 = null;
		String query7 = null;
		String query8 = null;
		String query9 = null;
		if(video.getSynopsis().isEmpty())
			query2 = "INSERT INTO nmc_media VALUES (DEFAULT, '"+video.getTitle()+"', '"+video.getAbsPath()+"', 'video', null, '"+Integer.parseInt(video.getYear())+"', 0, (SELECT id FROM nmc_permissions WHERE level = '"+video.getVisibilityID()+"'), (SELECT id FROM nmc_permissions WHERE level = '"+video.getModificiationID()+"'), (SELECT id FROM nmc_additions ORDER BY id DESC LIMIT 1));";
		else
			query2 = "INSERT INTO nmc_media VALUES (DEFAULT, '"+video.getTitle()+"', '"+video.getAbsPath()+"', 'video', '"+video.getSynopsis()+"', '"+Integer.parseInt(video.getYear())+"', 0, (SELECT id FROM nmc_permissions WHERE level = '"+video.getVisibilityID()+"'), (SELECT id FROM nmc_permissions WHERE level = '"+video.getModificiationID()+"'), (SELECT id FROM nmc_additions ORDER BY id DESC LIMIT 1));";
		String query4 = "INSERT INTO nmc_videos(id, filename) VALUES (DEFAULT, '"+temp+"');";
		String query5 = "INSERT INTO nmc_media_films VALUES ((SELECT id FROM nmc_media ORDER BY id DESC LIMIT 1), (SELECT id FROM nmc_videos ORDER BY id DESC LIMIT 1));";
		if (!video.getGenre().isEmpty()){
			query6 = "INSERT INTO nmc_categories(\"category\") SELECT '"+video.getGenre()+"' WHERE NOT EXISTS (SELECT id FROM nmc_categories WHERE category = '"+video.getGenre()+"');";
			query7 = "INSERT INTO nmc_films_categories VALUES ((SELECT id FROM nmc_media ORDER BY id DESC LIMIT 1),(SELECT id FROM nmc_categories WHERE category = '"+video.getGenre()+"'));";
		}
		if (!video.getDirector().isEmpty()){
			query8 = "INSERT INTO nmc_persons(\"name\") SELECT '"+video.getDirector()+"' WHERE NOT EXISTS (SELECT id FROM nmc_persons WHERE name = '"+video.getDirector()+"');";
			query9 = "INSERT INTO nmc_videos_directors VALUES ((SELECT id FROM nmc_videos ORDER BY id DESC LIMIT 1),(SELECT id FROM nmc_persons WHERE name = '"+video.getDirector()+"'));";
		}
		String query10 = "INSERT INTO nmc_users_watched_videos VALUES ('"+Profil.getInstance().getId()+"',(SELECT id FROM nmc_videos ORDER BY id DESC LIMIT 1), 'false');";

		db.modify(query1);
		db.modify(query2);
		db.modify(query4);
		db.modify(query5);
		if (!video.getGenre().isEmpty()){
			db.modify(query6);
			db.modify(query7);
		}
		if(!video.getDirector().isEmpty()){
			db.modify(query8);
			db.modify(query9);
		}
		db.modify(query10);

		db.closeConnection();
	}

	public void injector(SeriesCollector series) throws SQLException{
		series.setAbsPath(Config.getInstance().getProp("root_dir")+Parser.getInstance().getSlash()+"Series"+Parser.getInstance().getSlash()+(Retriever.getInstance().selectLastEntry()+1)+series.getTitle()+Parser.getInstance().getSlash());
		db.openConnection();
		String query1 = "INSERT INTO nmc_additions VALUES (DEFAULT, NOW(), '"+series.getAdder()+"');";
		String query2;
		if(series.getSynopsis().isEmpty())
			query2 = "INSERT INTO nmc_media VALUES (DEFAULT, '"+series.getTitle()+"', '"+series.getAbsPath()+"', 'series', null, '"+Integer.parseInt(series.getYear())+"', 0, (SELECT id FROM nmc_permissions WHERE level = '"+series.getVisibilityID()+"'), (SELECT id FROM nmc_permissions WHERE level = '"+series.getModificiationID()+"'), (SELECT id FROM nmc_additions ORDER BY id DESC LIMIT 1));";
		else
			query2 = "INSERT INTO nmc_media VALUES (DEFAULT, '"+series.getTitle()+"', '"+series.getAbsPath()+"', 'series', '"+series.getSynopsis()+"', '"+Integer.parseInt(series.getYear())+"', 0, (SELECT id FROM nmc_permissions WHERE level = '"+series.getVisibilityID()+"'), (SELECT id FROM nmc_permissions WHERE level = '"+series.getModificiationID()+"'), (SELECT id FROM nmc_additions ORDER BY id DESC LIMIT 1));";
		String query4 = "INSERT INTO nmc_categories(\"category\") SELECT '"+series.getGenre()+"' WHERE NOT EXISTS (SELECT id FROM nmc_categories WHERE category = '"+series.getGenre()+"');";
		String query5 = "INSERT INTO nmc_series_categories VALUES ((SELECT id FROM nmc_media ORDER BY id DESC LIMIT 1),(SELECT id FROM nmc_categories WHERE category = '"+series.getGenre()+"'));";

		db.modify(query1);
		db.modify(query2);
		db.modify(query4);
		db.modify(query5);

		db.closeConnection();		
	}

	public void injector(EpisodeCollector episode) throws SQLException{
		db.openConnection();
		String query1 = "INSERT INTO nmc_additions VALUES (DEFAULT, NOW(), '"+episode.getAdder()+"');";
		String query2 = "INSERT INTO nmc_videos(id, filename) VALUES (DEFAULT, '"+episode.getFilename()+"');";
		String query3 = "INSERT INTO nmc_users_watched_videos VALUES ('"+episode.getAdder()+"', (SELECT id FROM nmc_videos ORDER BY id DESC LIMIT 1),  'false');";
		String query4;
		if (episode.getSeason().isEmpty() && episode.getChrono().isEmpty())
			query4 = "INSERT INTO nmc_media_series(media_id, episodes_id, title, additions_id) VALUES ((SELECT id FROM nmc_media WHERE id = "+episode.getSeries()+"), (SELECT id FROM nmc_videos ORDER BY id DESC LIMIT 1),'"+episode.getTitle()+"', (SELECT id FROM nmc_additions ORDER BY id DESC LIMIT 1));";
		else if (episode.getSeason().isEmpty())
			query4 = "INSERT INTO nmc_media_series(media_id, episodes_id, title, chrono, additions_id) VALUES ((SELECT id FROM nmc_media WHERE id = '"+episode.getSeries()+"') , (SELECT id FROM nmc_videos ORDER BY id DESC LIMIT 1), '"+episode.getTitle()+"', '"+Integer.parseInt(episode.getChrono())+"', (SELECT id FROM nmc_additions ORDER BY id DESC LIMIT 1));";
		else if(episode.getChrono().isEmpty())
			query4 = "INSERT INTO nmc_media_series(media_id, episodes_id, title, season, additions_id) VALUES ((SELECT id FROM nmc_media WHERE id = '"+episode.getSeries()+"') , (SELECT id FROM nmc_videos ORDER BY id DESC LIMIT 1), '"+episode.getTitle()+"', '"+Integer.parseInt(episode.getSeason())+"', (SELECT id FROM nmc_additions ORDER BY id DESC LIMIT 1));";
		else
			query4 = "INSERT INTO nmc_media_series(media_id, episodes_id, title, season, chrono, additions_id) VALUES ((SELECT id FROM nmc_media WHERE id = '"+episode.getSeries()+"') , (SELECT id FROM nmc_videos ORDER BY id DESC LIMIT 1), '"+episode.getTitle()+"', '"+Integer.parseInt(episode.getSeason())+"', '"+Integer.parseInt(episode.getChrono())+"', (SELECT id FROM nmc_additions ORDER BY id DESC LIMIT 1));";
		String query5 = null;
		String query6 = null;
		if (!episode.getDirector().isEmpty()){
			query5 = "INSERT INTO nmc_persons(\"name\") SELECT '"+episode.getDirector()+"' WHERE NOT EXISTS (SELECT id FROM nmc_persons WHERE name = '"+episode.getDirector()+"');";
			query6 = "INSERT INTO nmc_videos_directors VALUES ((SELECT id FROM nmc_videos ORDER BY id DESC LIMIT 1),(SELECT id FROM nmc_persons WHERE name = '"+episode.getDirector()+"'));";
		}

		db.modify(query1);
		db.modify(query2);
		db.modify(query3);
		db.modify(query4);
		if (!episode.getDirector().isEmpty()){
			db.modify(query5);
			db.modify(query6);

		}

		db.closeConnection();	
	}
}
