package CUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Valueobjects.Kunde;
import Valueobjects.User;
import Domain.ShopVerwaltung;

public class CUI {
	
	private ShopVerwaltung shopVer;
	private	User aktuellerBenutzer;
	private String eingabe;
	private BufferedReader in;
	
	public CUI() { // Konstruktor
		shopVer = new ShopVerwaltung();
		aktuellerBenutzer = null;
		in = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public static void main(String[] args){
		
		CUI shop = new CUI();
//shop.shopVer.fuegeArtikelEin("SECHSSTEIN", 9.99, null, 48, 6);
		shop.run();
		
	}
	
	public void run() {
		try {
			//shopVer.ladeDaten();
//			char[] pw = {'1','2','3'};
//			shopVer.fuegeUserEin("Kunde", pw, "Herr", "Axel Schweiss","Elbenweg 3", 13337, "Bruchtal", "Mittelerde");
//			shopVer.fuegeUserEin("Mitarbeiter", pw, "Herr", "Voll iDiot");

//			shopVer.fuegeArtikelEin("EINSTEIN", 1.99, null, 12);
			gibMenue();
			//shopVer.speichereDaten();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void gibMenue() throws IOException{
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
				aktuellerBenutzer = userLogin(name, passwort.toCharArray());
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
		}
	}
	
	private void eingeloggt() throws IOException{
		System.out.println("Herzlich Willkommen im Shop\n" +
		"wir wünschen einen angenehmen Aufenthalt\n" +
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
		System.out.println("Straße und Hausnr:");
		String adresse = liesEingabe();
		System.out.println("Postleitzahl");
		int plz = Integer.parseInt(liesEingabe());
		System.out.println("Ort:");
		String ort = liesEingabe();
		this.shopVer.fuegeUserEin(name, passwort.toCharArray(), anrede, vorName, nachName, adresse, plz, ort);
	}
	
	private void gibArtikellisteAus() {
		shopVer.gibArtikellisteAus();
	}
	
	private User userLogin(String name, char[] passwort){
		return shopVer.userLogin(name, passwort);
	}
	
	public void menueMitarbeiter() throws IOException{
		System.out.println("n) neuen Artikel anlegen \n" +
				"m) Artikelmenge aendern\n" +
				"l) Artikel löschen\n" +
				"u) Alle Benutzer anzeigen\n" +
				"r) Neuen Mitarbeiter registrieren\n" +
				"d) Mitarbeiter löschen\n" + 
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
				System.out.println("Auf Wiedersehen!");
				break;
			default: System.out.println("Falsche Eingabe.");
		}
	}
	
	private void artikelLoeschen() throws IOException{
		System.out.println("Welchen Artikel willst du löschen?");
		int artID = Integer.parseInt(liesEingabe());
		shopVer.loescheArtikel(artID, aktuellerBenutzer);
	}
	
	private void benutzerLoeschen() throws IOException{
		System.out.println("Welchen Mitarbeiter willst du löschen?");
		int userNr = Integer.parseInt(liesEingabe());
		shopVer.loescheUser(userNr, aktuellerBenutzer);
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
			this.shopVer.fuegeUserEin(benutzername, passwort.toCharArray(), anrede, vorName, nachName);
		}
		catch(Exception e) {
			System.out.println(e);				
		}
	}
	
	private void artikelmengeAendern() throws IOException{
		System.out.println("Artikelliste:");
		gibArtikellisteAus();
		System.out.println("Artikelnummer des zu ändernden Artikel eingeben.");
		eingabe = liesEingabe();
		int nummer = Integer.parseInt(eingabe);
		System.out.println("Wieviele moechtest du hinzufügen?");
		eingabe = liesEingabe();
		int anzahl = Integer.parseInt(eingabe);
		try{
			shopVer.mengeAendern(nummer, anzahl, aktuellerBenutzer);
		}
		catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("Artikel wurden hinzugefügt!");
	}
	
	private void neuenArtikelAnlegen() throws IOException{
		System.out.println("Moechtes du einen Mehrfachartikel speichern? (j für ja und n für nein)");
		String mehrfach = liesEingabe();
		int packungsGroesse = 0;
		System.out.println("Name des Artikels: ");
		String artikelName = liesEingabe();
		System.out.println("Menge: ");
		eingabe = liesEingabe();
		int menge = Integer.parseInt(eingabe);
		System.out.println("Beschreibung des Artikels: ");
		String beschreibung = liesEingabe();
		System.out.println("Preis: ");
		eingabe = liesEingabe();
		double d = Double.parseDouble(eingabe);
		try{
			if (mehrfach.equals("j")) {
				System.out.println("Bitte gib die Portionsgröße ein.");
				String portion = liesEingabe();
				packungsGroesse = Integer.parseInt(portion);
				shopVer.fuegeArtikelEin(artikelName, menge, beschreibung, d, packungsGroesse, aktuellerBenutzer); // mehrfachartikel
			} else if (mehrfach.equals("n")) {
				shopVer.fuegeArtikelEin(artikelName, menge, beschreibung, d, aktuellerBenutzer);
	
			} else {
				throw new IOException("Bitte entscheide dich für ja oder nein.");
			}
			System.out.println("wird angelegt!");
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void menueKunde() throws IOException{
		System.out.println("w) Zum Warenkorb\n" +
				"m) Artikelmenge im Warenkorb ändern\n" +
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
				System.out.println("Von welchem Artikel möchtest du die Menge ändern?");
				eingabe = liesEingabe();
				int artID = Integer.parseInt(eingabe);
				System.out.println("Wieviel möchtest du hinzufügen oder abziehen?");
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
