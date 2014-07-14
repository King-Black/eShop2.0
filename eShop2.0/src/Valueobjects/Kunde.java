package Valueobjects;

/**
 * Klasse zur Repr�sentation eines Kunden.
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
	 * @param adresse Stra�e des Kunden.
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
	 Die Methode gibt die Adresse des Kunden zur�ck.
	 * @return Gibt die Adresse des Kunden zur�ck.
	 */
	public String getAdresse() {
		return adresse + " \n" + plz + " \n" + ort;
	}
	
	/**
	 * Die Methode gibt den Warenkorb des Kunden zur�ck.
	 * @return Gibt Warenkorb des Kunden zur�ck.
	 */
	public Warenkorb getWarenkorb() {
		return warenkorb;
	}
	
	/**
	 * Die Methode gibt die Strasse des Kunden zur�ck
	 * @return Gibt Strasse des kunden zur�ck.
	 */
	public String getStrasse() {
		return adresse;
	}
	
	/**
	 * Die Methode gibt die Postleitzahl des Wohnortes des Kundes zur�ck.
	 * @return Gibt die PLZ zur�ck.
	 */
	public int getPlz() {
		return plz;
	}
	
	/**
	 * Die Methode gibt den Wohnort des Kunden zur�ck.
	 * @return Gibt Wohnort zur�ck.
	 */
	public String getOrt(){
		return ort;
	}
}
