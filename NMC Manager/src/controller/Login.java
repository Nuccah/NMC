package controller;

import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.Config;
import model.ConnectorDB;
import model.Profil;

/**
 * Classe de gestion des Connections à la DB
 * @author Antoine
 *
 */
public class Login {
	private Config conf;
	/**
	 * Crée une nouvelle instance utilisateur
	 * @param username : nom d'utilisateur
	 * @param password : mot de passe de l'utilisateur
	 */
	public Login(String username, String password){
		conf = new Config();
		ConnectorDB.openConnection(conf.getProp("url_db"), conf.getProp("user_db"), conf.getProp("pass_db"));
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
			} else {
				JOptionPane.showMessageDialog(null, "Login/Mot de passe invalide!");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Impossible de vérifier le mot de passe!");
			e.printStackTrace();
		}
	}
}
