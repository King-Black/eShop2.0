package Valueobjects;

public class MehrfachArtikel extends Artikel {
	
	/**
	 * Klasse zur Repräsentation eines Mehrfachartikels.
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
	 * @param stueckPreis Stückpreis des Mehrfachartikels.
	 */
	public MehrfachArtikel(int artikelNummer, String artikelName, int menge, double preis, int packungsGroesse, float stueckPreis){
		super(artikelNummer, artikelName, menge, preis);
		this.packungsGroesse = packungsGroesse;
		this.stueckPreis = stueckPreis;
	}
	
	/**
	 * Die Methode gibt die Mehrfahartikel als String zurück.
	 */
	public String toString() {
		return (super.toString() + " Packungsgroesse: " + packungsGroesse + " Stueckpreis: " + stueckPreis);
	}
	
	/**
	 * Die Methode gibt die Packungsgröße des Mehrfachartikels zurück.
	 * @return Gibt Packungsgröße des Mehrfachartikels zurück.
	 */
	public int getPackungsgroesse() {
		return packungsGroesse;
	}
	
	/**
	 * Die Methode setzt die Packungsgröße des Mehrfachartikels.
	 * @param packungsGroesse Größe, in der der Mehrfachartikel verkauft wird.
	 */
	public void setPackungsGroesse(int packungsGroesse) {
		this.packungsGroesse = packungsGroesse;
	}
	
	/**
	 * Die Methode gibt den Stueckpreis des Artikels zurück.
	 * @return Gibt den Stueckpreis des Artikels zurück.
	 */
	public float getStueckPreis() {
		return this.stueckPreis;
	}
	
	/**
	 * Die Methode setzt den Stückpreis des Artikels.
	 * @param stueckPreis Setzt den Stückpreis des Artikels.
	 */
	public void setStueckPreis(float stueckPreis) {
		this.stueckPreis = stueckPreis;
	}
}
