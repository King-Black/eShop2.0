package Valueobjects;

public class MehrfachArtikel extends Artikel {
	
	private int packungsGroesse;
	private float stueckPreis;
	
	public MehrfachArtikel(int artikelNummer, String artikelName, int menge, double preis, int packungsGroesse, float stueckPreis){
		super(artikelNummer, artikelName, menge, preis);
		this.packungsGroesse = packungsGroesse;
		this.stueckPreis = stueckPreis;
	}
	
	public String toString() {
		return (super.toString() + " Packungsgroesse: " + packungsGroesse);
	}
	
	public int getPackungsgroesse() {
		return packungsGroesse;
	}
	
	public void setPackungsGroesse(int packungsGroesse) {
		this.packungsGroesse = packungsGroesse;
	}
	
	public float getStueckPreis() {
		return this.stueckPreis;
	}
	
	public void setStueckPreis(float stueckPreis) {
		this.stueckPreis = stueckPreis;
	}
}
