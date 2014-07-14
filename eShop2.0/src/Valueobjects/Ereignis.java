package Valueobjects;

import java.util.Date;

/*
 * Klasse zur Repräsentation einer Veränderung eines Lagerbestandes.
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
	 * @param anzahl 
	 * @param aktion Aktion die Ausgeführt wird. (Ein-/Auslagern)
	 * @param nutzer Nutzer, der das Lagerereignis ausführt.
	 */
	public Ereignis(Artikel artikel, int anzahl	, String aktion, User nutzer) {
		
		this.datum = new Date();
		this.artikel = artikel;
		this.anzahl = anzahl;
		this.aktion = aktion;
		this.nutzer = nutzer;
	}
	
	public Artikel getArtikel() {
		return this.artikel;
	}
	
	public Date getDate() {
		return this.datum;
	}
	
		
	public int getMenge() {
		return anzahl;
	}
	
	public String getAktion() {
		return aktion;
	}
	
	public User getNutzer(){
		return nutzer;
	}
	
	public String toString() {
		return ("Datum: " + this.datum + " Artikel: " + artikel.getArtikelName() + " | Anzahl: " + anzahl + " | Aktion: " + aktion + " | Benutzer: " + nutzer.getVorName() + nutzer.getNachName());
	}

}
