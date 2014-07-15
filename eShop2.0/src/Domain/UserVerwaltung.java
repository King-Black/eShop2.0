package Domain;

import java.io.FileNotFoundException;
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
import exceptions.UserNichtGefundenException;

/**
 * Klasse zur Verwaltung der User
 * @author Imke Schneider
 *
 */
public class UserVerwaltung {
	private List<User> userBestand = new Vector<User>();
	private int laufnr = 0;
	private PersistenceManager pm = new FilePersistenceManager();
	
	/**
	 * Konstruktor: Läd alle Mitarbeiter und Kunden Daten.
	 */
	public UserVerwaltung(){
		try {
			this.ladeDatenMitarbeiter();
			this.ladeDatenKunden();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Methode zum einfügen eines Mitarbeiter.
	 * @param name Benutzername des Mitarbeiters.
	 * @param passwort Passwort des Mitarbeiters.
	 * @param anrede Anrede des Mitarbeiters.
	 * @param vorName Vorname des Mitarbeiters.
	 * @param nachName Nachname des Mitarbeiters.
	 */
	public void einfuegen(String name, String passwort, String anrede, String vorName, String nachName){
		int nr = bestimmeNr();
		User einUser = new Mitarbeiter(name, passwort, nr, anrede, vorName, nachName);
		userBestand.add(einUser);
	}
	/**
	 * Methode zum einfügen eines Kunden.
	 * @param name Benutzername des Kunden.
	 * @param passwort Passwort des Kunden.
	 * @param anrede Anrede des Kunden.
	 * @param vorName Vorname des Kunden.
	 * @param nachName Nachname des Kunden.
	 * @param adresse Straße und Hausnr. des Kunden.
	 * @param plz Postleitzahl des Kunden.
	 * @param ort Ort des Kunden.
	 */
	public void einfuegen(String name, String passwort, String anrede, String vorName, String nachName, String adresse, int plz, String ort){
		int nr = bestimmeNr();
		User einUser = new Kunde(name, passwort, nr, anrede, vorName, nachName, adresse, plz, ort);
		userBestand.add(einUser);
		
	}
	
	/**
	 * Methode zählt ID hoch.
	 * @return Gibt ID wieder.
	 */
	private int bestimmeNr(){
		return ++laufnr;
	}
	
	/**
	 * Methode gibt alle User zurück.
	 * @return Gibt alle User zurück.
	 */
	public List<User> getUserBestand() {
		return userBestand;
	}
	
	/**
	 * Methode zum einloggen des Users.
	 * @param name Benutzername des Users.
	 * @param passwort Passwort des Users.
	 * @return den eingeloggten User.
	 * @throws KennwortFalschException wird geworfen, wenn das Kennwort falsch ist.
	 * @throws UserNichtGefundenException wird geworfen, wenn der User nicht gefunden wurde.
	 */
	public User userLogin(String name, String passwort) throws KennwortFalschException, UserNichtGefundenException{
		Iterator<User> it = userBestand.iterator();
		boolean gefunden = false;
		User user = null;
		while(it.hasNext()) {	
			user = it.next();
			if(user.getName().equals(name)){
				gefunden = true;
				break;
			}
		}
		if(gefunden) {
			if (user.getPasswort().equals(passwort)) { //geändert von array equal
				return user;
			}else{
				KennwortFalschException e = new KennwortFalschException();
				throw e;
			}
		} else {
			UserNichtGefundenException e1 = new UserNichtGefundenException();
			throw e1;
		}
	}
	
	/**
	 * Methode gibt eine Liste aller Benutzer aus.
	 */
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
	
	/**
	 * Methode sucht einen User anhand der Nummer.
	 * @param ID User ID.
	 * @return Gibt gefundenen User zurück.
	 * @throws UserNichtGefundenException wird geworfen, wenn gesuchte Usernummer nicht gefunden wurde.
	 */
	public User findUserByNumber(int ID) throws UserNichtGefundenException{
		Iterator<User> it = userBestand.iterator();
		// Artikel erstellen
		User user = null;
		// Artikelverzeichnis durchlaufen, solange es noch ein nächstes Obejekt hat macht er weiter 
		while (it.hasNext()) {
			user = it.next();
			// gesuchter User gefunden
			if(ID==user.getNummer()){
				return user;				
			} else if (!(ID==user.getNummer())&&!it.hasNext()){ // gesuchte Artikel ID nicht gefunden
				throw new UserNichtGefundenException(ID);
			}
		}
		return null;
	}
	
	/**
	 * Methode zum löschen eines Users.
	 * @param userNr User ID. 
	 * @throws UserNichtGefundenException wird geworfen, wenn der zu löschende User nicht gefunden wurde.
	 */
	public void loescheUser(int userNr) throws UserNichtGefundenException{
		if(findUserByNumber(userNr)!=null){
			userBestand.remove(findUserByNumber(userNr));
		}		
		else{
			throw new UserNichtGefundenException(userNr);
		}
	}
	
	/**
	 * Methode speichert alle Mitarbeiter in einer Textdatei.
	 * @throws FileNotFoundException wird geworfen, wenn Datei nicht gefunden wurde.
	 * @throws IOException wenn es einen fehler beim schreiben gab.
	 */
	public void schreibeDatenMitarbeiter() throws FileNotFoundException, IOException{
		pm.openForWriting("Mitarbeiter.txt");
		if (!userBestand.isEmpty()) {
			for (User user : userBestand) {
				if (user instanceof Mitarbeiter)
				pm.speichereMitarbeiter((Mitarbeiter)user);				
			}
		}
		pm.close();
	}
	
	/**
	 * Methode speichert alle Kunden in einer Textdatei.
	 * @throws FileNotFoundException wird geworfen, wenn Datei nicht gefunden wurde.
	 * @throws IOException wenn es einen fehler beim schreiben gab.
	 */
	public void schreibeDatenKunden() throws FileNotFoundException, IOException{
		pm.openForWriting("Kunde.txt");
		if (!userBestand.isEmpty()) {
			for (User user : userBestand){
				if (user instanceof Kunde)
				pm.speichereKunde((Kunde)user);				
			}
		}
		pm.close();
	}
	
	/**
	 * Methode läd alle Mitarbeiter aus einer Textdatei.
	 * @throws FileNotFoundException wird geworfen, wenn Datei nicht gefunden wurde.
	 * @throws IOException wenn es einen fehler beim lesen gab.
	 * @throws ClassNotFoundException
	 */
	public void ladeDatenMitarbeiter() throws FileNotFoundException, IOException{
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
	
	/**
	 * Methode läd alle Kunden aus einer Textdatei.
	 * @throws FileNotFoundException wird geworfen, wenn Datei nicht gefunden wurde.
	 * @throws IOException wenn es einen fehler beim lesen gab.
	 * @throws ClassNotFoundException
	 */
	public void ladeDatenKunden() throws FileNotFoundException, IOException{
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
