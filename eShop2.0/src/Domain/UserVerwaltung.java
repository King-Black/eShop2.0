package Domain;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import persistence.FilePersistenceManager;
import persistence.PersistenceManager;
import Valueobjects.Kunde;
import Valueobjects.Mitarbeiter;
import Valueobjects.User;
import exceptions.BereitsEingeloggtException;
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
	
	public User userLogin(String name, String passwort) throws KennwortFalschException, BereitsEingeloggtException{
		Iterator<User> it = userBestand.iterator();
	
		while  (it.hasNext()) {	
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
	
//	public void schreibeDaten() throws FileNotFoundException, IOException {
//		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("User.txt")); 
//		// hier schleife in der dir jeweiligen objekte (artikel, user, ereignisse durchgegangen werden
//		
//		Iterator<User> it = userBestand.iterator();
//		// Artikel erstellen
//		User user = null;
//		// Artikelverzeichnis durchlaufen
//		int count = 0;
//		while (it.hasNext()) {
//			user = it.next();
//			// artikel in Datei speichern
//			out.writeObject(user);
//			count ++;
//		}
//		System.out.println(count + " User gespeichert.");
//		// muss aufgerufen werden, bevor der datenstrom zur eingabe verwendet werden soll
//		out.close();
//	}
//	
//	public void ladeDaten() throws FileNotFoundException, IOException, ClassNotFoundException{
//		int count = 0;
//		ObjectInputStream in = new ObjectInputStream(new FileInputStream("User.txt"));
//		userBestand.clear();
//		try {  
//			User u = null;
//			for(;;) {
//				u = (User) in.readObject();
//				count++;
//				userBestand.add(u);
//				if (u.getNummer() > this.laufnr)
//					this.laufnr = u.getNummer();
//			}
//		} catch (EOFException e) { // wg. readObject
//			System.out.println("Es wurden " + count + " User geladen.");
//		} catch (IOException e) {
//			System.out.println(e);
//		} catch (ClassNotFoundException e) { // wg readObject
//			System.out.println(e);
//		} finally {
//			try {
//				if (in!=null) {
//					in.close();
//				} 
//			} catch (IOException e) {
//					e.printStackTrace();
//				}
//		}
//	}
//------------------------Fabians Speichern u Laden-------------------------
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
	
	
//	public void schreibeDatenKunden() throws IOException  {
//		pm.openForWriting("Kunden.txt");
//		if (!kundenListe.isEmpty()) {
//			for (Kunde kunde : kundenListe){
//				pm.speicherKunde(kunde);				
//			}
//		}
//		pm.close();
//	}
	
	
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
		pm.openForReading("Kunden.txt");
		Kunde kunde;
		do {
			kunde = pm.ladeKunden();
			if (kunde != null) {
				kundenListe.add(kunde);
			}
		} while (kunde != null);	
		pm.close();
	}
}
