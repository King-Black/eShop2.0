package GUI;

import java.util.Collection;

import javax.swing.table.AbstractTableModel;

import Valueobjects.Kunde;
import Valueobjects.MehrfachArtikel;
import Valueobjects.Position;
import Valueobjects.Warenkorb;

/**
 * Klasse zur Erstellung eines Warenkorb-Tabellen-Modells.
 *
 */

public class WarenkorbTableModel extends AbstractTableModel {
		//Spaltenüberschriften
		private static final String[] COLUMN_NAMES = new String[] {"Nr.", "Artikel", "Menge", "Verkaufseinheit", "Preis", "Stückpreis"};
		//Datentypen der Spalten
		private static final Class<?>[] COLUMN_CLASSES = new Class<?>[]{Integer.class, String.class, Integer.class, String.class, String.class, String.class};
		private Kunde k;
		private Warenkorb w;
		@SuppressWarnings("unused")
		private Collection<Position> positionen;
		
		public WarenkorbTableModel(){
			super();
			
			k = (Kunde)HauptFenster.benutzer;
			w = k.getWarenkorb();
			positionen = w.getPositionen();
		}
		
		@Override
		public int getColumnCount() {
			return COLUMN_NAMES.length;
		}
		
		@Override
		public int getRowCount() {
			return w.getAnzahlPositionen();
		}
		
		public Object getValueAt(int row, int col) {
			Position p = this.getPosition(row);
			boolean massengut = p.getArtikel() instanceof MehrfachArtikel;
			MehrfachArtikel tmp = massengut ? (MehrfachArtikel)p.getArtikel() : null;
			
			//Spalten mit Werten belegen
			switch(col) {
			case 0:
				//1. Spalte: ID
				return p.getArtikel().getArtikelNummer();
			case 1:
				//2. Spalte: Name
				return p.getArtikel().getArtikelName();
			case 2:
				//3. Spalte: Lagerbestand
				return p.getMenge();
			case 3:
				//4. Spalte: Massengut?
				return massengut ? "" + tmp.getPackungsgroesse() : " ";
			case 4:
				//5. Spalte: Gesamtpreis
				return massengut ? (tmp.getPackungsgroesse() * p.getArtikel().getPreis()) + "€" : p.getArtikel().getStueckPreis() + "€";
			case 5:
				//6. Spalte: Stückpreis
				return massengut ?  p.getArtikel().getPreis() + "€" : " ";
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

		public Position getPosition(int row) {
			return (Position)(w.getPositionen().toArray())[row];
		}

}
