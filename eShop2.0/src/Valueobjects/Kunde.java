package Valueobjects;

public class Kunde extends User {
	
	private String adresse;
	private int plz;
	private String ort;
	private Warenkorb warenkorb = new Warenkorb();
	
	public Kunde(String name, String passwort, int nr, String anrede, String vorName, String nachName, String adresse, int plz, String ort){
		super(name, passwort, nr, anrede, vorName, nachName);
		this.adresse = adresse;
		this.plz = plz;
		this.ort = ort;
		
	}
	
	public String toString() {
		return ("Kunde Nr: " + nr + " | Benutzername: " + name + " | Anrede: " + anrede + " | Name: " + vorName + " " + nachName + " | Strasse und Nr: " + adresse + " | PLZ: " + plz + " | Ort: " + ort);
	}
	
	public String getAdresse() {
		return adresse + " \n" + plz + " \n" + ort;
	}
	
	public Warenkorb getWarenkorb() {
		return warenkorb;
	}
	
}
