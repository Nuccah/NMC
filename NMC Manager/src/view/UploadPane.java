package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.AlbumCollector;
import model.AudioCollector;
import model.BookCollector;
import model.EpisodeCollector;
import model.ImageCollector;
import model.MetaDataCollector;
import model.Permissions;
import model.SeriesCollector;
import model.VideoCollector;

import com.jgoodies.forms.layout.FormLayout;

import controller.SocketManager;

/** Les panneaux utilisées pour l'ajout des medias
 * @author Derek
 *
 */
public class UploadPane extends JPanel implements ActionListener, PropertyChangeListener {
	private static final long serialVersionUID = 4071471356054977303L;
	private JFileChooser fc = new JFileChooser();
	private CommonUsed cu = new CommonUsed();

	private JProgressBar progressBar;
	private UploadTask uTask;

	private MetaDataCollector fileC;
	private String messagePB;
	private JDialog dlg;

	private final FormLayout uploadLayout = new FormLayout(
			"right:pref, 4dlu, fill:130dlu",
			"pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, "
					+ "2dlu, pref, 10dlu, pref, 10dlu, pref, 10dlu, pref,");

	private final JLabel titleLabel = new JLabel("Titre");
	private final JLabel yearLabel = new JLabel("Année");
	private final JLabel authorLabel = new JLabel("Auteur");
	private final JLabel synopsisLabel = new JLabel("Synopsis");
	private final JLabel genreLabel = new JLabel("Genre");
	private final JLabel photographerLabel = new JLabel("Photographe");
	private final JLabel artistLabel = new JLabel("Artiste");
	private final JLabel directorLabel = new JLabel("Réalisateur");
	private final JLabel visibilityLabel = new JLabel("Qui peut visionner?");
	private final JLabel modificationLabel = new JLabel("Qui peut modifier?");
	private final JLabel albumLabel = new JLabel("Album");
	private final JLabel seasonLabel = new JLabel("Saison");
	private final JLabel chronoLabel = new JLabel("Chronologie");
	private final JLabel seriesLabel = new JLabel("Séries");

	private JTextField titleField = new JTextField();
	private JTextField yearField = new JTextField();
	private JTextField synopsisField = new JTextField();
	private JTextField genreField = new JTextField();
	private JTextField personField = new JTextField();
	private JTextField chronoField = new JTextField();
	private JTextField seasonField = new JTextField();

	private final FileFilter videoFilter = new FileNameExtensionFilter("Fichier Vidéo", "mp4", "avi", "mkv", "flv", "mov", "wmv", "vob", "3gp", "3g2");
	private final FileFilter musicFilter = new FileNameExtensionFilter("Fichier Audio", "aac", "mp3", "wav", "wma", "flac");
	private final FileFilter bookFilter = new FileNameExtensionFilter("Fichier Livre", "pdf", "ebook", "epub", "cbr", "cbz");
	private final FileFilter imageFilter = new FileNameExtensionFilter("Fichier Image", "jpg", "jpeg", "png", "gif", "bmp");

	private GridBagConstraints rcc = new GridBagConstraints();

	public UploadPane(String tab, Frame frame_owner){
		JPanel dataPane = new JPanel();
		dataPane.removeAll();
		cu.node = tab;
		cu.frameOwner = frame_owner;
		setFileChooser(fc.getComponents());
		setComponentLists();

		cu.uploadButton.addActionListener(this);
		cu.clearButton.addActionListener(this);
		cu.addButton.addActionListener(this);
		cu.modifyButton.addActionListener(this);

		clear();
		uploadLayout.setRowGroups(new int[][]{{1, 3, 5, 7, 9, 11,13,15,17}});
		dataPane.setLayout(uploadLayout);
		if(tab == cu.uploadAlbums || tab == cu.uploadSeries){
			this.setLayout(new GridBagLayout());
			cu.addButton.setEnabled(true);
			dataPane.add(cu.addButton,cu.cc.xy(1, 17));
			this.add(dataPane, new GridBagConstraints());
		}
		else{
			this.setLayout(new GridBagLayout());
			rcc.weightx = 1; rcc.weighty = 1; rcc.fill = GridBagConstraints.BOTH;
			this.add(fc, rcc);
			this.add(new JPanel(), rcc);
			this.add(dataPane, rcc);
			cu.uploadButton.setEnabled(false);
			dataPane.add(cu.uploadButton,cu.cc.xy(1, 17));
		}
		dataPane.add(titleLabel,cu.cc.xy(1, 1)); dataPane.add(new JLabel("*"), cu.cc.xy(2,1)); dataPane.add(titleField,cu.cc.xy(3, 1));
		if (tab == cu.uploadAlbums){
			dataPane.add(yearLabel,cu.cc.xy(1, 3)); dataPane.add(new JLabel("*"), cu.cc.xy(2,3)); dataPane.add(yearField,cu.cc.xy(3, 3));
			dataPane.add(artistLabel,cu.cc.xy(1, 5)); dataPane.add(new JLabel("*"), cu.cc.xy(2,5)); dataPane.add(personField,cu.cc.xy(3, 5));
			dataPane.add(genreLabel,cu.cc.xy(1, 7) ); dataPane.add(new JLabel("*"), cu.cc.xy(2,7)); dataPane.add(genreField,cu.cc.xy(3, 7));
			dataPane.add(synopsisLabel,cu.cc.xy(1, 9) ); dataPane.add(synopsisField,cu.cc.xy(3, 9));
			dataPane.add(visibilityLabel,cu.cc.xy(1, 11)); dataPane.add(cu.visibilityBox,cu.cc.xy(3, 11)); 
			dataPane.add(modificationLabel,cu.cc.xy(1, 13)); dataPane.add(cu.modificationBox,cu.cc.xy(3, 13));
		}
		else if (tab == cu.uploadBooks){
			dataPane.add(yearLabel,cu.cc.xy(1, 3)); dataPane.add(new JLabel("*"), cu.cc.xy(2,3)); dataPane.add(yearField,cu.cc.xy(3, 3));
			dataPane.add(authorLabel,cu.cc.xy(1, 5) ); dataPane.add(new JLabel("*"), cu.cc.xy(2,5)); dataPane.add(personField,cu.cc.xy(3, 5));
			dataPane.add(synopsisLabel,cu.cc.xy(1, 7)); dataPane.add(synopsisField,cu.cc.xy(3, 7));
			dataPane.add(genreLabel,cu.cc.xy(1, 9)); dataPane.add(genreField,cu.cc.xy(3, 9));
			dataPane.add(visibilityLabel,cu.cc.xy(1, 11)); dataPane.add(new JLabel("*"), cu.cc.xy(2,11)); dataPane.add(cu.visibilityBox,cu.cc.xy(3, 11)); 
			dataPane.add(modificationLabel,cu.cc.xy(1, 13)); dataPane.add(new JLabel("*"), cu.cc.xy(2,13)); dataPane.add(cu.modificationBox,cu.cc.xy(3, 13));
			fc.setFileFilter(bookFilter);
		}
		else if (tab == cu.uploadEpisodes){
			dataPane.add(seriesLabel,cu.cc.xy(1, 3)); dataPane.add(new JLabel("*"), cu.cc.xy(2,3)); dataPane.add(cu.seriesBox,cu.cc.xy(3, 3));
			dataPane.add(directorLabel,cu.cc.xy(1, 5) ); dataPane.add(personField,cu.cc.xy(3, 5));
			dataPane.add(seasonLabel,cu.cc.xy(1, 7) ); dataPane.add(seasonField,cu.cc.xy(3, 7));
			dataPane.add(chronoLabel,cu.cc.xy(1, 9) ); dataPane.add(chronoField,cu.cc.xy(3, 9));
			fc.setFileFilter(videoFilter);
		}
		else if (tab == cu.uploadImages){
			dataPane.add(yearLabel,cu.cc.xy(1, 3)); dataPane.add(yearField,cu.cc.xy(3, 3));
			dataPane.add(photographerLabel,cu.cc.xy(1, 5)); dataPane.add(personField,cu.cc.xy(3, 5));
			dataPane.add(visibilityLabel,cu.cc.xy(1, 7)); dataPane.add(new JLabel("*"), cu.cc.xy(2,7)); dataPane.add(cu.visibilityBox,cu.cc.xy(3, 7)); 
			dataPane.add(modificationLabel,cu.cc.xy(1, 9)); dataPane.add(new JLabel("*"), cu.cc.xy(2,9)); dataPane.add(cu.modificationBox,cu.cc.xy(3, 9));
			fc.setFileFilter(imageFilter);
		}
		else if (tab == cu.uploadMusic){
			dataPane.add(albumLabel,cu.cc.xy(1, 3)); dataPane.add(new JLabel("*"), cu.cc.xy(2,3)); dataPane.add(cu.albumBox,cu.cc.xy(3, 3));
			dataPane.add(artistLabel,cu.cc.xy(1, 5)); dataPane.add(new JLabel("*"), cu.cc.xy(2,5)); dataPane.add(personField,cu.cc.xy(3, 5));
			fc.setFileFilter(musicFilter);
		}
		else if (tab == cu.uploadMovies){
			dataPane.add(yearLabel,cu.cc.xy(1, 3)); dataPane.add(new JLabel("*"), cu.cc.xy(2,3)); dataPane.add(yearField,cu.cc.xy(3, 3));
			dataPane.add(directorLabel,cu.cc.xy(1, 5)); dataPane.add(personField,cu.cc.xy(3, 5));
			dataPane.add(genreLabel,cu.cc.xy(1, 7)); dataPane.add(genreField,cu.cc.xy(3, 7));
			dataPane.add(synopsisLabel,cu.cc.xy(1, 9)); dataPane.add(synopsisField,cu.cc.xy(3, 9));
			dataPane.add(visibilityLabel,cu.cc.xy(1, 11)); dataPane.add(new JLabel("*"), cu.cc.xy(2,11)); dataPane.add(cu.visibilityBox,cu.cc.xy(3, 11)); 
			dataPane.add(modificationLabel,cu.cc.xy(1, 13)); dataPane.add(new JLabel("*"), cu.cc.xy(2,13)); dataPane.add(cu.modificationBox,cu.cc.xy(3, 13));
			fc.setFileFilter(videoFilter);
		}
		else if (tab == cu.uploadSeries){
			dataPane.add(yearLabel,cu.cc.xy(1, 3)); dataPane.add(new JLabel("*"), cu.cc.xy(2,3)); dataPane.add(yearField,cu.cc.xy(3, 3));
			dataPane.add(synopsisLabel,cu.cc.xy(1, 5) ); dataPane.add(synopsisField,cu.cc.xy(3, 5));
			dataPane.add(genreLabel,cu.cc.xy(1, 7) ); dataPane.add(new JLabel("*"), cu.cc.xy(2,7)); dataPane.add(genreField,cu.cc.xy(3, 7));
			dataPane.add(visibilityLabel,cu.cc.xy(1, 9)); dataPane.add(new JLabel("*"), cu.cc.xy(2,9)); dataPane.add(cu.visibilityBox,cu.cc.xy(3, 9)); 
			dataPane.add(modificationLabel,cu.cc.xy(1, 11)); dataPane.add(new JLabel("*"), cu.cc.xy(2,11)); dataPane.add(cu.modificationBox,cu.cc.xy(3, 11));
		}
		dataPane.add(new JLabel("* = champs requis"), cu.cc.xy(3,15));
		dataPane.add(cu.clearButton,cu.cc.xy(3, 17));

		dataPane.repaint(); dataPane.revalidate();
	}

	/**
	 * Fonction qui réinitialise les composants
	 */
	public void clear(){
		for (JTextField fl : cu.fieldList) 
			fl.setText(null);
		cu.seriesBox.setSelectedItem(null);
		cu.albumBox.setSelectedItem(null);
		for (JComboBox<Permissions> cbl : cu.cbPList)
			cbl.setSelectedItem(null);
		cu.uploadButton.setEnabled(false);
		cu.modifyButton.setEnabled(false);
	}

	/** Fonction qui choisissent quels médias dossier de transférer à en fonction du type de media
	 * @param node le type de media a televerser
	 * @return le nom de dossier cible
	 */
	public String chooseDirectory(String node) {
		if (node == cu.uploadAlbums) return "Music";
		else if (node == cu.uploadBooks) return "Books";
		else if (node == cu.uploadEpisodes) return "Series";
		else if (node == cu.uploadImages) return "Images";
		else if (node == cu.uploadMusic) return "Music";
		else if (node == cu.uploadMovies) return "Movies";
		else if (node == cu.uploadSeries) return "Series";
		else return null;
	}

	/** Ajoute des differents field components dans leur listes respectives
	 * 
	 */
	private void setComponentLists() {
		cu.fieldList.add(titleField);
		cu.fieldList.add(yearField);
		cu.fieldList.add(synopsisField);
		cu.fieldList.add(genreField);
		cu.fieldList.add(personField);
		cu.fieldList.add(chronoField);
		cu.fieldList.add(seasonField);

		cu.cbPList.add(cu.modificationBox);
		cu.cbPList.add(cu.visibilityBox);
		cu.cbPList.add(cu.permissionsBox);
	}

	/** Fonction définir les options JFileChooser
	 * @param components
	 */
	private void setFileChooser(Component[] components) {
		fc.setAcceptAllFileFilterUsed(false);
		fc.setControlButtonsAreShown(false);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.addActionListener(this);
		fc.setMinimumSize(new Dimension(500,600));
		fc.setBorder(new EmptyBorder(0, 0, 0, 0));
		Color bg = Color.WHITE;
		setBG(fc.getComponents(), bg, 0 );
		fc.setBackground( bg );
		fc.setOpaque(true);
	}

	/** Fonction qui définit le fond de la JFileChooser
	 * @param jc
	 * @param bg
	 * @param depth
	 */
	private void setBG( Component[] jc, Color bg, int depth )
	{
		for( int i = 0; i < jc.length; i++ ) {
			Component c = jc[i];
			if( c instanceof Container )// {
				setBG( ((Container)c).getComponents(), bg, depth );
			c.setBackground( bg );
		}
	}

	/**
	 * Appelé lorsque les changements de propriété de progression de la tâche.
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = (Integer) evt.getNewValue();
			progressBar.setValue(progress);
			if(progress == 100) dlg.dispose();
			else progressBar.setString("Téléversement en cours");
		}
	}

	/**
	 * Création d'une nouvelle barre de progression de dialogue pour chaque téléchargement
	 */
	public void progressBar(String message) {
		//Create the demo's UI.
		dlg = new JDialog(cu.frameOwner, "Progression", true);
		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setString(message);
		dlg.add(BorderLayout.CENTER, progressBar);
		dlg.add(BorderLayout.NORTH, new JLabel("En cours..."));
		dlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dlg.setSize(300, 75);
		dlg.setLocationRelativeTo(cu.frameOwner);
		dlg.setVisible(true);
	}

	/** Détermine si les champs de métadonnées sont vides ou non
	 * @return boolean si les champs de métadonnées sont vides ou non
	 **/
	private boolean verify(){
		if (titleField.getText().equals(""))
			return false;
		else{
			if (cu.node == cu.uploadAlbums){
				if (personField.getText().equals("") || cu.visibilityBox.getSelectedItem() == null || cu.modificationBox.getSelectedItem() == null || yearField.getText().equals("") || genreField.getText().equals("") )
					return false;
				else return true;
			}
			else if (cu.node == cu.uploadBooks){
				if (personField.getText().equals("")|| cu.visibilityBox.getSelectedItem() == null || cu.modificationBox.getSelectedItem() == null || yearField.getText().equals(""))
					return false;
				else return true;
			}
			else if (cu.node == cu.uploadEpisodes){
				if (cu.seriesBox.getSelectedItem() == null)
					return false;
				else return true;	
			}
			else if (cu.node == cu.uploadImages){
				if (cu.visibilityBox.getSelectedItem() == null || cu.modificationBox.getSelectedItem() == null)
					return false;
				else return true;
			}
			else if (cu.node == cu.uploadMusic){
				if (personField.getText().equals("") || cu.albumBox.getSelectedItem() == null || genreField.getText().equals(""))
					return false;
				else return true;
			}
			else if (cu.node == cu.uploadMovies){
				if (cu.visibilityBox.getSelectedItem() == null || cu.modificationBox.getSelectedItem() == null || yearField.getText().equals(""))
					return false;
				else return true;
			}
			else if (cu.node == cu.uploadSeries){
				if (cu.visibilityBox.getSelectedItem() == null || cu.modificationBox.getSelectedItem() == null || yearField.getText().equals("")|| genreField.getText().equals(""))
					return false;
				else return true;
			}
			else
				return false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == cu.uploadButton){
			if (!verify()){
				JOptionPane.showMessageDialog(this,
						"Tous les champs requis n'ont pas été rempli",
						"Pas asssez de données",
						JOptionPane.ERROR_MESSAGE);
			} else{
				//Instances of javax.swing.SwingWorker are not reusuable, so
				//we create new instances as needed.
				if (cu.node == cu.uploadBooks){
					fileC = new BookCollector(titleField.getText(), yearField.getText(), (int)((Permissions) cu.modificationBox.getSelectedItem()).getLevel(), 
							(int)((Permissions) cu.visibilityBox.getSelectedItem()).getLevel(), fc.getSelectedFile().getName(), personField.getText(), genreField.getText(), synopsisField.getText());					
					messagePB = "Téléversement";
				} else if (cu.node == cu.uploadEpisodes){
					fileC = new EpisodeCollector(titleField.getText(), fc.getSelectedFile().getName(), ((SeriesCollector)cu.seriesBox.getSelectedItem()).getId(), 
							((SeriesCollector) cu.seriesBox.getSelectedItem()).getTitle(), personField.getText(), seasonField.getText(), chronoField.getText()); 
					messagePB = "Conversion... (cela peut prendre un certain temps)";
				} else if (cu.node == cu.uploadImages){
					fileC = new ImageCollector(titleField.getText(), yearField.getText(), (int)((Permissions) cu.modificationBox.getSelectedItem()).getLevel(), 
							(int)((Permissions) cu.visibilityBox.getSelectedItem()).getLevel(), fc.getSelectedFile().getName(), personField.getText());
					messagePB = "Téléversement";
				} else if (cu.node == cu.uploadMusic){
					fileC = new AudioCollector(titleField.getText(), fc.getSelectedFile().getName(), personField.getText(), 
							((AlbumCollector) cu.albumBox.getSelectedItem()).getId(), ((AlbumCollector) cu.albumBox.getSelectedItem()).getTitle()); 
					messagePB = "Conversion... (cela peut prendre un certain temps)";
				} else if (cu.node == cu.uploadMovies){
					fileC = new VideoCollector(titleField.getText(), yearField.getText(), (int)((Permissions) cu.modificationBox.getSelectedItem()).getLevel(), 
							(int)((Permissions) cu.visibilityBox.getSelectedItem()).getLevel(), fc.getSelectedFile().getName(), personField.getText(), genreField.getText(), synopsisField.getText()); 
					messagePB = "Conversion... (cela peut prendre un certain temps)";
				}
				uTask = new UploadTask(chooseDirectory(cu.node), fc.getSelectedFile(), fileC);
				uTask.addPropertyChangeListener(this);
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				uTask.execute();
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						progressBar(messagePB);
						setCursor(null); //turn off the wait cursor
					}
				});
				clear();
			} 
		} else if(e.getSource() == cu.clearButton){
			clear();
		} else if(e.getSource() == cu.addButton){
			if (!verify()){
				JOptionPane.showMessageDialog(this,
						"Tous les champs requis n'ont pas été rempli",
						"Pas asssez de données",
						JOptionPane.ERROR_MESSAGE);
			}
			else{
				if (cu.node == cu.uploadAlbums){
					fileC = new AlbumCollector(titleField.getText(), yearField.getText(), (int)((Permissions) cu.modificationBox.getSelectedItem()).getLevel(), 
							(int)((Permissions) cu.visibilityBox.getSelectedItem()).getLevel(), personField.getText(), synopsisField.getText(), genreField.getText());
				} else if (cu.node == cu.uploadSeries){
					fileC = new SeriesCollector(titleField.getText(), yearField.getText(), (int)((Permissions) cu.modificationBox.getSelectedItem()).getLevel(), 
							(int)((Permissions) cu.visibilityBox.getSelectedItem()).getLevel(), synopsisField.getText(), genreField.getText()); 
				}
				if(SocketManager.getInstance().sendMeta(fileC)){
					try {
						if (fileC instanceof AlbumCollector)
							SocketManager.getInstance().getList("albums");
						else 
							SocketManager.getInstance().getList("series");
					} catch (ClassNotFoundException | IOException e1) {
						JOptionPane.showMessageDialog(this,
								"Impossible de récupérer la liste mise à jour après l'ajout d'une série ou d'un album."
										+ "Veuillez relancer l'application",
										"Erreur de mise à jour",
										JOptionPane.WARNING_MESSAGE);
						e1.printStackTrace();
					}	
					cu.populateLists();
					JOptionPane.showMessageDialog(this,
							"Votre série / album a bien été ajouté!",
							"Ajout effectué",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		} else{
			JFileChooser theFileChooser = (JFileChooser)e.getSource();
			String command = e.getActionCommand();
			if (command.equals(JFileChooser.APPROVE_SELECTION)) {
				File selectedFile = theFileChooser.getSelectedFile();
				if (((double)(((selectedFile.length()/1024)/1024/1024))) > 10){
					int n = JOptionPane.showConfirmDialog(this,
							"Le fichier que vous souhaitez téléverser est plus grand\n"
									+ "que 10Go, êtes-vous sûr de vouloir\n"
									+ "le téléverser tout de même?",
									"Avertissement de téléversement",
									JOptionPane.YES_NO_OPTION);
					if (n == JOptionPane.NO_OPTION) {
						fc.cancelSelection();
						cu.uploadButton.setEnabled(false);
					} else{
						titleField.setText(selectedFile.getName().substring(0, selectedFile.getName().lastIndexOf(".")));
						cu.uploadButton.setEnabled(true);
					}
				} else{
					titleField.setText(selectedFile.getName().substring(0, selectedFile.getName().lastIndexOf(".")));
					cu.uploadButton.setEnabled(true);
				}
			} else if (command.equals(JFileChooser.CANCEL_SELECTION)) {
				titleField.setText("");
				cu.uploadButton.setEnabled(false);
			}
		}

	}

}
