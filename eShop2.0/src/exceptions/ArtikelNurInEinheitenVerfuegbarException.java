package exceptions;

@SuppressWarnings("serial")
public class ArtikelNurInEinheitenVerfuegbarException extends Exception {
	
	/**
	 * Wird geworfen, wenn ein Artikel nur in bestimmten Packungsgr��en verf�gbar ist.
	 * @param packungsGroesse Die Einheit in die der Artikel verpackt wird.
	 */
	
	public ArtikelNurInEinheitenVerfuegbarException(int packungsGroesse) {
		super("Dieser Artikel ist nur in Einheiten von " + packungsGroesse + " verfuegbar.");
	}

}
