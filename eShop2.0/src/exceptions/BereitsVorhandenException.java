package exceptions;

import Valueobjects.Artikel;
import Valueobjects.Position;
import Valueobjects.User;
//import Valueobjects.LagerEreignis;

@SuppressWarnings("serial")
public class BereitsVorhandenException extends Exception {
	
	/**
	 * Wird geworfen, wenn versucht wird einen Mitarbeiter anzulegen 
	 * und dieser bereits in der Mitarbeiterverwaltung eingetragen ist. 
	 * @param mitarbeiter Der Mitarbeiter der eingelegt werden sollte.
	 */
	

	public BereitsVorhandenException(User user) {
		super("User " + user.getVorName() + " " + user.getNachName() + " bereits eingetragen!");
	}
	
		
	/**
	 * Wird geworfen, wenn ein Artikel eingetragen werden soll und dieser dort bereits eingetragen ist.
	 * @param artikel Der Artikel der eingetragen werden sollte.
	 */
	public BereitsVorhandenException(Artikel artikel) {
		super("Artikel " + artikel.getArtikelName() + " mit der Nummer: " + artikel.getArtikelNummer() + " bereits eingetragen!");
	}

	/**
	 * Wird geworfen, wenn eine Position bzw. im Warenkorb eingetragen werden soll und diese dort bereits eingetragen ist.
	 * @param position Die Position die eingetragen werden sollte.
	 */
	public BereitsVorhandenException(Position position) {
		super("Position mit dem Artikel: " + position.getArtikel().getArtikelName() + " bereits eingetragen!");
	}
}
