package view;

import java.awt.GridBagConstraints;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Lists;
import model.NMCTableModel;
import controller.SocketManager;

public class ViewPane extends JPanel{
	private static final long serialVersionUID = 7182514090249903324L;
	private CommonUsed cu = new CommonUsed();
	private JPanel viewPane;
	private String node;
	
	private GridBagConstraints vmc = new GridBagConstraints();
	
	private final String[] albumColumns = new String[]{"Title", "Artist", "Year", "Genre", "Description", "Modification Rights", "Visibility Rights"};
	private final String[] imageColumns = new String[]{"Title", "Photographer", "Year", "Modification Rights", "Visibility Rights"};
	private final String[] bookColumns = new String[]{"Title", "Author", "Year", "Genre", "Synopsis", "Modification Rights", "Visibility Rights"};
	private final String[] videoColumns = new String[]{"Title", "Year", "Genre", "Synopsis", "Director", "Modification Rights", "Visibility Rights"};
	private final String[] seriesColumns = new String[]{"Title", "Year", "Genre", "Synopsis", "Modification Rights", "Visibility Rights"};

	public ViewPane(String tab){
		viewPane = new JPanel();
		this.removeAll();
		viewPane.removeAll();
		this.node = tab;
		vmc.weightx = 1; vmc.weighty = 1; vmc.fill = GridBagConstraints.BOTH;

		if ((node.equals(cu.viewBooks)))
			viewPane.add(createTable("books"));
		else if (node.equals(cu.viewImages))
			viewPane.add(createTable("images"));
		else if (node == cu.viewMusic)
			viewPane.add(createTable("albums"));
		else if (node == cu.viewMovies)
			viewPane.add(createTable("videos"));
		else if (node == cu.viewSeries)
			viewPane.add(createTable("series"));
		viewPane.revalidate();
		this.add(viewPane);
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
		JTable table = null;
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
		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);
		// Add the scroll pane to this panel.
		return scrollPane;
	}
}
