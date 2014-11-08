package view;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.ImageIcon;

import controller.Main;
/**
 * Crée une icône dans la barre des tâches permettant les actions suivantes:<br /><br />
 * - Ouvrir la fenêtre "A propos"<br />
 * - Ouvrir le navigateur vers le site mediacenter.nukama.be<br />
 * - Redémarrer le serveur <br />
 * - Arrêter le serveur <br />
 * @author Antoine
 *
 */
public class MinIcon {
	private PopupMenu popup;
	private SystemTray tray;
	
	public MinIcon(){
		popup = new PopupMenu();
		URL url = getClass().getResource("nmc.png");
		ImageIcon icon = new ImageIcon(url);
		TrayIcon trayIcon = new TrayIcon(icon.getImage());
		trayIcon.setImageAutoSize(true);
		trayIcon.setToolTip("NMC Server");
		tray = SystemTray.getSystemTray();
		
		MenuItem aboutItem = new MenuItem("A propos");
		aboutItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						About ab = new About();
						ab.setVisible(true);						
					}
				});	
			}
		});
		MenuItem supportItem = new MenuItem("Support");
		supportItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Desktop.isDesktopSupported())
				{
					try {
						Desktop.getDesktop().browse(new URI("http://mediacenter.nukama.be"));
					} catch (IOException | URISyntaxException e1) {
						if(Main.getDebug()){
							System.out.println("[Error] - Couldn't open the default browser.");
							e1.printStackTrace();
						}
					}
				}				
			}
		});
		MenuItem exitItem = new MenuItem("Quitter");
		exitItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);				
			}
		});
		MenuItem restartItem = new MenuItem("Redémarrer");
		restartItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					final ProcessBuilder pb = new ProcessBuilder("nmc-server");
					pb.start();
				} catch (IOException e1) {
					System.out.println("[Error] - Unable to restart the nmc-server");
					if(Main.getDebug()) e1.printStackTrace();
				}
				System.exit(0);				
			}
		});
		popup.add(aboutItem);
		popup.addSeparator();
		popup.add(supportItem);
		popup.addSeparator();
		popup.add(restartItem);
		popup.add(exitItem);
		popup.setFont(new Font(Font.SANS_SERIF, Font.ROMAN_BASELINE, 14));
		
		trayIcon.setPopupMenu(popup);
		try{
			tray.add(trayIcon);
		} catch(AWTException e){
			System.out.println("[Error] - TrayIcon couldn't be added.");
		}
	}
}
