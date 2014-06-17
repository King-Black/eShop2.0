package exceptions;

import Valueobjects.Position;

@SuppressWarnings("serial")
public class NichtGefundenException extends Exception {
	
	/**
	 * Wird geworfen, wenn anhand der Artikel-Id. nach einem Artikel gesucht wird und dieser nicht gefunden wurde.  
	 * @param artikelId Die Artikel-Id. nach der gesucht wurde.
	 */
	public NichtGefundenException(int artikelId) {
		super("Der gesuchte Artikel mit der Id: " + artikelId +
				" konnte nicht gefunden werden!");
	}
	
	/**
	 * Wird geworfen, wenn nach einer Position gesucht wird und dieser nicht gefunden wurde.
	 * @param position Die Position nach der gesucht wurde.
	 */
	public NichtGefundenException(Position position) {
		super("Position mit dem Artikel: " + position.getArtikel().getArtikelName() + " nicht gefunden!");
	}
}