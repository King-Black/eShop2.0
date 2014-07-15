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
import exceptions.BereitsVorhandenException;
import exceptions.EinlagernException;
import exceptions.KennwortFalschException;
import exceptions.NichtEingeloggtException;
import exceptions.NichtGenugAufLagerException;
import exceptions.WarenkorbLeerException;

/**
 * Klasse zur allgemeinen ShopVerwaltung. beinhaltet die Logik und besitzt die ArtikelVerwaltung, UserVerwaltung, WarenkorbVerwaltung und EreignisVerwaltung.
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
	 * Methode die einen neuen Artikel einfügt und den Auftrag an die Artikelverwaltung weiterreicht.
	 * @param artikelName
	 * @param menge
	 * @param d
	 * @throws EinlagernException
	 * @throws BereitsVorhandenException
	 */
	public void fuegeArtikelEin(String artikelName, int menge, double d) throws EinlagernException, BereitsVorhandenException{
		Artikel a = artVer.einfuegen(artikelName, menge, d);
		erVer.ereignisEinfuegen(a, a.getArtikelBestand(), "Neuer Artikel erstellt.", eingeloggterUser);
	}
	
	/**
	 * Methode die einen neuen MehrfachArtikel einfügt und den Auftrag an die Artikelverwaltung weiterreicht.
	 * @param artikelName
	 * @param menge
	 * @param d
	 * @param packungsGroesse
	 * @param stueckPreis
	 * @throws EinlagernException
	 * @throws BereitsVorhandenException
	 */
	public void fuegeArtikelEin(String artikelName, int menge, double d, int packungsGroesse, float stueckPreis) throws EinlagernException, BereitsVorhandenException{ 
		MehrfachArtikel a = artVer.einfuegen(artikelName, menge, d, packungsGroesse, stueckPreis);
		erVer.ereignisEinfuegen(a, a.getArtikelBestand(), "Neuer Artikel erstellt.", eingeloggterUser);
	}
	
	/**
	 * Methode zum erstellen eines Mitarbeiters.
	 * @param name
	 * @param passwort
	 * @param anrede
	 * @param vorName
	 * @param nachName
	 */
	public void fuegeUserEin(String name, String passwort, String anrede, String vorName, String nachName){
		userVer.einfuegen(name, passwort, anrede, vorName, nachName);	
	}
	
	/**
	 * Methode zum erstellen eines Kunden.
	 * @param name
	 * @param passwort
	 * @param anrede
	 * @param vorName
	 * @param nachName
	 * @param adresse
	 * @param plz
	 * @param ort
	 */
	public void fuegeUserEin(String name, String passwort, String anrede, String vorName, String nachName, String adresse, int plz, String ort){ 
		userVer.einfuegen(name, passwort, anrede, vorName, nachName, adresse, plz, ort);	
	}
	
	/**
	 * Methode die einen neuen Artikel in der Warenkorb ein und den Auftrag an die Warenkorbverwaltung weiterreicht.
	 * @param artID
	 * @param menge
	 * @param akteur
	 * @return Kunde
	 * @throws ArtikelNichtGefundenException
	 * @throws ArtikelNurInEinheitenVerfuegbarException
	 */
	public Kunde artikelInWarenkorb(int artID, int menge, Kunde akteur) throws ArtikelNichtGefundenException, ArtikelNurInEinheitenVerfuegbarException{	
		Artikel a = artVer.findArtikelByNumber(artID);
		// überprüfe: sind schon mehr in warenkorb als im bestand?
		Kunde k = null;
		try {
			k = warkoVer.artikelInWarenkorb(a, menge, akteur/*(Kunde)userVer.findUserByNumber(akteur.getNummer())*/);
		} catch (NichtGenugAufLagerException e) {
			e.printStackTrace();
		}
		return k;
	}
	
	/**
	 * Methode die einen Artikel einliest und an die Warenkorbverwaltung durchreicht.
	 * @param artID
	 * @param akteur
	 * @return Kunde
	 * @throws ArtikelNichtGefundenException
	 * @throws WarenkorbLeerException
	 */
	public Kunde artikelAusWarenkorb(int artID, Kunde akteur) throws ArtikelNichtGefundenException, WarenkorbLeerException{	
		Artikel a = artVer.findArtikelByNumber(artID);
		Kunde kunde = warkoVer.artikelAusWarenkorb(a, (Kunde)userVer.findUserByNumber(akteur.getNummer()));
		erVer.ereignisEinfuegen(a, a.getArtikelBestand(), "Artikel " + a.getArtikelName() + " aus dem Warenkorb entfernt.", eingeloggterUser);
		return kunde;
	}
	
	/**
	 * Methode, um den Warenkorb des Kunden zu leeren.
	 * @param akteur
	 * @return
	 * @throws WarenkorbLeerException
	 */
	public Kunde warenkorbLeeren(Kunde akteur) throws WarenkorbLeerException{
		akteur = (Kunde)userVer.findUserByNumber(akteur.getNummer());
		akteur.getWarenkorb().leeren();
		return akteur;
		
	}
	
	/**
	 * Methode, um einen Artikel nach der Nummer zu suchen.
	 * @param artID
	 * @return
	 * @throws ArtikelNichtGefundenException
	 */
	public Artikel findArtikelByNumber(int artID) throws ArtikelNichtGefundenException{
		return artVer.findArtikelByNumber(artID);
	}
	
	/**
	 * Methode, die die Artikelliste ausgibt.
	 * @return
	 */
	public List<Artikel> gibAlleArtikel(){
		return artVer.getArtikelBestand();
	}
	
	/**
	 * Methode, die die Benutzerliste ausgibt.
	 * @return
	 */
	public List<User> gibAlleUser(){
		return userVer.getUserBestand();
	}
	
	/**
	 * Methode die den Warenkorb des Users ausgibt.
	 * @param user
	 * @return
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
	 * @return
	 */
	public List<Ereignis> gibProtokollListe() {
		return erVer.gibProtokollListe();
	}
	
	/**
	 * Methode, die die Mengenänderung einliest und an die Artikelverwaltung weitergibt.
	 * @param nummer
	 * @param anzahl
	 * @param akteur
	 * @throws ArtikelNichtGefundenException
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
	 * @param userNr
	 * @param aktuellerBenutzer
	 */
	public void loescheUser(int userNr, User aktuellerBenutzer){
		userVer.loescheUser(userNr, aktuellerBenutzer);
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
	 * @param user
	 */
	public void getWarenkorbInhalt(User user){
		warkoVer.getWarenkorbInhalt(user);
	}
	
	/**
	 * Methode, die einließt, welcher Artikel im Warenkorb geändert werden soll und das an die Warenkorbverwaltung weiterleitet.
	 * @param artID
	 * @param menge
	 * @param akteur
	 * @return
	 * @throws ArtikelNichtGefundenException
	 */
	public HashMap<Artikel, Integer> artikelMengeImWarenkorbAendern(int artID, int menge, Kunde akteur) throws ArtikelNichtGefundenException{			
		Artikel a = artVer.findArtikelByNumber(artID);
		return warkoVer.setArtikelMenge(a, menge, (Kunde)userVer.findUserByNumber(akteur.getNummer()));
	}
	
	/**
	 * Methode zum ausgeben des Ereignisverlaufs eines Artikels.
	 * @param artID
	 * @throws ArtikelNichtGefundenException
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
	 * @param artID
	 * @param aktuellerBenutzer
	 * @throws ArtikelNichtGefundenException
	 */
	public void loescheArtikel(int artID, User aktuellerBenutzer) throws ArtikelNichtGefundenException{
		Artikel a = artVer.findArtikelByNumber(artID);
		erVer.ereignisEinfuegen(a, a.getArtikelBestand(), "Artikel geloescht.", eingeloggterUser); //aktuellerBenutzer,
		artVer.loescheArtikel(a);		
	}
	
	/**
	 * Methode, die einließt, welcher User sich einloggen will und die Überprüfung an die Userverwaltung übergibt.
	 * @param name
	 * @param passwort
	 * @return
	 * @throws KennwortFalschException
	 * @throws BereitsEingeloggtException
	 */
	public User userLogin(String name, String passwort) throws KennwortFalschException{
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
	
	
	public void speichereDaten() throws FileNotFoundException, IOException {
		artVer.schreibeDatenMehrfachartikel();
//		artVer.schreibeDatenArtikel();
		userVer.schreibeDatenMitarbeiter();
		userVer.schreibeDatenKunden();
//		erVer.schreibeDaten();
	}

	/**
	 * Methode, die zum kaufen des Warenkorbinhaltes dient. Erstellen einer Rechnung und leeren des Warenkorbs. Weiterreichen an die entsprechenden Verwaltungen.
	 * @param k
	 * @return
	 * @throws NichtEingeloggtException
	 * @throws NichtGenugAufLagerException
	 * @throws BereitsVorhandenException
	 * @throws WarenkorbLeerException
	 */
	public Rechnung kaufen(Kunde k) throws NichtEingeloggtException, NichtGenugAufLagerException, BereitsVorhandenException, WarenkorbLeerException{
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
