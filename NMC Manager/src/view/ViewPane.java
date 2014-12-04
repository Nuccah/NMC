package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Lists;
import model.NMCTableModel;
import controller.SocketManager;

/** Les panneaux utilisées pour l'affichage et l'apercu des medias
 * @author Derek & Antoine
 *
 */
public class ViewPane extends JPanel implements ActionListener{
	private static final long serialVersionUID = 7182514090249903324L;
	private CommonUsed cu = new CommonUsed();
	private JPanel viewPane;
	private String node;
	private String type;
	
	private GridBagConstraints vmc = new GridBagConstraints();
	private final JButton deleteBtn = new JButton("Supprimer");
	private JTable table;
	private JScrollPane scrollPane;
	
	private final String[] albumColumns = new String[]{"", "Titre", "Artiste", "Année", "Genre", "Description", "Peut éditer", "Peut écouter"};
	private final String[] imageColumns = new String[]{"", "Titre", "Photographe", "Année", "Peut éditer", "Peut voir"};
	private final String[] bookColumns = new String[]{"", "Titre", "Auteur", "Année", "Genre", "Synopsis", "Peut éditer", "Peut lire"};
	private final String[] videoColumns = new String[]{"", "Titre", "Année", "Genre", "Synopsis", "Réalisateur", "Peut éditer", "Peut visionner"};
	private final String[] seriesColumns = new String[]{"", "Titre", "Année", "Genre", "Synopsis", "Peut éditer", "Peut visionner"};

	public ViewPane(String tab){
		viewPane = new JPanel();
		this.setLayout(new GridBagLayout());
		viewPane.setLayout(new GridBagLayout());
		this.node = tab;
		vmc.weightx = 1; vmc.weighty = 0.9; vmc.fill = GridBagConstraints.BOTH;
		vmc.gridx = 0; vmc.gridy = 0;
		deleteBtn.addActionListener(this);
		refreshDisplay();
	}
	
	/**
	 * Fonction qui rafraîchit la table pour afficher
	 */
	public void refreshDisplay(){
		this.removeAll();
		viewPane.removeAll();
		scrollPane = null;
		if ((node.equals(cu.viewBooks)))
			createTable("books");
		else if (node.equals(cu.viewImages))
			createTable("images");
		else if (node == cu.viewMusic)
			createTable("albums");
		else if (node == cu.viewMovies)
			createTable("videos");
		else if (node == cu.viewSeries)
			createTable("series");
		viewPane.add(scrollPane, vmc);
		GridBagConstraints vmx = new GridBagConstraints();
		vmx.gridx = 0; vmx.fill = GridBagConstraints.NONE;
		viewPane.add(deleteBtn, vmx);
		viewPane.repaint();
		viewPane.revalidate();
		this.add(viewPane, vmc);
		this.repaint();
		this.revalidate();
	}
	
	/** Function that creates the different types of media necessary
	 * @param type le type de media a afficher
	 */
	private void createTable(String type) {
		try {
			SocketManager.getInstance().getList(type);
		} catch (ClassNotFoundException | IOException e) {
			JOptionPane.showMessageDialog(this,
					"Impossible de récupérer la liste mise à jour après l'ajout d'une série ou d'un album."
							+ "Veuillez relancer l'application",
							"Erreur de mise à jour",
							JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		table = null;
		this.type = type;
		switch (type){
		case "albums": 
			table = new JTable(new NMCTableModel(Lists.getInstance().getAlbumList(), albumColumns));
			break;
		case "books": 
			table = new JTable(new NMCTableModel(Lists.getInstance().getBookList(), bookColumns));
			break;
		case "images": 
			table = new JTable(new NMCTableModel(Lists.getInstance().getImageList(), imageColumns)); 
			break;
		case "series": 
			table = new JTable(new NMCTableModel(Lists.getInstance().getSeriesList(), seriesColumns)); 
			break;
		case "videos": 
			table = new JTable(new NMCTableModel(Lists.getInstance().getVideoList(), videoColumns)); 
			break;
		}
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		// Create the scroll pane and add the table to it.
		scrollPane = new JScrollPane(table);
		scrollPane.repaint();
		scrollPane.revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == deleteBtn){
			if((JOptionPane.showConfirmDialog(this, 
					"Confirmer la suppression?", 
					"Suppression", JOptionPane.YES_NO_OPTION) == 0) && (table.getSelectedRow() != -1))
			if(SocketManager.getInstance().delObject((int)table.getValueAt(table.getSelectedRow(), 0), type))
				JOptionPane.showMessageDialog(this,
						"Suppression du média réussi",
						"Succes",
						JOptionPane.INFORMATION_MESSAGE);
			else
				JOptionPane.showMessageDialog(this,
						"Le systeme n'a pas pu supprimer du média. "
						+ "Vérifier si vous avez les droits!",
						"Echec",
						JOptionPane.ERROR_MESSAGE);
			this.refreshDisplay();
		}		
	}
}
