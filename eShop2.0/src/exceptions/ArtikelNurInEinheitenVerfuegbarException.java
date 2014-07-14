package exceptions;

@SuppressWarnings("serial")
public class ArtikelNurInEinheitenVerfuegbarException extends Exception {
	
	/**
	 * Wird geworfen, wenn ein Artikel nur in bestimmten Packungsgrößen verfügbar ist.
	 * @param packungsGroesse 
	 */
	
	public ArtikelNurInEinheitenVerfuegbarException(int packungsGroesse) {
		super("Dieser Artikel ist nur in Einheiten von " + packungsGroesse + " verfuegbar.");
	}

}
