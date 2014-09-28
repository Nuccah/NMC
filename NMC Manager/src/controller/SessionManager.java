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
	/**
	 * Crée une nouvelle instance utilisateur
	 * @param username : nom d'utilisateur
	 * @param password : mot de passe de l'utilisateur
	 * @return Vrai si la connexion a réussi. Faux dans tous les autres cas
	 */
	public static boolean login(String username, String password){
		ConnectorDB.openConnection(Config.getProp("url_db"), Config.getProp("user_db"), Config.getProp("pass_db"));
		ResultSet res = ConnectorDB.select("SELECT * FROM nmc_users WHERE login LIKE '"+username+"'");
		try {
			res.first();
		} catch (HeadlessException | SQLException e1) {
			JOptionPane.showMessageDialog(null, "Login/Mot de passe invalide!");
			e1.printStackTrace();
		}
		try {
			if(Crypter.verify(Crypter.encrypt(password), res.getString("password"))){
				JOptionPane.showMessageDialog(null, "Vous êtes à présent connecté!");
				Profil.setUsername(username);
				Profil.setMail(res.getString("mail"));
				Profil.setFirstName(res.getString("first_name"));
				Profil.setLastName(res.getString("last_name"));
				Profil.setBirthdate(res.getDate("birthdate"));
				Profil.setRegDate(res.getDate("reg_date"));
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
	public static void logout(){
		Profil.reset();
		try {
			if(!ConnectorDB.getDB().isClosed()) ConnectorDB.closeConnection();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "Vous êtes à présent déconnecté!");
	}
}
