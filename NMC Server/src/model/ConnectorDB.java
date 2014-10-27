package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.postgresql.util.PSQLException;
/**
 * Classe de gestion de la connexion avec la DB
 * @author Antoine Ceyssens
 *
 */
public class ConnectorDB {
	private static ConnectorDB instance = null;
	private Connection db;
	private Config conf;
	
	protected ConnectorDB(){
		this.conf = Config.getInstance();
	}
	
	/**
	 * Permet de récupérer/créer l'instance ConnectorDB
	 * @return l'objet instancié de ConnectorDB
	 */
	public static ConnectorDB getInstance(){
		if(instance == null){
			instance = new ConnectorDB();
		}
		return instance;
	}
	
	/**
	 * Permet d'ouvrir la connexion à la DB 
	 */
	public void openConnection(){
		String uri = "jdbc:postgresql:"+conf.getProp("url_db");
		Properties props = new Properties();
		props.setProperty("user", conf.getProp("user_db"));
		props.setProperty("password", conf.getProp("pass_db"));
		try {
			db = DriverManager.getConnection(uri, props);
		} catch (SQLException e) {
			System.out.println("Connexion à la base de données impossible!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Permet la fermeture de la connection à la base de données
	 */
	public void closeConnection(){
		try {
			if(!db.isClosed()) db.close();
			else System.out.println("La connexion à la base de données est déjà fermée!");
		} catch (SQLException e) {	
			System.out.println("La connection à la base de données ne s'est pas cloturée!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Permet de réaliser des SELECT sur la base de données "db"
	 * @param query : La requête à exécuter
	 * @return Le ResultSet reçu en réponse à la requête "query"
	 * @throws SQLException  Envoyée si la requête n'a pas pu s'exécuter correctement soit à cause de la connection
	 * soit à cause la syntaxe de la requête
	 */
	public ResultSet select(String query) {		
		ResultSet rs = null;
		try {
			Statement st = db.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			rs = st.executeQuery(query);
		} catch (SQLException e) {
			switch(((PSQLException)e).getServerErrorMessage().getSQLState().substring(0, 2)){
				case "42": System.out.println("Erreur de syntaxe ou violation d'accès détectée!"
						+ "\nValeur: "+((PSQLException)e).getServerErrorMessage());
						break;
				default: System.out.println("Error: "+((PSQLException)e).getServerErrorMessage().getSQLState());
			}
			e.printStackTrace();			
		}
		return rs;
	}
	
	/**
	 * Permet de réaliser des requêtes de modifications de la base de données "db" (insert, update, delete)
	 * @param query : La requête à exécuter
	 * @throws SQLException Envoyée si la requête n'a pas pu s'exécuter correctement soit à cause de la connection
	 * soit à cause la syntaxe de la requête
	 */
	public void modify(String query) throws SQLException{
		PreparedStatement st = db.prepareStatement(query);
		st.executeUpdate();
		st.close();
	}
}
