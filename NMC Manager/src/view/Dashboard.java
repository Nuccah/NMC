package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.AlbumCollector;
import model.AudioCollector;
import model.BookCollector;
import model.Config;
import model.EpisodeCollector;
import model.ImageCollector;
import model.Lists;
import model.MetaDataCollector;
import model.NMCTableModel;
import model.Permissions;
import model.Profil;
import model.SeriesCollector;
import model.VideoCollector;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import controller.Crypter;
import controller.SocketManager;

/**
 * Fenêtre principale du programme
 * @author Derek & Antoine
 *
 */

public class Dashboard extends JFrame implements ActionListener, PropertyChangeListener, ChangeListener{
	private static final long serialVersionUID = -5998048938167814342L;
	private JPanel topPane = new JPanel();
	private JPanel titlePane = new JPanel();
	private JPanel centerPane = new JPanel();
	private JPanel bottomPane = new JPanel();
	private JPanel uploadDataPane = new JPanel();
	private JPanel viewPane = new JPanel();
	private JPanel createUserPane = new JPanel();
	private JPanel profilePane = new JPanel();
	
	private static final JButton uploadButton = new JButton("Uploader");
	private static final JButton addButton = new JButton("Ajouter");
	private static final JButton modifyButton = new JButton("Confirmer");
	private static final JButton modifyPassButton = new JButton("Modifier le mot de passe");
	private static final JButton clearButton = new JButton("Réinitialiser");
	private static final JButton mnProfil = new JButton("Profil");
	private static final JButton mnAide = new JButton("Aide");
	private static final JButton mnQuitter = new JButton("Quitter");
	private static final JButton confirmButton = new JButton("OK");
	private static final JButton cancelButton = new JButton("Annuler");

	private static final JLabel titleLabel = new JLabel("Titre");
	private static final JLabel yearLabel = new JLabel("Année");
	private static final JLabel authorLabel = new JLabel("Auteur");
	private static final JLabel synopsisLabel = new JLabel("Synopsis");
	private static final JLabel genreLabel = new JLabel("Genre");
	private static final JLabel photographerLabel = new JLabel("Photographe");
	private static final JLabel artistLabel = new JLabel("Artiste");
	private static final JLabel directorLabel = new JLabel("Réalisateur");
	private static final JLabel visibilityLabel = new JLabel("Qui peut visionner?");
	private static final JLabel modificationLabel = new JLabel("Qui peut modifier?");
	private static final JLabel albumLabel = new JLabel("Album");
	private static final JLabel seasonLabel = new JLabel("Saison");
	private static final JLabel chronoLabel = new JLabel("Chronologie");
	private static final JLabel seriesLabel = new JLabel("Séries");

	private static final JLabel userLabel= new JLabel("Nom d'utilisateur");
	private static final JLabel passLabel= new JLabel("Mot de passe");
	private static final JLabel confirmPassLabel = new JLabel("Confirmer le mot de passe");
	private static final JLabel mailLabel= new JLabel("Email");
	private static final JLabel firstNameLabel= new JLabel("Prénom");
	private static final JLabel lastNameLabel= new JLabel("Nom");
	private static final JLabel birthLabel= new JLabel("Date de naissance (dd/MM/YYYY)");
	private static final JLabel regLabel= new JLabel("Date d'enregistrement");
	private static final JLabel permLabel= new JLabel("Droits d'accès");
	
	private JTextField userField = new JTextField();
	private JPasswordField passField = new JPasswordField();
	private JPasswordField confirmPassField = new JPasswordField();
	private JTextField mailField = new JTextField();
	private JTextField firstNameField = new JTextField();
	private JTextField lastNameField = new JTextField();
	private JTextField birthField = new JTextField();
	private JTextField regField = new JTextField();
	private JTextField permField = new JTextField();
	private JTextField permLevelField = new JTextField();

	private JTextField titleField = new JTextField();
	private JTextField yearField = new JTextField();
	private JTextField synopsisField = new JTextField();
	private JTextField genreField = new JTextField();
	private JTextField personField = new JTextField();
	private JTextField chronoField = new JTextField();
	private JTextField seasonField = new JTextField();
	
	private JComboBox<AlbumCollector> albumBox = new JComboBox<AlbumCollector>();
	private JComboBox<SeriesCollector> seriesBox = new JComboBox<SeriesCollector>();
	private JComboBox<Permissions> visibilityBox = new JComboBox<Permissions>();
	private JComboBox<Permissions> modificationBox = new JComboBox<Permissions>();
	private JComboBox<Permissions> permissionsBox = new JComboBox<Permissions>();
	private List<JTextField> fieldList = new ArrayList<JTextField>();
	private List<JComboBox<Permissions>> cbPList = new ArrayList<JComboBox<Permissions>>();

	private static final FileFilter videoFilter = new FileNameExtensionFilter("Fichier Vidéo", "mp4", "avi", "mkv", "flv", "mov", "wmv", "vob", "3gp", "3g2");
	private static final FileFilter musicFilter = new FileNameExtensionFilter("Fichier Audio", "aac", "mp3", "wav", "wma", "flac");
	private static final FileFilter bookFilter = new FileNameExtensionFilter("Fichier Livre", "pdf", "ebook", "epub", "cbr", "cbz");
	private static final FileFilter imageFilter = new FileNameExtensionFilter("Fichier Image", "jpg", "jpeg", "png", "gif", "bmp");

	private static final String[] albumColumns = new String[]{"Title", "Artist", "Year", "Genre", "Description", "Modification Rights", "Visibility Rights"};
	private static final String[] imageColumns = new String[]{"Title", "Photographer", "Year", "Modification Rights", "Visibility Rights"};
	private static final String[] bookColumns = new String[]{"Title", "Author", "Year", "Genre", "Synopsis", "Modification Rights", "Visibility Rights"};
	private static final String[] videoColumns = new String[]{"Title", "Year", "Genre", "Synopsis", "Director", "Modification Rights", "Visibility Rights"};
	private static final String[] seriesColumns = new String[]{"Title", "Year", "Genre", "Synopsis", "Modification Rights", "Visibility Rights"};
	private static final String[] userColumns = new String[]{"Username", "First Name", "Last Name", "Email", "Birthdate", "Registration Date", "Permission Level"};
	private static final String[] permissionColumns = new String[]{"Label", "Level"};

	private GridBagConstraints lc = new GridBagConstraints();
	private GridBagConstraints tc = new GridBagConstraints();
	private GridBagConstraints rcc = new GridBagConstraints();
	private GridBagConstraints ltc = new GridBagConstraints();
	private GridBagConstraints rtc = new GridBagConstraints();
	private GridBagConstraints gcc = new GridBagConstraints();
	private GridBagConstraints bc = new GridBagConstraints();
	private GridBagConstraints vmc = new GridBagConstraints();
	private CellConstraints cc = new CellConstraints();

	private final FormLayout profileLayout = new FormLayout(
			"right:pref, 4dlu, fill:130dlu",
			"pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, "
					+ "2dlu, pref, 10dlu, pref, 10dlu, pref, 10dlu, pref, 10dlu, pref");

	private final FormLayout uploadLayout = new FormLayout(
			"right:pref, 4dlu, fill:130dlu",
			"pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, "
					+ "2dlu, pref, 10dlu, pref, 10dlu, pref, 10dlu, pref,");

	private final FormLayout userLayout = new FormLayout(
			"right:pref, 4dlu, fill:130dlu",
			"pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, "
					+ "2dlu, pref, 10dlu, pref, 10dlu, pref, 10dlu, pref,");

	private JDialog dlg;
	private JDialog passDialog;

	private JTabbedPane menuBar;
	private JTabbedPane mediaNode;
	private JTabbedPane uploadNode;
	private JTabbedPane usersNode;
	private JTabbedPane profilNode;
	
	private String node;
	private static final String mediaString = "Afficher";
	private static final String uploadString = "Ajouter";
	private static final String usersString = "Administration";
	private static final String profilString = "Profil";
	
	private static final String viewBooks = "Livres";
	private static final String viewImages = "Images";
	private static final String viewMovies = "Vidéos";
	private static final String viewMusic = "Albums";
	private static final String viewSeries = "Séries";
	
	private static final String uploadEpisodes = "Episode";
	private static final String uploadSeries = "Série";
	private static final String uploadAlbums = "Album";
	private static final String uploadMusic = "Musique";
	private static final String uploadBooks = "Livre";
	private static final String uploadImages = "Image";
	private static final String uploadMovies = "Vidéo";
	
	private static final String userNode = "Créer un utilisateur";
	private static final String adminNode = "Gestion des utilisateurs";        
	private static final String permNode = "Gestion des permissions";
	
	private JFileChooser fc = new JFileChooser();
	
	private JProgressBar progressBar;
	private UploadTask uTask;
	
	private MetaDataCollector fileC;

	private String messagePB;
	
	public Dashboard(){
		super(Config.getInstance().getProp("base_title")+"Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout main = new GridBagLayout();
		URL iconURL = getClass().getResource("nmc.png");
		ImageIcon icon = new ImageIcon(iconURL);
		setIconImage(icon.getImage());
		getContentPane().setLayout(main);
		setSize(new Dimension(1024,864));
		setMinimumSize(new Dimension(1024,768));
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		setBounds((width/2) - (width/3), (height/2) - (height/3), (int)(width/1.5), (int)(height/1.5));
		ltc.gridx = 0; ltc.weightx = 1; ltc.weighty = 0.1; ltc.fill = GridBagConstraints.BOTH;
		rtc.gridx = 0; rtc.weightx = 1; rtc.weighty = 0.9; rtc.fill = GridBagConstraints.BOTH;
		gcc.gridx = 0; gcc.gridy = 1; gcc.weightx = 1; gcc.weighty = 0.8; gcc.fill = GridBagConstraints.BOTH;
		bc.gridx = 0; bc.gridy = 2; bc.weightx = 1; bc.weighty = 0.1; bc.fill = GridBagConstraints.BOTH;
		lc.gridx = 0; lc.gridy = 0; lc.weightx = 0.25; lc.weighty = 1; lc.fill = GridBagConstraints.BOTH;
		tc.gridx = 1; tc.gridy = 0; tc.weightx = 0.75; tc.weighty = 1; tc.fill = GridBagConstraints.BOTH;
		
		topPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		centerPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		bottomPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		topPane.setLayout(new GridBagLayout());
		titlePane.setLayout(new GridBagLayout());
		centerPane.setLayout(new GridBagLayout());
		viewPane.setLayout(new GridBagLayout());

		centerPane.setMinimumSize(new Dimension(1024, 700));

		setTitleBar();
		setMenuBar();
		setBottomBar();
		setFileChooser(fc.getComponents());

		populateLists();
		setComponentLists();
		setCreateUserPane();

		mnProfil.addActionListener(this);
		mnAide.addActionListener(this);
		uploadButton.addActionListener(this);
		clearButton.addActionListener(this);
		addButton.addActionListener(this);
		modifyPassButton.addActionListener(this);
		confirmButton.addActionListener(this);
		cancelButton.addActionListener(this);
		modifyButton.addActionListener(this);

		clear();
		
		getContentPane().add(topPane, tc);
		//getContentPane().add(centerPane, gcc);
		getContentPane().add(bottomPane, bc);

		pack();
		
	}
	
	/**
	 * Barre de menu titulaire
	 */
	public void setTitleBar(){

		mnProfil.setHorizontalAlignment(SwingConstants.LEFT);
		titlePane.add(mnProfil);

		mnAide.setHorizontalAlignment(SwingConstants.LEFT);
		titlePane.add(mnAide);

		mnQuitter.setHorizontalAlignment(SwingConstants.LEFT);
		mnQuitter.addActionListener(this);
		titlePane.add(mnQuitter);

		titlePane.repaint();
		titlePane.revalidate();
		topPane.add(titlePane, ltc);
	}
	
	/**
	 * Bar de menu pour les gestions possibles
	 */
	private void setMenuBar() {
		mediaNode = new JTabbedPane();
		mediaNode.addChangeListener(this);
		mediaNode.addTab(viewBooks, mediaResultSet(viewBooks));
		mediaNode.addTab(viewImages, mediaResultSet(viewImages));
		mediaNode.addTab(viewMovies, mediaResultSet(viewMovies));
		mediaNode.addTab(viewMusic, mediaResultSet(viewMusic));
		mediaNode.addTab(viewSeries, mediaResultSet(viewSeries));

		uploadNode = new JTabbedPane();
		uploadNode.addChangeListener(this);
		JPanel uEpisodeTmp = new JPanel();
		uploadNode.addTab(uploadEpisodes, uploadFilePage(uploadEpisodes, uEpisodeTmp));
		JPanel uSerieTmp = new JPanel();
		uploadNode.addTab(uploadSeries,uploadFilePage(uploadSeries, uSerieTmp));
		JPanel uAlbumTmp = new JPanel();
		uploadNode.addTab(uploadAlbums, uploadFilePage(uploadAlbums, uAlbumTmp));
		JPanel uMusicTmp = new JPanel();
		uploadNode.addTab(uploadMusic, uploadFilePage(uploadMusic, uMusicTmp));
		JPanel uBookTmp = new JPanel();
		uploadNode.addTab(uploadBooks, uploadFilePage(uploadBooks, uBookTmp));
		JPanel uImageTmp = new JPanel();
		uploadNode.addTab(uploadImages, uploadFilePage(uploadImages, uImageTmp));
		JPanel uMovieTmp = new JPanel();
		uploadNode.addTab(uploadMovies, uploadFilePage(uploadMovies, uMovieTmp));

		usersNode = new JTabbedPane();
		usersNode.addChangeListener(this);
		JPanel userTmp = new JPanel();
		usersNode.addTab(userNode, userAdmin(userNode, userTmp));
		JPanel adminTmp = new JPanel();
		usersNode.addTab(adminNode, userAdmin(adminNode, adminTmp)); 
		JPanel permTmp = new JPanel();
		usersNode.addTab(permNode, userAdmin(permNode, permTmp));
	
		menuBar = new JTabbedPane();
		menuBar.addChangeListener(this);
		menuBar.addTab(mediaString, mediaNode);
		menuBar.addTab(uploadString, uploadNode);
		menuBar.addTab(usersString, usersNode);
		getContentPane().add(menuBar, rtc);

	}
	
	/**
	 * Bottom bar containing Copyright information
	 */
	private void setBottomBar() {
		JTextPane txtpnTest = new JTextPane();
		txtpnTest.setEditable(false);
		txtpnTest.setText("Tous droits réservés - \u00a9 Nukama Team 2014 - nmc_team@nukama.be - Developpé par Antoine Ceyssens & Derek Van Hove" );
		bottomPane.add(txtpnTest);
	}

	private void setFileChooser(Component[] components) {
		fc.setAcceptAllFileFilterUsed(false);
		fc.setControlButtonsAreShown(false);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.addActionListener(this);
		fc.setMinimumSize(new Dimension(500,600));
		fc.setBorder(new EmptyBorder(0, 0, 0, 0));
		Color bg = Color.WHITE;
		setBG(fc.getComponents(), bg, 0 );
		fc.setBackground( bg );
		fc.setOpaque(true);
	}

	private void setBG( Component[] jc, Color bg, int depth )
	{
		for( int i = 0; i < jc.length; i++ ) {
			Component c = jc[i];
			if( c instanceof Container )// {
				setBG( ((Container)c).getComponents(), bg, depth );
			c.setBackground( bg );
		}
	}

	private void setComponentLists() {
		fieldList.add(titleField);
		fieldList.add(yearField);
		fieldList.add(synopsisField);
		fieldList.add(genreField);
		fieldList.add(personField);
		fieldList.add(chronoField);
		fieldList.add(seasonField);
		fieldList.add(passField);
		fieldList.add(confirmPassField);
		fieldList.add(mailField);
		fieldList.add(userField);
		fieldList.add(firstNameField);
		fieldList.add(lastNameField);
		fieldList.add(birthField);
		fieldList.add(permLevelField);

		cbPList.add(modificationBox);
		cbPList.add(visibilityBox);
		cbPList.add(permissionsBox);
	}
	
	private void setCreateUserPane(){
		userField.setEditable(true);
		mailField.setEditable(true);
		firstNameField.setEditable(true);
		lastNameField.setEditable(true);
		birthField.setEditable(true);
		regField.setEditable(true);
		permField.setEditable(true);
		createUserPane.add(userLabel, cc.xy(1, 1)); createUserPane.add(new JLabel("*"), cc.xy(2,1)); createUserPane.add(userField, cc.xy(3,1));
		createUserPane.add(passLabel, cc.xy(1, 3)); createUserPane.add(new JLabel("*"), cc.xy(2,3)); createUserPane.add(passField, cc.xy(3,3));
		createUserPane.add(confirmPassLabel, cc.xy(1, 5)); createUserPane.add(new JLabel("*"), cc.xy(2,5)); createUserPane.add(confirmPassField, cc.xy(3,5));
		createUserPane.add(mailLabel, cc.xy(1, 7)); createUserPane.add(new JLabel("*"), cc.xy(2,7)); createUserPane.add(mailField, cc.xy(3,7));
		createUserPane.add(firstNameLabel, cc.xy(1, 9)); createUserPane.add(new JLabel("*"), cc.xy(2,9)); createUserPane.add(firstNameField, cc.xy(3,9));
		createUserPane.add(lastNameLabel, cc.xy(1, 11)); createUserPane.add(new JLabel("*"), cc.xy(2,11)); createUserPane.add(lastNameField, cc.xy(3,11));
		createUserPane.add(birthLabel, cc.xy(1, 13)); createUserPane.add(new JLabel("*"), cc.xy(2,13)); createUserPane.add(birthField, cc.xy(3,13));
		createUserPane.add(permLabel, cc.xy(1, 15)); createUserPane.add(new JLabel("*"), cc.xy(2,15)); createUserPane.add(permissionsBox, cc.xy(3,15));
		createUserPane.add(new JLabel("* = champs requis"), cc.xy(3,17));
		createUserPane.add(addButton, cc.xy(1, 19)); createUserPane.add(clearButton, cc.xy(3,19));
		createUserPane.revalidate(); 
		createUserPane.repaint();
	}
	
	private void setProfilPane() {
		initFields("profil");
		userField.setText(Profil.getInstance().getUsername()); userField.setEditable(false);
		mailField.setText(Profil.getInstance().getMail()); mailField.setEditable(false);
		firstNameField.setText(Profil.getInstance().getFirstName()); firstNameField.setEditable(false);
		lastNameField.setText(Profil.getInstance().getLastName()); lastNameField.setEditable(false);
		birthField.setText(Profil.getInstance().getBirthdate()); birthField.setEditable(false);
		regField.setText(Profil.getInstance().getRegDate()); regField.setEditable(false);
		permField.setText(Lists.getInstance().returnLabel(Profil.getInstance().getPermissions_id())); permField.setEditable(false);

		profileLayout.setRowGroups(new int[][]{{1, 3, 5, 7, 9, 11, 13, 15, 17, 19}});
		profilePane.setLayout(profileLayout);
		profilePane.add(userLabel, cc.xy(1, 1)); profilePane.add(userField, cc.xy(3,1));
		profilePane.add(passLabel, cc.xy(1, 3)); profilePane.add(modifyPassButton, cc.xy(3,3));
		profilePane.add(mailLabel, cc.xy(1, 7)); profilePane.add(mailField, cc.xy(3,7));
		profilePane.add(firstNameLabel, cc.xy(1, 9)); profilePane.add(firstNameField, cc.xy(3,9));
		profilePane.add(lastNameLabel, cc.xy(1, 11)); profilePane.add(lastNameField, cc.xy(3,11));
		profilePane.add(birthLabel, cc.xy(1, 13)); profilePane.add(birthField, cc.xy(3,13));
		profilePane.add(regLabel, cc.xy(1, 15)); profilePane.add(regField, cc.xy(3,15));
		profilePane.add(permLabel, cc.xy(1, 17)); profilePane.add(permField, cc.xy(3,17));
		profilePane.add(modifyButton, cc.xy(1, 19)); profilePane.add(clearButton, cc.xy(3,19));
		modifyButton.setEnabled(false);
	}

	/**
	 * Switchcase redirection to appropriate methods
	 * @param node
	 */
	private JComponent mediaResultSet(String node) {
		centerPane.removeAll();
		viewPane.removeAll();
		this.node = node;
		//vmc.weightx = 1; vmc.weighty = 1; vmc.fill = GridBagConstraints.BOTH;

		if ((node.equals(viewBooks)))
			viewPane.add(createTable("books"));
		else if (node.equals(viewImages))
			viewPane.add(createTable("images"));
		else if (node == viewMusic)
			viewPane.add(createTable("albums"));
		else if (node == viewMovies)
			viewPane.add(createTable("videos"));
		else if (node == viewSeries)
			viewPane.add(createTable("series"));
		viewPane.revalidate();
		centerPane.add(viewPane);
		centerPane.revalidate();
		return centerPane;
	}

	private JScrollPane createTable(String type) {
		try {
			SocketManager.getInstance().getList(type);
		} catch (ClassNotFoundException | IOException e) {
			JOptionPane.showMessageDialog(getContentPane(),
					"Impossible de récupérer la liste mise à jour après l'ajout d'une série ou d'un album."
							+ "Veuillez relancer l'application",
							"Erreur de mise à jour",
							JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		JTable table = null;
		switch (type){
		case "albums": 
			table = new JTable(new NMCTableModel(Lists.getInstance().getAlbumList(), albumColumns)); 
			break;
		case "books": 
			table = new JTable(new NMCTableModel(Lists.getInstance().getBookList(), bookColumns)); 
			break;
		case "images": 
			table = new JTable(new NMCTableModel(Lists.getInstance().getImageList(), imageColumns)); 
			break;
		case "series": 
			table = new JTable(new NMCTableModel(Lists.getInstance().getSeriesList(), seriesColumns)); 
			break;
		case "videos": 
			table = new JTable(new NMCTableModel(Lists.getInstance().getVideoList(), videoColumns)); 
			break;
		case "users":
			table = new JTable(new NMCTableModel(Lists.getInstance().getUsersList(), userColumns));
			break;
		case "permissions":
			table = new JTable(new NMCTableModel(Lists.getInstance().getPermissionsList(), permissionColumns));;
			break;
		}
		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);
		// Add the scroll pane to this panel.
		return scrollPane;
	}
	
	/**
	 * Switchcase redirection to appropriate methods
	 * @param node
	 */
	private JComponent userAdmin(String node, JPanel pane) {
		clear();
		pane.removeAll();
		pane.setLayout(new GridBagLayout());
		this.node = node;
		if (node.equals(userNode)){
			createUserPane.removeAll();
			userLayout.setRowGroups(new int[][]{{1, 3, 5, 7, 9, 11, 13, 15, 17, 19}});
			createUserPane.setLayout(userLayout);
			pane.add(createUserPane, new GridBagConstraints());
			setCreateUserPane();
		}
		else if (node.equals(adminNode)){
			JPanel admin_pane = new JPanel(); 
			admin_pane.removeAll();
			try {
				SocketManager.getInstance().getList("users");
			} catch (ClassNotFoundException | IOException e) {
				JOptionPane.showMessageDialog(getContentPane(),
						"Impossible de récupérer la liste des utilisateurs."
								+ "Veuillez relancer l'application",
								"Erreur de mise à jour",
								JOptionPane.WARNING_MESSAGE);
				e.printStackTrace();
			}
			vmc.weightx = 1; vmc.weighty = 1; vmc.fill = GridBagConstraints.BOTH;
			admin_pane.add(createTable("users"), vmc);
			pane.add(admin_pane, vmc);
		}
		else if (node.equals(permNode)){
			JPanel perm_pane = new JPanel();
			perm_pane.removeAll();
			try {
				SocketManager.getInstance().getList("permissions");
			} catch (ClassNotFoundException | IOException e) {
				JOptionPane.showMessageDialog(getContentPane(),
						"Impossible de récupérer la liste des permissions."
								+ "Veuillez relancer l'application",
								"Erreur de mise à jour",
								JOptionPane.WARNING_MESSAGE);
				e.printStackTrace();
			} 
			perm_pane.add(createTable("permissions"));
			pane.add(perm_pane, new GridBagConstraints());
		}
		pane.revalidate();
		return pane;
	}

	/**
	 * Creation and layout of required upload components
	 * @param node
	 */
	private JComponent uploadFilePage(String node, JPanel pane) {
		pane.removeAll();
		JPanel dataPane = new JPanel();
		dataPane.removeAll();
		this.node = node;
		
		clear();
		uploadLayout.setRowGroups(new int[][]{{1, 3, 5, 7, 9, 11,13,15,17}});
		dataPane.setLayout(uploadLayout);
		if(node == uploadAlbums || node == uploadSeries){
			pane.setLayout(new GridBagLayout());
			addButton.setEnabled(true);
			dataPane.add(addButton,cc.xy(1, 17));
			pane.add(dataPane, new GridBagConstraints());
		}
		else{
			pane.setLayout(new GridBagLayout());
			rcc.weightx = 1; rcc.weighty = 1; rcc.fill = GridBagConstraints.BOTH;
			pane.add(fc, rcc);
			pane.add(dataPane, rcc);
			uploadButton.setEnabled(false);
			dataPane.add(uploadButton,cc.xy(1, 17));
		}
		dataPane.add(titleLabel,cc.xy(1, 1)); dataPane.add(new JLabel("*"), cc.xy(2,1)); dataPane.add(titleField,cc.xy(3, 1));
		if (node == uploadAlbums){
			dataPane.add(yearLabel,cc.xy(1, 3)); dataPane.add(new JLabel("*"), cc.xy(2,3)); dataPane.add(yearField,cc.xy(3, 3));
			dataPane.add(artistLabel,cc.xy(1, 5)); dataPane.add(new JLabel("*"), cc.xy(2,5)); dataPane.add(personField,cc.xy(3, 5));
			dataPane.add(genreLabel,cc.xy(1, 7) ); dataPane.add(new JLabel("*"), cc.xy(2,7)); dataPane.add(genreField,cc.xy(3, 7));
			dataPane.add(synopsisLabel,cc.xy(1, 9) ); dataPane.add(synopsisField,cc.xy(3, 9));
			dataPane.add(visibilityLabel,cc.xy(1, 11)); dataPane.add(visibilityBox,cc.xy(3, 11)); 
			dataPane.add(modificationLabel,cc.xy(1, 13)); dataPane.add(modificationBox,cc.xy(3, 13));
		}
		else if (node == uploadBooks){
			dataPane.add(yearLabel,cc.xy(1, 3)); dataPane.add(new JLabel("*"), cc.xy(2,3)); dataPane.add(yearField,cc.xy(3, 3));
			dataPane.add(authorLabel,cc.xy(1, 5) ); dataPane.add(new JLabel("*"), cc.xy(2,5)); dataPane.add(personField,cc.xy(3, 5));
			dataPane.add(synopsisLabel,cc.xy(1, 7)); dataPane.add(synopsisField,cc.xy(3, 7));
			dataPane.add(genreLabel,cc.xy(1, 9)); dataPane.add(genreField,cc.xy(3, 9));
			dataPane.add(visibilityLabel,cc.xy(1, 11)); dataPane.add(new JLabel("*"), cc.xy(2,11)); dataPane.add(visibilityBox,cc.xy(3, 11)); 
			dataPane.add(modificationLabel,cc.xy(1, 13)); dataPane.add(new JLabel("*"), cc.xy(2,13)); dataPane.add(modificationBox,cc.xy(3, 13));
			fc.setFileFilter(bookFilter);
		}
		else if (node == uploadEpisodes){
			dataPane.add(seriesLabel,cc.xy(1, 3)); dataPane.add(new JLabel("*"), cc.xy(2,3)); dataPane.add(seriesBox,cc.xy(3, 3));
			dataPane.add(directorLabel,cc.xy(1, 5) ); dataPane.add(personField,cc.xy(3, 5));
			dataPane.add(seasonLabel,cc.xy(1, 7) ); dataPane.add(seasonField,cc.xy(3, 7));
			dataPane.add(chronoLabel,cc.xy(1, 9) ); dataPane.add(chronoField,cc.xy(3, 9));
			fc.setFileFilter(videoFilter);
		}
		else if (node == uploadImages){
			dataPane.add(yearLabel,cc.xy(1, 3)); dataPane.add(yearField,cc.xy(3, 3));
			dataPane.add(photographerLabel,cc.xy(1, 5)); dataPane.add(personField,cc.xy(3, 5));
			dataPane.add(visibilityLabel,cc.xy(1, 7)); dataPane.add(new JLabel("*"), cc.xy(2,7)); dataPane.add(visibilityBox,cc.xy(3, 7)); 
			dataPane.add(modificationLabel,cc.xy(1, 9)); dataPane.add(new JLabel("*"), cc.xy(2,9)); dataPane.add(modificationBox,cc.xy(3, 9));
			fc.setFileFilter(imageFilter);
		}
		else if (node == uploadMusic){
			dataPane.add(albumLabel,cc.xy(1, 3)); dataPane.add(new JLabel("*"), cc.xy(2,3)); dataPane.add(albumBox,cc.xy(3, 3));
			dataPane.add(artistLabel,cc.xy(1, 5)); dataPane.add(new JLabel("*"), cc.xy(2,5)); dataPane.add(personField,cc.xy(3, 5));
			fc.setFileFilter(musicFilter);
		}
		else if (node == uploadMovies){
			dataPane.add(yearLabel,cc.xy(1, 3)); dataPane.add(new JLabel("*"), cc.xy(2,3)); dataPane.add(yearField,cc.xy(3, 3));
			dataPane.add(directorLabel,cc.xy(1, 5)); dataPane.add(personField,cc.xy(3, 5));
			dataPane.add(genreLabel,cc.xy(1, 7)); dataPane.add(genreField,cc.xy(3, 7));
			dataPane.add(synopsisLabel,cc.xy(1, 9)); dataPane.add(synopsisField,cc.xy(3, 9));
			dataPane.add(visibilityLabel,cc.xy(1, 11)); dataPane.add(new JLabel("*"), cc.xy(2,11)); dataPane.add(visibilityBox,cc.xy(3, 11)); 
			dataPane.add(modificationLabel,cc.xy(1, 13)); dataPane.add(new JLabel("*"), cc.xy(2,13)); dataPane.add(modificationBox,cc.xy(3, 13));
			fc.setFileFilter(videoFilter);
		}
		else if (node == uploadSeries){
			dataPane.add(yearLabel,cc.xy(1, 3)); dataPane.add(new JLabel("*"), cc.xy(2,3)); dataPane.add(yearField,cc.xy(3, 3));
			dataPane.add(synopsisLabel,cc.xy(1, 5) ); dataPane.add(synopsisField,cc.xy(3, 5));
			dataPane.add(genreLabel,cc.xy(1, 7) ); dataPane.add(new JLabel("*"), cc.xy(2,7)); dataPane.add(genreField,cc.xy(3, 7));
			dataPane.add(visibilityLabel,cc.xy(1, 9)); dataPane.add(new JLabel("*"), cc.xy(2,9)); dataPane.add(visibilityBox,cc.xy(3, 9)); 
			dataPane.add(modificationLabel,cc.xy(1, 11)); dataPane.add(new JLabel("*"), cc.xy(2,11)); dataPane.add(modificationBox,cc.xy(3, 11));
		}
		dataPane.add(new JLabel("* = champs requis"), cc.xy(3,15));
		dataPane.add(clearButton,cc.xy(3, 17));
			
		dataPane.repaint(); dataPane.revalidate();
		pane.revalidate();
		return pane;
	}
	
	public void clear(){
		for (JTextField fl : fieldList) 
			fl.setText(null);
		seriesBox.setSelectedItem(null);
		albumBox.setSelectedItem(null);
		for (JComboBox<Permissions> cbl : cbPList)
			cbl.setSelectedItem(null);
		uploadButton.setEnabled(false);
		modifyButton.setEnabled(false);
	}

	public void initFields(String type){
		if (type.equals("profil")){
			passField.setText(null);
			confirmPassField.setText(null);
			profilePane.add(clearButton, cc.xy(3,19));
		}
	}

	private void populateLists() {
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
	 * Creation of new progress bar, dialog for each upload
	 */
	public void progressBar(String message) {
		//Create the demo's UI.
		dlg = new JDialog((Frame) getOwner(), "Progression", true);
		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setString(message);
		dlg.add(BorderLayout.CENTER, progressBar);
		dlg.add(BorderLayout.NORTH, new JLabel("En cours..."));
		dlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dlg.setSize(300, 75);
		dlg.setLocationRelativeTo((Frame) getOwner());
		dlg.setVisible(true);
	}

	private void setPassDialog() {
		passDialog = new JDialog((Frame) getOwner(), "Modifier le mot de passe", true);
		passDialog.setLayout(new GridLayout(3, 2));
		passDialog.add(passLabel);
		passDialog.add(confirmPassLabel);
		passDialog.add(passField);
		passDialog.add(confirmPassField);
		passDialog.add(confirmButton);
		passDialog.add(cancelButton);
		passDialog.setSize(300, 125);
		passDialog.setLocationRelativeTo((Frame) getOwner());
		passDialog.setVisible(true);
	}

	/**
	 * Invoked when task's progress property changes.
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = (Integer) evt.getNewValue();
			progressBar.setValue(progress);
			if(progress == 100) dlg.dispose();
			else progressBar.setString("Téléversement en cours");
		}
	}

	/** Determines whether  metadata fields are empty or not
	 * @param node the String of the node selected
	 * @return boolean whether metadata fields are empty or not
	 */
	public boolean verify(){
		if (node == userNode){
			if(userField.getText().equals("") || passField.getPassword().equals("") || confirmPassField.getPassword().equals("") || mailField.getText().equals("")
					|| firstNameField.getText().equals("") || lastNameField.getText().equals("") || birthField.getText().equals("") 
					|| permissionsBox.getSelectedItem() == null){
				return false;
			}
			else{
				return true;
			}
		}
		else{
			if (titleField.getText().equals(""))
				return false;
			else{
				if (node == uploadAlbums){
					if (personField.getText().equals("") || visibilityBox.getSelectedItem() == null || modificationBox.getSelectedItem() == null || yearField.getText().equals("") || genreField.getText().equals("") )
						return false;
					else return true;
				}
				else if (node == uploadBooks){
					if (personField.getText().equals("")|| visibilityBox.getSelectedItem() == null || modificationBox.getSelectedItem() == null || yearField.getText().equals(""))
						return false;
					else return true;
				}
				else if (node == uploadEpisodes){
					if (seriesBox.getSelectedItem() == null)
						return false;
					else return true;	
				}
				else if (node == uploadImages){
					if (visibilityBox.getSelectedItem() == null || modificationBox.getSelectedItem() == null)
						return false;
					else return true;
				}
				else if (node == uploadMusic){
					if (personField.getText().equals("") || albumBox.getSelectedItem() == null || genreField.getText().equals(""))
						return false;
					else return true;
				}
				else if (node == uploadMovies){
					if ( visibilityBox.getSelectedItem() == null || modificationBox.getSelectedItem() == null || yearField.getText().equals(""))
						return false;
					else return true;
				}
				else if (node == uploadSeries){
					if (visibilityBox.getSelectedItem() == null || modificationBox.getSelectedItem() == null || yearField.getText().equals("")|| genreField.getText().equals(""))
						return false;
					else return true;
				}
				else
					return false;
			}
		}
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == mnQuitter){
			SocketManager.getInstance().logout();
			System.exit(0);
		} else if(e.getSource() == mnAide){
			if(Desktop.isDesktopSupported())
			{
				try {
					Desktop.getDesktop().browse(new URI("http://mediacenter.nukama.be/support.php"));
				} catch (IOException | URISyntaxException e1){
					e1.printStackTrace();
				}
			}		
		} else if(e.getSource() == mnProfil){
			clear();
			setProfilPane();
			initFields("profil");
			centerPane.removeAll();
			centerPane.setLayout(new GridBagLayout());
			centerPane.add(profilePane, new GridBagConstraints());
			profilePane.repaint(); profilePane.revalidate();
			centerPane.revalidate();
		} else if(e.getSource() == modifyButton){
			if(SocketManager.getInstance().modifyUser(String.valueOf(passField.getPassword())))
				JOptionPane.showMessageDialog(getContentPane(),
						"Le mot de passe a été changé avec succès!",
						"Modifications effectuées",
						JOptionPane.INFORMATION_MESSAGE);
			else
				JOptionPane.showMessageDialog(getContentPane(),
						"Le mot de passe n'a pas été changé!",
						"Modifications annulées",
						JOptionPane.ERROR_MESSAGE);
			modifyButton.setEnabled(false);
		} else if(e.getSource() == modifyPassButton){
			clear();
			setPassDialog();
		} else if (e.getSource() == confirmButton){
			if(String.valueOf(passField.getPassword()).isEmpty() && String.valueOf(confirmPassField.getPassword()).isEmpty()){
				JOptionPane.showMessageDialog(getContentPane(),
						"Le nouveau mot de passe n'a pas été entré!",
						"Aucun mot de passe!",
						JOptionPane.ERROR_MESSAGE);
			} else if(!String.valueOf(passField.getPassword()).equals(String.valueOf(confirmPassField.getPassword()))){
				JOptionPane.showMessageDialog(getContentPane(),
						"Les mots de passe ne correspondent pas",
						"Mauvais mot de passe!",
						JOptionPane.ERROR_MESSAGE);
			} else{
				if(String.valueOf(passField.getPassword()).length() < 8){
					JOptionPane.showMessageDialog(getContentPane(),
							"Les mots de passe doivent contenir au minimum 8 caractères",
							"Mauvais mot de passe!",
							JOptionPane.ERROR_MESSAGE);
				}
				else{
					modifyButton.setEnabled(true);
					passDialog.dispose();
				}

			}
		} else if (e.getSource() == cancelButton){
			passDialog.dispose();
			modifyButton.setEnabled(false);
		} else if(e.getSource() == addButton){
			if (node == userNode){
				if (!verify()){
					JOptionPane.showMessageDialog(getContentPane(),
							"Tous les champs requis n'ont pas été rempli",
							"Pas asssez de données",
							JOptionPane.ERROR_MESSAGE);
				}
				else if(!String.valueOf(passField.getPassword()).equals(String.valueOf(confirmPassField.getPassword()))){
					JOptionPane.showMessageDialog(getContentPane(),
							"Passwords do not match",
							"Bad Credentials",
							JOptionPane.ERROR_MESSAGE);
				}
				else if(passField.getPassword().length < 8){
					JOptionPane.showMessageDialog(getContentPane(),
							"Passwords must be at least 8 characters",
							"Bad Credentials",
							JOptionPane.ERROR_MESSAGE);
				}
				else{
					long sql = 0;
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					try {
						Date parsed = format.parse(birthField.getText());
				        sql = parsed.getTime();
					} catch (ParseException e1) {
						JOptionPane.showMessageDialog(getContentPane(),
								"Date must be encoded as dd/MM/YYYY",
								"Bad Date",
								JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
						return;
					}
					Profil tmp = new Profil(userField.getText(), Crypter.encrypt(passField.getPassword().toString()), mailField.getText(), firstNameField.getText(),
							lastNameField.getText(), new java.sql.Date(sql), ((Permissions)permissionsBox.getSelectedItem()).getId());
					if(SocketManager.getInstance().createUser(tmp))
						JOptionPane.showMessageDialog(getContentPane(),
								"Un nouveau utilisateur a été crée!",
								"Ajout effectué",
								JOptionPane.INFORMATION_MESSAGE);
					else
						JOptionPane.showMessageDialog(getContentPane(),
								"Un nouveau utilisateur n'a pas pu etre crée",
								"Echec",
								JOptionPane.WARNING_MESSAGE);
				}
			}
			else{
				if (!verify()){
					JOptionPane.showMessageDialog(getContentPane(),
							"Tous les champs requis n'ont pas été rempli",
							"Pas asssez de données",
							JOptionPane.ERROR_MESSAGE);
				}
				else{
					if (node == uploadAlbums){
						fileC = new AlbumCollector(titleField.getText(), yearField.getText(), (int)((Permissions) modificationBox.getSelectedItem()).getLevel(), 
								(int)((Permissions) visibilityBox.getSelectedItem()).getLevel(), personField.getText(), synopsisField.getText(), genreField.getText());
					} else if (node == uploadSeries){
						fileC = new SeriesCollector(titleField.getText(), yearField.getText(), (int)((Permissions) modificationBox.getSelectedItem()).getLevel(), 
								(int)((Permissions) visibilityBox.getSelectedItem()).getLevel(), synopsisField.getText(), genreField.getText()); 
					}
					if(SocketManager.getInstance().sendMeta(fileC)){
						try {
							if (fileC instanceof AlbumCollector)
								SocketManager.getInstance().getList("albums");
							else 
								SocketManager.getInstance().getList("series");
						} catch (ClassNotFoundException | IOException e1) {
							JOptionPane.showMessageDialog(getContentPane(),
									"Impossible de récupérer la liste mise à jour après l'ajout d'une série ou d'un album."
											+ "Veuillez relancer l'application",
											"Erreur de mise à jour",
											JOptionPane.WARNING_MESSAGE);
							e1.printStackTrace();
						}	
						populateLists();
						JOptionPane.showMessageDialog(getContentPane(),
								"Votre série / album a bien été ajouté!",
								"Ajout effectué",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		} else if(e.getSource() == uploadButton){
			if (!verify()){
				JOptionPane.showMessageDialog(getContentPane(),
						"Tous les champs requis n'ont pas été rempli",
						"Pas asssez de données",
						JOptionPane.ERROR_MESSAGE);
			} else{
				//Instances of javax.swing.SwingWorker are not reusuable, so
				//we create new instances as needed.
				if (node == uploadBooks){
					fileC = new BookCollector(titleField.getText(), yearField.getText(), (int)((Permissions) modificationBox.getSelectedItem()).getLevel(), 
							(int)((Permissions) visibilityBox.getSelectedItem()).getLevel(), fc.getSelectedFile().getName(), personField.getText(), genreField.getText(), synopsisField.getText());					
					messagePB = "Téléversement";
				} else if (node == uploadEpisodes){
					fileC = new EpisodeCollector(titleField.getText(), fc.getSelectedFile().getName(), ((SeriesCollector)seriesBox.getSelectedItem()).getId(), 
							((SeriesCollector)seriesBox.getSelectedItem()).getTitle(), personField.getText(), seasonField.getText(), chronoField.getText()); 
					messagePB = "Conversion... (cela peut prendre un certain temps)";
				} else if (node == uploadImages){
					fileC = new ImageCollector(titleField.getText(), yearField.getText(), (int)((Permissions) modificationBox.getSelectedItem()).getLevel(), 
							(int)((Permissions) visibilityBox.getSelectedItem()).getLevel(), fc.getSelectedFile().getName(), personField.getText());
					messagePB = "Téléversement";
				} else if (node == uploadMusic){
					fileC = new AudioCollector(titleField.getText(), fc.getSelectedFile().getName(), personField.getText(), 
							((AlbumCollector) albumBox.getSelectedItem()).getId(), ((AlbumCollector) albumBox.getSelectedItem()).getTitle()); 
					messagePB = "Conversion... (cela peut prendre un certain temps)";
				} else if (node == uploadMovies){
					fileC = new VideoCollector(titleField.getText(), yearField.getText(), (int)((Permissions) modificationBox.getSelectedItem()).getLevel(), 
							(int)((Permissions) visibilityBox.getSelectedItem()).getLevel(), fc.getSelectedFile().getName(), personField.getText(), genreField.getText(), synopsisField.getText()); 
					messagePB = "Conversion... (cela peut prendre un certain temps)";
				}
				uTask = new UploadTask(chooseDirectory(node), fc.getSelectedFile(), fileC);
				uTask.addPropertyChangeListener(this);
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				uTask.execute();
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						progressBar(messagePB);
						setCursor(null); //turn off the wait cursor
					}
				});
				clear();
			}
		} else if(e.getSource() == clearButton){
			clear();
		} else{
			JFileChooser theFileChooser = (JFileChooser)e.getSource();
			String command = e.getActionCommand();
			if (command.equals(JFileChooser.APPROVE_SELECTION)) {
				File selectedFile = theFileChooser.getSelectedFile();
				if (((double)(((selectedFile.length()/1024)/1024/1024))) > 10){
					int n = JOptionPane.showConfirmDialog((JPanel) getContentPane(),
							"Le fichier que vous souhaitez téléverser est plus grand\n"
									+ "que 10Go, êtes-vous sûr de vouloir\n"
									+ "le téléverser tout de même?",
									"Avertissement de téléversement",
									JOptionPane.YES_NO_OPTION);
					if (n == JOptionPane.NO_OPTION) {
						fc.cancelSelection();
						uploadButton.setEnabled(false);
					} else{
						titleField.setText(selectedFile.getName().substring(0, selectedFile.getName().lastIndexOf(".")));
						uploadButton.setEnabled(true);
					}
				} else{
					titleField.setText(selectedFile.getName().substring(0, selectedFile.getName().lastIndexOf(".")));
					uploadButton.setEnabled(true);
				}
			} else if (command.equals(JFileChooser.CANCEL_SELECTION)) {
				titleField.setText("");
				uploadButton.setEnabled(false);
			}
		}
	}

	public static String chooseDirectory(String node) {
		if (node == uploadAlbums) return "Music";
		else if (node == uploadBooks) return "Books";
		else if (node == uploadEpisodes) return "Series";
		else if (node == uploadImages) return "Images";
		else if (node == uploadMusic) return "Music";
		else if (node == uploadMovies) return "Movies";
		else if (node == uploadSeries) return "Series";
		else return null;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == uploadNode){
			if(uploadNode.getSelectedComponent() != null) uploadNode.getSelectedComponent().revalidate();
			else uploadNode.revalidate();
		} else if(e.getSource() == mediaNode){			
			if(mediaNode.getSelectedComponent() != null) mediaNode.getSelectedComponent().revalidate();
			else mediaNode.revalidate();
		} else if(e.getSource() == usersNode){
			if(usersNode.getSelectedComponent() != null) usersNode.getSelectedComponent().revalidate();
			else usersNode.revalidate();
		} else if(e.getSource() == menuBar){
			if(menuBar.getSelectedComponent() != null) menuBar.getSelectedComponent().revalidate();
		}
		//topPane.revalidate();
		//centerPane.revalidate();
	}
	
}