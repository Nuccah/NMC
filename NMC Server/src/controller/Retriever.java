package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.AlbumCollector;
import model.AudioCollector;
import model.BookCollector;
import model.ConnectorDB;
import model.EpisodeCollector;
import model.ImageCollector;
import model.Permissions;
import model.Profil;
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
	private ArrayList<AudioCollector> audioList;
	private ArrayList<AlbumCollector> albumList;
	private ArrayList<BookCollector> bookList;
	private ArrayList<ImageCollector> imageList;
	private ArrayList<EpisodeCollector> episodeList;
	private ArrayList<SeriesCollector> seriesList;
	private ArrayList<VideoCollector> videoList;
	private ArrayList<Profil> usersList;
	private ArrayList<Permissions> permissionsList;
	
	protected Retriever(){
		this.db = ConnectorDB.getInstance();
	}
	
	public static Retriever getInstance(){
		if(instance == null){
			instance = new Retriever();
		}
		return instance;
	}
	
	public ArrayList<AudioCollector>  selectAudioList(String queryAdd) throws SQLException{
		db.openConnection();
		audioList = new ArrayList<AudioCollector>();
		//TODO: [Derek] SQL Select Audio
		String query = "SELECT nt.id as id, nt.title as title, filename, name, nmi.id as album, nmi.title as albumName FROM nmc_media as nmi "
				+ "INNER JOIN nmc_tracks_albums as nta ON nmi.id = nta.media_id "
				+ "INNER JOIN nmc_tracks as nt ON nta.tracks_id = nt.id "
				+ "INNER JOIN nmc_tracks_bands as ntb ON nt.id = ntb.tracks_id "
				+ "INNER JOIN nmc_bands as nb ON ntb.bands_id = nb.id ;";
		if (queryAdd != null)
			query.concat(queryAdd);
		query.concat(";");
		ResultSet rs = db.select(query);
		while(rs.next()){
			audioList.add(new AudioCollector(rs.getInt("id"), rs.getString("title"), rs.getString("filename"), rs.getString("name"), rs.getInt("album"), rs.getString("albumName")));
		}
		db.closeConnection();
		return audioList;
	}
	
	public ArrayList<AlbumCollector>  selectAlbumList(String queryAdd) throws SQLException{
		db.openConnection();
		albumList = new ArrayList<AlbumCollector>();
		//TODO: [Derek] SQL Select Album
		String query = "SELECT nmi.id as id, title, release_date, modification, visibility, name, nmi.description as description, category FROM nmc_media as nmi "
				+ "INNER JOIN nmc_albums_categories as nac ON nmi.id = nac.media_id "
				+ "INNER JOIN nmc_categories as nc ON nac.categories_id = nc.id "
				+ "INNER JOIN nmc_media_bands as nmib ON nmi.id = nmib.media_id "
				+ "INNER JOIN nmc_bands as nb ON nmib.bands_id = nb.id ;";
		if (queryAdd != null)
			query.concat(queryAdd);
		query.concat(";");
		ResultSet rs = db.select(query);
		while(rs.next()){
			albumList.add(new AlbumCollector(rs.getInt("id"), rs.getString("title"), String.valueOf(rs.getInt("release_date")), rs.getInt("modification"), rs.getInt("visibility"), rs.getString("name"), rs.getString("description"), rs.getString("category")));
		}
		db.closeConnection();
		return albumList;
	}
	
	public ArrayList<BookCollector>  selectBookList(String queryAdd) throws SQLException{
		db.openConnection();
		bookList = new ArrayList<BookCollector>();
		//TODO: [Derek] SQL Select Book
		String query = "SELECT nmi.id as id, title, release_date, modification, visibility, path, name, category, description FROM nmc_media as nmi "
				+ "INNER JOIN nmc_media_authors as nmia ON nmi.id = nmia.media_id "
				+ "INNER JOIN nmc_persons as np ON nmia.persons_id = np.id "
				+ "INNER JOIN nmc_books_categories as nbc ON nmi.id = nbc.media_id "
				+ "INNER JOIN nmc_categories as nc ON nbc.categories_id = nc.id;";
		if (queryAdd != null)
			query.concat(queryAdd);
		query.concat(";");
		ResultSet rs = db.select(query);
		while(rs.next()){
			bookList.add(new BookCollector(rs.getInt("id"), rs.getString("title"), String.valueOf(rs.getInt("release_date")), rs.getInt("modification"), rs.getInt("visibility"), rs.getString("path"), rs.getString("name"), rs.getString("category"), rs.getString("description")));
		}
		db.closeConnection();
		return bookList;
	}
	
	public ArrayList<EpisodeCollector>  selectEpisodeList(String queryAdd) throws SQLException{
		db.openConnection();
		episodeList = new ArrayList<EpisodeCollector>();
		//TODO: [Derek] SQL Select Episode
		String query = "SELECT nmis.id as id, nmis.title as title, filename, nmi.id as series, nmi.title as seriesName, season, chrono FROM nmc_media as nmi "
				+ "INNER JOIN nmc_media_series as nmis ON nmi.id = nmis.media_id "
				+ "INNER JOIN nmc_videos as nv ON nmis.media_id = nv.id ;";
		if (queryAdd != null)
			query.concat(queryAdd);
		query.concat(";");
		ResultSet rs = db.select(query);
		while(rs.next()){
			episodeList.add(new EpisodeCollector(rs.getInt("id"), rs.getString("title"), rs.getString("filename"), rs.getInt("series"), rs.getString("seriesName"), rs.getString("name"), String.valueOf(rs.getInt("season")), String.valueOf(rs.getInt("chrono"))));
		}
		db.closeConnection();
		return episodeList;
	}
	
	public ArrayList<ImageCollector>  selectImageList(String queryAdd) throws SQLException{
		db.openConnection();
		imageList = new ArrayList<ImageCollector>();
		String query = "SELECT nmi.id as id, title, release_date, modification, visibility, path, name FROM nmc_media as nmi "
				+ "INNER JOIN nmc_media_photographers as nmp ON nmi.id = nmp.media_id "
				+ "INNER JOIN nmc_persons as np ON nmp.persons_id = np.id ;";
		if (queryAdd != null)
			query.concat(queryAdd);
		query.concat(";");
		ResultSet rs = db.select(query);
		while(rs.next()){
			imageList.add(new ImageCollector(rs.getInt("id"), rs.getString("title"), String.valueOf(rs.getInt("release_date")), rs.getInt("modification"), rs.getInt("visibility"), rs.getString("path"), rs.getString("name")));
		}
		db.closeConnection();
		return imageList;
	}
	
	public ArrayList<SeriesCollector>  selectSeriesList(String queryAdd) throws SQLException{
		db.openConnection();
		seriesList = new ArrayList<SeriesCollector>();
		String query = "SELECT nmi.id as id, title, release_date, modification, visibility, description, category FROM nmc_media as nmi "
				+ "INNER JOIN nmc_series_categories as nfc ON nmi.id = nfc.media_id "
				+ "INNER JOIN nmc_categories as nc ON nfc.categories_id = nc.id ;";
		if (queryAdd != null)
			query.concat(queryAdd);
		query.concat(";");
		ResultSet rs = db.select(query);
		while(rs.next()){
			seriesList.add(new SeriesCollector(rs.getInt("id"), rs.getString("title"), String.valueOf(rs.getInt("release_date")), rs.getInt("modification"), rs.getInt("visibility"), rs.getString("description"), rs.getString("category")));
		}
		db.closeConnection();
		return seriesList;
	}
	
	public ArrayList<VideoCollector> selectVideoList(String queryAdd) throws SQLException{
		db.openConnection();
		videoList = new ArrayList<VideoCollector>();
		String query = "SELECT nmi.id as id, title, release_date, modification, visibility, filename, name, category, description FROM nmc_media as nmi "
				+ "INNER JOIN nmc_media_films as nmif ON nmi.id = nmif.media_id "
				+ "INNER JOIN nmc_videos as nv ON nmif.videos_id = nv.id "
				+ "INNER JOIN nmc_videos_directors as nvd ON nv.id = nvd.videos_id "
				+ "INNER JOIN nmc_persons as np ON nvd.persons_id = np.id "
				+ "INNER JOIN nmc_films_categories as nfc ON nmi.id = nfc.media_id "
				+ "INNER JOIN nmc_categories as nc ON nfc.categories_id = nc.id ;";
		if (queryAdd != null)
			query.concat(queryAdd);
		query.concat(";");
		ResultSet rs = db.select(query);
		while(rs.next()){
			videoList.add(new VideoCollector(rs.getInt("id"), rs.getString("title"), String.valueOf(rs.getInt("release_date")), rs.getInt("modification"), rs.getInt("visibility"), rs.getString("filename"), rs.getString("name"), rs.getString("category"), rs.getString("description")));
		}
		db.closeConnection();
		return videoList;
	}
	
	public ArrayList<Profil> selectUsers(String queryAdd) throws SQLException{
		db.openConnection();
		usersList = new ArrayList<Profil>();
		//TODO: [Derek] SQL Select Users
		String query = "SELECT * FROM nmc_users ;";
		if (queryAdd != null)
			query.concat(queryAdd);
		query.concat(";");
		ResultSet rs = db.select(query);
		while(rs.next()){
			usersList.add(new Profil(rs.getInt("id"), rs.getString("login"), rs.getString("mail"), rs.getString("first_name"), rs.getString("last_name"), rs.getDate("birthdate"), rs.getTimestamp("reg_date"), rs.getInt("permissions_id")));
		}
		db.closeConnection();
		return usersList;
	}
	
	public ArrayList<Permissions> selectPermissions(String queryAdd) throws SQLException{
		db.openConnection();
		permissionsList = new ArrayList<Permissions>();
		//TODO: [Derek] SQL Select Permissions
		String query = "SELECT * FROM nmc_permissions ;";
		if (queryAdd != null)
			query.concat(queryAdd);
		query.concat(";");
		ResultSet rs = db.select(query);
		while(rs.next()){
			permissionsList.add(new Permissions(rs.getString("label"), rs.getInt("level")));
		}
		db.closeConnection();
		return permissionsList;
	}
}
