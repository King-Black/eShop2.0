package Valueobjects;

import java.util.Date;

/**
 * Klasse zur Repräsentation einer Veränderung eines Lagerbestandes.
 * @author Imke Schneider
 */
public class Ereignis {
	
	private Date datum;
	private Artikel artikel;
	private String aktion;
	private int anzahl;
	private User nutzer;
	
	/**
	 * Initalisiert ein Lagerereignisobjekt.
	 * @param artikel Artikel der am Lagerereignis beteiligt ist.
	 * @param anzahl Gibt Anzahl an die ein-/ausgelagert wurde. Bzw wie viel von einem Artikel vorhanden ist.
	 * @param aktion Aktion die Ausgeführt wird. (Ein-/Auslagern).
	 * @param nutzer Nutzer, der das Lagerereignis ausführt.
	 */
	public Ereignis(Artikel artikel, int anzahl	, String aktion, User nutzer) {
		
		this.datum = new Date();
		this.artikel = artikel;
		this.anzahl = anzahl;
		this.aktion = aktion;
		this.nutzer = nutzer;
	}
	
	/**
	 * DIe Methode gibt den bearbeiteten Artikel zurück.
	 * @return Gibt den bearbeiteten Artikel zurück.
	 */
	public Artikel getArtikel() {
		return this.artikel;
	}
	
	/**
	 * Die Methode gibt das Datum des Lagerereignisses zurück.
	 * @return Gibt das Datum des Lagerereignisses zurück.
	 */
	public Date getDate() {
		return this.datum;
	}
	
	/**
	 * Die Methode gibt die Anzahl der ein-/ausgelagerten Artikel wieder.
	 * @return Gibt die Anzahl der ein-/ausgelagerten Artikel wieder.
	 */
	public int getMenge() {
		return anzahl;
	}
	
	/**
	 * Die Methode gibt die ausgeührte Aktion zurück.
	 * @return Gibt die ausgeührte Aktion zurück.
	 */
	public String getAktion() {
		return aktion;
	}
	
	/**
	 * Die Methode gibt den für das Ereignis verantwortlichen Benutzer zurück.
	 * @return Gibt den für das Ereignis verantwortlichen Benutzer zurück.
	 */
	public User getNutzer(){
		return nutzer;
	}
	
	/**
	 * Die Methode gibt ein Ereignis als String aus.
	 */
	public String toString() {
		return ("Datum: " + this.datum + " Artikel: " + artikel.getArtikelName() + " | Anzahl: " + anzahl + " | Aktion: " + aktion + " | Benutzer: " + nutzer.getVorName() + nutzer.getNachName());
	}

}
