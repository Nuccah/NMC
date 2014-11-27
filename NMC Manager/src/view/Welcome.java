package view;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import model.Config;
import controller.SocketManager;
/**
 * Fenêtre de connexion
 * @author Antoine
 *
 */
public class Welcome extends JFrame implements ActionListener, KeyListener {
	private static final long serialVersionUID = -5711057253134386117L;
	private JTextField txtLogin;
	private JPasswordField pwdMotDePasse;
	private JButton btnQuitter;
	private JButton btnSeConnecter;
	private JButton btnChangeIP;

	/**
	 * Initialisation de la fenêtre et de ses composants
	 */
	public Welcome() {
		super(Config.getInstance().getProp("base_title")+"Bienvenue");
		setResizable(false);
		BoxLayout mainBox = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
		getContentPane().setLayout(mainBox);
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		setBounds((width/2) - (450/2), (height/2) - (300/2), 450, 300);
		this.addWindowListener(new WindowAdapter() 
		{
			@Override
			public void windowClosing(WindowEvent e){
				SocketManager.getInstance().logout();
				System.exit(0);
			}
		});
		
		getContentPane().addKeyListener(this);
		
		URL iconURL = getClass().getResource("nmc.png");
		ImageIcon icon = new ImageIcon(iconURL);
		setIconImage(icon.getImage());

		JPanel headerPanel = new JPanel();
		getContentPane().add(headerPanel);
		headerPanel.setLayout(null);

		JLabel lblConnexion = new JLabel("Connexion");
		lblConnexion.setBounds(166, 26, 200, 29);
		headerPanel.add(lblConnexion);
		lblConnexion.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblConnexion.setAlignmentX(Component.CENTER_ALIGNMENT);

		JPanel centerPanel = new JPanel();
		centerPanel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		getContentPane().add(centerPanel);
		centerPanel.setLayout(null);
		
		txtLogin = new JTextField();
		txtLogin.setToolTipText("Identifiant");
		txtLogin.setHorizontalAlignment(SwingConstants.CENTER);
		txtLogin.setBounds(180, 11, 140, 30);
		txtLogin.addKeyListener(this);
		txtLogin.setColumns(10);
		centerPanel.add(txtLogin);		

		pwdMotDePasse = new JPasswordField();
		pwdMotDePasse.setToolTipText("Mot de Passe");
		pwdMotDePasse.setHorizontalAlignment(SwingConstants.CENTER);
		pwdMotDePasse.setBounds(180, 59, 140, 30);
		pwdMotDePasse.addKeyListener(this);
		centerPanel.add(pwdMotDePasse);

		JLabel lblLogin = new JLabel("Login :");
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLogin.setBounds(127, 12, 50, 30);
		centerPanel.add(lblLogin);

		JLabel lblMotDePasse = new JLabel("Mot de passe :");
		lblMotDePasse.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMotDePasse.setBounds(70, 59, 112, 30);
		centerPanel.add(lblMotDePasse);

		JPanel footerPanel = new JPanel();
		getContentPane().add(footerPanel);
		footerPanel.setLayout(null);

		btnSeConnecter = new JButton("Se connecter");
		btnSeConnecter.setBounds(15, 28, 122, 29);
		btnSeConnecter.addActionListener(this);
		footerPanel.add(btnSeConnecter);

		btnChangeIP = new JButton("Changer l'adresse serveur");
		btnChangeIP.setBounds(145, 28, 160, 29);
		btnChangeIP.addActionListener(this);
		footerPanel.add(btnChangeIP);

		btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(this);
		btnQuitter.setBounds(310, 28, 122, 29);
		footerPanel.add(btnQuitter);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnQuitter){
			System.exit(0);
		} else if(e.getSource() == btnSeConnecter){
			if(SocketManager.getInstance().connect(txtLogin.getText(), 
					String.valueOf(pwdMotDePasse.getPassword()))){				
				this.dispose();
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						Dashboard dbScreen = new Dashboard();
						dbScreen.setVisible(true);
					}
				});
			}
		} else if(e.getSource() == btnChangeIP){
			this.dispose();
			EventQueue.invokeLater(new Runnable(){
				public void run() {
					IpAsk iAScreen = new IpAsk();
					iAScreen.setVisible(true);
				}				
			});
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {	
		if(e.getExtendedKeyCode() == KeyEvent.VK_ENTER){
			if(SocketManager.getInstance().connect(txtLogin.getText(), 
					String.valueOf(pwdMotDePasse.getPassword()))){				
				this.dispose();
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						Dashboard dbScreen = new Dashboard();
						dbScreen.setVisible(true);
					}
				});
			}
		}	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getExtendedKeyCode() == KeyEvent.VK_ENTER){
			if(SocketManager.getInstance().connect(txtLogin.getText(), 
					String.valueOf(pwdMotDePasse.getPassword()))){				
				this.dispose();
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						Dashboard dbScreen = new Dashboard();
						dbScreen.setVisible(true);
					}
				});
			}
		}	
	}
	
	
}
