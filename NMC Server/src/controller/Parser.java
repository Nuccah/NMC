package controller;


/**
 * Classe permettant de repérer le type de fichier média
 * @author Antoine Ceyssens
 *
 */
public class Parser {
	private static Parser instance = null;
	
	protected Parser(){
		
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
	 * @return 1 - si c'est une vidéo<br />
	 * 		 2 - si c'est une musique<br />
	 * 		 3 - si c'est une image<br />
	 * 		 4 - si c'est un ebook<br />
	 * 		 -1 - si erreur
	 */
	public int getMediaType(String filename){
		int i = filename.lastIndexOf('.');
		String extension;
		if(i > 0) extension = filename.substring(i+1);
		else return -1;
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
					return 1;
			case "aac":
			case "mp3":
			case "wav":
					return 2;
			case "jpeg":
			case "jpg":
			case "png":
			case "gif":
			case "bmp":
					return 3;
			case "pdf":
			case "ebook":
					return 4;			
		}
		return -1;
	}
}
