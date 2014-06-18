package Valueobjects;

public class Mitarbeiter extends User {
	
	public Mitarbeiter(String name, String passwort, int nr, String anrede, String vorName, String nachName){
		super(name, passwort, nr, anrede, vorName, nachName);
		
	}
	
	public String toString() {
		return ("Mitarbeiter Nr: " + nr + " | " + "Benutzername: " + name + " | Anrede: " + anrede + " | Name: " + vorName + " " + nachName);
	}
}
