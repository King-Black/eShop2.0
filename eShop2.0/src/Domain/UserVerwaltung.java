package Domain;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import Valueobjects.Kunde;
import Valueobjects.Mitarbeiter;
import Valueobjects.User;

public class UserVerwaltung {
	private List<User> userBestand = new Vector<User>();
	private int laufnr = 0;
	
	public void einfuegen(String name, char[] passwort, String anrede, String vorName, String nachName){
		int nr = bestimmeNr();
		User einUser = new Mitarbeiter(name, passwort, nr, anrede, vorName, nachName);
	}
	
	public void einfuegen(String name, char[] passwort, String anrede, String vorName, String nachName, String adresse, int plz, String ort){
		int nr = bestimmeNr();
		User einUser = new Kunde(name, passwort, nr, anrede, vorName, nachName, adresse, plz, ort);
	}
	
	private int bestimmeNr(){
		return ++laufnr;
	}
	
	public List<User> getUserBestand() {
		return userBestand;
	}
	
	public User userLogin(String name, char[] passwort){
		Iterator<User> it = userBestand.iterator();
		while  (it.hasNext()) {
			User user = it.next();
			if(user.getName().equals(name)){
				if (Arrays.equals(user.getPasswort(),passwort)) {
					return user;
				}
			}
		}
		return null;
	}
	
	public void gibBenutzerlisteAus() {
		if(userBestand.isEmpty()) {
			System.out.println("Liste ist leer.");
		} else {
			Iterator<User> it = userBestand.iterator();
			while (it.hasNext()) {
				User user = it.next();
				System.out.println(user.toString());
			}			
		}
		System.out.println(" ");
	}
	
	public User findUserByNumber(int ID){
		Iterator<User> it = userBestand.iterator();
		// Artikel erstellen
		User user = null;
		// Artikelverzeichnis durchlaufen
		while (it.hasNext()) {
			user = it.next();
			// gesuchter User gefunden
			if(ID==user.getNummer()){
				return user;				
			} else if (!(ID==user.getNummer())&&!it.hasNext()){ // gesuchte Artikel ID nicht gefunden
				//exception UserIdNichtGefundenException
			}
		}
		return null;
	}
	
	public void loescheUser(int userNr, User aktuellerBenutzer){
		if(findUserByNumber(userNr)!=null){
			userBestand.remove(findUserByNumber(userNr));
		}		
		else{
			//exception UserNichtVorhandenException
		}
	}
}
