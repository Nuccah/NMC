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
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import model.AlbumCollector;
import model.AudioCollector;
import model.BookCollector;
import model.Config;
import model.EpisodeCollector;
import model.ImageCollector;
import model.Lists;
import model.MetaDataCollector;
import model.Permissions;
import model.Profil;
import model.SeriesCollector;
import model.VideoCollector;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import controller.SocketManager;
import controller.TransferManager;

/**
 * Fenêtre principale du programme
 * @author Derek
 *
 */

public class Dashboard extends JFrame implements Runnable, ActionListener, TreeSelectionListener, PropertyChangeListener{
	private static final long serialVersionUID = -5998048938167814342L;
	private JPanel topPane;
	private JPanel leftPane;
	private JPanel rightPane;
	private JPanel centerPane;
	private JPanel bottomPane;
	private JPanel uploadDataPane;

	private JButton uploadButton = new JButton("Upload");
	private JButton addButton = new JButton("Confirm");
	private JButton clearButton = new JButton("Clear");
	private JButton mnProfil = new JButton("Profil");
	private JButton mnAide = new JButton("Aide");
	private JButton mnParametres = new JButton("Parametres");
	private JButton mnQuitter = new JButton("Quitter");

	private JLabel titleLabel = new JLabel("Title");
	private JLabel yearLabel = new JLabel("Year");
	private JLabel authorLabel = new JLabel("Author");
	private JLabel synopsisLabel = new JLabel("Synopsis");
	private JLabel genreLabel = new JLabel("Genre");
	private JLabel photographerLabel = new JLabel("Photographer");
	private JLabel artistLabel = new JLabel("Artist");
	private JLabel directorLabel = new JLabel("Director");
	private JLabel visibilityLabel = new JLabel("Visibility Level");
	private JLabel modificationLabel = new JLabel("Modification Level");
	private JLabel albumLabel = new JLabel("Album");
	private JLabel seasonLabel = new JLabel("Season");
	private JLabel chronoLabel = new JLabel("Chronology");
	private JLabel seriesLabel = new JLabel("Series");

	private JTree menuBar;
	private JFileChooser fc;

	private DefaultMutableTreeNode node;
	private DefaultMutableTreeNode home = new DefaultMutableTreeNode("Home");
	private DefaultMutableTreeNode mediaNode = new DefaultMutableTreeNode("Media");
	private DefaultMutableTreeNode uploadNode = new DefaultMutableTreeNode("Upload");
	private DefaultMutableTreeNode usersNode = new DefaultMutableTreeNode("User Admin");
	private DefaultMutableTreeNode seriesNode = new DefaultMutableTreeNode("Series");
	private DefaultMutableTreeNode musicNode = new DefaultMutableTreeNode("Music");

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
	private List<JTextField> fieldList = new ArrayList<JTextField>();
	private List<JComboBox<Permissions>> cbPList = new ArrayList<JComboBox<Permissions>>();

	private FileFilter videoFilter = new FileNameExtensionFilter("Video file", "mp4", "avi", "mkv", "flv", "mov", "wmv", "vob", "3gp", "3g2");
	private FileFilter musicFilter = new FileNameExtensionFilter("Music file", "aac", "mp3", "wav", "wma", "flac");
	private FileFilter bookFilter = new FileNameExtensionFilter("Book file", "pdf", "ebook", "epub", "cbr", "cbz");
	private FileFilter imageFilter = new FileNameExtensionFilter("Image file", "jpg", "jpeg", "png", "gif", "bmp");

	private GridBagConstraints lc = new GridBagConstraints();
	private GridBagConstraints rc = new GridBagConstraints();
	private GridBagConstraints rcc = new GridBagConstraints();

	private CellConstraints cc;
	private JDialog dlg;
	private JProgressBar progressBar;
	private UploadTask uTask;
	private static Dashboard instance = null;

	AlbumCollector albumC;
	SeriesCollector seriesC;
	MetaDataCollector fileC;

	/**
	 * Creates instance of Dashboard
	 * @return
	 */
	public static Dashboard getInstance(){
		if(instance == null) instance = new Dashboard();
		return instance;
	}

	/**
	 * Initialise la fenêtre et ses composants
	 */
	protected Dashboard() {
		super(Config.getInstance().getProp("base_title")+"Nukama Media Center");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					UIManager.getLookAndFeelDefaults().put("Panel.background", Color.WHITE);
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look and feel.
		}
		GridBagLayout main = new GridBagLayout();
		URL iconURL = getClass().getResource("nmc.png");
		ImageIcon icon = new ImageIcon(iconURL);
		setIconImage(icon.getImage());
		getContentPane().setLayout(main);
		setSize(new Dimension(1024,768));
		setMinimumSize(new Dimension(1024,768));
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		setBounds((width/2) - (width/3), (height/2) - (height/3), (int)(width/1.5), (int)(height/1.5));
		GridBagConstraints tc = new GridBagConstraints(), cc = new GridBagConstraints(), bc = new GridBagConstraints();
		tc.gridx = 0; tc.gridy = 0; tc.weightx = 1; tc.weighty = 0.1; tc.fill = GridBagConstraints.BOTH;
		cc.gridx = 0; cc.gridy = 1; cc.weightx = 1; cc.weighty = 0.8; cc.fill = GridBagConstraints.BOTH;
		bc.gridx = 0; bc.gridy = 2; bc.weightx = 1; bc.weighty = 0.1; bc.fill = GridBagConstraints.BOTH;

		topPane = new JPanel();
		centerPane = new JPanel();
		leftPane = new JPanel();
		rightPane = new JPanel();
		bottomPane = new JPanel();
		uploadDataPane = new JPanel();
		fc = new JFileChooser();


		topPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		centerPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		leftPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		rightPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		bottomPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		leftPane.setLayout(new GridLayout(1,1));
		rightPane.setLayout(new GridLayout(1,1));
		centerPane.setLayout(new GridBagLayout());

		System.out.println(getSize().toString());

		lc.gridx = 0; lc.gridy = 0; lc.weightx = 0.25; lc.weighty = 1; lc.fill = GridBagConstraints.BOTH;
		rc.gridx = 1; rc.gridy = 0; rc.weightx = 0.75; rc.weighty = 1; rc.fill = GridBagConstraints.BOTH;

		centerPane.add(leftPane, lc);
		centerPane.add(rightPane, rc);
		getContentPane().add(topPane, tc);
		getContentPane().add(centerPane, cc);
		getContentPane().add(bottomPane, bc);

		leftPane.setMinimumSize(new Dimension(175, 600));
		centerPane.setMinimumSize(new Dimension(900, 700));
		rightPane.setMinimumSize(new Dimension(675, 600));

		pack();

		titleBar();
		menuBar();
		bottomBar();

		modifyFileChooser(fc.getComponents());

		fieldList.add(titleField);
		fieldList.add(yearField);
		fieldList.add(synopsisField);
		fieldList.add(genreField);
		fieldList.add(personField);
		fieldList.add(chronoField);
		fieldList.add(seasonField);
		cbPList.add(modificationBox);
		cbPList.add(visibilityBox);

		clear();
		populateLists();		

		uploadButton.addActionListener(this);
		clearButton.addActionListener(this);
		addButton.addActionListener(this);
	}

	private void modifyFileChooser(Component[] components) {
		fc.setAcceptAllFileFilterUsed(false);
		fc.setControlButtonsAreShown(false);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.addActionListener(this);
		fc.setMinimumSize(new Dimension(600,600));
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

	/**
	 * Barre de menu titulaire
	 */
	public void titleBar(){

		mnProfil.setHorizontalAlignment(SwingConstants.LEFT);
		topPane.add(mnProfil);

		mnParametres.setHorizontalAlignment(SwingConstants.LEFT);
		topPane.add(mnParametres);

		mnAide.setHorizontalAlignment(SwingConstants.LEFT);
		topPane.add(mnAide);

		mnQuitter.setHorizontalAlignment(SwingConstants.LEFT);
		mnQuitter.addActionListener(this);
		topPane.add(mnQuitter);

		topPane.repaint();
		topPane.revalidate();
	}

	/**
	 * Bar de menu pour les gestions possibles
	 */
	private void menuBar() {
		//create the child nodes
		mediaNode.add(new DefaultMutableTreeNode("Books"));
		mediaNode.add(new DefaultMutableTreeNode("Images"));
		mediaNode.add(new DefaultMutableTreeNode("Music"));
		mediaNode.add(new DefaultMutableTreeNode("Movies"));
		mediaNode.add(new DefaultMutableTreeNode("Series"));

		uploadNode.add(new DefaultMutableTreeNode("Books"));
		uploadNode.add(new DefaultMutableTreeNode("Images"));
		uploadNode.add(musicNode);
		uploadNode.add(new DefaultMutableTreeNode("Movies"));
		uploadNode.add(seriesNode);

		seriesNode.add(new DefaultMutableTreeNode("Add New Episodes"));
		seriesNode.add(new DefaultMutableTreeNode("Add New Series"));
		musicNode.add(new DefaultMutableTreeNode("Add New Music"));
		musicNode.add(new DefaultMutableTreeNode("Add New Albums"));

		usersNode.add(new DefaultMutableTreeNode("Create User"));
		usersNode.add(new DefaultMutableTreeNode("Administration"));
		usersNode.add(new DefaultMutableTreeNode("Permissions"));        
		usersNode.add(new DefaultMutableTreeNode("Preferences"));

		//add the child nodes to the root node
		home.add(mediaNode);
		home.add(uploadNode);
		home.add(usersNode);

		//create the tree by passing in the root node
		menuBar = new JTree(home);
		menuBar.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		menuBar.addTreeSelectionListener(this);
		leftPane.add(new JScrollPane(menuBar));

	}

	/**
	 * Bottom bar containing Copyright information
	 */
	private void bottomBar() {
		JTextPane txtpnTest = new JTextPane();
		txtpnTest.setEditable(false);
		txtpnTest.setText("Copyright 2014 - nmc_team@nukama.be - Developed By Antoine Ceyssens & Derek Van Hove" );
		bottomPane.add(txtpnTest);
	}

	/**
	 * Home page of client program
	 * @param node --- TO DELETE
	 */
	private void homePage(DefaultMutableTreeNode node) {
		rightPane.removeAll();
		rightPane.add(new JTextArea(node.toString()));
		rightPane.revalidate();
	}

	/**
	 * Switchcase redirection to appropriate methods
	 * @param node
	 */
	private void parentPage(DefaultMutableTreeNode node) {
		switch (node.toString()) {
		case "Media": mediaResultSet(node); break;
		case "Upload": uploadFilePage(node); break;
		case "User Administration": userAdmin(node); break;
		default: uploadFilePage(node); break;
		}
	}

	/**
	 * Creation and layout of required upload components
	 * @param node
	 */
	private void uploadFilePage(DefaultMutableTreeNode node) {
		rightPane.removeAll();
		uploadDataPane.removeAll();
		if (node.toString() == "Upload"){
			rightPane.setLayout(new GridLayout(1,1));
			rightPane.add(uploadDataPane);
		}
		else if(node.toString() == "Music" || node.toString() == "Series"){
			rightPane.setLayout(new GridLayout(1,1));
			rightPane.add(uploadDataPane);
		}
		else{
			clear();
			FormLayout layout = new FormLayout(
					"right:pref, 4dlu, fill:150dlu",
					"pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 10dlu, pref, 10dlu, pref, 10dlu");
			layout.setRowGroups(new int[][]{{1, 3, 5, 7, 9, 11,13,15,17}});
			uploadDataPane.setLayout(layout);
			cc = new CellConstraints();
			if(node.toString() == "Add New Albums" || node.toString() == "Add New Series"){
				rightPane.setLayout(new GridLayout(1,1));
				addButton.setEnabled(true);
				uploadDataPane.add(addButton,cc.xy(1, 17));
				rightPane.add(uploadDataPane);
			}
			else{
				rightPane.setLayout(new GridBagLayout());
				rcc.weightx = 1; rcc.weighty = 1; rcc.fill = GridBagConstraints.BOTH;
				rightPane.add(fc, rcc);
				rightPane.add(uploadDataPane, rcc);
				uploadButton.setEnabled(false);
				uploadDataPane.add(uploadButton,cc.xy(1, 17));
			}
			
			uploadDataPane.add(titleLabel,cc.xy(1, 1)); uploadDataPane.add(titleField,cc.xy(3, 1));
			switch (node.toString()) {
			case "Books":
				uploadDataPane.add(yearLabel,cc.xy(1, 3)); uploadDataPane.add(yearField,cc.xy(3, 3));
				uploadDataPane.add(authorLabel,cc.xy(1, 5) ); uploadDataPane.add(personField,cc.xy(3, 5));
				uploadDataPane.add(synopsisLabel,cc.xy(1, 7)); uploadDataPane.add(synopsisField,cc.xy(3, 7));
				uploadDataPane.add(genreLabel,cc.xy(1, 9)); uploadDataPane.add(genreField,cc.xy(3, 9));
				uploadDataPane.add(visibilityLabel,cc.xy(1, 11)); uploadDataPane.add(visibilityBox,cc.xy(3, 11)); 
				uploadDataPane.add(modificationLabel,cc.xy(1, 13)); uploadDataPane.add(modificationBox,cc.xy(3, 13));
				fc.setFileFilter(bookFilter);
				break;
			case "Images":
				uploadDataPane.add(yearLabel,cc.xy(1, 3)); uploadDataPane.add(yearField,cc.xy(3, 3));
				uploadDataPane.add(photographerLabel,cc.xy(1, 5)); uploadDataPane.add(personField,cc.xy(3, 5));
				uploadDataPane.add(visibilityLabel,cc.xy(1, 7)); uploadDataPane.add(visibilityBox,cc.xy(3, 7)); 
				uploadDataPane.add(modificationLabel,cc.xy(1, 9)); uploadDataPane.add(modificationBox,cc.xy(3, 9));
				fc.setFileFilter(imageFilter);
				break;
			case "Add New Music": 
				uploadDataPane.add(albumLabel,cc.xy(1, 3)); uploadDataPane.add(albumBox,cc.xy(3, 3));
				uploadDataPane.add(artistLabel,cc.xy(1, 5)); uploadDataPane.add(personField,cc.xy(3, 5));
				fc.setFileFilter(musicFilter);
				break;
			case "Movies":
				uploadDataPane.add(yearLabel,cc.xy(1, 3)); uploadDataPane.add(yearField,cc.xy(3, 3));
				uploadDataPane.add(directorLabel,cc.xy(1, 5)); uploadDataPane.add(personField,cc.xy(3, 5));
				uploadDataPane.add(genreLabel,cc.xy(1, 7)); uploadDataPane.add(genreField,cc.xy(3, 7));
				uploadDataPane.add(synopsisLabel,cc.xy(1, 9)); uploadDataPane.add(synopsisField,cc.xy(3, 9));
				uploadDataPane.add(visibilityLabel,cc.xy(1, 11)); uploadDataPane.add(visibilityBox,cc.xy(3, 11)); 
				uploadDataPane.add(modificationLabel,cc.xy(1, 13)); uploadDataPane.add(modificationBox,cc.xy(3, 13));
				fc.setFileFilter(videoFilter);
				break;
			case "Add New Episodes": 
				uploadDataPane.add(seriesLabel,cc.xy(1, 3)); uploadDataPane.add(seriesBox,cc.xy(3, 3));
				uploadDataPane.add(directorLabel,cc.xy(1, 5) ); uploadDataPane.add(personField,cc.xy(3, 5));
				uploadDataPane.add(seasonLabel,cc.xy(1, 7) ); uploadDataPane.add(seasonField,cc.xy(3, 7));
				uploadDataPane.add(chronoLabel,cc.xy(1, 9) ); uploadDataPane.add(chronoField,cc.xy(3, 9));
				fc.setFileFilter(videoFilter);
				break;
			case "Add New Series":
				uploadDataPane.add(yearLabel,cc.xy(1, 3)); uploadDataPane.add(yearField,cc.xy(3, 3));
				uploadDataPane.add(synopsisLabel,cc.xy(1, 5) ); uploadDataPane.add(synopsisField,cc.xy(3, 5));
				uploadDataPane.add(genreLabel,cc.xy(1, 7) ); uploadDataPane.add(genreField,cc.xy(3, 7));
				uploadDataPane.add(visibilityLabel,cc.xy(1, 9)); uploadDataPane.add(visibilityBox,cc.xy(3, 9)); 
				uploadDataPane.add(modificationLabel,cc.xy(1, 11)); uploadDataPane.add(modificationBox,cc.xy(3, 11));
				break;
			case "Add New Albums":
				uploadDataPane.add(yearLabel,cc.xy(1, 3)); uploadDataPane.add(yearField,cc.xy(3, 3));
				uploadDataPane.add(artistLabel,cc.xy(1, 5)); uploadDataPane.add(personField,cc.xy(3, 5));
				uploadDataPane.add(genreLabel,cc.xy(1, 7) ); uploadDataPane.add(genreField,cc.xy(3, 7));
				uploadDataPane.add(synopsisLabel,cc.xy(1, 9) ); uploadDataPane.add(synopsisField,cc.xy(3, 9));
				uploadDataPane.add(visibilityLabel,cc.xy(1, 11)); uploadDataPane.add(visibilityBox,cc.xy(3, 11)); 
				uploadDataPane.add(modificationLabel,cc.xy(1, 13)); uploadDataPane.add(modificationBox,cc.xy(3, 13));

				break;
			default: break;
			}
			uploadDataPane.add(clearButton,cc.xy(3, 17));
		}
		uploadDataPane.repaint(); uploadDataPane.revalidate();
		rightPane.revalidate();
	}

	/**
	 * Switchcase redirection to appropriate methods
	 * @param node
	 */
	private void mediaResultSet(DefaultMutableTreeNode node) {
		switch (node.toString()) {
		case "Media": homePage(node); break;
		case "Books": homePage(node); break;
		case "Images": homePage(node); break;
		case "Music": homePage(node); break;
		case "Movies": homePage(node); break;
		case "Series": homePage(node); break;
		default: break;
		}
	}

	/**
	 * Switchcase redirection to appropriate methods
	 * @param node
	 */
	private void userAdmin(DefaultMutableTreeNode node) {
		switch (node.toString()) {
		case "Create User": homePage(node); break;
		case "Administration": homePage(node); break;
		case "Permissions": homePage(node); break;
		case "Preferences": homePage(node); break;
		default: break;
		}
	}

	public void clear(){
		for (JTextField fl : fieldList) 
			fl.setText(null);
		seriesBox.setSelectedItem(null);
		albumBox.setSelectedItem(null);
		for (JComboBox<Permissions> cbl : cbPList)
			cbl.setSelectedItem(null);
		uploadButton.setEnabled(false);
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
		}
		for(SeriesCollector series : Lists.getInstance().getSeriesList()){
			seriesBox.addItem(series);
		}
		for(AlbumCollector albums : Lists.getInstance().getAlbumList()){
			albumBox.addItem(albums);
		}
	}

	/** Determines whether or not metadata fields are empty or not
	 * @param node the String of the node selected
	 * @return boolean whether metadata fields are empty or not
	 */
	public boolean verify(String node){
		if (titleField == null)
			return false;
		else{
			switch (node) {
			case "Books":
				if (personField == null || visibilityBox.getSelectedItem() == null || modificationBox.getSelectedItem() == null || yearField == null)
					return false;
				else return true;
			case "Images":
				if (visibilityBox.getSelectedItem() == null || modificationBox.getSelectedItem() == null)
					return false;
				else return true;
			case "Add New Music":
				if (personField == null || albumBox.getSelectedItem() == null || genreField == null)
					return false;
				else return true;
			case "Add New Albums":
				if (personField == null || visibilityBox.getSelectedItem() == null || modificationBox.getSelectedItem() == null || yearField == null || genreField == null )
					return false;
				else return true;
			case "Movies":
				if ( visibilityBox.getSelectedItem() == null || modificationBox.getSelectedItem() == null || yearField == null)
					return false;
				else return true;
			case "Add New Series":
				if (visibilityBox.getSelectedItem() == null || modificationBox.getSelectedItem() == null || yearField == null || genreField == null)
					return false;
				else return true;
			case "Add New Episodes":
				if (seriesBox.getSelectedItem() == null)
					return false;
				else return true;	
			default: return true;
			}
		}
	}

	/**
	 * Creation of new progress bar, dialog for each upload
	 */
	public void progressBar() {
		//Create the demo's UI.
		dlg = new JDialog((Frame) getOwner(), "Progress Dialog", true);

		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setString("Converting... (this could take some time)");
		dlg.add(BorderLayout.CENTER, progressBar);
		dlg.add(BorderLayout.NORTH, new JLabel("Progress..."));
		dlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dlg.setSize(300, 75);
		dlg.setLocationRelativeTo((Frame) getOwner());
		dlg.setVisible(true);
	}

	/**
	 * Invoked when task's progress property changes.
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = (Integer) evt.getNewValue();
			progressBar.setValue(progress);
			if(progress == 100) dlg.dispose();
			else progressBar.setString("Uploading");
		} 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == mnQuitter){
			SocketManager.getInstance().logout();
			System.exit(0);
		}
		else if(e.getSource() == addButton){
			if (!verify(node.toString())){
				JOptionPane.showMessageDialog(getContentPane(),
						"Not all data fields have been set",
						"Insufficient Data",
						JOptionPane.ERROR_MESSAGE);
			}
			else{
				switch (node.toString()) {
				case "Add New Albums": fileC = new AlbumCollector(titleField.getText(), yearField.getText(), (int)((Permissions) modificationBox.getSelectedItem()).getLevel(), 
						(int)((Permissions) visibilityBox.getSelectedItem()).getLevel(), personField.getText(), synopsisField.getText(), genreField.getText());
				break;
				case "Add New Series": fileC = new SeriesCollector(titleField.getText(), yearField.getText(), (int)((Permissions) modificationBox.getSelectedItem()).getLevel(), 
						(int)((Permissions) visibilityBox.getSelectedItem()).getLevel(), synopsisField.getText(), genreField.getText()); 
				break;
				default: break;
				}

				SocketManager.getInstance().sendMeta(fileC);

				if(fileC instanceof AlbumCollector) SocketManager.getInstance().getList("albums");
				else SocketManager.getInstance().getList("series");

				populateLists();

				JOptionPane.showMessageDialog(getContentPane(),
						"Your series/album has been successfully added!",
						"Success",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else if(e.getSource() == uploadButton){
			if (!verify(node.toString())){
				JOptionPane.showMessageDialog(getContentPane(),
						"Not all data fields have been set",
						"Insufficient Data",
						JOptionPane.ERROR_MESSAGE);
			}
			else{
				//Instances of javax.swing.SwingWorker are not reusuable, so
				//we create new instances as needed.
				switch (node.toString()) {
				case "Books": fileC = new BookCollector(titleField.getText(), yearField.getText(), (int)((Permissions) modificationBox.getSelectedItem()).getLevel(), 
						(int)((Permissions) visibilityBox.getSelectedItem()).getLevel(), fc.getSelectedFile().getName(), personField.getText(), genreField.getText(), synopsisField.getText());break;
				case "Images": fileC = new ImageCollector(titleField.getText(), yearField.getText(), (int)((Permissions) modificationBox.getSelectedItem()).getLevel(), 
						(int)((Permissions) visibilityBox.getSelectedItem()).getLevel(), fc.getSelectedFile().getName(), personField.getText()); break;
				case "Add New Music": fileC = new AudioCollector(titleField.getText(), fc.getSelectedFile().getName(), personField.getText(), 
						((AlbumCollector) albumBox.getSelectedItem()).getId(), ((AlbumCollector) albumBox.getSelectedItem()).getTitle()); break;
				case "Movies": fileC = new VideoCollector(titleField.getText(), yearField.getText(), (int)((Permissions) modificationBox.getSelectedItem()).getLevel(), 
						(int)((Permissions) visibilityBox.getSelectedItem()).getLevel(), fc.getSelectedFile().getName(), personField.getText(), genreField.getText(), synopsisField.getText()); break;
				case "Add New Episodes": fileC = new EpisodeCollector(titleField.getText(), fc.getSelectedFile().getName(), ((SeriesCollector)seriesBox.getSelectedItem()).getId(), 
						((SeriesCollector)seriesBox.getSelectedItem()).getTitle(), personField.getText(), seasonField.getText(), chronoField.getText()); break;
				default: break;
				}

				uTask = new UploadTask(TransferManager.chooseDirectory(node.toString()), fc.getSelectedFile(), fileC);
				uTask.addPropertyChangeListener(this);
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				uTask.execute();
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						progressBar();
						setCursor(null); //turn off the wait cursor
					}
				});
				clear();
			}
		}
		else if(e.getSource() == clearButton){
			clear();
		}
		else{
			JFileChooser theFileChooser = (JFileChooser)e.getSource();
			String command = e.getActionCommand();
			if (command.equals(JFileChooser.APPROVE_SELECTION)) {
				File selectedFile = theFileChooser.getSelectedFile();
				if (((double)(((selectedFile.length()/1024)/1024/1024))) > 5){
					int n = JOptionPane.showConfirmDialog((JPanel) getContentPane(),
							"The file you wish to upload is larger\n"
									+ "than 10GB, are you sure you wish.\n"
									+ "to upload this file?",
									"Upload Warning",
									JOptionPane.YES_NO_OPTION);
					if (n == JOptionPane.NO_OPTION) {
						fc.cancelSelection();
						uploadButton.setEnabled(false);
					}
					else{
						titleField.setText(selectedFile.getName());
						uploadButton.setEnabled(true);
					}
				}
				else{
					titleField.setText(selectedFile.getName());
					uploadButton.setEnabled(true);
				}
			}  
			else if (command.equals(JFileChooser.CANCEL_SELECTION)) {
				titleField.setText(" ");
				uploadButton.setEnabled(false);
			}
		}
	}

	public void valueChanged(TreeSelectionEvent e) {
		node = (DefaultMutableTreeNode)
				menuBar.getLastSelectedPathComponent();

		/* if nothing is selected */ 
		if (node == null) return;

		/* React to the node selection. */
		if (node.toString() == "Home") homePage(node);
		else{
			switch (node.getParent().toString()) {
			case "Media": mediaResultSet(node); break;
			case "Upload": uploadFilePage(node);	break;
			case "User Administration": userAdmin(node); break;
			default: parentPage(node); break;
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
}