package Valueobjects;

public class EinArtikel extends Artikel {
	
	public EinArtikel(int artikelNummer, String artikelName, int menge,  double preis){
		super(artikelNummer, artikelName, menge, preis);
	}
	
	/**
	 * Die Methode gibt ein Artikel als String zurück.
	 */
	public String toString() {
		return ("Nr: " + artikelNummer + " | Titel: " + artikelName + " | Anzahl: " + menge + " | Einzelpreis: " + preis);
	}

}
