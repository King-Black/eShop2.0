package Domain;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import persistence.FilePersistenceManager;
import persistence.PersistenceManager;
import Valueobjects.Kunde;
import Valueobjects.Mitarbeiter;
import Valueobjects.User;
import exceptions.KennwortFalschException;

public class UserVerwaltung {
	private List<User> userBestand = new Vector<User>();
	private int laufnr = 0;
	private PersistenceManager pm = new FilePersistenceManager();
	
	public UserVerwaltung(){
		try {
			this.ladeDatenMitarbeiter();
			this.ladeDatenKunden();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void einfuegen(String name, String passwort, String anrede, String vorName, String nachName){
		int nr = bestimmeNr();
		User einUser = new Mitarbeiter(name, passwort, nr, anrede, vorName, nachName);
		userBestand.add(einUser);
	}
	
	public void einfuegen(String name, String passwort, String anrede, String vorName, String nachName, String adresse, int plz, String ort){
		int nr = bestimmeNr();
		User einUser = new Kunde(name, passwort, nr, anrede, vorName, nachName, adresse, plz, ort);
		userBestand.add(einUser);
		
	}
	
	private int bestimmeNr(){
		return ++laufnr;
	}
	
	public List<User> getUserBestand() {
		return userBestand;
	}
	
	public User userLogin(String name, String passwort) throws KennwortFalschException{
		Iterator<User> it = userBestand.iterator();
	
		while(it.hasNext()) {	
			User user = it.next();
			if(user.getName().equals(name)){
				if (user.getPasswort().equals(passwort)) { //geändert von array equal
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
	

	public void schreibeDatenMitarbeiter() throws IOException  {
		pm.openForWriting("Mitarbeiter.txt");
		if (!userBestand.isEmpty()) {
			for (User user : userBestand) {
				if (user instanceof Mitarbeiter)
				pm.speichereMitarbeiter((Mitarbeiter)user);				
			}
		}
		pm.close();
	}
	
	
	public void schreibeDatenKunden() throws IOException  {
		pm.openForWriting("Kunde.txt");
		if (!userBestand.isEmpty()) {
			for (User user : userBestand){
				if (user instanceof Kunde)
				pm.speichereKunde((Kunde)user);				
			}
		}
		pm.close();
	}
	
	
	public void ladeDatenMitarbeiter() throws  IOException {
		pm.openForReading("Mitarbeiter.txt");
		Mitarbeiter mit;
		do {
			mit = pm.ladeMitarbeiter();
			if (mit != null) {
				userBestand.add(mit);
			}				
		} while (mit != null);	
		pm.close();
	}
	
	public void ladeDatenKunden() throws IOException {
		pm.openForReading("Kunde.txt");
		Kunde kunde;
		do {
			kunde = pm.ladeKunden();
			if (kunde != null) {
				userBestand.add(kunde);
			}
		} while (kunde != null);	
		pm.close();
	}
}
