package controller;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import model.ConnectorDB;

/**
 * Classe de suppression définitive de fichiers sur le serveur
 * @author Antoine Ceyssens
 *
 */
public class Deleter {
	private static Deleter instance = null;
	private ArrayList<File> listFileToDelete;
	private ConnectorDB db;
	
	protected Deleter(){
		listFileToDelete = new ArrayList<File>();
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
	 */
	public void deleteFile(){
		while(!listFileToDelete.isEmpty()){
			listFileToDelete.get(listFileToDelete.size() - 1).delete();
			listFileToDelete.remove(listFileToDelete.size() - 1);
		}
	}
	
	/**
	 * Permet de récupérer la liste des fichiers à supprimer
	 * @return L'ArrayList des fichiers à supprimer
	 */
	public ArrayList<File> getFilesToConvert(){
		return listFileToDelete;
	}
	
	public void deleteAlbum(int id) throws SQLException{
		db.openConnection();
		
		String query1 = "DELETE FROM nmc_tracks WHERE id = (SELECT tracks_id FROM nmc_tracks_albums WHERE media_id = "+id+");";
		String query2 = "DELETE FROM nmc_media WHERE id = "+id+";";
		db.modify(query1);
		db.modify(query2);
		db.closeConnection();
	}
	
	public void deleteAudio(int id) throws SQLException{
		db.openConnection();
		
		String query1 = "DELETE from nmc_tracks WHERE id = "+id+";";
		db.modify(query1);
		db.closeConnection();
	}
	
	public void deleteBook(int id) throws SQLException{
		db.openConnection();
		
		String query1 = "DELETE FROM nmc_media WHERE id = "+id+";";
		db.modify(query1);
		db.closeConnection();
	}
	
	public void deleteEpisode(int id) throws SQLException{
		db.openConnection();
		
		String query1 = "DELETE FROM nmc_videos WHERE id = "+id+";"; 		
		db.modify(query1);
		db.closeConnection();
	}
	
	public void deleteImage(int id) throws SQLException{
		db.openConnection();
		
		String query1 = "DELETE FROM nmc_media WHERE id = "+id+";"; 		
		db.modify(query1);
		db.closeConnection();
	}
	
	public void deleteSeries(int id) throws SQLException{
		db.openConnection();
		String query1 = "DELETE FROM nmc_videos WHERE id = (SELECT episodes_id FROM nmc_media_series WHERE media_id = "+id+");";
		String query2 = "DELETE FROM nmc_media WHERE id = "+id+";"; 		
		db.modify(query1);
		db.modify(query2);
		db.closeConnection();
	}
	
	public void deleteVideo(int id) throws SQLException{
		db.openConnection();
		String query1 = "DELETE FROM nmc_videos WHERE id = (SELECT videos_id FROM nmc_media_films WHERE media_id = "+id+");";
		String query2 =	"DELETE FROM nmc_media WHERE id = "+id+";";  		
		db.modify(query1);
		db.modify(query2);
		db.closeConnection();
	}
	
	public void deleteUser(int id) throws SQLException{
		db.openConnection();
		
		String query1 = "DELETE FROM nmc_users WHERE id = "+id+";";		
		db.modify(query1);
		db.closeConnection();
	}
}
