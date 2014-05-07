package Valueobjects;

public class Mitarbeiter extends User {
	
	private int mitarbeiterId;
	
	public Mitarbeiter(int mitarbeitderId, String vorName, String nachName, String passwort){
		super(vorName, nachName, passwort);
		this.mitarbeiterId = mitarbeiterId;
		
	}
	
	public int getMitarbeiterId(){
		return this.mitarbeiterId;
	}
	
	public void setMitarbeiterId(int mitarbeiterId){
		this.mitarbeiterId = mitarbeiterId;
	}

}
