package Valueobjects;

public class Kunde extends User {
	
	public String adresse;
	public int plz;
	public String ort;
	
	public Kunde(String name, String passwort, int nr, String anrede, String vorName, String nachName, String adresse, int plz, String ort){
		super(name, passwort, nr, anrede, vorName, nachName);
		this.adresse = adresse;
		this.plz = plz;
		this.ort = ort;
		
	}
	
/*	public int getKundenId(){
		return this.kundenId;
	}
	
	public void setKundenId(int kundenId){
		this.kundenId = kundenId;
	}
*/
}
