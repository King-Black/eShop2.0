package exceptions;

public class KonnteNichtSpeichernException extends Exception {
	/**
	 * Wird geworfen, wenn die EShop-Daten nicht in der Datei gespeichert werden konnten.
	 */
	public KonnteNichtSpeichernException() {
		super("Fehler: EShop-Daten konnten nicht gespeichert werden!");
	}
}
