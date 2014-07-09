package GUI;

import javax.swing.table.AbstractTableModel;

import Valueobjects.Artikel;
import Valueobjects.MehrfachArtikel;

@SuppressWarnings("serial")
public class ArtikelTableModel extends AbstractTableModel{
	//Spaltenüberschriften
	private static final String[] COLUMN_NAMES = new String[] {"Nr.", "Artikel", "Menge", "Verkaufseinheit", "Preis", "Stückpreis"};
	//Datentypen der Spalten
	private static final Class<?>[] COLUMN_CLASSES = new Class<?>[]{Integer.class, String.class, Integer.class, String.class, String.class, String.class};
	
	/**
	 * Initialisiert ein ArtikelTableModel-Objekt.
	 */
	public ArtikelTableModel(){
		super();
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public int getRowCount() {
		return HauptFenster.shopVerwaltung.gibAlleArtikel().size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Artikel a = this.getArtikel(row);
		boolean mehrfachA = a instanceof MehrfachArtikel;
		MehrfachArtikel tmp = mehrfachA ? (MehrfachArtikel)a : null;
		
		switch(col) {
		case 0:
			//1. Spalte: Nr
			return a.getArtikelNummer();
		case 1:
			//2. Spalte: Artikel
			return a.getArtikelName();
		case 2:
			//3. Spalte: Menge
			return a.getArtikelBestand();
		case 3:
			//4. Spalte: Verkaufseinheit
			return mehrfachA ? "" + tmp.getPackungsgroesse() : " ";
		case 4:
			//5. Spalte: Preis
			return mehrfachA ? (tmp.getPackungsgroesse() * tmp.getStueckPreis()) + "€" : a.getPreis() + "€";
		case 5:
			//6. Spalte: Stückpreis
			return mehrfachA ? tmp.getStueckPreis() + "€" : " ";
		default:
			return null;
		}
	}
	
	public Class<?> getColumnClass(int col) {
	    return COLUMN_CLASSES[col];
	}
	
	public String getColumnName(int col) {
		return COLUMN_NAMES[col];
	}
	
	public Artikel getArtikel(int row) {
		return (Artikel)(HauptFenster.shopVerwaltung.gibAlleArtikel().toArray())[row];
	}
}