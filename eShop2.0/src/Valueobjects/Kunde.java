package Valueobjects;

/**
 * Klasse zur Repräsentation eines Kunden.
 * @author Imke Schneider
 *
 */

public class Kunde extends User {
	
	private String adresse;
	private int plz;
	private String ort;
	private Warenkorb warenkorb = new Warenkorb();
	
	/**
	 * Initialisiert ein Kundenobjekt.
	 * @param name Benutzername des Kunden.
	 * @param passwort Passwort des Kunden.
	 * @param nr Laufendenr. des Kunden.
	 * @param anrede Anrede des Kunden.
	 * @param vorName Vorname des Kunden.
	 * @param nachName Nachname des Kunden.
	 * @param adresse Straße des Kunden.
	 * @param plz Postleitzahl des Wohnortes des Kunden.
	 * @param ort Wohnort des Kunden.
	 */
	
	public Kunde(String name, String passwort, int nr, String anrede, String vorName, String nachName, String adresse, int plz, String ort){
		super(name, passwort, nr, anrede, vorName, nachName);
		this.adresse = adresse;
		this.plz = plz;
		this.ort = ort;
		
	}
	/**
	 * Die Methode regelt die Ausgabe des Kunden.
	 */
	public String toString() {
		return ("Kunde Nr: " + nr + " | Benutzername: " + name + " | Anrede: " + anrede + " | Name: " + vorName + " " + nachName + " | Strasse und Nr: " + adresse + " | PLZ: " + plz + " | Ort: " + ort);
	}
	
	/**
	 Die Methode gibt die Adresse des Kunden zurück.
	 * @return Gibt die Adresse des Kunden zurück.
	 */
	public String getAdresse() {
		return adresse + " \n" + plz + " \n" + ort;
	}
	
	/**
	 * Die Methode gibt den Warenkorb des Kunden zurück.
	 * @return Gibt Warenkorb des Kunden zurück.
	 */
	public Warenkorb getWarenkorb() {
		return warenkorb;
	}
	
	/**
	 * Die Methode gibt die Strasse des Kunden zurück
	 * @return Gibt Strasse des kunden zurück.
	 */
	public String getStrasse() {
		return adresse;
	}
	
	/**
	 * Die Methode gibt die Postleitzahl des Wohnortes des Kundes zurück.
	 * @return Gibt die PLZ zurück.
	 */
	public int getPlz() {
		return plz;
	}
	
	/**
	 * Die Methode gibt den Wohnort des Kunden zurück.
	 * @return Gibt Wohnort zurück.
	 */
	public String getOrt(){
		return ort;
	}
}
