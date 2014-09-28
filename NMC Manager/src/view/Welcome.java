package view;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import model.Config;
import controller.SessionManager;
/**
 * Fenêtre de connexion
 * @author Antoine
 *
 */
public class Welcome extends JFrame implements ActionListener {
	private static final long serialVersionUID = -5711057253134386117L;
	private JTextField txtLogin;
	private JPasswordField pwdMotDePasse;
	private JButton btnQuitter;
	private JButton btnSeConnecter;

	/**
	 * Initialisation de la fenêtre et de ses composants
	 */
	public Welcome() {
		super(Config.getProp("base_title")+"Bienvenue");
		setResizable(false);
		BoxLayout mainBox = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
		getContentPane().setLayout(mainBox);
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		setBounds((width/2) - (450/2), (height/2) - (300/2), 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel headerPanel = new JPanel();
		getContentPane().add(headerPanel);
		headerPanel.setLayout(null);
		
		JLabel lblConnexion = new JLabel("Connexion");
		lblConnexion.setBounds(166, 26, 109, 29);
		headerPanel.add(lblConnexion);
		lblConnexion.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblConnexion.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		getContentPane().add(centerPanel);
		centerPanel.setLayout(null);
		
		txtLogin = new JTextField();
		txtLogin.setToolTipText("Login");
		txtLogin.setHorizontalAlignment(SwingConstants.CENTER);
		txtLogin.setBounds(180, 11, 140, 20);
		centerPanel.add(txtLogin);
		txtLogin.setColumns(10);
		
		pwdMotDePasse = new JPasswordField();
		pwdMotDePasse.setToolTipText("Mot de Passe");
		pwdMotDePasse.setHorizontalAlignment(SwingConstants.CENTER);
		pwdMotDePasse.setBounds(180, 59, 140, 20);
		centerPanel.add(pwdMotDePasse);
		
		JLabel lblLogin = new JLabel("Login :");
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLogin.setBounds(120, 12, 50, 14);
		centerPanel.add(lblLogin);
		
		JLabel lblMotDePasse = new JLabel("Mot de passe :");
		lblMotDePasse.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMotDePasse.setBounds(73, 59, 97, 17);
		centerPanel.add(lblMotDePasse);
		
		JPanel footerPanel = new JPanel();
		getContentPane().add(footerPanel);
		footerPanel.setLayout(null);
		
		btnSeConnecter = new JButton("Se connecter");
		btnSeConnecter.setBounds(75, 28, 122, 29);
		btnSeConnecter.addActionListener(this);
		footerPanel.add(btnSeConnecter);
		
		btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(this);
		btnQuitter.setBounds(251, 28, 122, 29);
		footerPanel.add(btnQuitter);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnQuitter){
			System.exit(0);
		} else if(e.getSource() == btnSeConnecter){
			if(SessionManager.login(txtLogin.getText(), String.valueOf(pwdMotDePasse.getPassword()))){
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
