package view;

import java.awt.BorderLayout;
import java.awt.Cursor;
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
import model.MetaDataCollector;
import model.Profil;
import model.SeriesCollector;
import model.VideoCollector;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import controller.SessionManager;

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
	private JLabel chronoLabel = new JLabel("Episode Chronology");
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
	
	private JComboBox<String> albumBox = new JComboBox<String>();
	private JComboBox<String> seriesBox = new JComboBox<String>();
	private JComboBox<String> visibilityBox = new JComboBox<String>();
	private JComboBox<String> modificationBox = new JComboBox<String>();
	private List<JTextField> fieldList = new ArrayList<JTextField>();
	private List<JComboBox<String>> cbList = new ArrayList<JComboBox<String>>();
	
	private FileFilter videoFilter = new FileNameExtensionFilter("Video file", "mp4", "avi", "mkv", "flv", "mov", "wmv", "vob", "3gp", "3g2");
	private FileFilter musicFilter = new FileNameExtensionFilter("Music file", "aac", "mp3", "wav");
	private FileFilter bookFilter = new FileNameExtensionFilter("Book file", "pdf", "ebook", "epub", "cbr", "cbz");
	private FileFilter imageFilter = new FileNameExtensionFilter("Image file", "jpg", "jpeg", "png", "gif", "bmp");
	
	private GridBagConstraints lc = new GridBagConstraints();
	private GridBagConstraints rc = new GridBagConstraints();
	
	private CellConstraints cc;
	private JDialog dlg;
	private JProgressBar progressBar;
	private Task task;
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
		leftPane.setMinimumSize(new Dimension(getSize().width/5, getSize().height));
		lc.gridx = 0; lc.gridy = 0; lc.weightx = 0.25; lc.weighty = 1; lc.fill = GridBagConstraints.BOTH;
		rc.gridx = 1; rc.gridy = 0; rc.weightx = 0.75; rc.weighty = 1; rc.fill = GridBagConstraints.BOTH;

		centerPane.add(leftPane, lc);
		centerPane.add(rightPane, rc);
		getContentPane().add(topPane, tc);
		getContentPane().add(centerPane, cc);
		getContentPane().add(bottomPane, bc);

		titleBar();
		menuBar();
		bottomBar();

		fc.setAcceptAllFileFilterUsed(false);
		fc.setControlButtonsAreShown(false);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.addActionListener(this);

		fieldList.add(titleField);
		fieldList.add(yearField);
		fieldList.add(synopsisField);
		fieldList.add(genreField);
		fieldList.add(personField);
		cbList.add(modificationBox);
		cbList.add(visibilityBox);

		uploadButton.addActionListener(this);
		clearButton.addActionListener(this);
		addButton.addActionListener(this);
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
		txtpnTest.setText("Salut "+Profil.getInstance().getUsername()+
				", ton adresse mail est: "+Profil.getInstance().getMail());
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
			}
			else{
				rightPane.setLayout(new GridLayout(1,2));
				rightPane.add(fc);
				uploadButton.setEnabled(false);
				uploadDataPane.add(uploadButton,cc.xy(1, 17));
			}
			rightPane.add(uploadDataPane);
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

	/**
	 * Creation of new progress bar, dialog for each upload
	 */
	public void progressBar() {
		//Create the demo's UI.
		dlg = new JDialog((Frame) getOwner(), "Progress Dialog", true);

		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		dlg.add(BorderLayout.CENTER, progressBar);
		dlg.add(BorderLayout.NORTH, new JLabel("Progress..."));
		dlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dlg.setSize(300, 75);
		dlg.setLocationRelativeTo((Frame) getOwner());
		dlg.setVisible(true);
	}
	
	public void clear(){
		for (JTextField fl : fieldList) 
			fl.setText("");
		for (JComboBox<?> cbl : cbList)
			cbl.setSelectedItem(null);
		uploadButton.setEnabled(false);
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
				if (personField == null)
					return false;
				else return true;
			case "Add New Albums":
				if (personField == null || visibilityBox.getSelectedItem() == null || modificationBox.getSelectedItem() == null)
					return false;
				else return true;
			case "Movies":
				if ( visibilityBox.getSelectedItem() == null || modificationBox.getSelectedItem() == null || yearField == null)
					return false;
				else return true;
			case "Add New Series":
				if (visibilityBox.getSelectedItem() == null || modificationBox.getSelectedItem() == null)
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
	 * Invoked when task's progress property changes.
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = (Integer) evt.getNewValue();
			progressBar.setValue(progress);
			if(progress == 100) dlg.dispose();
		} 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == mnQuitter){
			SessionManager.getInstance().logout();
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
				case "Add New Albums": albumC = new AlbumCollector(titleField.getText(), Integer.parseInt(yearField.getText()), (int)modificationBox.getSelectedItem(), 
						(int)visibilityBox.getSelectedItem(), personField.getText(), synopsisField.getText(), genreField.getText());break;
				case "Add New Series": seriesC = new SeriesCollector(titleField.getText(), Integer.parseInt(yearField.getText()), (int)modificationBox.getSelectedItem(), 
						(int)visibilityBox.getSelectedItem(), synopsisField.getText(), genreField.getText()); break;
				default: break;
				}
				clear();
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
				case "Books": fileC = new BookCollector(titleField.getText(), Integer.parseInt(yearField.getText()), (int)modificationBox.getSelectedItem(), 
						(int)visibilityBox.getSelectedItem(), fc.getSelectedFile().getName(), personField.getText(), genreField.getText(), synopsisField.getText());break;
				case "Images": fileC = new ImageCollector(titleField.getText(), Integer.parseInt(yearField.getText()), (int)modificationBox.getSelectedItem(), 
						(int)visibilityBox.getSelectedItem(), fc.getSelectedFile().getName(), personField.getText()); break;
				case "Add New Music": fileC = new AudioCollector(titleField.getText(), fc.getSelectedFile().getName(), personField.getText(), (String) albumBox.getSelectedItem()); break;
				case "Movies": fileC = new VideoCollector(titleField.getText(), Integer.parseInt(yearField.getText()), (int)modificationBox.getSelectedItem(), 
						(int)visibilityBox.getSelectedItem(), fc.getSelectedFile().getName(), personField.getText(), genreField.getText(), synopsisField.getText()); break;
				case "Add New Episodes": fileC = new EpisodeCollector(titleField.getText(), fc.getSelectedFile().getName(), (String) seriesBox.getSelectedItem(), personField.getText(), 
						Integer.parseInt(seasonField.getText()), Integer.parseInt(chronoField.getText())); break;
				default: break;
				}
				task = new Task(node.toString(), fc.getSelectedFile(), fileC);
				task.addPropertyChangeListener(this);
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				task.execute();
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