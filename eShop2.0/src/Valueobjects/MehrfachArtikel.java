package Valueobjects;

public class MehrfachArtikel extends Artikel {
	
	private int packungsGroesse;
	
	public MehrfachArtikel(int artikelNummer, String artikelName, int menge, String beschreibung, double preis, int packungsGroesse){
		super(artikelNummer, artikelName, menge, beschreibung, preis);
		this.packungsGroesse = packungsGroesse;
	}
	
	public int getPackungsgroesse( ) {
		return packungsGroesse;
	}
}
