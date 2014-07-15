package exceptions;

@SuppressWarnings("serial")
public class KennwortFalschException extends Exception {
	/**
	 * Wird geworfen, wenn ein falsches Passwort bei Login in eingegeben wird.
	 */
	public KennwortFalschException(){
		super("Das eingegebene Kennwort ist falsch!");
	}

}
