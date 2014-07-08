package CUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import CUI.CUI;
import exceptions.ArtikelNichtGefundenException;
import exceptions.ArtikelNurInEinheitenVerfuegbarException;
import exceptions.BereitsEingeloggtException;
import exceptions.EinlagernException;
import exceptions.KennwortFalschException;
import Valueobjects.Kunde;
import Valueobjects.User;
import Domain.ShopVerwaltung;
import GUI.HauptFenster;

public class CUI implements Runnable {
	
	private static  ShopVerwaltung shopVer;
	private	User aktuellerBenutzer;
	private String eingabe;
	private BufferedReader in;
	
	public CUI() { // Konstruktor
		shopVer = new ShopVerwaltung();
		aktuellerBenutzer = null;
		in = new BufferedReader(new InputStreamReader(System.in));
	}
	
public static void main(String[] args) throws EinlagernException {
		
		CUI shop = new CUI();
		shopVer.fuegeArtikelEin("Hose", 48, 6);
		shopVer.fuegeUserEin("Kunde", "123", "Frau", "Regina", "Regenbogen","Elbenweg 3", 13337, "Bruchtal");
		shopVer.fuegeUserEin("Mitarbeiter", "123", "Herr", "Max", "Mustermann");
		try {
			shop.run();
		}
		catch (Exception e) {
			System.out.println("Fehler bei der Eingabe");
			e.printStackTrace();
		}		
	}	
	
	@SuppressWarnings("unused")
	private static  void guiStarten() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		@SuppressWarnings("unused")
		HauptFenster h = new HauptFenster(shopVer);
		}
	
	public void run() {
		try {
//			shopVer.ladeDaten();
			gibMenue();
			shopVer.speichereDaten();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void gibMenue() throws IOException, ArtikelNichtGefundenException, ArtikelNurInEinheitenVerfuegbarException{
		do{
			if(!(aktuellerBenutzer == null)){
				eingeloggt();
			} else {
				menueNichtEingeloggt();
			}
		} while (!eingabe.equals("q"));
	}
	
	private void menueNichtEingeloggt() throws IOException {
		System.out.println("e) Einloggen\n" +
				"r) Registriere Kunden Account\n" +
				"q) Beenden");
		eingabe = liesEingabe();
		if(eingabe.equals("e")){
			System.out.println("Dein Benutzername?");
			String name = liesEingabe();
			System.out.println("Dein Passwort?");	
			String passwort = liesEingabe();
			try {
				aktuellerBenutzer = userLogin(name, passwort);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e);
			}
		} else if (eingabe.equals("r")) {
			try {
				userRegistrieren();
			} catch (Exception e) {
				System.out.println(e);
			}					
		} else if (eingabe.equals("q")){
			System.exit(0);
		}
	}
	
	private void eingeloggt() throws IOException, ArtikelNichtGefundenException, ArtikelNurInEinheitenVerfuegbarException{
		System.out.println("Herzlich Willkommen im Shop\n" +
		"wir w�nschen einen angenehmen Aufenthalt\n" +
		"und ein erfolgreiches Kauferlebnis.\n");
		do {
			System.out.println("Artikelliste:");
			gibArtikellisteAus(); 
			if(!(aktuellerBenutzer instanceof Kunde)) {
				menueMitarbeiter();
			} else {
				menueKunde();
			}
		} while (!eingabe.equals("a"));
		aktuellerBenutzer = null;
	}
	
	public void userRegistrieren() throws IOException{
		System.out.println("Waehle deinen Benutzernamen:");
		String name = liesEingabe();
		String passwort;
		String passwort1;
		do {
			System.out.println("Waehle dein Passwort:");
			passwort = liesEingabe();
			System.out.println("Eingabe Wiederholen:");
			passwort1 = liesEingabe();
		} while (!passwort.equals(passwort1));
		System.out.println("Anrede:");
		String anrede = liesEingabe();
		System.out.println("Vorname:");
		String vorName = liesEingabe();
		System.out.println("Nachname:");
		String nachName = liesEingabe();
		System.out.println("Stra�e und Hausnr:");
		String adresse = liesEingabe();
		System.out.println("Postleitzahl");
		int plz = Integer.parseInt(liesEingabe());
		System.out.println("Ort:");
		String ort = liesEingabe();
		CUI.shopVer.fuegeUserEin(name, passwort, anrede, vorName, nachName, adresse, plz, ort);
	}
	
	private void gibArtikellisteAus() {
		shopVer.gibArtikellisteAus();
	}
	
	private User userLogin(String name, String passwort) throws KennwortFalschException, BereitsEingeloggtException{
		return shopVer.userLogin(name, passwort);
	}
	
	public void menueMitarbeiter() throws IOException, ArtikelNichtGefundenException{
		System.out.println("n) neuen Artikel anlegen \n" +
				"m) Artikelmenge aendern\n" +
				"l) Artikel loeschen\n" +
				"u) Alle Benutzer anzeigen\n" +
				"r) Neuen Mitarbeiter registrieren\n" +
				"d) Mitarbeiter loeschen\n" + 
				"p) Protokoll anzeigen\n" +
				"c) Artikelmengenverlauf der letzten 30 Tage anzeigen lassen\n" + 
				"a) Ausloggen");
		eingabe = liesEingabe();
		switch(eingabe) {
			case "n": 	
				neuenArtikelAnlegen();
				break;
			case "m":
				artikelmengeAendern();
				break;
			case "l":
				artikelLoeschen();
				break;
			case "u":
				shopVer.gibBenutzerlisteAus();
				break;
			case "r": 
				mitarbeiterErstellen();
				break;
			case "d":
				benutzerLoeschen();
				break;
			case "p":
				shopVer.gibProtokoll();
				break;
			case "c":
				System.out.println("Welchen Artikel?");
				int artID = Integer.parseInt(liesEingabe());
				shopVer.einkaufsVerlauf(artID);
				break;
			case "a":
				ausloggen();
				break;
			default: System.out.println("Falsche Eingabe.");
		}
	}
	
	private void artikelLoeschen() throws IOException, ArtikelNichtGefundenException{
		System.out.println("Welchen Artikel willst du l�schen?");
		int artID = Integer.parseInt(liesEingabe());
		shopVer.loescheArtikel(artID, aktuellerBenutzer);
	}
	
	private void benutzerLoeschen() throws IOException{
		System.out.println("Welchen Mitarbeiter willst du l�schen?");
		int userNr = Integer.parseInt(liesEingabe());
		shopVer.loescheUser(userNr, aktuellerBenutzer);
	}
	
	private void ausloggen() throws IOException{
		System.out.println("Auf Wiedersehen!");
		menueNichtEingeloggt();
	}
	
	private void mitarbeiterErstellen() throws IOException{
		System.out.println("Waehle deinen Benutzernamen:");
		String benutzername = liesEingabe();
		String passwort;
		String passwort1;
		do {
			System.out.println("Waehle dein Passwort:");
			passwort = liesEingabe();
			System.out.println("Eingabe Wiederholen:");
			passwort1 = liesEingabe();
		} while (!passwort.equals(passwort1));
		System.out.println("Anrede:");
		String anrede = liesEingabe();
		System.out.println("Vorname:");
		String vorName = liesEingabe();
		System.out.println("Nachname:");
		String nachName = liesEingabe();
		try{	
			CUI.shopVer.fuegeUserEin(benutzername, passwort, anrede, vorName, nachName);
		}
		catch(Exception e) {
			System.out.println(e);				
		}
	}
	
	private void artikelmengeAendern() throws IOException{
		System.out.println("Artikelliste:");
		gibArtikellisteAus();
		System.out.println("Artikelnummer des zu �ndernden Artikel eingeben.");
		eingabe = liesEingabe();
		int nummer = Integer.parseInt(eingabe);
		System.out.println("Wieviele moechtest du hinzuf�gen?");
		eingabe = liesEingabe();
		int anzahl = Integer.parseInt(eingabe);
		try{
			shopVer.mengeAendern(nummer, anzahl, aktuellerBenutzer);
		}
		catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("Artikel wurden hinzugef�gt!");
	}
	
	private void neuenArtikelAnlegen() throws IOException{
		System.out.println("Moechtes du einen Mehrfachartikel speichern? (j fuer ja und n fuer nein)");
		String mehrfach = liesEingabe();
		int packungsGroesse = 0;
		float stueckPreis = 0;
		System.out.println("Name des Artikels: ");
		String artikelName = liesEingabe();
		System.out.println("Menge: ");
		eingabe = liesEingabe();
		int menge = Integer.parseInt(eingabe);
		//System.out.println("Beschreibung des Artikels: ");
		//String beschreibung = liesEingabe();
		System.out.println("Preis: ");
		eingabe = liesEingabe();
		double preis = Double.parseDouble(eingabe);
		try{
			if (mehrfach.equals("j")) {
				System.out.println("Bitte gib die Portionsgr��e ein.");
				String portion = liesEingabe();
				packungsGroesse = Integer.parseInt(portion);
				String sPreis = liesEingabe();
				stueckPreis = Float.parseFloat(sPreis);
				shopVer.fuegeArtikelEin(artikelName, menge, preis, packungsGroesse, stueckPreis); // mehrfachartikel
			} else if (mehrfach.equals("n")) {
				shopVer.fuegeArtikelEin(artikelName, menge, preis);
	
			} else {
				throw new IOException("Bitte entscheide dich f�r ja oder nein.");
			}
			System.out.println("wird angelegt!");
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void menueKunde() throws IOException, ArtikelNichtGefundenException, ArtikelNurInEinheitenVerfuegbarException{
		System.out.println("w) Zum Warenkorb\n" +
				"m) Artikelmenge im Warenkorb �ndern\n" +
				//"b) Artikelbeschreibung aufrufen\n" +
				"n) Artikel nach Namen ordnen\n" +
				"f) Artikel nach Nummern ordnen\n" +
				"c) Artikel in Warenkorb legen\n" +
				"d) Artikel aus Warenkorb\n" +
				"e) Warenkorb leeren\n" +
				"k) Zur Kasse\n" +
				"a) Ausloggen");
		
			eingabe = liesEingabe();
		switch(eingabe) {
			case "w": 
				shopVer.getWarenkorbInhalt(aktuellerBenutzer);
				//gibArtikellisteAus();
				break;
			case "m": 
				System.out.println("Von welchem Artikel m�chtest du die Menge �ndern?");
				eingabe = liesEingabe();
				int artID = Integer.parseInt(eingabe);
				System.out.println("Wieviel m�chtest du hinzuf�gen oder abziehen?");
				eingabe = liesEingabe();
				int menge = Integer.parseInt(eingabe);
				try{
					shopVer.artikelMengeImWarenkorbAendern(artID, menge, (Kunde)aktuellerBenutzer);
				}
				catch (Exception e) {
					System.out.println(e);
				}
				//gibArtikellisteAus();
				break;
//			case "b": 
//				System.out.println("Welchen Artikel?");
//				eingabe = liesEingabe();
//				int artID = Integer.parseInt(eingabe);
//				artikelBeschreibung(artID);
//				break;
			case "n": shopVer.artikelNachNamenOrdnen();
					break;
			case "f": shopVer.artikelNachZahlenOrdnen();
					break;
			case "c": 
				System.out.println("Welchen Artikel kaufen?");
				eingabe = liesEingabe();
				artID = Integer.parseInt(eingabe);
				System.out.println("Wieviele davon?");
				eingabe = liesEingabe();
				menge = Integer.parseInt(eingabe);
				shopVer.artikelInWarenkorb(artID, menge,(Kunde) aktuellerBenutzer);
				break;
			case "d": 
				System.out.println("Welchen Artikel entfernen?");
				eingabe = liesEingabe();
				artID = Integer.parseInt(eingabe);
				try{
					shopVer.artikelAusWarenkorb(artID, (Kunde)aktuellerBenutzer);
				}
				catch (Exception e) {
					System.out.println(e);
				}
				break;
			case "e":
				try{
					shopVer.warenkorbLeeren((Kunde)aktuellerBenutzer);
				}
				catch (Exception e) {
					System.out.println(e);
				}
				break;
			case "k": zurKasse();
				break;
			case "p":
				shopVer.gibProtokoll();
				break;
			case "a":
				System.out.println("Auf Wiedersehen!");
				break;
			default: System.out.println("Falsche Eingabe.");
		}
		
	}
	
	public void zurKasse(){
		try {
			shopVer.rechnungErstellen((Kunde)aktuellerBenutzer);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private String liesEingabe() throws IOException{
		return in.readLine();
	}

}
