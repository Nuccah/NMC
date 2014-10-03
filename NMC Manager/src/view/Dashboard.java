package view;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;

import model.Config;
import model.Profil;

import javax.swing.JMenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.JMenuItem;

import controller.SessionManager;
/**
 * Fenêtre principale du programme
 * @author Antoine
 *
 */

public class Dashboard extends JFrame implements ActionListener{
	private static final long serialVersionUID = -5998048938167814342L;
	private JPanel topPane;
	private JSplitPane splitPane;
	private JPanel leftPane;
	private JPanel rightPane;
	private JPanel centerPane;
	private JPanel bottomPane;
	private GridBagLayout main;
	private JTree menuBar;

	/**
	 * Initialise la fenêtre et ses composants
	 */
	public Dashboard() {
		super(Config.getInstance().getProp("base_title")+"Nukama Media Center");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main = new GridBagLayout();
		main.rowWeights = new double[]{0.0, 1.0, 0.0};
		main.columnWeights = new double[]{1.0};
		getContentPane().setLayout(main);
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		setBounds((width/2) - (width/3), (height/2) - (height/3), (int)(width/1.5), (int)(height/1.5));
		GridBagConstraints c = new GridBagConstraints();
		c.gridheight = 1; c.gridwidth = 1; c.gridx = 0; c.gridy = 0; c.weightx = 1; c.weighty = 0.1; c.fill = GridBagConstraints.BOTH;
		GridBagConstraints d = new GridBagConstraints();
		d.fill = GridBagConstraints.VERTICAL;
		d.gridheight = 1; d.gridwidth = 1; d.gridx = 0; d.gridy = 1; d.weightx = 1; d.weighty = 0.8; d.fill = GridBagConstraints.BOTH;
		GridBagConstraints e = new GridBagConstraints();
		e.gridheight = 1; e.gridwidth = 1; e.gridx = 0; e.gridy = 2; e.weightx = 1; e.weighty = 0.1;
		
		topPane = new JPanel();
		topPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(topPane, c);
		
		centerPane = new JPanel();
		centerPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		leftPane = new JPanel();
		leftPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		leftPane.setLayout(new GridLayout(1,1));
		centerPane.add(leftPane);
		
		
		rightPane = new JPanel();
		rightPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		centerPane.add(rightPane);
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane, rightPane);
		splitPane.setDividerLocation(0.5);
		centerPane.add(splitPane);
		centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.X_AXIS));
		getContentPane().add(centerPane, d);
		
		bottomPane = new JPanel();
		bottomPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(bottomPane, e);
		
		titleBar();
		menuBar();
		scrollList();
		bottomBar();
	}

	

	/**
	 * Barre des menus globaux
	 */
	public void titleBar(){
		
		JMenuItem mnProfil = new JMenuItem("Profil");
		mnProfil.setHorizontalAlignment(SwingConstants.LEFT);
		topPane.add(mnProfil);
		
		JMenuItem mnParametres = new JMenuItem("Parametres");
		mnParametres.setHorizontalAlignment(SwingConstants.LEFT);
		topPane.add(mnParametres);
		
		JMenuItem mnAide = new JMenuItem("Aide");
		mnAide.setHorizontalAlignment(SwingConstants.LEFT);
		topPane.add(mnAide);
		
		JMenuItem mnQuitter = new JMenuItem("Quitter");
		mnQuitter.setHorizontalAlignment(SwingConstants.LEFT);
		mnQuitter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SessionManager.getInstance().logout();
				System.exit(0);
			}
		});
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
        leftPane.add(new JScrollPane(menuBar));
	}

	private void scrollList() {
		JScrollPane Test = new JScrollPane();
		rightPane.add(Test);
	}

	private void bottomBar() {
		JTextPane txtpnTest = new JTextPane();
		txtpnTest.setEditable(false);
		txtpnTest.setText("Salut "+Profil.getInstance().getUsername()+
				", ton adresse mail est: "+Profil.getInstance().getMail());
		bottomPane.add(txtpnTest);
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}