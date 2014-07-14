package exceptions;

import Valueobjects.Artikel;

@SuppressWarnings("serial")
public class NichtGenugAufLagerException extends Exception {public NichtGenugAufLagerException(Artikel EinArtikel) {
	/**
	 * Wird geworfen, wenn ein Artikel ausgelagert oder verkauft wird und nicht mehr genug auf Lager ist.
	 */
	super("Artikel \"" + EinArtikel.getArtikelName() + "\" ist nicht ausreichend auf Lager! ");
	}
}
