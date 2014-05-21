package exceptions;

public class ArtikelNurInEinheitenVerfuegbarException extends Exception {
	
	public ArtikelNurInEinheitenVerfuegbarException(int packungsGroesse) {
		super("Dieser Artikel ist nur in Einheiten von " + packungsGroesse + " verfuegbar.");
	}

}
