package GUI;

import javax.swing.table.AbstractTableModel;

import Valueobjects.Artikel;
import Valueobjects.Kunde;
import Valueobjects.MehrfachArtikel;
import Valueobjects.Warenkorb;

/**
 * Klasse zur Erstellung eines Warenkorb-Tabellen-Modells.
 *
 */

@SuppressWarnings("serial")
public class WarenkorbTableModel extends AbstractTableModel {
		//Spaltenüberschriften
		private static final String[] COLUMN_NAMES = new String[] {"Nr.", "Artikel", "Menge", "Verkaufseinheit", "Preis", "Stückpreis"};
		//Datentypen der Spalten
		private static final Class<?>[] COLUMN_CLASSES = new Class<?>[]{Integer.class, String.class, Integer.class, String.class, String.class, String.class};
		private Kunde k;
		private Warenkorb w;
		
		public WarenkorbTableModel(){
			super();
			
			k = (Kunde)HauptFenster.benutzer;
			w = k.getWarenkorb();
		}
		
		@Override
		public int getColumnCount() {
			return COLUMN_NAMES.length;
		}
		
		@Override
		public int getRowCount() {
			return w.getInhalt().size();
		}
		
		public Object getValueAt(int row, int col) {
			Artikel a = this.getArtikel(row);
			int menge = this.getMenge(row);
			boolean massengut = a instanceof MehrfachArtikel;
			MehrfachArtikel tmp = massengut ? (MehrfachArtikel)a : null;
			
			//Spalten mit Werten belegen
			switch(col) {
			case 0:
				//1. Spalte: Nr
				return a.getArtikelNummer();
			case 1:
				//2. Spalte: Artikel
				return a.getArtikelName();
			case 2:
				//3. Spalte: Menge (gewählte Menge)
				return menge;
			case 3:
				//4. Spalte: Verkaufseinheit
				return massengut ? "" + tmp.getPackungsgroesse() : " ";
			case 4:
				//5. Spalte: GPreis
				return massengut ? (tmp.getPackungsgroesse() * tmp.getStueckPreis()) + "€" : a.getPreis() + "€";
			case 5:
				//6. Spalte: Stückpreis
				return massengut ?  a.getPreis() + "€" : " ";
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
		
		// das statt 2 dem Array
		//holt sich das Artikelobjekt
		public Artikel getArtikel(int row) {
			return (Artikel)((w.getInhalt().keySet()).toArray())[row];
		}
		//holt sich Menge des Artikels die in den WK gelegt wurde
		public int getMenge(int row) {
			return (int)((w.getInhalt().values()).toArray())[row];
		}

}
