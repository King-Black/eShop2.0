package GUI;

import java.util.Collection;

import javax.swing.table.AbstractTableModel;

import Valueobjects.Ereignis;
import Valueobjects.Mitarbeiter;
import Valueobjects.User;

/**
 * Klasse zur Erstellung eines LagerEreignis-Tabellen-Modells.
 *
 */
@SuppressWarnings("serial")
public class LagerEreignisTableModel extends AbstractTableModel {
	//Spalten�berschriften
	private static final String[] COLUMN_NAMES = new String[] {"Art", "Artikel", "Menge", "Datum", "Nutzer", "Typ"};
	//Datentypen der Spalten
	private static final Class<?>[] COLUMN_CLASSES = new Class<?>[]{String.class, String.class, Integer.class, String.class, String.class, String.class};
	private Collection<Ereignis> ereignisse;
	
	/**
	 * Initialisiert ein LagerEreignisTableModel-Objekt.
	 */
	public LagerEreignisTableModel(Collection<Ereignis> ereignisse){
		super();
		
		this.ereignisse = ereignisse;
	}
	
	/**
	 * Die Methode gibt die Anzahl der Spalten zur�ck.
	 * @return Gibt die Anzahl der Spalten zur�ck.
	 */
	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}
	
	/**
	 * Die Methode gibt die Anzahl der Zeilen zur�ck.
	 * @return Gibt die Anzahl der Zeilen zur�ck.
	 */
	@Override
	public int getRowCount() {
		return this.ereignisse.size();
	}
	
	/**
	 * Die Methode belegt die �bergebenen Spalten und Zeilen mit Werten
	 * und gibt das Objekt an der jeweiligen Stelle zur�ck.
	 * @param row Die Zeile, die mit Werten belegt werden soll.
	 * @param col Die Spalte, die mit Werten belegt werden soll.
	 * @return Das einzutragende Objekt f�r die jeweilige Spalte.
	 */
	@Override
	public Object getValueAt(int row, int col) {
		Ereignis l = this.getLagerEreignis(row);
		//Spalten mit Werten belegen
		switch(col) {
		case 0:
			//1. Spalte: Art
			/*String aktion = "";
			if(l.getAktion() == 0){
				aktion = "Auslagern";
			}else{
				aktion = "Einlagern";
			}
			return aktion;*/
			return l.getAktion();
		case 1:
			//2. Spalte: Artikel
			return l.getArtikel().getArtikelName();
		case 2:
			//3. Spalte: Menge
			return l.getMenge();
		case 3:
			//4. Spalte: Datum
			return l.getDate();
		//keine Personen in den Ereignissen
		case 4:
			//5. Spalte: Name
			String person = l.getNutzer().getVorName() + " " + l.getNutzer().getNachName();
			return person;
		case 5:
			//6. Spalte: Typ
			String typ = "";
			User p = l.getNutzer();
			if(p instanceof Mitarbeiter){
				typ = "Mitarbeiter";
			}else{
				typ = "Kunde";
			}
			return typ;
		default:
			return null;
		}
	}

	/**
	 * Die Methode gibt die Klassen der einzelnen Spalten zur�ck.
	 * @return Gibt die Klassen der einzelnen Spalten zur�ck.
	 */
	public Class<?> getColumnClass(int col) {
	    return COLUMN_CLASSES[col];
	}

	/**
	 * Die Methode gibt die Namen der Spalten zur�ck.
	 * @return Gibt die Namen der Spalten zur�ck.
	 */
	public String getColumnName(int col) {
		return COLUMN_NAMES[col];
	}

	/**
	 * Die Methode gibt das LagerEreignis in der �bergebenen Zeile zur�ck.
	 * @param row Aus dieser Spalte soll das LagerEreignis zur�ckgegeben werden.
	 * @return Gibt das LagerEreignis in der �bergebenen Zeile zur�ck.
	 */
	public Ereignis getLagerEreignis(int row) {
		return (Ereignis)this.ereignisse.toArray()[row];
	}
}

