package controller;


/**
 * Classe de gestion du stockage sur la machine serveur
 * @author Antoine Ceyssens
 *
 */
public class StockManager {
	private static StockManager instance = null;
	
	protected StockManager(){
		
	}
	
	public static StockManager getInstance(){
		if(instance == null){
			instance = new StockManager();
		}
		return instance;
	}


}
