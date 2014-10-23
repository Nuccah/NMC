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
	 * Permet de récupérer le format du slash en fonction de l'OS
	 * @return La chaine contenant le format du slash
	 */
	public String getSlash(){
		if(isWindows()) return "\\";
		else return "/";
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
