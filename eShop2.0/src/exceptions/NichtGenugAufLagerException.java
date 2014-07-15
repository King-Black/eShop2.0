package exceptions;

import Valueobjects.Artikel;

@SuppressWarnings("serial")
public class NichtGenugAufLagerException extends Exception {
	/**
	 * Wird geworfen, wenn ein Artikel ausgelagert oder verkauft wird und nicht mehr genug auf Lager ist.
	 */
	public NichtGenugAufLagerException(Artikel EinArtikel) {
		super("Artikel \"" + EinArtikel.getArtikelName() + "\" ist nicht ausreichend auf Lager! ");
	}
}
