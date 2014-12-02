package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import model.Config;
import controller.SocketManager;

/** Fenêtre pour définir le serveur IP
 * @author Antoine
 *
 */
public class IpAsk extends JFrame implements ActionListener{
	private static final long serialVersionUID = -1447417979022041682L;
	private JButton bQuitter;
	private JButton bSuivant;
	private JTextField ipField;
	
	public IpAsk(){
		super(Config.getInstance().getProp("base_title")+"Initialisation");
		setResizable(false);
		BoxLayout mainBox = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
		getContentPane().setLayout(mainBox);
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		setBounds((width/2) - (450/2), (height/2) - (300/2), 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.WHITE);
		
		URL iconURL = getClass().getResource("nmc.png");
		ImageIcon icon = new ImageIcon(iconURL);
		setIconImage(icon.getImage());
		
		JTextPane intro = new JTextPane();
		intro.setText("Pour commencer à utiliser le manager, veuillez introduire l'adresse IP du serveur (ex: 192.168.1.50).\n"
				+ "Si vous êtes sur la même machine que le serveur NMC, il suffit d'indiquer: 127.0.0.1 .\n"
				+ "Si vous ne la connaissez pas, demandez-la à la personne qui s'est occupée de l'installation du serveur NMC.\n"
				+ "Vous devrez relancer le manager après avoir confirmé.");
		intro.setFont(new Font(Font.SANS_SERIF, Font.ROMAN_BASELINE, 12));
		intro.setEditable(false);
		intro.setBackground(Color.WHITE);
		intro.setBounds(0, 0, 450, 100);
		getContentPane().add(intro);
		
		JPanel horiPane = new JPanel();
		GridLayout horiLayout = new GridLayout(1, 2);
		horiPane.setLayout(horiLayout);
		horiPane.setBackground(Color.WHITE);
		JLabel ipLabel = new JLabel("Adresse IP: ");
		ipLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		ipLabel.setLocation(20, 0);
		horiPane.add(ipLabel);
		
		ipField = new JTextField("ex: 192.168.1.1");
		ipField.setBounds(0, 0, 150, 30);
		horiPane.add(ipField);
		getContentPane().add(horiPane);
		
		JPanel footer = new JPanel();
		footer.setBackground(Color.WHITE);
		GridLayout footLayout = new GridLayout(1, 2);
		footLayout.setHgap(10);
		footer.setLayout(footLayout);
		bSuivant = new JButton("Confirmer");
		bSuivant.addActionListener(this);
		footer.add(bSuivant);
		
		bQuitter = new JButton("Quitter");
		bQuitter.addActionListener(this);
		footer.add(bQuitter);
		
		getContentPane().add(footer);
		setVisible(true);
	}
	
	/** Fonction qui vérifie le format IP saisie
	 * @param ip l'IP pour vérifier
	 * @return Vrai si l'IP est vérifiée
	 */
	private boolean checkIpFormat(String ip){
		int i, k = 0;
		for(i = 0; i < ip.length(); i++){
			char tmp = ip.toCharArray()[i];
			if(tmp == '.') k++;
			if((tmp != '.') && (tmp != '1') && (tmp != '2') && (tmp != '3')
					&& (tmp != '4') && (tmp != '5') && (tmp != '6') && (tmp != '7')
					&& (tmp != '8') && (tmp != '9') && (tmp != '0'))
				return false;
		}
		if(k == 3) return true;
		return false;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bQuitter){
			System.exit(0);
		} else if(e.getSource() == bSuivant){
			if(ipField.getText() == null) 
				JOptionPane.showMessageDialog(null, "Veuillez introduire l'ip du serveur.");
			else if (!checkIpFormat(ipField.getText()))
				JOptionPane.showMessageDialog(null, "Le format de l'ip doit obligatoirement être de ce genre-ci: Nb.Nb.Nb.Nb (ex: 192.168.4.32)");
			else {
				Properties prop = new Properties();
				prop.setProperty("srv_url", ipField.getText());
				Config.getInstance().saveProp(prop);
				this.dispose();
				//------------ Auto Config ----------
				while(Config.getInstance().getProp("init").compareTo("0") == 0){
					SocketManager.getInstance().getConfig();
				}
				//---------- Auto Config End---------
				EventQueue.invokeLater(new Runnable() {
					
					@Override
					public void run() {
							Welcome wcScreen = new Welcome();
							wcScreen.setVisible(true);								
					}
				});	
			}			
		}
	}
}
