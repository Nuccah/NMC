package controller;


/**
 * Classe permettant de récupérer certaines informations systèmes
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
	 * Définit si le fichier passé en paramètre doit être converti ou non
	 * @param filename : Nom du fichier à vérifier
	 * @return Vrai - si le fichier doit être converti
	 */
	public boolean mustBeConverted(String filename){
		int i = filename.lastIndexOf('.');
		String extension;
		if(i > 0) extension = filename.substring(i+1);
		else return false;
		switch(extension.toLowerCase()){
			case "avi":
			case "mkv":
			case "flv":
			case "mov":
			case "wmv":
			case "vob":
			case "3gp":
			case "3g2":
			case "aac":
			case "wav":
			case "flac":
			case "m4v":
				return true;
			default:
				return false;
		}

	}
	
	/**
	 * Permet de récupérer le format du slash en fonction de l'OS
	 * @return La chaine contenant le format du slash
	 */
	public String getSlash(){
		if(isWindows()) return "\\";
		else return "/";
	}
	
	/**
	 * Permet de savoir si l'OS courant est Windows
	 * @return Vrai si l'OS courant est Windows
	 */
	public boolean isWindows() {
		 
		return (OS.indexOf("win") >= 0);
 
	}
}
