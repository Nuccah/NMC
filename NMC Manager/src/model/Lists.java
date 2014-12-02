/**
 * 
 */
package model;

import java.util.ArrayList;

/** Classe contenant les differents listes de media necessaires ainsi que les utilisateurs
 * et les permissions
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
	
	/** Permet de récouperer la liste des audios
	 * @return la liste des audios
	 */
	public ArrayList<AudioCollector> getAudioList() {
		return audioList;
	}
	/** Permet de modifier la liste des audios
	 * @param audioList la liste des audios a modifier
	 */
	public void setAudioList(ArrayList<AudioCollector> audioList) {
		this.audioList = audioList;
	}
	/** Permet de récouperer la liste des albums
	 * @return la liste des albums
	 */
	public ArrayList<AlbumCollector> getAlbumList() {
		return albumList;
	}
	/** Permet de modifier la liste des albums
	 * @param albumList la liste des albums a modifier
	 */
	public void setAlbumList(ArrayList<AlbumCollector> albumList) {
		this.albumList = albumList;
	}
	/** Permet de récouperer la liste des livres
	 * @return la liste des livres
	 */
	public ArrayList<BookCollector> getBookList() {
		return bookList;
	}
	/** Permet de modifier la liste des livres
	 * @param bookList la liste des livres a modifier
	 */
	public void setBookList(ArrayList<BookCollector> bookList) {
		this.bookList = bookList;
	}
	/** Permet de récouperer la liste des images
	 * @return la liste des images
	 */
	public ArrayList<ImageCollector> getImageList() {
		return imageList;
	}
	/** Permet de modifier la liste des images
	 * @param imageList la liste des images a modifier
	 */
	public void setImageList(ArrayList<ImageCollector> imageList) {
		this.imageList = imageList;
	}
	/** Permet de récouperer la list des episodes
	 * @return la liste des episodes
	 */
	public ArrayList<EpisodeCollector> getEpisodeList() {
		return episodeList;
	}
	/** Permet de modifier la liste des episodes
	 * @param episodeList la liste des episodes a modifier
	 */
	public void setEpisodeList(ArrayList<EpisodeCollector> episodeList) {
		this.episodeList = episodeList;
	}
	/** Permet de récouperer la liste des series
	 * @return la liste des series
	 */
	public ArrayList<SeriesCollector> getSeriesList() {
		return seriesList;
	}
	/** Permet de modifier la liste des series
	 * @param seriesList la liste des series a modifier
	 */
	public void setSeriesList(ArrayList<SeriesCollector> seriesList) {
		this.seriesList = seriesList;
	}
	/** Permet de récouperer la liste des videos
	 * @return la liste des videos
	 */
	public ArrayList<VideoCollector> getVideoList() {
		return videoList;
	}
	/** Permet de modifier la liste des videos
	 * @param videoList la liste des videos a modifier
	 */
	public void setVideoList(ArrayList<VideoCollector> videoList) {
		this.videoList = videoList;
	}
	/** Permet de récouperer la liste des utilisateurs
	 * @return la liste des utilisateurs
	 */
	public ArrayList<Profil> getUsersList() {
		return usersList;
	}
	/** Permet de modifier la liste des utilisateurs
	 * @param usersList la liste des utilisateurs a modifier
	 */
	public void setUsersList(ArrayList<Profil> usersList) {
		this.usersList = usersList;
	}
	/** Permet de récouperer la liste des permissions
	 * @return la liste des permissions
	 */
	public ArrayList<Permissions> getPermissionsList() {
		return permissionsList;
	}
	/** Permet de modifier la liste des permissions
	 * @param permissionsList la liste des permissions a modifier
	 */
	public void setPermissionsList(ArrayList<Permissions> permissionsList) {
		this.permissionsList = permissionsList;
	}
	/** Permet d'obtenir le label du rang a partir d'un identifiant
	 * @param le identifiant de la permission
	 * @return le label de la permission
	 */
	public String returnLabel(int id){
		for (Permissions perms : this.permissionsList) {
			if (perms.getId() == id)
				return perms.getLabel();
		}
		return "Unknown";
	}
}
