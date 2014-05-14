package exceptions;

import Valueobjects.Artikel;

public class NichtGenugAufLagerException extends Exception {public NichtGenugAufLagerException(Artikel artikel, int menge) {
	super("Artikel \"" + artikel.getArtikelName() + "\" ist nicht ausreichend auf Lager! ");
	}

}
