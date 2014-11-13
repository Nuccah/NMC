package model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class NMCTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 510301159530188722L;
	private String[] columnNames = null;
	ArrayList<?> list = null;

	public NMCTableModel(ArrayList<?> list, String[] columnNames) {
		this.list = list;
		this.columnNames = columnNames;
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return list.size();
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Object getValueAt(int row, int col) {

		MetaDataCollector mdc = (MetaDataCollector) list.get(row);
		switch (col) {
		case 0:
			return mdc.getId();
		case 1:
			return mdc.getTitle();
		}
		if (mdc instanceof AudioCollector){
			switch (col) {
			case 2:
				return ((AudioCollector) mdc).getAlbumName();
			case 3:
				return ((AudioCollector) mdc).getArtist();
			default: return "unknown";
			}
		}
		else if (mdc instanceof AlbumCollector){
			switch (col) {
			case 2:
				return ((AlbumCollector) mdc).getArtist();
			case 3:
				return ((AlbumCollector) mdc).getYear();
			case 4:
				return ((AlbumCollector) mdc).getGenre();
			case 5:
				return ((AlbumCollector) mdc).getDescription();
			case 6:
				return Lists.getInstance().returnLabel(mdc.getModificiationID());
			case 7:
				return Lists.getInstance().returnLabel(mdc.getVisibilityID());
			default: return "unknown";
			}
		}
		else if (mdc instanceof BookCollector){
			System.out.println(((BookCollector) mdc).getAuthor());
			switch (col) {
			case 2:
				return ((BookCollector) mdc).getAuthor();
			case 3:
				return ((BookCollector) mdc).getYear();
			case 4:
				return ((BookCollector) mdc).getGenre();
			case 5:
				return ((BookCollector) mdc).getSynopsis();
			case 6:
				return Lists.getInstance().returnLabel(mdc.getModificiationID());
			case 7:
				return Lists.getInstance().returnLabel(mdc.getVisibilityID());
			default: return "unknown";
			}
		}
		else if (mdc instanceof EpisodeCollector){
			switch (col) {
			case 2:
				return ((EpisodeCollector) mdc).getSeriesName();
			case 3:
				return ((EpisodeCollector) mdc).getSeason();
			case 4:
				return ((EpisodeCollector) mdc).getChrono();
			case 5:
				return ((EpisodeCollector) mdc).getDirector();
			case 6:
				return Lists.getInstance().returnLabel(mdc.getModificiationID());
			case 7:
				return Lists.getInstance().returnLabel(mdc.getVisibilityID());
			default: return "unknown";
			}
		}
		else if (mdc instanceof ImageCollector){
			switch (col) {
			case 2:
				return ((ImageCollector) mdc).getPhotographer();
			case 3:
				return ((ImageCollector) mdc).getYear();
			case 4:
				return Lists.getInstance().returnLabel(mdc.getModificiationID());
			case 5:
				return Lists.getInstance().returnLabel(mdc.getVisibilityID());
			default: return "unknown";
			}
		}	
		else if (mdc instanceof SeriesCollector){
			switch (col) {
			case 2:
				return ((SeriesCollector) mdc).getYear();
			case 3:
				return ((SeriesCollector) mdc).getGenre();
			case 4:
				return ((SeriesCollector) mdc).getSynopsis();
			case 5:
				return Lists.getInstance().returnLabel(mdc.getModificiationID());
			case 6:
				return Lists.getInstance().returnLabel(mdc.getVisibilityID());
			default: return "unknown";
			}
		}
		else if (mdc instanceof VideoCollector){
			switch (col) {
			case 2:
				return ((VideoCollector) mdc).getYear();
			case 3:
				return ((VideoCollector) mdc).getGenre();
			case 4:
				return ((VideoCollector) mdc).getSynopsis();
			case 5:
				return ((VideoCollector) mdc).getDirector();
			case 6:
				return Lists.getInstance().returnLabel(mdc.getModificiationID());
			case 7:
				return Lists.getInstance().returnLabel(mdc.getVisibilityID());
			default: return "unknown";
			}
		}
		else
			return "unknown";
		
	}

	public Class<?> getColumnClass(int c) {
		Object value=this.getValueAt(0,c);  
		return (value==null?Object.class:value.getClass());
	}
}