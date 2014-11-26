package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.ConnectorDB;

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
	
	public ResultSet selectAudioList(String queryAdd) throws SQLException{
		db.openConnection();
		String query = "SELECT nt.id as id, nt.title as title, filename, name, nmi.id as album, nmi.title as albumName FROM nmc_media as nmi "
				+ "INNER JOIN nmc_tracks_albums as nta ON nmi.id = nta.media_id "
				+ "INNER JOIN nmc_tracks as nt ON nta.tracks_id = nt.id "
				+ "INNER JOIN nmc_tracks_bands as ntb ON nt.id = ntb.tracks_id "
				+ "INNER JOIN nmc_bands as nb ON ntb.bands_id = nb.id ";
		if (queryAdd != null)
			query.concat(queryAdd);
		query.concat(" ORDER BY title DESC;");
		ResultSet rs = db.select(query);
		db.closeConnection();
		return (rs);		
	}
	
	public ResultSet selectAlbumList(String queryAdd) throws SQLException{
		db.openConnection();
		String query = "SELECT nmi.id as id, title, release_date, modification, visibility, name, nmi.description as description, category FROM nmc_media as nmi "
				+ "INNER JOIN nmc_albums_categories as nac ON nmi.id = nac.media_id "
				+ "INNER JOIN nmc_categories as nc ON nac.categories_id = nc.id "
				+ "INNER JOIN nmc_media_bands as nmib ON nmi.id = nmib.media_id "
				+ "INNER JOIN nmc_bands as nb ON nmib.bands_id = nb.id ";
		if (queryAdd != null)
			query.concat(queryAdd);
		query.concat(" ORDER BY title DESC;");
		ResultSet rs = db.select(query);
		db.closeConnection();
		return (rs);		
	}
	
	public ResultSet selectBookList(String queryAdd) throws SQLException{
		db.openConnection();
		String query = "SELECT nmi.id as id, title, release_date, modification, visibility, path, name, category, description FROM nmc_media as nmi "
				+ "INNER JOIN nmc_media_authors as nmia ON nmi.id = nmia.media_id "
				+ "INNER JOIN nmc_persons as np ON nmia.persons_id = np.id "
				+ "LEFT OUTER JOIN nmc_books_categories as nbc ON nmi.id = nbc.media_id "
				+ "LEFT OUTER JOIN nmc_categories as nc ON nbc.categories_id = nc.id ";
		if (queryAdd != null)
			query.concat(queryAdd);
		query.concat(" ORDER BY title DESC;");
		ResultSet rs = db.select(query);
		db.closeConnection();
		return (rs);		
	}
	
	public ResultSet selectEpisodeList(String queryAdd) throws SQLException{
		db.openConnection();
		String query = "SELECT nv.id as id, nmis.title as title, filename, nmi.id as series, nmi.title as seriesName, season, chrono FROM nmc_media as nmi "
				+ "INNER JOIN nmc_media_series as nmis ON nmi.id = nmis.media_id "
				+ "INNER JOIN nmc_videos as nv ON nmis.episodes_id = nv.id ";
		if (queryAdd != null)
			query.concat(queryAdd);
		query.concat(" ORDER BY title DESC;");
		ResultSet rs = db.select(query);
		db.closeConnection();
		return (rs);		
	}
	
	public ResultSet selectImageList(String queryAdd) throws SQLException{
		db.openConnection();
		String query = "SELECT nmi.id as id, title, release_date, modification, visibility, path, name FROM nmc_media as nmi "
				+ "LEFT OUTER JOIN nmc_media_photographers as nmp ON nmi.id = nmp.media_id "
				+ "LEFT OUTER JOIN nmc_persons as np ON nmp.persons_id = np.id "
				+ "WHERE type = 'image' ";
		if (queryAdd != null)
			query.concat(queryAdd);
		query.concat(" ORDER BY title DESC;");
		ResultSet rs = db.select(query);
		db.closeConnection();
		return (rs);		
	}
	
	public ResultSet selectSeriesList(String queryAdd) throws SQLException{
		db.openConnection();
		String query = "SELECT nmi.id as id, title, release_date, modification, visibility, description, category FROM nmc_media as nmi "
				+ "INNER JOIN nmc_series_categories as nfc ON nmi.id = nfc.media_id "
				+ "INNER JOIN nmc_categories as nc ON nfc.categories_id = nc.id ";
		if (queryAdd != null)
			query.concat(queryAdd);
		query.concat(" ORDER BY title, year DESC;");
		ResultSet rs = db.select(query);
		db.closeConnection();
		return (rs);		

	}
	
	public ResultSet selectVideoList(String queryAdd) throws SQLException{
		db.openConnection();
		String query = "SELECT nmi.id as id, title, release_date, modification, visibility, filename, name, category, description FROM nmc_media as nmi "
				+ "INNER JOIN nmc_media_films as nmif ON nmi.id = nmif.media_id "
				+ "INNER JOIN nmc_videos as nv ON nmif.videos_id = nv.id "
				+ "LEFT OUTER JOIN nmc_videos_directors as nvd ON nv.id = nvd.videos_id "
				+ "LEFT OUTER JOIN nmc_persons as np ON nvd.persons_id = np.id "
				+ "LEFT OUTER JOIN nmc_films_categories as nfc ON nmi.id = nfc.media_id "
				+ "LEFT OUTER JOIN nmc_categories as nc ON nfc.categories_id = nc.id ";
		if (queryAdd != null)
			query.concat(queryAdd);
		query.concat(" ORDER BY title, year DESC;");
		ResultSet rs = db.select(query);
		db.closeConnection();
		return (rs);		
	}
	
	public ResultSet selectUsers(String queryAdd) throws SQLException{
		db.openConnection();
		String query = "SELECT * FROM nmc_users ";
		if (queryAdd != null)
			query.concat(queryAdd);
		query.concat(";");
		ResultSet rs = db.select(query);
		db.closeConnection();
		return (rs);
	}
	
	public ResultSet selectPermissions(String queryAdd) throws SQLException{
		db.openConnection();
		String query = "SELECT * FROM nmc_permissions ";
		if (queryAdd != null)
			query.concat(queryAdd);
		query.concat(";");
		ResultSet rs = db.select(query);
		db.closeConnection();
		return (rs);		
	}

	public int selectLastEntry() throws SQLException{
		db.openConnection();
		String query = "SELECT id FROM nmc_media ORDER BY id DESC LIMIT 1;";
		ResultSet rs = db.select(query);
		db.closeConnection();
		if(!rs.next())
			return 0;
		else
			return rs.getInt("id");
	}
}
