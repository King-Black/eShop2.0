package exceptions;

import Valueobjects.Artikel;

public class NichtGenugAufLagerException extends Exception {public NichtGenugAufLagerException(Artikel EinArtikel, int menge) {
	super("Artikel \"" + EinArtikel.getArtikelName() + "\" ist nicht ausreichend auf Lager! ");
	}

}
