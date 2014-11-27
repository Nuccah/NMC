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
import model.MetaDataCollector;
import model.NMCTableModel;
import controller.SocketManager;

public class ViewPane extends JPanel implements ActionListener{
	private static final long serialVersionUID = 7182514090249903324L;
	private CommonUsed cu = new CommonUsed();
	private JPanel viewPane;
	private String node;
	
	private GridBagConstraints vmc = new GridBagConstraints();
	private final JButton deleteBtn = new JButton("Supprimer");
	private JTable table;
	
	private final String[] albumColumns = new String[]{"", "Title", "Artist", "Year", "Genre", "Description", "Modification Rights", "Visibility Rights"};
	private final String[] imageColumns = new String[]{"", "Title", "Photographer", "Year", "Modification Rights", "Visibility Rights"};
	private final String[] bookColumns = new String[]{"", "Title", "Author", "Year", "Genre", "Synopsis", "Modification Rights", "Visibility Rights"};
	private final String[] videoColumns = new String[]{"", "Title", "Year", "Genre", "Synopsis", "Director", "Modification Rights", "Visibility Rights"};
	private final String[] seriesColumns = new String[]{"", "Title", "Year", "Genre", "Synopsis", "Modification Rights", "Visibility Rights"};

	public ViewPane(String tab){
		viewPane = new JPanel();
		this.removeAll();
		this.setLayout(new GridBagLayout());
		viewPane.removeAll();
		viewPane.setLayout(new GridBagLayout());
		this.node = tab;
		vmc.weightx = 1; vmc.weighty = 0.9; vmc.fill = GridBagConstraints.BOTH;
		vmc.gridx = 0; vmc.gridy = 0;
		GridBagConstraints vmx = new GridBagConstraints();
		vmx.gridx = 0; vmx.fill = GridBagConstraints.NONE;
		deleteBtn.addActionListener(this);
		if ((node.equals(cu.viewBooks)))
			viewPane.add(createTable("books"), vmc);
		else if (node.equals(cu.viewImages))
			viewPane.add(createTable("images"), vmc);
		else if (node == cu.viewMusic)
			viewPane.add(createTable("albums"), vmc);
		else if (node == cu.viewMovies)
			viewPane.add(createTable("videos"), vmc);
		else if (node == cu.viewSeries)
			viewPane.add(createTable("series"), vmc);
		viewPane.add(deleteBtn, vmx);
		viewPane.revalidate();
		this.add(viewPane, vmc);
	}
	
	private JScrollPane createTable(String type) {
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
		JScrollPane scrollPane = new JScrollPane(table);
	
		// Add the scroll pane to this panel.
		return scrollPane;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == deleteBtn){
			if(JOptionPane.showConfirmDialog(this, 
					"Confirmer la suppression?", 
					"Suppression", JOptionPane.YES_NO_OPTION) == 0)
			SocketManager.getInstance().delObject(new MetaDataCollector((int)table.getValueAt(table.getSelectedRow(), 0),
					(String)table.getValueAt(table.getSelectedRow(), 1)));
		}		
	}
}
