package Valueobjects;

/**
 * Klasse zur Repräsentation eines Artikels
 * @author Imke Schneider
 *
 */

public class Artikel {
	
	private int artikelNummer;
	private String artikelName;
	private int menge;
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
	
	/**
	 * Die Methode gibt ein Artikel als String zurück.
	 */
	public String toString() {
		return ("Nr: " + artikelNummer + " | Titel: " + artikelName + " | Anzahl: " + menge + " | Einzelpreis: " + preis);
	}
	
	/**
	 * Die Methode gibt die Artikelnummer des Artikels zurück. 
	 * @return Gibt Artikelnummer des Artikels zurück.
	 */
	public int getArtikelNummer(){
		return this.artikelNummer;
	}
	
	/**
	 * Die Methode setzt die Artikelnummer des Artikels.
	 * @param artikelNummer Artikelnummer, die der Artikel später bekommt.
	 */
	public void setArtikelNummer(int artikelNummer){
		this.artikelNummer = artikelNummer;
	}
	
	/**
	 * Die Methode gibt den Namen des Artikels zurück.
	 * @return Gibt Namen des Artikels zurück.
	 */
	public String getArtikelName(){
		return this.artikelName;
	}
	
	/**
	 *  Die Methode setzt den Namen des Artikels.
	 * @param artikelName Name, die der Artikel später bekommt.
	 */
	public void setArtikelName(String artikelName){
		this.artikelName = artikelName;
	}
	
	/**
	 * Die Methode gibt den Preis des Artikels zurück.
	 * @return Gibt Preis des Artikels zurück.
	 */
	public double getPreis(){
		return this.preis;
	}
	
	/**
	 * Die Methode setzt einen Preis für den Artikel.
	 * @param preis Preis für Artikel.
	 */
	public void setPreis(double preis){
		this.preis = preis;
	}
	
	/**
	 * Die Methode gibt den Artikelbestand des Artikels zurück.
	 * @return Gibt Artikelbestand des Artikels zurück.
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
