package view;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import model.Config;
import model.Profil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import controller.SessionManager;
/**
 * Fenêtre principale du programme
 * @author Derek
 *
 */

public class Dashboard extends JFrame implements ActionListener, TreeSelectionListener{
	private static final long serialVersionUID = -5998048938167814342L;
	private JPanel topPane;
	private JSplitPane splitPane;
	private JPanel leftPane;
	private JPanel rightPane;
	private JPanel centerPane;
	private JPanel bottomPane;
	private GridBagLayout main;
	private JTree menuBar;
	private JTextArea Test;
	private JFileChooser fc;
	private JPanel uploadDataPane;

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
		scrollList();
		bottomBar();
	}


	/**
	 * Barre de menu titulaire
	 */
	public void titleBar(){

		JButton mnProfil = new JButton("Profil");
		mnProfil.setHorizontalAlignment(SwingConstants.LEFT);
		topPane.add(mnProfil);

		JButton mnParametres = new JButton("Parametres");
		mnParametres.setHorizontalAlignment(SwingConstants.LEFT);
		topPane.add(mnParametres);

		JButton mnAide = new JButton("Aide");
		mnAide.setHorizontalAlignment(SwingConstants.LEFT);
		topPane.add(mnAide);

		JButton mnQuitter = new JButton("Quitter");
		mnQuitter.setHorizontalAlignment(SwingConstants.LEFT);
		mnQuitter.addActionListener(this);
		topPane.add(mnQuitter);
	}

	private void menuBar() {

		//create the root node
		DefaultMutableTreeNode home = new DefaultMutableTreeNode("Home");
		//create the child nodes
		DefaultMutableTreeNode mediaNode = new DefaultMutableTreeNode("Media");
		mediaNode.add(new DefaultMutableTreeNode("Books"));
		mediaNode.add(new DefaultMutableTreeNode("Images"));
		mediaNode.add(new DefaultMutableTreeNode("Music"));
		mediaNode.add(new DefaultMutableTreeNode("Movies"));
		mediaNode.add(new DefaultMutableTreeNode("Series"));
		DefaultMutableTreeNode uploadNode = new DefaultMutableTreeNode("Upload");
		uploadNode.add(new DefaultMutableTreeNode("Books"));
		uploadNode.add(new DefaultMutableTreeNode("Images"));
		uploadNode.add(new DefaultMutableTreeNode("Music"));
		uploadNode.add(new DefaultMutableTreeNode("Movies"));
		uploadNode.add(new DefaultMutableTreeNode("Series"));
		DefaultMutableTreeNode usersNode = new DefaultMutableTreeNode("User Admin");
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

	@SuppressWarnings("unused")
	private void scrollList() {
		Test = new JTextArea(5,20);
	}

	private void bottomBar() {
		JTextPane txtpnTest = new JTextPane();
		txtpnTest.setEditable(false);
		txtpnTest.setText("Salut "+Profil.getInstance().getUsername()+
				", ton adresse mail est: "+Profil.getInstance().getMail());
		bottomPane.add(txtpnTest);
	}

	private void homePage(DefaultMutableTreeNode node) {
		Test.setText(node.toString());
	}

	private void parentPage(DefaultMutableTreeNode node) {
		switch (node.toString()) {
		case "Media": mediaResultSet(node); break;
		case "Upload": uploadFilePage(node); break;
		case "User Administration": userAdmin(node); break;
		default: Test.setText(node.toString()); break;
		}
	}

	private void uploadFilePage(DefaultMutableTreeNode node) {
		rightPane.removeAll(); 
		uploadDataPane = new JPanel();
		if (node.toString() == "Upload"){
			rightPane.setLayout(new GridLayout(1,1));
			rightPane.add(uploadDataPane);
		}
		else{
			int rows = 0;
			fc = new JFileChooser();
			FormLayout layout = new FormLayout(
					"pref, 4dlu, fill:150dlu",
					"pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 10dlu, pref, 10dlu");
			rightPane.setLayout(new GridLayout(1,2));
			rightPane.add(fc);
			rightPane.add(uploadDataPane);
			layout.setRowGroups(new int[][]{{1, 3, 5, 7, 9, 11,13,15}});
			uploadDataPane.setLayout(layout);
			CellConstraints cc = new CellConstraints();
			uploadDataPane.add(new JButton("Title"),cc.xy(1, 1));
			uploadDataPane.add(new JButton("Year"),cc.xy(1, 3));
			switch (node.toString()) {
			case "Books":
				uploadDataPane.add(new JButton("Author"),cc.xy(1, 5) );
				uploadDataPane.add(new JButton("Synopsis"),cc.xy(1, 7));
				uploadDataPane.add(new JButton("Genre"),cc.xy(1, 9));
				rows=9;
				break;
			case "Images": 
				uploadDataPane.add(new JButton("Photographer"),cc.xy(1, 5) );
				rows=5;
				break;
			case "Music": 
				uploadDataPane.add(new JButton("Artist"),cc.xy(1, 5) );
				uploadDataPane.add(new JButton("Genre"),cc.xy(1, 7) );
				rows=7;
				break;
			case "Movies": 
				uploadDataPane.add(new JButton("Director"),cc.xy(1, 5));
				uploadDataPane.add(new JButton("Genre"),cc.xy(1, 7) );
				uploadDataPane.add(new JButton("Synopsis"),cc.xy(1, 9) );
				rows=9;
				break;
			case "Series": 
				uploadDataPane.add(new JButton("Synposis"),cc.xy(1, 5) );
				uploadDataPane.add(new JButton("Genre"),cc.xy(1, 7) );
				rows=7;
				break;
			default: break;
			}
			
			uploadDataPane.add(new JButton("Visibility Level"),cc.xy(1, (rows+2)));
			uploadDataPane.add(new JButton("Modification Level"),cc.xy(1, (rows+4)));
			for(int i=1;i<=(rows+4);i++){
				if (i % 2 == 1)
					uploadDataPane.add(new JTextField(),cc.xy(3, i));
			}
			uploadDataPane.add(new JButton("Upload"),cc.xy(1, 15));
			uploadDataPane.add(new JButton("Clear"),cc.xy(3, 15));
		}
		uploadDataPane.repaint(); uploadDataPane.revalidate();
		rightPane.revalidate();
	}

	private void mediaResultSet(DefaultMutableTreeNode node) {
		switch (node.toString()) {
		case "Media": Test.setText(node.toString()); break;
		case "Books": Test.setText(node.toString()); break;
		case "Images": Test.setText(node.toString()); break;
		case "Music": Test.setText(node.toString()); break;
		case "Movies": Test.setText(node.toString()); break;
		case "Series": Test.setText(node.toString()); break;
		default: break;
		}
	}

	private void userAdmin(DefaultMutableTreeNode node) {
		switch (node.toString()) {
		case "Create User": Test.setText(node.toString()); break;
		case "Administration": Test.setText(node.toString()); break;
		case "Permissions": Test.setText(node.toString()); break;
		case "Preferences": Test.setText(node.toString()); break;
		default: break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SessionManager.getInstance().logout();
		System.exit(0);
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
			case "Home": parentPage(node);
			default: Test.setText(node.toString()); break;
			}
		}
	}



}