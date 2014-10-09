package view;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import model.Config;
import model.Profil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import controller.SessionManager;
import controller.TransferManager;
/**
 * Fenêtre principale du programme
 * @author Derek
 *
 */

public class Dashboard extends JFrame implements Runnable, ActionListener, TreeSelectionListener{
	private static final long serialVersionUID = -5998048938167814342L;
	private JPanel topPane;
	private JSplitPane splitPane;
	private JPanel leftPane;
	private JPanel rightPane;
	private JPanel centerPane;
	private JPanel bottomPane;
	private GridBagLayout main;
	private JTree menuBar;
	private JFileChooser fc;
	private JPanel uploadDataPane;
	private JButton uploadButton = new JButton("Upload");;
	private JButton clearButton = new JButton("Clear");
	private JButton mnProfil = new JButton("Profil");
	private JButton mnAide = new JButton("Aide");
	private JButton mnParametres = new JButton("Parametres");
	private JButton mnQuitter = new JButton("Quitter");
	private DefaultMutableTreeNode home = new DefaultMutableTreeNode("Home");
	private DefaultMutableTreeNode mediaNode = new DefaultMutableTreeNode("Media");
	private DefaultMutableTreeNode uploadNode = new DefaultMutableTreeNode("Upload");
	private DefaultMutableTreeNode usersNode = new DefaultMutableTreeNode("User Admin");
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
	private JTextField titleField = new JTextField();
	private JTextField yearField = new JTextField();
	private JTextField synopsisField = new JTextField();
	private JTextField genreField = new JTextField();
	private JComboBox<String> visibilityBox = new JComboBox<String>();
	private JComboBox<String> modificationBox = new JComboBox<String>();
	private JTextField personField = new JTextField();
	private FileFilter videoFilter = new FileNameExtensionFilter("Video file", "mp4", "avi", "mkv", "flv", "mov", "wmv", "vob", "3gp", "3g2");
	private FileFilter musicFilter = new FileNameExtensionFilter("Music file", "aac", "mp3", "wav");
	private FileFilter bookFilter = new FileNameExtensionFilter("Book file", "pdf", "ebook", "epub", "cbr", "cbz");
	private FileFilter imageFilter = new FileNameExtensionFilter("Image file", "jpg", "jpeg", "png", "gif", "bmp");
	private List<JTextField> fieldList = new ArrayList<JTextField>();
	private List<JComboBox<String>> cbList = new ArrayList<JComboBox<String>>();
	private CellConstraints cc;
	private JProgressBar pb;

	/**
	 * Initialise la fenêtre et ses composants
	 */
	public Dashboard() {
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
		main = new GridBagLayout();
		main.rowWeights = new double[]{0.0, 1.0, 0.0};
		main.columnWeights = new double[]{1.0};
		getContentPane().setLayout(main);
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		setBounds((width/2) - (width/3), (height/2) - (height/3), (int)(width/1.5), (int)(height/1.5));
		GridBagConstraints c = new GridBagConstraints(), d = new GridBagConstraints(), e = new GridBagConstraints();
		c.gridheight = 1; c.gridwidth = 1; c.gridx = 0; c.gridy = 0; c.weightx = 1; c.weighty = 0.1; c.fill = GridBagConstraints.BOTH;
		d.gridheight = 1; d.gridwidth = 1; d.gridx = 0; d.gridy = 1; d.weightx = 1; d.weighty = 0.8; d.fill = GridBagConstraints.BOTH;
		e.gridheight = 1; e.gridwidth = 1; e.gridx = 0; e.gridy = 2; e.weightx = 1; e.weighty = 0.1; e.fill = GridBagConstraints.BOTH;

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
		centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.X_AXIS));

		centerPane.add(leftPane);
		centerPane.add(rightPane);
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane, rightPane);
		centerPane.add(splitPane);

		getContentPane().add(topPane, c);
		getContentPane().add(centerPane, d);
		getContentPane().add(bottomPane, e);

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
	}

	private void menuBar() {
		//create the child nodes
		mediaNode.add(new DefaultMutableTreeNode("Books"));
		mediaNode.add(new DefaultMutableTreeNode("Images"));
		mediaNode.add(new DefaultMutableTreeNode("Music"));
		mediaNode.add(new DefaultMutableTreeNode("Movies"));
		mediaNode.add(new DefaultMutableTreeNode("Series"));

		uploadNode.add(new DefaultMutableTreeNode("Books"));
		uploadNode.add(new DefaultMutableTreeNode("Images"));
		uploadNode.add(new DefaultMutableTreeNode("Music"));
		uploadNode.add(new DefaultMutableTreeNode("Movies"));
		uploadNode.add(new DefaultMutableTreeNode("Series"));

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

	private void bottomBar() {
		JTextPane txtpnTest = new JTextPane();
		txtpnTest.setEditable(false);
		txtpnTest.setText("Salut "+Profil.getInstance().getUsername()+
				", ton adresse mail est: "+Profil.getInstance().getMail());
		bottomPane.add(txtpnTest);
	}

	private void homePage(DefaultMutableTreeNode node) {
		rightPane.removeAll();
		rightPane.add(new JTextArea(node.toString()));
		rightPane.revalidate();
	}

	private void parentPage(DefaultMutableTreeNode node) {
		switch (node.toString()) {
		case "Media": mediaResultSet(node); break;
		case "Upload": uploadFilePage(node); break;
		case "User Administration": userAdmin(node); break;
		default: homePage(node); break;
		}
	}

	private void uploadFilePage(DefaultMutableTreeNode node) {
		rightPane.removeAll();
		uploadDataPane.removeAll();
		if (node.toString() == "Upload"){
			rightPane.setLayout(new GridLayout(1,1));
			rightPane.add(uploadDataPane);
		}
		else{
			int rows = 0;
			FormLayout layout = new FormLayout(
					"right:pref, 4dlu, fill:150dlu",
					"pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 10dlu, pref, 10dlu, pref, 10dlu");
			rightPane.setLayout(new GridLayout(1,2));
			rightPane.add(fc);
			rightPane.add(uploadDataPane);
			layout.setRowGroups(new int[][]{{1, 3, 5, 7, 9, 11,13,15,17}});
			uploadDataPane.setLayout(layout);
			cc = new CellConstraints();
			uploadDataPane.add(titleLabel,cc.xy(1, 1)); uploadDataPane.add(titleField,cc.xy(3, 1));
			uploadDataPane.add(yearLabel,cc.xy(1, 3)); uploadDataPane.add(yearField,cc.xy(3, 3));
			switch (node.toString()) {
			case "Books":
				uploadDataPane.add(authorLabel,cc.xy(1, 5) ); uploadDataPane.add(personField,cc.xy(3, 5));
				uploadDataPane.add(synopsisLabel,cc.xy(1, 7)); uploadDataPane.add(synopsisField,cc.xy(3, 7));
				uploadDataPane.add(genreLabel,cc.xy(1, 9)); uploadDataPane.add(genreField,cc.xy(3, 9));
				fc.setFileFilter(bookFilter);
				rows=9;
				break;
			case "Images": 
				uploadDataPane.add(photographerLabel,cc.xy(1, 5) ); uploadDataPane.add(personField,cc.xy(3, 5));
				fc.setFileFilter(imageFilter);
				rows=5;
				break;
			case "Music": 
				uploadDataPane.add(artistLabel,cc.xy(1, 5) ); uploadDataPane.add(personField,cc.xy(3, 5));
				uploadDataPane.add(genreLabel,cc.xy(1, 7) ); uploadDataPane.add(genreField,cc.xy(3, 7));
				fc.setFileFilter(musicFilter);
				rows=7;
				break;
			case "Movies": 
				uploadDataPane.add(directorLabel,cc.xy(1, 5)); uploadDataPane.add(personField,cc.xy(3, 5));
				uploadDataPane.add(genreLabel,cc.xy(1, 7) ); uploadDataPane.add(genreField,cc.xy(3, 7));
				uploadDataPane.add(synopsisLabel,cc.xy(1, 9) ); uploadDataPane.add(synopsisField,cc.xy(3, 9));
				fc.setFileFilter(videoFilter);
				rows=9;
				break;
			case "Series": 
				uploadDataPane.add(synopsisLabel,cc.xy(1, 5) ); uploadDataPane.add(synopsisField,cc.xy(3, 5));
				uploadDataPane.add(genreLabel,cc.xy(1, 7) ); uploadDataPane.add(genreField,cc.xy(3, 7));
				fc.setFileFilter(videoFilter);
				rows=7;
				break;
			default: break;
			}

			uploadDataPane.add(visibilityLabel,cc.xy(1, (rows+2))); uploadDataPane.add(visibilityBox,cc.xy(3, (rows+2))); 
			uploadDataPane.add(modificationLabel,cc.xy(1, (rows+4))); uploadDataPane.add(modificationBox,cc.xy(3, (rows+4)));
			uploadDataPane.add(uploadButton,cc.xy(1, 17));
			uploadDataPane.add(clearButton,cc.xy(3, 17));

			//			pb = new JProgressBar(0, 100);
			//			pb.setValue(0);
			//			pb.setStringPainted(true);
			//			uploadDataPane.add(pb,cc.xy(3, 15));
		}
		uploadDataPane.repaint(); uploadDataPane.revalidate();
		rightPane.revalidate();
	}

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

	private void userAdmin(DefaultMutableTreeNode node) {
		switch (node.toString()) {
		case "Create User": homePage(node); break;
		case "Administration": homePage(node); break;
		case "Permissions": homePage(node); break;
		case "Preferences": homePage(node); break;
		default: break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == mnQuitter){
			SessionManager.getInstance().logout();
			System.exit(0);
		}
		else if(e.getSource() == uploadButton){
			Thread queryThread = new Thread() {
				public void run() {
					runQueries();
				}
			};
			queryThread.start();
			updateProgress();
		}
		else if(e.getSource() == clearButton){
			for (JTextField fl : fieldList) 
				fl.setText("");
			for (JComboBox<?> cbl : cbList)
				cbl.setSelectedItem(null);
		}
		else{
			JFileChooser theFileChooser = (JFileChooser)e.getSource();
			String command = e.getActionCommand();
			if (command.equals(JFileChooser.APPROVE_SELECTION)) {
				File selectedFile = theFileChooser.getSelectedFile();
				titleField.setText(selectedFile.getName());
			}  else if (command.equals(JFileChooser.CANCEL_SELECTION)) {
				titleField.setText(" ");
			}
		}
	}

	private void runQueries() {
		TransferManager.getInstance().sendFile(fc.getSelectedFile());
	}

	private void updateProgress() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JOptionPane.showMessageDialog(getContentPane(),
						
						JOptionPane.INFORMATION_MESSAGE
						);
				pb = new JProgressBar();
				pb.setMinimum(0);
				pb.setMaximum(100);
				pb.setValue(0);
				pb.setStringPainted(true);
				uploadDataPane.add(pb,cc.xy(3, 15));
			}
		});
	}

	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)
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

	}

}