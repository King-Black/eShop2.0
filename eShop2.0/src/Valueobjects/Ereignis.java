package Valueobjects;

import java.util.Date;

public class Ereignis {
	
	private User akteur;
	private Date datum;
	private Artikel artikel;
	private String aktion;
	private int anzahl;
	
	public Ereignis(User akteur, Artikel artikel, int anzahl	, String aktion) {
		this.akteur = akteur;
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
	
	public User getUser() {
		return akteur;
	}
	
	public int getMenge() {
		return anzahl;
	}
	
	public String getAktion() {
		return aktion;
	}
	
	public String toString() {
		return ("Datum: " + this.datum + " User: " + akteur + " Artikel: " + artikel.getArtikelName() + " | Anzahl: " + anzahl + " | " + " Aktion: " + aktion);
	}

}
