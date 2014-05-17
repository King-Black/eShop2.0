package Domain;

import java.util.Comparator;

import Valueobjects.Artikel;
/**
 * Klasse zum sortieren nach ArtikelNummern.
 *
 */
public class comperatorArtikelNummer implements Comparator<Artikel> {
		/**
		 * Methode zum sortieren nach ArtikelNummern.
		 */
		public comperatorArtikelNummer() {
			super();
		}
		public int compare(Artikel arg0, Artikel arg1) {
				if (arg0.getArtikelNummer() > arg1.getArtikelNummer()) {
					return 1;
				} else {
					if (arg0.getArtikelNummer() == arg1.getArtikelNummer()){
						return 0;
					} else {
						return -1;
					}
				} 	
		}
}
