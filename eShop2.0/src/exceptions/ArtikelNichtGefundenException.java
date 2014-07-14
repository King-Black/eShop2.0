package exceptions;

import Valueobjects.Artikel;

@SuppressWarnings("serial")
public class ArtikelNichtGefundenException extends Exception {
	public ArtikelNichtGefundenException (Artikel a){
		super("Der Artikel " + a.getArtikelName() + " wurde nicht gefunden.");
		/**
		 * Wird geworfen, wenn ein Artikel anhand des Namen nicht gefunden wird.
		 */
	}
	
	public ArtikelNichtGefundenException (int artID){
		super("Der Artikel mit der ID " + artID + " wurde nicht gefunden.");
	}
	/**
	 * Wird geworfen, wenn ein Artikel anhand der Artikelnr. nicht gefunden wird.
	 */
	
}
