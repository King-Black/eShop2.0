package Domain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import exceptions.ArtikelNichtGefundenException;
import exceptions.ArtikelNurInEinheitenVerfuegbarException;
import exceptions.BereitsEingeloggtException;
import exceptions.EinlagernException;
import exceptions.KennwortFalschException;
import exceptions.NichtGenugAufLagerException;
import exceptions.UserNichtGefundenException;
import exceptions.WarenkorbLeerException;
import Valueobjects.Artikel;
import Valueobjects.Ereignis;
import Valueobjects.Kunde;
import Valueobjects.MehrfachArtikel;
import Valueobjects.Rechnung;
import Valueobjects.User;
import Valueobjects.Warenkorb;

public class ShopVerwaltung {
	private ArtikelVerwaltung artVer;
	private UserVerwaltung userVer;
	private WarenkorbVerwaltung warkoVer;
	public EreignisVerwaltung erVer;
	
	public ShopVerwaltung(){
		artVer = new ArtikelVerwaltung();
		userVer = new UserVerwaltung();
		warkoVer = new WarenkorbVerwaltung();
		erVer = new EreignisVerwaltung();
	}
	
	public void fuegeArtikelEin(String artikelName, int menge, double d, User akteur) throws EinlagernException{ // hier fehlt ArtikelExistiertBereitsException
		Artikel a = artVer.einfuegen(artikelName, menge, d);
		erVer.ereignisEinfuegen(akteur, a, a.getArtikelBestand(), "Neuer Artikel erstellt.");
	}
	
	public void fuegeArtikelEin(String artikelName, int menge, double d, int packungsGroesse, User akteur) throws EinlagernException{ // hier fehlt ArtikelExistiertBereitsException
		MehrfachArtikel a = artVer.einfuegen(artikelName, menge, d, packungsGroesse);
		erVer.ereignisEinfuegen(akteur, a, a.getArtikelBestand(), "Neuer Artikel erstellt.");
	}
	
	public void fuegeUserEin(String name, char[] passwort, String anrede, String vorName, String nachName){
		userVer.einfuegen(name, passwort, anrede, vorName, nachName);	
	}
	
	public void fuegeUserEin(String name, char[] passwort, String anrede, String vorName, String nachName, String adresse, int plz, String ort){ 
		userVer.einfuegen(name, passwort, anrede, vorName, nachName, adresse, plz, ort);	
	}
	
	public Kunde artikelInWarenkorb(int artID, int menge, Kunde akteur) throws ArtikelNichtGefundenException, ArtikelNurInEinheitenVerfuegbarException{	
		Artikel a = artVer.findArtikelByNumber(artID);
		// �berpr�fe: sind schon mehr in warenkorb als im bestand?
		Kunde k = null;
		try {
			k = warkoVer.artikelInWarenkorb(a, menge, (Kunde)userVer.findUserByNumber(akteur.getNummer()));
		} catch (NichtGenugAufLagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}
	
	public Kunde artikelAusWarenkorb(int artID, Kunde akteur) throws ArtikelNichtGefundenException, WarenkorbLeerException{	
		Artikel a = artVer.findArtikelByNumber(artID);
		Kunde kunde = warkoVer.artikelAusWarenkorb(a, (Kunde)userVer.findUserByNumber(akteur.getNummer()));
		erVer.ereignisEinfuegen(akteur, a, a.getArtikelBestand(), "Artikel " + a.getArtikelName() + " aus dem Warenkorb entfernt.");
		return kunde;
	}
	
	public Kunde warenkorbLeeren(Kunde akteur) throws WarenkorbLeerException{
		akteur = (Kunde)userVer.findUserByNumber(akteur.getNummer());
		akteur.getWarenkorb().leeren();
		return akteur;
		
	}
	
	public Artikel findArtikelByNumber(int artID) throws ArtikelNichtGefundenException{
		return artVer.findArtikelByNumber(artID);
	}
	
	public List<Artikel> gibAlleArtikel(){
		return artVer.getArtikelBestand();
	}
	
	public List<User> gibAlleUser(){
		return userVer.getUserBestand();
	}
	
	public Warenkorb gibWarenkorb(Kunde user){
		return user.getWarenkorb();
	}
	
	public void gibProtokoll() {
		erVer.gibProtokollAus();
	}

	public List<Ereignis> gibProtokollListe() {
		return erVer.gibProtokollListe();
	}
	
	public void mengeAendern(int nummer, int anzahl, User akteur) throws ArtikelNichtGefundenException{
		Artikel derWars = artVer.findArtikelByNumber(nummer);
		if (derWars != null) {
			artVer.setArtikelMenge(nummer, anzahl);		
	
			// aus nummer und anzahl muss ich den rest herausfinden
			erVer.ereignisEinfuegen(akteur, derWars, anzahl, "Bestandsanzahl ge�ndert.");
		}
	}
	
	public void artikelNachNamenOrdnen() {
		Collections.sort(gibAlleArtikel(), new comperatorArtikelName()); 
	}
	
	public void artikelNachZahlenOrdnen() { 
		Collections.sort(gibAlleArtikel(), new comperatorArtikelNummer());
	}
	
	public void loescheUser(int userNr, User aktuellerBenutzer){
		userVer.loescheUser(userNr, aktuellerBenutzer);
	}
	
	public void gibArtikellisteAus() {
		artVer.gibArtikellisteAus();		
	}
	
	public void gibBenutzerlisteAus() {
		userVer.gibBenutzerlisteAus();
	}
	
	public void getWarenkorbInhalt(User user){
		warkoVer.getWarenkorbInhalt(user);
	}
	
	public HashMap<Artikel, Integer> artikelMengeImWarenkorbAendern(int artID, int menge, Kunde akteur) throws ArtikelNichtGefundenException{			
		Artikel a = artVer.findArtikelByNumber(artID);
		return warkoVer.setArtikelMenge(a, menge, (Kunde)userVer.findUserByNumber(akteur.getNummer()));
	}
	
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
	
	public void loescheArtikel(int artID, User aktuellerBenutzer) throws ArtikelNichtGefundenException{
		Artikel a = artVer.findArtikelByNumber(artID);
		erVer.ereignisEinfuegen(aktuellerBenutzer, a, a.getArtikelBestand(), "Artikel gel�scht.");
		artVer.loescheArtikel(a);		
	}
	
	public User userLogin(String name, String passwort) throws KennwortFalschException, BereitsEingeloggtException{
		return userVer.userLogin(name, passwort);
	}

	public Rechnung rechnungErstellen(Kunde akteur) throws ArtikelNichtGefundenException, WarenkorbLeerException, UserNichtGefundenException{
		// key == Artikel
		akteur = (Kunde)userVer.findUserByNumber(akteur.getNummer());
		HashMap<Artikel, Integer> warenkorb = akteur.getWarenkorb().getInhalt();
		System.out.println(warenkorb);
		if(warenkorb.isEmpty()){
			throw new WarenkorbLeerException();
		} else {
			// Artikelmenge im Artikelbestand verringern
			for(Artikel key : warenkorb.keySet()) {
				artVer.setArtikelMenge(key.getArtikelNummer(), (warenkorb.get(key)*-(1))); 

				erVer.ereignisEinfuegen(akteur, key, warenkorb.get(key), "Artikel gekauft. (Rechnung wurde erstellt)");
		    }
		}
		Rechnung rechnung = new Rechnung(akteur, akteur.getWarenkorb(), new Date());
//		warenkorbLeeren(akteur);
		return rechnung;
	}
	
	/*public void ladeDaten() throws FileNotFoundException, IOException, ClassNotFoundException {
		artVer.ladeDaten(); //funktioniert
		userVer.ladeDaten(); // user objekte
		erVer.ladeDaten();			
	
	}*/
	
	public void speichereDaten() throws FileNotFoundException, IOException {
		artVer.schreibeDaten();
		userVer.schreibeDaten(); //user objekte
		erVer.schreibeDaten();
	}
	
}
