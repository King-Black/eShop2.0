package Domain;

import java.util.Comparator;

import Valueobjects.Artikel;
/**
 * Klasse zum sortieren nach ArtikelNamen.
 *
 */
	public class comperatorArtikelName implements Comparator<Artikel>{
		public comperatorArtikelName() {
			super();
		}
		/**
		 *  Methode zum sortieren nach ArtikelNamen.
		 */
		@Override
		public int compare(Artikel arg0, Artikel arg1) {
			return arg0.getArtikelName().compareTo(arg1.getArtikelName());
		}
}
