package Valueobjects;

import java.util.Date;

public class Ereignis {
	
	private Date datum;
	private Artikel artikel;
	private String aktion;
	private int anzahl;
	
	public Ereignis(Artikel artikel, int anzahl	, String aktion) {
		
		this.datum = new Date();
		this.artikel = artikel;
		this.anzahl = anzahl;
		this.aktion = aktion;
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
	
	public String toString() {
		return ("Datum: " + this.datum + " Artikel: " + artikel.getArtikelName() + " | Anzahl: " + anzahl + " | " + " Aktion: " + aktion);
	}

}
