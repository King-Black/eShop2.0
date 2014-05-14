package Valueobjects;

public class Artikel {
	
	//private static final String ArtikelBeschreibung = null;
	private int ArtikelNummer;
	private String ArtikelName;
	private int ArtikelBestand;
	private String Beschreibung;
	private float Preis;
	
	public Artikel(int ArtikelNummer, String ArtikelName, int ArtikelBestand, String Beschreibung, float Preis){
		this.ArtikelNummer = ArtikelNummer;
		this.ArtikelName = ArtikelName;
		this.ArtikelBestand = ArtikelBestand;
		this.Beschreibung = Beschreibung;
		this.Preis = Preis;
		
	}
	
	public int getArtikelNummer(){
		return this.ArtikelNummer;
	}
	
	public void setArtikelNummer(int ArtikelNummer){
		this.ArtikelNummer = ArtikelNummer;
	}
	
	public String getArtikelName(){
		return this.ArtikelName;
	}
	
	public void setArtikelName(String ArtikelName){
		this.ArtikelName = ArtikelName;
	}
	
	public String getBeschreibung(){
		return this.Beschreibung;
	}
	
	public void setBeschreibung(String Beschreibung){
		this.Beschreibung = Beschreibung;
	}
	
	public float getPreis(){
		return this.Preis;
	}
	
	public void setPreis(float Preis){
		this.Preis = Preis;
	}
	
	public int getArtikelBestand(){
		return this.ArtikelBestand;
	}
	
	public void setArtikelBestand(int ArtikelBestand){
		this.ArtikelBestand = ArtikelBestand;
	}
	
}
