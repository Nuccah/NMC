package controller;


/**
 * Classe permettant de repérer le type de fichier média
 * @author Antoine Ceyssens
 *
 */
public class Parser {
	private static Parser instance = null;
	private String OS;
	
	protected Parser(){
		OS = System.getProperty("os.name").toLowerCase();
	}
	
	public static Parser getInstance(){
		if(instance == null){
			instance = new Parser();
		}
		return instance;
	}
	
	/**
	 * Permet de détecter le type de média
	 * @param filename : fichier média à analyser
	 * @return video - si c'est une vidéo<br />
	 * 		 music - si c'est une musique<br />
	 * 		 picture - si c'est une image<br />
	 * 		 ebook - si c'est un ebook<br />
	 * 		 null - si erreur
	 */
	public String getMediaType(String filename){
		int i = filename.lastIndexOf('.');
		String extension;
		if(i > 0) extension = filename.substring(i+1);
		else return null;
		switch(extension.toLowerCase()){
			case "mp4":
			case "avi":
			case "mkv":
			case "flv":
			case "mov":
			case "wmv":
			case "vob":
			case "3gp":
			case "3g2":
					return "video";
			case "aac":
			case "mp3":
			case "wav":
					return "music";
			case "jpeg":
			case "jpg":
			case "png":
			case "gif":
			case "bmp":
					return "picture";
			case "pdf":
			case "ebook":
			case "epub":
			case "cbr":
			case "cbz":
					return "ebook";			
		}
		return null;
	}
	
	
	
	public boolean isWindows() {
		 
		return (OS.indexOf("win") >= 0);
 
	}
 
	public boolean isMac() {
 
		return (OS.indexOf("mac") >= 0);
 
	}
 
	public boolean isUnix() {
 
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
 
	}
 
	public boolean isSolaris() {
 
		return (OS.indexOf("sunos") >= 0);
 
	}
}
