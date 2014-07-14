package Valueobjects;

public class MehrfachArtikel extends Artikel {
	
	/**
	 * Klasse zur Repr�sentation eines Mehrfachartikels.
	 */
	private int packungsGroesse;
	private float stueckPreis;
	
	/**
	 * Initalisiert ein Mehrfachartikelobjekts.
	 * @param artikelNummer Nummer des Mehrfachartikels.
	 * @param artikelName Name des Mehrfachartikels.
	 * @param menge
	 * @param preis 
	 * @param packungsGroesse
	 * @param stueckPreis St�ckpreis des Mehrfachartikels.
	 */
	public MehrfachArtikel(int artikelNummer, String artikelName, int menge, double preis, int packungsGroesse, float stueckPreis){
		super(artikelNummer, artikelName, menge, preis);
		this.packungsGroesse = packungsGroesse;
		this.stueckPreis = stueckPreis;
	}
	
	public String toString() {
		return (super.toString() + " Packungsgroesse: " + packungsGroesse);
	}
	
	/**
	 * Die Methode gibt die Packungsgr��e des Mehrfachartikels zur�ck.
	 * @return Gibt Packungsgr��e des Mehrfachartikels zur�ck.
	 */
	public int getPackungsgroesse() {
		return packungsGroesse;
	}
	
	/**
	 * Die Methode setzt die Packungsgr��e des Mehrfachartikels.
	 * @param packungsGroesse Gr��e, in der der Mehrfachartikel verkauft wird.
	 */
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
