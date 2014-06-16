package exceptions;

import Valueobjects.Artikel;
import Valueobjects.Kunde;
//import Valueobjects.LagerEreignis;
import Valueobjects.Mitarbeiter;
import Valueobjects.Position;

public class BereitsVorhandenException {
	
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
		super("Artikel " + artikel.getName() + " mit der Id: " + artikel.getArtikelNummer() + " bereits eingetragen!");
	}

	/**
	 * Wird geworfen, wenn eine Position bzw. im Warenkorb eingetragen werden soll und diese dort bereits eingetragen ist.
	 * @param position Die Position die eingetragen werden sollte.
	 */
	public BereitsVorhandenException(Position position) {
		super("Position mit dem Artikel: " + position.getArtikel().getName() + " bereits eingetragen!");
	}
}
