package Valueobjects;

public class Mitarbeiter extends User {
	
	public Mitarbeiter(String benutzerName, String passwort, int nr, String anrede, String vorName, String nachName){
		super(benutzerName, passwort, nr, anrede, vorName, nachName);
		
	}
	
	public String toString() {
		return ("Mitarbeiter Nr: " + nr + " | " + "Benutzername: " + name + " | Anrede: " + anrede + " | Name: " + vorName + " " + nachName);
	}
}
