/**
 * 
 */
package controller;

import java.sql.SQLException;

import model.ConnectorDB;
import model.Profil;

/**
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
	
	public void modifyUser(Profil profil) throws SQLException{
		db.openConnection();
		
		String query1 = "UPDATE nmc_users SET password = '"+profil.getPassword()+"' WHERE id = '"+profil.getId()+"';";
		
		db.modify(query1);
		db.closeConnection();
	}
}
