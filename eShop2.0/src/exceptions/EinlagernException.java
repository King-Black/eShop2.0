package exceptions;

@SuppressWarnings("serial")
public class EinlagernException extends Exception {
	/**
	 * Wird geworfen, wenn beim Einlagern ein Wert von 0 oder Minus eingegeben wird.
	 */
	public EinlagernException(){
		super("Die Eingaben muessen groesser 0 sein.");
	}
}
