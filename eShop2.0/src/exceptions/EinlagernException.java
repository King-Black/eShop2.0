package exceptions;

public class EinlagernException extends Exception {
	public EinlagernException(){
		super("Die Eingaben muessen groesser 0 sein.");
	}

}
