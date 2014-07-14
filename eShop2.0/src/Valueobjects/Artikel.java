package Valueobjects;

/**
 * Klasse zur Repr�sentation eines Artikels
 * @author Imke Schneider
 *
 */

public class Artikel {
	
	//private static final String ArtikelBeschreibung = null;
	private int artikelNummer;
	private String artikelName;
	private int menge;
	//private String beschreibung;
	private double preis;
	
	/**
	 * Initalisieren eines Artikelobjets.
	 * @param artikelNummer Nummer des Arikels.
	 * @param artikelName Name des Artikels.
	 * @param menge Menge des Artikels.
	 * @param preis Preis des Artikels.
	 */
	public Artikel(int artikelNummer, String artikelName, int menge,  double preis){
		this.artikelNummer = artikelNummer;
		this.artikelName = artikelName;
		this.menge = menge;
		this.preis = preis;	
	}
	
	public String toString() {
		return ("Nr: " + artikelNummer + " | Titel: " + artikelName + " | Anzahl: " + menge + " | Einzelpreis: " + preis);
	}
	
	/**
	 * Die Methode gibt die Artikelnummer des Artikels zur�ck. 
	 * @return Gibt Artikelnummer des Artikels zur�ck.
	 */
	public int getArtikelNummer(){
		return this.artikelNummer;
	}
	
	/**
	 * DIe Methode setzt die Artikelnummer des Artikels.
	 * @param artikelNummer Artikelnummer, die der Artikel sp�ter bekommt.
	 */
	public void setArtikelNummer(int artikelNummer){
		this.artikelNummer = artikelNummer;
	}
	
	/**
	 * Die Methode gibt den Namen des Artikels zur�ck.
	 * @return Gibt Namen des Artikels zur�ck.
	 */
	public String getArtikelName(){
		return this.artikelName;
	}
	
	/**
	 *  Die Methode setzt den Namen des Artikels.
	 * @param artikelName Name, die der Artikel sp�ter bekommt.
	 */
	public void setArtikelName(String artikelName){
		this.artikelName = artikelName;
	}
	
	/**
	 * Die Methode gibt dem Artikel eine Beschreibung.
	 * @return Gibt Artikel eine Beschreibung.
	 */
	public String getBeschreibung(){
		return null;
	}
	/*
	public void setBeschreibung(String beschreibung){
		this.beschreibung = beschreibung;
	}*/
	
	/**
	 * Die Methode gibt dem Artikel einen Preis.
	 * @return Gibt Artikel einen Preis.
	 */
	public double getPreis(){
		return this.preis;
	}
	
	/**
	 * Die Methode setzt einen Preis f�r den Artikel.
	 * @param preis Preis f�r Artikel.
	 */
	public void setPreis(double preis){
		this.preis = preis;
	}
	
	/**
	 * Die Methode gibt den Artikelbestand des Artikels zur�ck.
	 * @return Gibt Artikelbestand des Artikels zur�ck.
	 */
	public int getArtikelBestand(){
		return this.menge;
	}
	
	/**
	 * Die Methode setzt den Artikelbestand des Artikels.
	 * @param menge Menge, wie viel es auf Lager sein soll.
	 */
	public void setArtikelBestand(int menge){
		this.menge = menge;
	}
	
}
