package controller;

import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.Config;
import model.ConnectorDB;
import model.Profil;

/**
 * Classe de gestion des authentifications utilisateur à la DB
 * @author Antoine
 * @version 1.1
 */
public class SessionManager {
	private static SessionManager instance = null;
	
	protected SessionManager(){
		// Fake constructor for singleton
	}
	
	public static SessionManager getInstance(){
		if(instance == null) instance = new SessionManager();
		return instance;
	}
	
	/**
	 * Crée une nouvelle instance utilisateur
	 * @param username : nom d'utilisateur
	 * @param password : mot de passe de l'utilisateur
	 * @return Vrai si la connexion a réussi. Faux dans tous les autres cas
	 */
	public boolean login(String username, String password){
		Config conf = Config.getInstance();
		ConnectorDB connDB = ConnectorDB.getInstance();
		connDB.openConnection(conf.getProp("url_db"), conf.getProp("user_db"), conf.getProp("pass_db"));
		ResultSet res = connDB.select("SELECT * FROM nmc_users WHERE login LIKE '"+username+"'");
		try {
			res.first();
		} catch (HeadlessException | SQLException e1) {
			JOptionPane.showMessageDialog(null, "Login/Mot de passe invalide!");
			e1.printStackTrace();
		}
		try {
			if(Crypter.verify(Crypter.encrypt(password), res.getString("password"))){
				Profil pf = Profil.getInstance();
				pf.setUsername(username);
				pf.setMail(res.getString("mail"));
				pf.setFirstName(res.getString("first_name"));
				pf.setLastName(res.getString("last_name"));
				pf.setBirthdate(res.getDate("birthdate"));
				pf.setRegDate(res.getDate("reg_date"));
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Login/Mot de passe invalide!");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Impossible de vérifier le mot de passe!");
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Permet la déconnexion du profil
	 */
	public void logout(){
		Profil.getInstance().reset();
		ConnectorDB.getInstance().closeConnection();	
	}
}
