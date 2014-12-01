package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import model.Config;
/**
 * Classe de génération de la fenêtre A Propos
 * @author Antoine
 *
 */
public class About extends JFrame {
	private static final long serialVersionUID = -7754918383926484094L;
	/**
	 * Permet de créer la fenêtre A Propos
	 */
	public About(){
		super(Config.getInstance().getProp("base_title")+" - A propos");
		URL url = getClass().getResource("nmc.png");
		ImageIcon icon = new ImageIcon(url);
		setIconImage(icon.getImage());
		
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		setBounds((width/2) - (450/2), (height/2) - (250/2), 450, 250);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		BoxLayout vbl = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
		setLayout(vbl);
		setBackground(Color.WHITE);
		getContentPane().setBackground(Color.WHITE);
		
		JPanel tempTop = new JPanel();
		tempTop.setLayout(new FlowLayout());
		tempTop.setBackground(Color.WHITE);
		JLabel title = new JLabel("NMC");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		title.setBackground(Color.WHITE);
		tempTop.add(title);
		getContentPane().add(tempTop);
		
		JTextPane text = new JTextPane();
		text.setText("Ce programme a été développé par Nukama Team.\n"
				+ "Plus d'informations et de renseignements sur https://mediacenter.nukama.be");
		text.setFont(new Font(Font.SANS_SERIF, Font.CENTER_BASELINE, 14));
		text.setMargin(new Insets(25, 25, 5, 15));
		text.setEditable(false);
		getContentPane().add(text);
		JPanel tempBot = new JPanel();
		tempBot.setBackground(Color.WHITE);
		tempBot.setLayout(new FlowLayout());
		JLabel copyright = new JLabel("Tous droits réservés - \u00a9 Nukama Team 2014");
		copyright.setFont(new Font(Font.SANS_SERIF, Font.CENTER_BASELINE, 14));
		copyright.setBackground(Color.WHITE);
		tempBot.add(copyright);
		getContentPane().add(tempBot);
	}
}
