package view;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.AlbumCollector;
import model.Lists;
import model.Permissions;
import model.SeriesCollector;

import com.jgoodies.forms.layout.CellConstraints;

/** Classe contenant les differents composants necessaires par tout panneaux GUI
 * @author Derek & Antoine
 */
public class CommonUsed {
	protected JButton uploadButton = new JButton("Uploader");
	protected JButton addButton = new JButton("Ajouter");
	protected JButton modifyButton = new JButton("Confirmer");
	protected JButton modifyPassButton = new JButton("Modifier le mot de passe");
	protected JButton clearButton = new JButton("Réinitialiser");
	protected JButton mnProfil = new JButton("Profil");
	protected JButton mnAide = new JButton("Aide");
	protected JButton mnQuitter = new JButton("Quitter");
	protected JButton confirmButton = new JButton("OK");
	protected JButton cancelButton = new JButton("Annuler");
	
	protected final JLabel userLabel= new JLabel("Nom d'utilisateur");
	protected final JLabel passLabel= new JLabel("Mot de passe");
	protected final JLabel confirmPassLabel = new JLabel("Confirmer le mot de passe");
	protected final JLabel mailLabel= new JLabel("Email");
	protected final JLabel firstNameLabel= new JLabel("Prénom");
	protected final JLabel lastNameLabel= new JLabel("Nom");
	protected final JLabel birthLabel= new JLabel("Date de naissance (dd/MM/YYYY)");
	protected final JLabel regLabel= new JLabel("Date d'enregistrement");
	protected final JLabel permLabel= new JLabel("Droits d'accès");
	
	protected JTextField userField = new JTextField();
	protected JPasswordField passField = new JPasswordField();
	protected JPasswordField confirmPassField = new JPasswordField();
	protected JTextField mailField = new JTextField();
	protected JTextField firstNameField = new JTextField();
	protected JTextField lastNameField = new JTextField();
	protected JTextField birthField = new JTextField();
	protected JTextField regField = new JTextField();
	protected JTextField permField = new JTextField();
	protected JTextField permLevelField = new JTextField();
	
	protected final String mediaString = "Afficher";
	protected final String uploadString = "Ajouter";
	protected final String usersString = "Administration";
	protected final String profilString = "Profil";
	
	protected final String viewBooks = "Livres";
	protected final String viewImages = "Images";
	protected final String viewMovies = "Vidéos";
	protected final String viewMusic = "Albums";
	protected final String viewSeries = "Séries";
	
	protected final String uploadEpisodes = "Episode";
	protected final String uploadSeries = "Série";
	protected final String uploadAlbums = "Album";
	protected final String uploadMusic = "Musique";
	protected final String uploadBooks = "Livre";
	protected final String uploadImages = "Image";
	protected final String uploadMovies = "Vidéo";
	
	protected final String userNode = "Créer un utilisateur";
	protected final String adminNode = "Gestion des utilisateurs";        
	protected final String permNode = "Gestion des rangs";
	
	protected String node;
	
	protected JDialog passDialog;
	
	protected JComboBox<AlbumCollector> albumBox = new JComboBox<AlbumCollector>();
	protected JComboBox<SeriesCollector> seriesBox = new JComboBox<SeriesCollector>();
	protected JComboBox<Permissions> visibilityBox = new JComboBox<Permissions>();
	protected JComboBox<Permissions> modificationBox = new JComboBox<Permissions>();
	protected JComboBox<Permissions> permissionsBox = new JComboBox<Permissions>();
	protected List<JTextField> fieldList = new ArrayList<JTextField>();
	protected List<JComboBox<Permissions>> cbPList = new ArrayList<JComboBox<Permissions>>();
	
	protected GridBagConstraints vmc = new GridBagConstraints();
	protected CellConstraints cc = new CellConstraints();	
	
	protected Frame frameOwner;
	
	public CommonUsed(){
		populateLists();
	}
	
	/**
	 * Fonction qui réinitialise les composants
	 */
	protected void clear(){
		for (JTextField fl : fieldList) 
			fl.setText(null);
		seriesBox.setSelectedItem(null);
		albumBox.setSelectedItem(null);
		for (JComboBox<Permissions> cbl : cbPList)
			cbl.setSelectedItem(null);
		uploadButton.setEnabled(false);
		modifyButton.setEnabled(false);
	}
	
	/**
	 * Fonction qui remplit les différentes listes requises
	 */
	protected void populateLists() {
		clear();
		seriesBox.removeAllItems();
		albumBox.removeAllItems();
		modificationBox.removeAllItems();
		visibilityBox.removeAllItems();
		for(Permissions perms : Lists.getInstance().getPermissionsList()){
			modificationBox.addItem(perms);
			visibilityBox.addItem(perms);
			permissionsBox.addItem(perms);
		}
		for(SeriesCollector series : Lists.getInstance().getSeriesList()){
			seriesBox.addItem(series);
		}
		for(AlbumCollector albums : Lists.getInstance().getAlbumList()){
			albumBox.addItem(albums);
		}
	}
	
	/**
	 * Fonction qui définit le cadre de dialogue de mot de passe
	 */
	protected void setPassDialog() {
		passDialog = new JDialog(frameOwner, "Modifier le mot de passe", true);
		passDialog.setLayout(new GridLayout(3, 2));
		passDialog.add(passLabel);
		passDialog.add(confirmPassLabel);
		passDialog.add(passField);
		passDialog.add(confirmPassField);
		passDialog.add(confirmButton);
		passDialog.add(cancelButton);
		passDialog.setSize(300, 125);
		passDialog.setLocationRelativeTo(frameOwner);
		passDialog.setVisible(true);
	}
}
