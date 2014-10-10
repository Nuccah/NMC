package controller;
/**
 * Classe permettant de convertir les fichiers multimédias au format adéquat 
 * @author Antoine Ceyssens
 *
 */
public class Converter {
	private static Converter instance = null;
	
	protected Converter(){
		
	}
	
	public static Converter getInstance(){
		if(instance == null){
			instance = new Converter();
		}
		return instance;
	}
}
