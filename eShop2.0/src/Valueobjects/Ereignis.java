package Valueobjects;

import java.util.Date;

/**
 * Klasse zur Repr�sentation einer Ver�nderung eines Lagerbestandes.
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
	 * @param aktion Aktion die Ausgef�hrt wird. (Ein-/Auslagern).
	 * @param nutzer Nutzer, der das Lagerereignis ausf�hrt.
	 */
	public Ereignis(Artikel artikel, int anzahl	, String aktion, User nutzer) {
		
		this.datum = new Date();
		this.artikel = artikel;
		this.anzahl = anzahl;
		this.aktion = aktion;
		this.nutzer = nutzer;
	}
	
	/**
	 * DIe Methode gibt den bearbeiteten Artikel zur�ck.
	 * @return Gibt den bearbeiteten Artikel zur�ck.
	 */
	public Artikel getArtikel() {
		return this.artikel;
	}
	
	/**
	 * Die Methode gibt das Datum des Lagerereignisses zur�ck.
	 * @return Gibt das Datum des Lagerereignisses zur�ck.
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
	 * Die Methode gibt die ausge�hrte Aktion zur�ck.
	 * @return Gibt die ausge�hrte Aktion zur�ck.
	 */
	public String getAktion() {
		return aktion;
	}
	
	/**
	 * Die Methode gibt den f�r das Ereignis verantwortlichen Benutzer zur�ck.
	 * @return Gibt den f�r das Ereignis verantwortlichen Benutzer zur�ck.
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
