package Domain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import Valueobjects.Artikel;
import Valueobjects.Ereignis;
import Valueobjects.Kunde;
import Valueobjects.MehrfachArtikel;
import Valueobjects.Rechnung;
import Valueobjects.User;
import Valueobjects.Warenkorb;
import exceptions.ArtikelNichtGefundenException;
import exceptions.ArtikelNurInEinheitenVerfuegbarException;
import exceptions.EinlagernException;
import exceptions.KennwortFalschException;
import exceptions.NichtGenugAufLagerException;
import exceptions.UserNichtGefundenException;
import exceptions.WarenkorbLeerException;

/**
 * Klasse zur allgemeinen ShopVerwaltung beinhaltet die Logik und besitzt die ArtikelVerwaltung, UserVerwaltung, WarenkorbVerwaltung und EreignisVerwaltung.
 *
 */
public class ShopVerwaltung {
	private ArtikelVerwaltung artVer;
	private UserVerwaltung userVer;
	private WarenkorbVerwaltung warkoVer;
	public EreignisVerwaltung erVer;
	private User eingeloggterUser;
	
	/**
	 *  Erstellt die Unterwaltungselemente.
	 */
	public ShopVerwaltung(){
		artVer = new ArtikelVerwaltung();
		userVer = new UserVerwaltung();
		warkoVer = new WarenkorbVerwaltung();
		erVer = new EreignisVerwaltung();
	}
	
	/**
	 * Methode leitet weiter an Methode in Artikelverwaltung 
	 * @param artikelName Name des einzufügenden Artikel.
	 * @param menge Menge des Artikels.
	 * @param d Preis des Artikels.
	 * @throws EinlagernException wird geworfen, wenn Menge oder Preis kleiner oder gleich 0 sind.
	 */
	public void fuegeArtikelEin(String artikelName, int menge, double d) throws EinlagernException{
		Artikel a = artVer.einfuegen(artikelName, menge, d);
		erVer.ereignisEinfuegen(a, a.getArtikelBestand(), "Neuer Artikel erstellt.", eingeloggterUser);
	}
	
	/**
	 * Methode leitet weiter an Methode in Artikelverwaltung.
	 * @param artikelName Name des einzufügenden Artikel.
	 * @param menge Menge des Artikels.
	 * @param d Preis des Artikels.
	 * @param packungsGroesse Packungsgröße des Artikels.
	 * @param stueckPreis Stückpreis des Artikels.
	 * @throws EinlagernException wird geworfen, wenn Menge oder Preis kleiner oder gleich 0 sind.
	 */
	public void fuegeArtikelEin(String artikelName, int menge, double d, int packungsGroesse, float stueckPreis) throws EinlagernException{ 
		MehrfachArtikel a = artVer.einfuegen(artikelName, menge, d, packungsGroesse, stueckPreis);
		erVer.ereignisEinfuegen(a, a.getArtikelBestand(), "Neuer Artikel erstellt.", eingeloggterUser);
	}
	
	/**
	 * Methode leitet weiter an Methode in Userverwaltung. 
	 * @param name Benutzername des Mitarbeiters.
	 * @param passwort Passwort des Mitarbeiters.
	 * @param anrede Anrede des Mitarbeiters.
	 * @param vorName Vorname des Mitarbeiters.
	 * @param nachName Nachname des Mitarbeiters.
	 */
	public void fuegeUserEin(String name, String passwort, String anrede, String vorName, String nachName){
		userVer.einfuegen(name, passwort, anrede, vorName, nachName);	
	}
	
	/**
	 * Methode leitet weiter an Methode in Userverwaltung.
	 * @param name Benutzername des Kunden.
	 * @param passwort Passwort des Kunden.
	 * @param anrede Anrede des Kunden.
	 * @param vorName Vorname des Kunden.
	 * @param nachName Nachname des Kunden.
	 * @param adresse Straße und Hausnr. des Kunden.
	 * @param plz Postleitzahl des Kunden.
	 * @param ort Ort des Kunden.
	 */
	public void fuegeUserEin(String name, String passwort, String anrede, String vorName, String nachName, String adresse, int plz, String ort){ 
		userVer.einfuegen(name, passwort, anrede, vorName, nachName, adresse, plz, ort);	
	}
	
	/**
	 * Methode leitet weiter an Methode in Warenkorbverwaltung.
	 * @param einArtikel Der Artikel der in den WK gelegt wurde.
	 * @param menge Menge des Artikels der in den WK gelegt wurde.
	 * @param k Der zur zeit eingeloggte Kunde der etwas in den WK legt.
	 * @return Gibt eingeloggten Kunden zurück.
	 * @throws ArtikelNichtGefundenException wird geworfen, wenn der Artikel nicht gefunden wurde.
	 * @throws NichtGenugAufLagerException wird geworfen, wenn nicht genügend Artikel des ausgeählten Produkts auf Lager sind.
	 * @throws ArtikelNurInEinheitenVerfuegbarException wird geworfen, wenn es sich um einen Artikel mit bestimmter Packungsgröße handelt.
	 */
	public Kunde artikelInWarenkorb(int artID, int menge, Kunde akteur) throws ArtikelNichtGefundenException, ArtikelNurInEinheitenVerfuegbarException, NichtGenugAufLagerException{	
		Artikel a = artVer.findArtikelByNumber(artID);
		// überprüfe: sind schon mehr in warenkorb als im bestand?
		Kunde k = null;
			k = warkoVer.artikelInWarenkorb(a, menge, akteur);
		return k;
	}
	
	/**
	 * Die Methode wird aufgerufen, wenn ein Kunde einen Artikel aus dem Warenkorb entfernt.
	 * @param artID Die Id des Artikels der gelöscht werden soll.
	 * @param akteur Der Kunder der eingeloggt ist.
	 * @return Kunde der eingeloggt ist.
	 * @throws ArtikelNichtGefundenException wird geworfen, wenn der Artikel nicht gefunden werden konnte.
	 * @throws WarenkorbLeerException wird geworfen, wenn kein Artikel im WK ist.
	 * @throws UserNichtGefundenException wird geworfen, wenn User nicht gefunden wurde.
	 */
	public Kunde artikelAusWarenkorb(int artID, Kunde akteur) throws ArtikelNichtGefundenException, WarenkorbLeerException, UserNichtGefundenException{	
		Artikel a = artVer.findArtikelByNumber(artID);
		Kunde kunde = warkoVer.artikelAusWarenkorb(a, (Kunde)userVer.findUserByNumber(akteur.getNummer()));
		erVer.ereignisEinfuegen(a, a.getArtikelBestand(), "Artikel " + a.getArtikelName() + " aus dem Warenkorb entfernt.", eingeloggterUser);
		return kunde;
	}
	
	/**
	 * Methode, um den Warenkorb des Kunden zu leeren.
	 * @param akteur Der eingeloggte Kunde
	 * @return eingeloggte Kunde
	 * @throws WarenkorbLeerException wird geworfen, wenn keine Artikel im WK sind.
	 * @throws UserNichtGefundenException wird geworfen, wenn User nicht gefunden wurde.
	 */
	public Kunde warenkorbLeeren(Kunde akteur) throws WarenkorbLeerException, UserNichtGefundenException{
		akteur = (Kunde)userVer.findUserByNumber(akteur.getNummer());
		akteur.getWarenkorb().leeren();
		return akteur;
		
	}
	
	/**
	 * Methode, um einen Artikel nach der Nummer zu suchen.
	 * @param artID Nummer des Artikels.
	 * @return Artikel der gefunden wurde.
	 * @throws ArtikelNichtGefundenException wird geworfen, wenn der Artikel nicht gefunden wurde.
	 */
	public Artikel findArtikelByNumber(int artID) throws ArtikelNichtGefundenException{
		return artVer.findArtikelByNumber(artID);
	}
	
	/**
	 * Methode, die die Artikelliste ausgibt.
	 * @return Gibt die Artikelliste zurück.
	 */
	public List<Artikel> gibAlleArtikel(){
		return artVer.getArtikelBestand();
	}
	
	/**
	 * Methode, die die Benutzerliste ausgibt.
	 * @return Gibt alle User zurück.
	 */
	public List<User> gibAlleUser(){
		return userVer.getUserBestand();
	}
	
	/**
	 * Methode die den Warenkorb des Users ausgibt.
	 * @param user eingeloggter User.
	 * @return Gibt WK des Kunden zurück.
	 */
	public Warenkorb gibWarenkorb(Kunde user){
		return user.getWarenkorb();
	}
	
	/**
	 * Methode, die das Protokoll ausgibt.
	 */
	public void gibProtokoll() {
		erVer.gibProtokollAus();
	}

	/**
	 * Methode, die die Protokollliste ausgibt.
	 * @return Protokollliste.
	 */
	public List<Ereignis> gibProtokollListe() {
		return erVer.gibProtokollListe();
	}
	
	/**
	 * Methode, die die Mengenänderung einliest und an die Artikelverwaltung weitergibt.
	 * @param nummer Artikel ID
	 * @param anzahl Menge die ein-/ausgelagert werden soll.
	 * @param akteur Der User der an Aktion beteiligt ist.
	 * @throws ArtikelNichtGefundenException wird geworfen, wenn der Artikel nicht gefunden wurde.
	 */
	public void mengeAendern(int nummer, int anzahl, User akteur) throws ArtikelNichtGefundenException{
		Artikel derWars = artVer.findArtikelByNumber(nummer);
		if (derWars != null) {
			artVer.setArtikelMenge(nummer, anzahl);		
	
			// aus nummer und anzahl muss ich den rest herausfinden
			erVer.ereignisEinfuegen(derWars, anzahl, "Bestandsanzahl geaendert.", eingeloggterUser);
		}
	}
	
	/**
	 * Methode, um Artikel nach Namen zu sortieren.
	 */
	public void artikelNachNamenOrdnen() {
		Collections.sort(gibAlleArtikel(), new comperatorArtikelName()); 
	}
	
	/**
	 * Methode, um Artikel nach der Nummer zu sortieren.
	 */
	public void artikelNachZahlenOrdnen() { 
		Collections.sort(gibAlleArtikel(), new comperatorArtikelNummer());
	}
	
	/**
	 * Methode, die den zu löschenden Benutzer an die Userverwaltung weitergibt.
	 * @param userNr User ID
	 * @param aktuellerBenutzer Der User der an der Aktion beteiligt ist.
	 * @throws UserNichtGefundenException wird geworfen, wenn der zu löschende User nicht gefunden wurde.
	 */
	public void loescheUser(int userNr) throws UserNichtGefundenException{
		userVer.loescheUser(userNr);
	}
	
	/**
	 * Methode zum Ausgeben der Artikelliste.
	 */
	public void gibArtikellisteAus() {
		artVer.gibArtikellisteAus();		
	}
	
	/**
	 * Methode zum Ausgeben der Benutzerliste.
	 */
	public void gibBenutzerlisteAus() {
		userVer.gibBenutzerlisteAus();
	}
	
	/**
	 * Methode zum Ausgeben des Warenkorbs.
	 * @param user User dem der WK gehört.
	 */
	public void getWarenkorbInhalt(User user){
		warkoVer.getWarenkorbInhalt(user);
	}
	
	/**
	 * Methode, die einließt, welcher Artikel im Warenkorb geändert werden soll und das an die Warenkorbverwaltung weiterleitet.
	 * @param artID Artikel nummer
	 * @param menge Menge die in den wk soll.
	 * @param akteur Der User der eingeloggt ist.
	 * @return Gibt neuen WK Bestand wieder.
	 * @throws ArtikelNichtGefundenException
	 * @throws UserNichtGefundenException 
	 */
	public HashMap<Artikel, Integer> artikelMengeImWarenkorbAendern(int artID, int menge, Kunde akteur) throws ArtikelNichtGefundenException, UserNichtGefundenException{			
		Artikel a = artVer.findArtikelByNumber(artID);
		return warkoVer.setArtikelMenge(a, menge, (Kunde)userVer.findUserByNumber(akteur.getNummer()));
	}
	
	/**
	 * Methode zum ausgeben des Ereignisverlaufs eines Artikels.
	 * @param artID Artikelnummer.
	 * @throws ArtikelNichtGefundenException wird geworfen, wenn ein Artikel nicht gefunden wurde.
	 */
	public void einkaufsVerlauf(int artID) throws ArtikelNichtGefundenException{
		Artikel a = artVer.findArtikelByNumber(artID);
		// die soll ich verwenden und bauen
		List<Ereignis> liste = erVer.gibEreignisseNachArtikelUndTagen(a); // Liste von Ereignissen
		if(liste.isEmpty()) {
			System.out.println("Liste ist leer.");
		} else {
			Iterator<Ereignis> it = liste.iterator();
			while (it.hasNext()) {
				Ereignis ereignis = it.next();
				System.out.println(ereignis.toString());
			}			
		}
		System.out.println(" ");
	}
	
	/**
	 * Methode, die einließt welcher Artikel gelöscht werden soll und das an die Artikelverwaltung weiterreicht.
	 * @param artID Artikelnummer.
	 * @param aktuellerBenutzer Benutzer der eingeloggt ist
	 * @throws ArtikelNichtGefundenException wird geworfen, wenn der Artikel nicht gefunden wurde.
	 */
	public void loescheArtikel(int artID, User aktuellerBenutzer) throws ArtikelNichtGefundenException{
		Artikel a = artVer.findArtikelByNumber(artID);
		erVer.ereignisEinfuegen(a, a.getArtikelBestand(), "Artikel geloescht.", eingeloggterUser); //aktuellerBenutzer,
		artVer.loescheArtikel(a);		
	}
	
	/**
	 * Methode, die einließt, welcher User sich einloggen will und die Überprüfung an die Userverwaltung übergibt.
	 * @param name Benutzername des Users.
	 * @param passwort PW des Users.
	 * @return Den eingeloggten User.
	 * @throws KennwortFalschException wird geworfen, wenn das PW falsch eingegeben wurde.
	 * @throws UserNichtGefundenException wird geworfe, wenn der User nicht gefunden wurde.
	 */
	public User userLogin(String name, String passwort) throws KennwortFalschException, UserNichtGefundenException{
		eingeloggterUser = userVer.userLogin(name, passwort);
		return eingeloggterUser;
	}

	/*public Rechnung rechnungErstellen(Kunde akteur) throws ArtikelNichtGefundenException, WarenkorbLeerException, UserNichtGefundenException{
		// key == Artikel
		//akteur = (Kunde)userVer.findUserByNumber(akteur.getNummer());
		HashMap<Artikel, Integer> warenkorb = akteur.getWarenkorb().getInhalt();
		System.out.println(warenkorb);
		if(warenkorb.isEmpty()){
			throw new WarenkorbLeerException();
		} else {
			// Artikelmenge im Artikelbestand verringern
			for(Artikel key : warenkorb.keySet()) {
				artVer.setArtikelMenge(key.getArtikelNummer(), (warenkorb.get(key)*-(1))); 

				erVer.ereignisEinfuegen(key, warenkorb.get(key), "Artikel gekauft. (Rechnung wurde erstellt)", eingeloggterUser);
		    }
		}
		Rechnung rechnung = new Rechnung(akteur, akteur.getWarenkorb(), new Date());
//		warenkorbLeeren,
		return rechnung;
	}*/
	
	/**
	 * Methode leitet an Speichermethoden in den anderen Verwaltungen weiter.
	 * @throws FileNotFoundException wird geworfen, wenn Datei nicht gefunden wurde.
	 * @throws IOException wenn es einen fehler beim schreiben gab.
	 */
	public void speichereDaten() throws FileNotFoundException, IOException {
		artVer.schreibeDatenMehrfachartikel();
//		artVer.schreibeDatenArtikel();
		userVer.schreibeDatenMitarbeiter();
		userVer.schreibeDatenKunden();
//		erVer.schreibeDaten();
	}

	/**
	 * Methode, die zum kaufen des Warenkorbinhaltes dient. Erstellen einer Rechnung und leeren des Warenkorbs. Weiterreichen an die entsprechenden Verwaltungen.
	 * @param k Kunde der einkauft.
	 * @return Gibt die Rechnung zurück.
	 * @throws NichtGenugAufLagerException wird geworfen, wenn nicht genug Artikel auf Lager sind.
	 * @throws WarenkorbLeerException wird geworfen, wenn der WK leer ist.
	 */
	public Rechnung kaufen(Kunde k) throws NichtGenugAufLagerException, WarenkorbLeerException{
		HashMap<Artikel, Integer> warenkorb = k.getWarenkorb().getInhalt();
		System.out.println(warenkorb);
		if(warenkorb.isEmpty()){
			throw new WarenkorbLeerException();
		} else {
			// Artikelmenge im Artikelbestand verringern
			for(Artikel key : warenkorb.keySet()) {
				artVer.setArtikelMenge(key.getArtikelNummer(), (warenkorb.get(key)*-(1))); 

				erVer.ereignisEinfuegen(key, warenkorb.get(key), "Artikel gekauft. (Rechnung wurde erstellt)", eingeloggterUser);
		    }
		}
		Rechnung rechnung = new Rechnung(k, k.getWarenkorb(), new Date());
//		warenkorbLeeren,
		return rechnung;
	}
	
}
