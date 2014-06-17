package Valueobjects;

public class Artikel {
	
	//private static final String ArtikelBeschreibung = null;
	private int artikelNummer;
	private String artikelName;
	private int menge;
	//private String beschreibung;
	private double preis;
	
	public Artikel(int artikelNummer, String artikelName, int menge,  double preis){
		this.artikelNummer = artikelNummer;
		this.artikelName = artikelName;
		this.menge = menge;
		//this.beschreibung = beschreibung;
		this.preis = preis;	
	}
	
	public String toString() {
		return ("Nr: " + artikelNummer + " | Titel: " + artikelName + " | Anzahl: " + menge + " | Einzelpreis: " + preis);
	}
	
	public int getArtikelNummer(){
		return this.artikelNummer;
	}
	
	public void setArtikelNummer(int artikelNummer){
		this.artikelNummer = artikelNummer;
	}
	
	public String getArtikelName(){
		return this.artikelName;
	}
	
	public void setArtikelName(String artikelName){
		this.artikelName = artikelName;
	}
	
	/*public String getBeschreibung(){
		return this.beschreibung;
	}
	
	public void setBeschreibung(String beschreibung){
		this.beschreibung = beschreibung;
	}*/
	
	public double getPreis(){
		return this.preis;
	}
	
	public void setPreis(double preis){
		this.preis = preis;
	}
	
	public int getArtikelBestand(){
		return this.menge;
	}
	
	public void setArtikelBestand(int menge){
		this.menge = menge;
	}

	public String getBeschreibung() {
		// TODO Auto-generated method stub
		return null;
		//return this.beschreibung
	}
	
}
