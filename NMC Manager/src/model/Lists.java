/**
 * 
 */
package model;

import java.util.ArrayList;

/**
 * @author Derek
 *
 */
public class Lists {
	private static Lists instance = null;
	private ArrayList<AudioCollector> audioList;
	private ArrayList<AlbumCollector> albumList;
	private ArrayList<BookCollector> bookList;
	private ArrayList<ImageCollector> imageList;
	private ArrayList<EpisodeCollector> episodeList;
	private ArrayList<SeriesCollector> seriesList;
	private ArrayList<VideoCollector> videoList;
	private ArrayList<Profil> usersList;
	private ArrayList<Permissions> permissionsList;

	protected Lists(){
		// Fake constructor for singleton
	}
	
	/**
	 * Permet de créer/récupérer l'instance Lists
	 * @return l'objet instancié du Lists
	 */
	public static Lists getInstance(){
		if(instance == null) instance = new Lists();
		return instance;
	}
	/**
	 * Permet de créer une instance statique profil à partir de celle passée en paramètre
	 * @param pf : Profil distant à copier localement
	 */
	public static void setInstance(Lists lists){
		instance = lists;
	}

	/**
	 * @param albumList
	 * @param episodeList
	 * @param seriesList
	 * @param usersList
	 * @param permissionsList
	 */
	public Lists(
				ArrayList<Profil> usersList,
				ArrayList<Permissions> permissionsList,
				ArrayList<AlbumCollector> albumList,
				ArrayList<SeriesCollector> seriesList) {
		this.audioList = null;
		this.albumList = albumList;
		this.bookList = null;
		this.imageList = null;
		this.episodeList = null;
		this.seriesList = seriesList;
		this.videoList = null;
		this.usersList = usersList;
		this.permissionsList = permissionsList;
	}
	
	public ArrayList<AudioCollector> getAudioList() {
		return audioList;
	}
	public void setAudioList(ArrayList<AudioCollector> audioList) {
		this.audioList = audioList;
	}
	public ArrayList<AlbumCollector> getAlbumList() {
		return albumList;
	}
	public void setAlbumList(ArrayList<AlbumCollector> albumList) {
		this.albumList = albumList;
	}
	public ArrayList<BookCollector> getBookList() {
		return bookList;
	}
	public void setBookList(ArrayList<BookCollector> bookList) {
		this.bookList = bookList;
	}
	public ArrayList<ImageCollector> getImageList() {
		return imageList;
	}
	public void setImageList(ArrayList<ImageCollector> imageList) {
		this.imageList = imageList;
	}
	public ArrayList<EpisodeCollector> getEpisodeList() {
		return episodeList;
	}
	public void setEpisodeList(ArrayList<EpisodeCollector> episodeList) {
		this.episodeList = episodeList;
	}
	public ArrayList<SeriesCollector> getSeriesList() {
		return seriesList;
	}
	public void setSeriesList(ArrayList<SeriesCollector> seriesList) {
		this.seriesList = seriesList;
	}
	public ArrayList<VideoCollector> getVideoList() {
		return videoList;
	}
	public void setVideoList(ArrayList<VideoCollector> videoList) {
		this.videoList = videoList;
	}
	public ArrayList<Profil> getUsersList() {
		return usersList;
	}
	public void setUsersList(ArrayList<Profil> usersList) {
		this.usersList = usersList;
	}
	public ArrayList<Permissions> getPermissionsList() {
		return permissionsList;
	}
	public void setPermissionsList(ArrayList<Permissions> permissionsList) {
		this.permissionsList = permissionsList;
	}
	public String returnLabel(int id){
		for (Permissions perms : this.permissionsList) {
			if (perms.getId() == id)
				return perms.getLabel();
		}
		return "Unknown";
	}
}
