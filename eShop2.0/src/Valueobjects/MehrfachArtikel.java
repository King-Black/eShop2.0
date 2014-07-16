package Valueobjects;

public class MehrfachArtikel extends Artikel {
	
	/**
	 * Klasse zur Repr�sentation eines Mehrfachartikels.
	 * @author Imke Schneider
	 */
	private int packungsGroesse;
	private float stueckPreis;
	
	/**
	 * Initalisiert ein Mehrfachartikelobjekts.
	 * @param artikelNummer Nummer des Mehrfachartikels.
	 * @param artikelName Name des Mehrfachartikels.
	 * @param menge Menge des Artikels.
	 * @param preis Gesamtpreis einer Packung.
	 * @param packungsGroesse Einheit in der ein Artikel verkauft wird.
	 * @param stueckPreis St�ckpreis des Mehrfachartikels.
	 */
	public MehrfachArtikel(int artikelNummer, String artikelName, int menge, double preis, int packungsGroesse, float stueckPreis){
		super(artikelNummer, artikelName, menge, preis);
		this.packungsGroesse = packungsGroesse;
		this.stueckPreis = stueckPreis;
	}
	
	/**
	 * Die Methode gibt die Mehrfahartikel als String zur�ck.
	 */
	public String toString() {
		return (super.toString() + " Packungsgroesse: " + packungsGroesse + " Stueckpreis: " + stueckPreis);
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
	
	/**
	 * Die Methode gibt den Stueckpreis des Artikels zur�ck.
	 * @return Gibt den Stueckpreis des Artikels zur�ck.
	 */
	public float getStueckPreis() {
		return this.stueckPreis;
	}
	
	/**
	 * Die Methode setzt den St�ckpreis des Artikels.
	 * @param stueckPreis Setzt den St�ckpreis des Artikels.
	 */
	public void setStueckPreis(float stueckPreis) {
		this.stueckPreis = stueckPreis;
	}
}
