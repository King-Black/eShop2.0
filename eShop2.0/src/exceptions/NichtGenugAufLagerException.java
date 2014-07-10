package exceptions;

import Valueobjects.Artikel;

@SuppressWarnings("serial")
public class NichtGenugAufLagerException extends Exception {public NichtGenugAufLagerException(Artikel EinArtikel) {
	super("Artikel \"" + EinArtikel.getArtikelName() + "\" ist nicht ausreichend auf Lager! ");
	}

}
