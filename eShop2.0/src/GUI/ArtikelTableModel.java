package GUI;

import javax.swing.table.AbstractTableModel;

import GUI.HauptFenster;
import Valueobjects.Artikel;
import Valueobjects.MehrfachArtikel;

public class ArtikelTableModel extends AbstractTableModel{
	//Spalten�berschriften
		private static final String[] COLUMN_NAMES = new String[] {"Nr.", "Artikel", "Menge", "Verkaufseinheit", "Preis", "St�ckpreis"};
		//Datentypen der Spalten
		private static final Class<?>[] COLUMN_CLASSES = new Class<?>[]{Integer.class, String.class, Integer.class, String.class, String.class, String.class};
		
		/**
		 * Initialisiert ein ArtikelTableModel-Objekt.
		 */
		public ArtikelTableModel(){
			super();
		}
}