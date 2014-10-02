package view;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import model.Config;
import model.Profil;

import javax.swing.JMenuBar;
import javax.swing.JMenu;

import java.awt.Component;
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

class VerticalMenuBar extends JMenuBar {
	  private static final LayoutManager grid = new GridLayout(0,1);
	  public VerticalMenuBar() {
	    setLayout(grid);
	  }
}


public class Dashboard extends JFrame implements ActionListener{
	private static final long serialVersionUID = -5998048938167814342L;
	private JPanel topPane;
	private JSplitPane centerPane;
	private JPanel leftPane;
	private JPanel rightPane;
	private JPanel bottomPane;
	private BoxLayout main;

	/**
	 * Initialise la fenêtre et ses composants
	 */
	public Dashboard() {
		super(Config.getInstance().getProp("base_title")+"Nukama Media Center");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
		getContentPane().setLayout(main);
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		setBounds((width/2) - (width/3), (height/2) - (height/3), (int)(width/1.5), (int)(height/1.5));
		
		topPane = new JPanel();
		topPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		topPane.setLayout(new BoxLayout(topPane, BoxLayout.X_AXIS));
		getContentPane().add(topPane);
		
		leftPane = new JPanel();
		leftPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		leftPane.setLayout(new BoxLayout(leftPane, BoxLayout.X_AXIS));
		getContentPane().add(leftPane);
		
		rightPane = new JPanel();
		rightPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.X_AXIS));
		
		getContentPane().add(rightPane);
		
		centerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane, rightPane);
		
		centerPane.setDividerLocation(0.5);
		getContentPane().add(centerPane);
		
		bottomPane = new JPanel();
		bottomPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(bottomPane);
		bottomPane.setLayout(new BoxLayout(bottomPane, BoxLayout.X_AXIS));
		
		titleBar();
		menuBar();
		scrollList();
		bottomBar();
	}

	

	/**
	 * Barre des menus globaux
	 */
	public void titleBar(){
		JMenuBar menuGlobal = new JMenuBar();
		menuGlobal.setAlignmentY(Component.LEFT_ALIGNMENT);
		topPane.add(menuGlobal);
		
		JMenuItem mnFichier = new JMenuItem("Profil");
		mnFichier.setHorizontalAlignment(SwingConstants.LEFT);
		menuGlobal.add(mnFichier);
		
		JMenuItem mnOutils = new JMenuItem("Parametres");
		mnOutils.setHorizontalAlignment(SwingConstants.LEFT);
		menuGlobal.add(mnOutils);
		
		JMenuItem mnAide = new JMenuItem("Aide");
		mnAide.setHorizontalAlignment(SwingConstants.LEFT);
		menuGlobal.add(mnAide);
		
		JMenuItem mnQuitter = new JMenuItem("Quitter");
		mnQuitter.setHorizontalAlignment(SwingConstants.LEFT);
		mnQuitter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SessionManager.getInstance().logout();
				System.exit(0);
			}
		});
		menuGlobal.add(mnQuitter);
	}
	
	private void menuBar() {
		JMenuBar menuBar = new VerticalMenuBar();
//		menuBar.setAlignmentX(Component.LEFT_ALIGNMENT);
//		menuBar.setAlignmentY(Component.TOP_ALIGNMENT);
		leftPane.add(menuBar);
		
		JMenu mnHome = new JMenu("Home");
		menuBar.add(mnHome);
		
		JMenu mnMedia = new JMenu("Media");
		menuBar.add(mnMedia);
		
		JMenu mnUsers = new JMenu("User Management");
		menuBar.add(mnUsers);
		
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