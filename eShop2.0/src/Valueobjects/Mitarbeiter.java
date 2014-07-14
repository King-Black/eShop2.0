package Valueobjects;

/**
 * Klasse zur Repräsentation eines Mitarbeiters.
 * @author Imke Schneider
 *
 */

public class Mitarbeiter extends User {
	/**
	 * Initialisiert Mitarbeiterobejkt.
	 * @param benutzerName Benutzername des Mitarbeiters.
	 * @param passwort Passwort des Mitarbeiters.
	 * @param nr Laufendenr. des Mitarbeiters.
	 * @param anrede Anrede des Mitarbeiters.
	 * @param vorName Vorname des Mitarbeiters.
	 * @param nachName Nachname des Mitarbeiters.
	 */
	public Mitarbeiter(String benutzerName, String passwort, int nr, String anrede, String vorName, String nachName){
		super(benutzerName, passwort, nr, anrede, vorName, nachName);
		
	}
	
	public String toString() {
		return ("Mitarbeiter Nr: " + nr + " | " + "Benutzername: " + name + " | Anrede: " + anrede + " | Name: " + vorName + " " + nachName);
	}
}
