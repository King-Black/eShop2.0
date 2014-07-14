package exceptions;

@SuppressWarnings("serial")
public class ArtikelNurInEinheitenVerfuegbarException extends Exception {
	
	/**
	 * Wird geworfen, wenn ein Artikel nur in bestimmten Packungsgr��en verf�gbar ist.
	 * @param packungsGroesse 
	 */
	
	public ArtikelNurInEinheitenVerfuegbarException(int packungsGroesse) {
		super("Dieser Artikel ist nur in Einheiten von " + packungsGroesse + " verfuegbar.");
	}

}
