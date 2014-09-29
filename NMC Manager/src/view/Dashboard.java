package view;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
public class Dashboard extends JFrame implements ActionListener{
	private static final long serialVersionUID = -5998048938167814342L;
	private JPanel contentPane;
	private JMenuItem mntmQuitter;

	/**
	 * Initialise la fenêtre et ses composants
	 */
	public Dashboard() {
		super(Config.getInstance().getProp("base_title")+"Nukama Media Center");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		setBounds((width/2) - (width/3), (height/2) - (height/3), (int)(width/1.5), (int)(height/1.5));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		menuBar();
		
		JTextPane txtpnTest = new JTextPane();
		txtpnTest.setEditable(false);
		txtpnTest.setText("Salut "+Profil.getInstance().getUsername()+
				", ton adresse mail est: "+Profil.getInstance().getMail());
		contentPane.add(txtpnTest);
	}
	
	/**
	 * Barre des menus globaux
	 */
	public void menuBar(){
		JMenuBar menuGlobal = new JMenuBar();
		menuGlobal.setAlignmentY(Component.LEFT_ALIGNMENT);
		contentPane.add(menuGlobal);
		
		JMenu mnFichier = new JMenu("Fichier");
		mnFichier.setHorizontalAlignment(SwingConstants.LEFT);
		menuGlobal.add(mnFichier);
		
		mntmQuitter = new JMenuItem("Quitter");
		mntmQuitter.addActionListener(this);
		mnFichier.add(mntmQuitter);
		
		JMenu mnOutils = new JMenu("Outils");
		mnOutils.setHorizontalAlignment(SwingConstants.LEFT);
		menuGlobal.add(mnOutils);
		
		JMenu mnAide = new JMenu("Aide");
		mnAide.setHorizontalAlignment(SwingConstants.LEFT);
		menuGlobal.add(mnAide);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == mntmQuitter){
			SessionManager.getInstance().logout();
			System.exit(0);
		}
	}

}
