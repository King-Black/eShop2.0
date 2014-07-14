package Valueobjects;

/**
 * Klasse zur Repräsentation eines Users.
 * @author Imke Schneider
 *
 */

public class User {	
	protected String name;
	protected String passwort;
	protected int nr;
	protected String anrede;
	protected String vorName;
	protected String nachName;

	/**
	 * Initalisiert ein Userobjekt
	 * @param name Benutzername des Users.
	 * @param passwort Passwort des Users.
	 * @param nr Laufendenr. des Users.
	 * @param anrede Anrede des Users.
	 * @param vorName Vorname des Users.
	 * @param nachName Nachname des Users.
	 */
	
	public User(String name, String passwort, int nr, String anrede, String vorName, String nachName){
		this.name = name;
		this.passwort = passwort;
		this.nr = nr;
		this.anrede = anrede;
		this.vorName = vorName;
		this.nachName = nachName;

	}
	
	public String toString() {
		return null;
	}
	
	/**
	 * Die Methode gibt den Benutzernamen des Usersn zurück.
	 * @return Gibt Benutzername des Users zurück.
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Die Methode setzt den Benutzernamen des Users.
	 * @param name Der Benutzernamen, den der User später erhalten soll.
	 */
	public void setName(String name){
		this.name = name;
	}
	/**
	 * Die Methode gibt die Usernummer wieder.
	 * @return Gibt Nummer des Users wieder.
	 */
	public int getNummer(){
		return this.nr;
	}
	
	/**
	 * Die Methode setzt Usernummer.
	 * @param nr Setzt Usernummer die der User später erhält.
	 */
	public void setNummer(int nr){
		this.nr = nr;
	}
	
	/**
	 * Die Methode gibt die Anrede des Users wieder.
	 * @return Gibt Anrede wieder.
	 */
	public String getAnrede(){
		return this.anrede;
	}
	/**
	 * Die Mathode setzt die Anrede des Users.
	 * @param anrede Setzt Anrede des Users.
	 */
	public void setAnrede(String anrede){
		this.anrede = anrede;
	}
	
	/**
	 * Die Methode gibt den Vornamen des Users wieder.
	 * @return Gibt den Vornamen des Users wieder.
	 */
	public String getVorName(){
		return this.vorName;
	}
	
	/**
	 * Die Methode setzt den Vornamen des späteren Users.
	 * @param vorName Setzt den Vornamen den der User später erhalten soll.
	 */
	public void setVorName(String vorName){
		this.vorName = vorName;
	}
	
	/**
	 * Die Methode gibt den Nachnamen der Person zurück.
	 * @return Gibt den Nachnamen der Person zurück.
	 */
	public String getNachName(){
		return this.nachName;
	}
	
	/**
	 * Die Methode setzt den Nachname der Person.
	 * @param nachName Der Nachname, den die Person erhalten soll.
	 */
	public void setNachName(String nachName){
		this.nachName = nachName;
	}
	
	/**
	 * Die Methode gibt das Passwort der Person zurück.
	 * @return Gibt das Passwort der Person zurück.
	 */
	public String getPasswort(){
		return this.passwort;
	}
	
	/**
	 * Die Methode setzt das Passwort der Person.
	 * @param kennwort Das Passwort der Person.
	 */
	public void setPasswort(String passwort){
		this.passwort = passwort;
	}
}
