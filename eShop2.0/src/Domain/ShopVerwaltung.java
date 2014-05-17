package Domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import Valueobjects.Artikel;
import Valueobjects.Ereignis;
import Valueobjects.Kunde;
import Valueobjects.MehrfachArtikel;
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
	
	public void fuegeArtikelEin(String artikelName, int menge, String beschreibung, double d, User akteur){ // hier fehlt ArtikelExistiertBereitsException
		Artikel a = artVer.einfuegen(artikelName, menge, beschreibung, d);
		erVer.ereignisEinfuegen(akteur, a, a.getArtikelBestand(), "Neuer Artikel erstellt.");
	}
	
	public void fuegeArtikelEin(String artikelName, int menge, String beschreibung, double d, int packungsGroesse, User akteur){ // hier fehlt ArtikelExistiertBereitsException
		MehrfachArtikel a = artVer.einfuegen(artikelName, menge, beschreibung, d, packungsGroesse);
		erVer.ereignisEinfuegen(akteur, a, a.getArtikelBestand(), "Neuer Artikel erstellt.");
	}
	
	public void fuegeUserEin(String name, char[] passwort, String anrede, String vorName, String nachName){
		userVer.einfuegen(name, passwort, anrede, vorName, nachName);	
	}
	
	public void fuegeUserEin(String name, char[] passwort, String anrede, String vorName, String nachName, String adresse, int plz, String ort){ 
		userVer.einfuegen(name, passwort, anrede, vorName, nachName, adresse, plz, ort);	
	}
	
	public Kunde artikelInWarenkorb(int artID, int menge, Kunde akteur){	
		Artikel a = artVer.findArtikelByNumber(artID);
		// überprüfe: sind schon mehr in warenkorb als im bestand?
		Kunde k = warkoVer.artikelInWarenkorb(a, menge, (Kunde)userVer.findUserByNumber(akteur.getNummer()));
		return k;
	}
	
	public Kunde artikelAusWarenkorb(int artID, Kunde akteur){	
		Artikel a = artVer.findArtikelByNumber(artID);
		Kunde kunde = warkoVer.artikelAusWarenkorb(a, (Kunde)userVer.findUserByNumber(akteur.getNummer()));
		erVer.ereignisEinfuegen(akteur, a, a.getArtikelBestand(), "Artikel " + a.getArtikelName() + " aus dem Warenkorb entfernt.");
		return kunde;
	}
	
	public Kunde warenkorbLeeren(Kunde akteur){
		akteur = (Kunde)userVer.findUserByNumber(akteur.getNummer());
		akteur.getWarenkorb().leeren();
		return akteur;
		
	}
	
	public Artikel findArtikelByNumber(int artID){
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
	
	public void mengeAendern(int nummer, int anzahl, User akteur){
		Artikel derWars = artVer.findArtikelByNumber(nummer);
		if (derWars != null) {
			artVer.setArtikelMenge(nummer, anzahl);		
	
			// aus nummer und anzahl muss ich den rest herausfinden
			erVer.ereignisEinfuegen(akteur, derWars, anzahl, "Bestandsanzahl geändert.");
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
	
	public HashMap<Artikel, Integer> artikelMengeImWarenkorbAendern(int artID, int menge, Kunde akteur){			
		Artikel a = artVer.findArtikelByNumber(artID);
		return warkoVer.setArtikelMenge(a, menge, (Kunde)userVer.findUserByNumber(akteur.getNummer()));
	}
	
	public void einkaufsVerlauf(int artID){
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
	
}
