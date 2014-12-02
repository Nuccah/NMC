/**
 * 
 */
package controller;

import java.sql.SQLException;

import model.ConnectorDB;
import model.Profil;

/**
 * Classe permettant de modifier les métadonnées dans la base de données
 * @author Derek
 *
 */
public class Modifier {
	private static Modifier instance = null;
	private ConnectorDB db;

	protected Modifier(){
		this.db = ConnectorDB.getInstance();
	}

	public static Modifier getInstance(){
		if(instance == null) instance = new Modifier();
		return instance;
	}
	/**
	 * Permet de modifier le profil d'un utilisateur dans la base de données
	 * @param profil : Métadonnées du profil modifiées à injecter
	 * @throws SQLException
	 */
	public void modifyUser(Profil profil) throws SQLException{
		db.openConnection();
		
		String query1 = "UPDATE nmc_users SET password = '"+profil.getPassword()+"' WHERE id = '"+profil.getId()+"';";
		
		db.modify(query1);
		db.closeConnection();
	}
}
