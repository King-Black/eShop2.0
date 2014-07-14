package exceptions;

@SuppressWarnings("serial")
public class WarenkorbLeerException extends Exception{
	/**
	 * Wird geworfen, wenn ein leerer Warenkorb gekauft werden soll.
	 */
	public WarenkorbLeerException(){
		super();
	}
}
