package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JOptionPane;

public class ConnectorDB {
	private static Connection db;
	
	public static void openConnection(String url, String user, String password){
		String uri = "jdbc:postgresql:"+url;
		Properties props = new Properties();
		props.setProperty("user", user);
		props.setProperty("password", password);
		try {
			ConnectorDB.db = DriverManager.getConnection(uri, props);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Connexion à la base de données impossible!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Permet la fermeture de la connection à la base de données
	 */
	public static void closeConnection(){
		try {
			ConnectorDB.db.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "La connection à la base de données ne s'est pas cloturée!");
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
	public static ResultSet select(String query) {		
		ResultSet rs = null;
		try {
			PreparedStatement st = db.prepareStatement(query);
			rs = st.executeQuery();
		} catch (SQLException e) {
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
	public static void modify(String query) throws SQLException{
		PreparedStatement st = db.prepareStatement(query);
		st.executeQuery();
		st.close();
	}
}
