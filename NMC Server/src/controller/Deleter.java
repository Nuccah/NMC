package controller;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.ConnectorDB;

/**
 * Classe de suppression définitive de fichiers sur le serveur
 * @author Antoine Ceyssens
 *
 */
public class Deleter {
	private static Deleter instance = null;
	private ConnectorDB db;

	protected Deleter(){
		this.db = ConnectorDB.getInstance();
	}

	public static Deleter getInstance(){
		if(instance == null){
			instance = new Deleter();
		}
		return instance;
	}
	/**
	 * Supprime les fichiers contenus dans l'arraylist listToDelete
	 * @param rs : Le resultset de la requête 
	 * @param type : Type d'élément à supprimer (Dossier ou Fichier)
	 * @return Vrai si les fichiers ont bien été supprimé
	 */
	private boolean deleteFile(ResultSet rs, String type){
		String path = null;
		try {
			if (!rs.next()){
				return false;
			}
		} catch (SQLException e) {
			System.out.println("[ERROR] - NO SQL RESULT");
			if(Main.getDebug()) e.printStackTrace();
			return false;
		}
		try {
			switch (type){
			case "pathfilename": 
				path = rs.getString("path");
				path.concat(rs.getString("filename"));
				break;
			case "path": 
				path = rs.getString("path");
				break;
			}
		} catch (SQLException e) {
			System.out.println("[ERROR] - Could not retrieve path or filename");
			if(Main.getDebug()) e.printStackTrace();
			return false;
		}
		try {
			if (!delete(path)){
				System.out.println(path);
				return false;
			}
				
		} catch (IOException e) {
			System.out.println("[ERROR] - Error when attempting to delete file/folder");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * Supprime le fichier physiquement du serveur
	 * @param path : Chemin du fichier à supprimer
	 * @return Vrai si le fichier a bien été supprimé
	 * @throws IOException
	 */
	public boolean delete(String path) throws IOException {
		File f = new File(path);
		if (!f.exists()) {
			System.out.println("3");
			return false;
		}

		if (!f.isDirectory()){
			f.delete();
			return true;
		}
		
		String[] list = f.list();
		for (int i = 0; i < list.length; i++) {
			System.out.println(f.getAbsolutePath() + File.separator + list[i]);
			delete(f.getAbsolutePath() + File.separator + list[i]);
		}
		f.delete();
		return true;
	}
	
	/**
	 * Supprime les métadonnées d'un album
	 * @param id : Identifiant de l'albume à supprimer
	 * @return Vrai si l'album a bien été supprimé
	 * @throws SQLException
	 */
	public boolean deleteAlbum(int id) throws SQLException{
		db.openConnection();

		String query0 = "SELECT path FROM nmc_media WHERE id = "+id+";";
		String query1 = "DELETE FROM nmc_tracks WHERE id = (SELECT tracks_id FROM nmc_tracks_albums WHERE media_id = "+id+");";
		String query2 = "DELETE FROM nmc_media WHERE id = "+id+";";

		if (deleteFile(db.select(query0), "path")){
			db.modify(query1);
			db.modify(query2);
		}
		else{
			db.closeConnection();
			return false;
		}
		db.closeConnection();
		return true;
	}
	
	/**
	 * Supprime les métadonnées d'une musique
	 * @param id : Identifiant de la musique à supprimer
	 * @return Vrai si la musique a bien été supprimée
	 * @throws SQLException
	 */
	public boolean deleteAudio(int id) throws SQLException{
		db.openConnection();

		String query0 = "SELECT path, filename FROM nmc_tracks as nt"
				+ " INNER JOIN nmc_tracks_albums as nta ON nt.id = nta.tracks_id"
				+ " INNER JOIN nmc_media as nm ON nta.media_id = nm.id"
				+ " WHERE nt.id = "+id+";";
		String query1 = "DELETE from nmc_tracks WHERE nt.id = "+id+";";

		if (deleteFile(db.select(query0), "pathfilename")){
			db.modify(query1);
		}
		else{
			db.closeConnection();
			return false;
		}
		db.closeConnection();
		return true;
	}

	/**
	 * Supprime les métadonnées d'un livre
	 * @param id : Identifiant du livre à supprimer
	 * @return Vrai si le livre a bien été supprimé
	 * @throws SQLException
	 */
	public boolean deleteBook(int id) throws SQLException{
		db.openConnection();

		String query0 = "SELECT path FROM nmc_media WHERE id = "+id+";";
		String query1 = "DELETE FROM nmc_media WHERE id = "+id+";";

		if (deleteFile(db.select(query0), "path")){
			db.modify(query1);
		}
		else{
			db.closeConnection();
			return false;
		}
		db.modify(query1);
		db.closeConnection();
		return true;
	}

	/**
	 * Supprime les métadonnées d'un épisode
	 * @param id : Identifiant de l'albume à supprimer
	 * @return Vrai si l'album a bien été supprimé
	 * @throws SQLException
	 */
	public boolean deleteEpisode(int id) throws SQLException{
		db.openConnection();

		String query0 = "SELECT path, filename FROM nmc_videos as nv"
				+ " INNER JOIN nmc_media_series as nms ON nv.id = nms.episodes_id"
				+ " INNER JOIN nmc_media as nm ON nms.media_id = nm.id"
				+ " WHERE nv.id = "+id+";";
		String query1 = "DELETE FROM nmc_videos WHERE id = "+id+";"; 

		if (deleteFile(db.select(query0), "pathfilename")){
			db.modify(query1);
		}
		else{
			db.closeConnection();
			return false;
		}
		db.modify(query1);
		db.closeConnection();
		return true;
	}
	/**
	 * Supprime les métadonnées d'une image
	 * @param id : Identifiant de l'image à supprimer
	 * @return Vrai si l'image a bien été supprimée
	 * @throws SQLException
	 */
	public boolean deleteImage(int id) throws SQLException{
		db.openConnection();

		String query0 = "SELECT path FROM nmc_media WHERE id = "+id+";";
		String query1 = "DELETE FROM nmc_media WHERE id = "+id+";"; 

		if (deleteFile(db.select(query0), "path")){
			db.modify(query1);
		}
		else{
			db.closeConnection();
			return false;
		}
		db.modify(query1);
		db.closeConnection();
		return true;
	}
	/**
	 * Supprime les métadonnées d'une série
	 * @param id : Identifiant de la série à supprimer
	 * @return Vrai si la série a bien été supprimée
	 * @throws SQLException
	 */
	public boolean deleteSeries(int id) throws SQLException{
		db.openConnection();
		String query0 = "SELECT path FROM nmc_media WHERE id = "+id+";";
		String query1 = "DELETE FROM nmc_videos WHERE id = (SELECT episodes_id FROM nmc_media_series WHERE media_id = "+id+");";
		String query2 = "DELETE FROM nmc_media WHERE id = "+id+";"; 

		if (deleteFile(db.select(query0), "path")){
			db.modify(query1);
			db.modify(query2);
		}
		else{
			System.out.println("yolo shit");
			db.closeConnection();
			return false;
		}
		db.closeConnection();
		return true;
	}
	/**
	 * Supprime les métadonnées d'une vidéo
	 * @param id : Identifiant de la vidéo à supprimer
	 * @return Vrai si la vidéo a bien été supprimée
	 * @throws SQLException
	 */
	public boolean deleteVideo(int id) throws SQLException{
		db.openConnection();
		String query0 = "SELECT path, filename FROM nmc_media as nm"
				+ " INNER JOIN nmc_media_films as nmf ON nm.id = nmf.media_id"
				+ " INNER JOIN nmc_videos as nv ON nmf.videos_id = nv.id"
				+ " WHERE nm.id = "+id+";";
		String query1 = "DELETE FROM nmc_videos WHERE id = (SELECT videos_id FROM nmc_media_films WHERE media_id = "+id+");";
		String query2 =	"DELETE FROM nmc_media WHERE id = "+id+";";  	
		
		if (deleteFile(db.select(query0), "path")){
			db.modify(query1);
			db.modify(query2);
		}
		else{
			db.closeConnection();
			return false;
		}
		db.closeConnection();
		return true;
	}
	/**
	 * Supprime les métadonnées d'un utilisateur
	 * @param id : Identifiant de l'utilisateur à supprimer
	 * @return Vrai si l'utilisateur a bien été supprimé
	 * @throws SQLException
	 */
	public boolean deleteUser(int id) throws SQLException{
		db.openConnection();

		String query1 = "DELETE FROM nmc_users WHERE id = "+id+";";		

		db.modify(query1);
		db.closeConnection();
		return true;
	}
}
