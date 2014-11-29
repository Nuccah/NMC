package view;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Config;
import controller.SocketManager;

/**
 * Fenêtre principale du programme
 * @author Derek & Antoine
 *
 */

public class Dashboard extends JFrame implements ActionListener, ChangeListener{
	private static final long serialVersionUID = -5998048938167814342L;
	private JPanel topPane = new JPanel();
	private JPanel titlePane = new JPanel();
	private JPanel bottomPane = new JPanel();

	private GridBagConstraints lc = new GridBagConstraints();
	private GridBagConstraints tc = new GridBagConstraints();
	private GridBagConstraints ltc = new GridBagConstraints();
	private GridBagConstraints rtc = new GridBagConstraints();
	private GridBagConstraints gcc = new GridBagConstraints();
	private GridBagConstraints bc = new GridBagConstraints();

	private JTabbedPane menuBar;
	private JTabbedPane mediaNode;
	private JTabbedPane uploadNode;
	private JTabbedPane usersNode;
	
	private CommonUsed cu = new CommonUsed();
	
	public Dashboard(){
		super(Config.getInstance().getProp("base_title")+"Manager");
		this.addWindowListener(new WindowAdapter() 
			{
				@Override
				public void windowClosing(WindowEvent e){
					SocketManager.getInstance().logout();
					System.exit(0);
				}
			});
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
		ltc.gridx = 0; ltc.weightx = 1; ltc.weighty = 0.01; ltc.fill = GridBagConstraints.BOTH;
		rtc.gridx = 0; rtc.weightx = 1; rtc.weighty = 0.9; rtc.fill = GridBagConstraints.BOTH;
		gcc.gridx = 0; gcc.gridy = 1; gcc.weightx = 1; gcc.weighty = 0.8; gcc.fill = GridBagConstraints.BOTH;
		bc.gridx = 0; bc.gridy = 2; bc.weightx = 1; bc.weighty = 0.1; bc.fill = GridBagConstraints.BOTH;
		lc.gridx = 0; lc.gridy = 0; lc.weightx = 0.25; lc.weighty = 1; lc.fill = GridBagConstraints.BOTH;
		tc.gridx = 0; tc.gridy = 0; tc.weightx = 0.75; tc.weighty = 0.01; tc.fill = GridBagConstraints.BOTH;
		
		topPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		bottomPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		topPane.setLayout(new GridBagLayout());
		titlePane.setLayout(new GridBagLayout());	
		
		cu.mnAide.addActionListener(this);
		cu.mnQuitter.addActionListener(this);
		setTitleBar();
		getContentPane().add(topPane, tc);
		setMenuBar();
		setBottomBar();
		getContentPane().add(bottomPane, bc);

		pack();		
	}
	
	/**
	 * Barre de menu titulaire
	 */
	public void setTitleBar(){

		cu.mnAide.setHorizontalAlignment(SwingConstants.LEFT);
		cu.mnAide.addActionListener(this);
		titlePane.add(cu.mnAide);

		titlePane.repaint();
		titlePane.revalidate();
		topPane.add(titlePane, ltc);
	}
	
	/**
	 * Bar de menu pour les gestions possibles
	 */
	private void setMenuBar() {
		mediaNode = new JTabbedPane();
		mediaNode.setBorder(new EmptyBorder(5, 5, 5, 5));
		ViewPane vBook = new ViewPane(cu.viewBooks);
		mediaNode.addTab(cu.viewBooks, vBook);
		ViewPane vImage = new ViewPane(cu.viewImages);
		mediaNode.addTab(cu.viewImages, vImage);
		ViewPane vMovie = new ViewPane(cu.viewMovies);
		mediaNode.addTab(cu.viewMovies, vMovie);
		ViewPane vMusic = new ViewPane(cu.viewMusic);
		mediaNode.addTab(cu.viewMusic, vMusic);
		ViewPane vSerie = new ViewPane(cu.viewSeries);
		mediaNode.addTab(cu.viewSeries, vSerie);
		mediaNode.addChangeListener(this);

		uploadNode = new JTabbedPane();
		uploadNode.setBorder(new EmptyBorder(5, 5, 5, 5));
		uploadNode.addTab(cu.uploadEpisodes, new UploadPane(cu.uploadEpisodes, (Frame) getOwner()));
		uploadNode.addTab(cu.uploadSeries, new UploadPane(cu.uploadSeries, (Frame) getOwner()));
		uploadNode.addTab(cu.uploadAlbums, new UploadPane(cu.uploadAlbums, (Frame) getOwner()));
		uploadNode.addTab(cu.uploadMusic, new UploadPane(cu.uploadMusic, (Frame) getOwner()));
		uploadNode.addTab(cu.uploadBooks, new UploadPane(cu.uploadBooks, (Frame) getOwner()));
		uploadNode.addTab(cu.uploadImages, new UploadPane(cu.uploadImages, (Frame) getOwner()));
		uploadNode.addTab(cu.uploadMovies, new UploadPane(cu.uploadMovies, (Frame) getOwner()));

		usersNode = new JTabbedPane();
		usersNode.setBorder(new EmptyBorder(5, 5, 5, 5));
		usersNode.addTab(cu.userNode, new AdminPane(cu.userNode));
		usersNode.addTab(cu.adminNode, new AdminPane(cu.adminNode)); 
		usersNode.addTab(cu.permNode, new AdminPane(cu.permNode));
	
		menuBar = new JTabbedPane();
		menuBar.addTab(cu.mediaString, mediaNode);
		menuBar.addTab(cu.uploadString, uploadNode);
		menuBar.addTab(cu.usersString, usersNode);
		menuBar.addTab(cu.profilString, new ProfilPane());
		getContentPane().add(menuBar, rtc);

	}
	
	/**
	 * Bottom bar containing Copyright information
	 */
	private void setBottomBar() {
		JTextPane txtpnTest = new JTextPane();
		txtpnTest.setEditable(false);
		txtpnTest.setText("Tous droits réservés - \u00a9 Nukama Team 2014 - nmc_team@nukama.be" );
		bottomPane.add(txtpnTest);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == cu.mnAide){
			if(Desktop.isDesktopSupported())
			{
				try {
					Desktop.getDesktop().browse(new URI("http://mediacenter.nukama.be/support.php"));
				} catch (IOException | URISyntaxException e1){
					e1.printStackTrace();
				}
			}
		} 
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == mediaNode){
			ViewPane tmp = (ViewPane) mediaNode.getSelectedComponent();
			tmp.refreshDisplay();
		} else if(e.getSource() == uploadNode){
			
		} else if(e.getSource() == usersNode){
			
		}
	}	
}