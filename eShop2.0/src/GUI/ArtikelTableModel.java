package GUI;

import javax.swing.table.AbstractTableModel;

import GUI.HauptFenster;
import Valueobjects.Artikel;
import Valueobjects.MehrfachArtikel;

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
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getValueAt(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return null;
		}
}