package CUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Domain.ShopVerwaltung;
import Valueobjects.Kunde;
import Valueobjects.User;
import exceptions.ArtikelNichtGefundenException;
import exceptions.ArtikelNurInEinheitenVerfuegbarException;
import exceptions.KennwortFalschException;


/**
 * CUI
 * Die CUI das Command U Interface, welches in der Kommandozeile alles ausgibt, was sie über die Shopverwaltung erhält.
 */
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
	
	public void run() {
		try {
			gibMenue();
			shopVer.speichereDaten();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Die Methode ptüft, ob ein Benutzer eingeloggt ist und gibt dementsprechend ein Menü aus.
	 * @throws IOException
	 * @throws ArtikelNichtGefundenException
	 * @throws ArtikelNurInEinheitenVerfuegbarException
	 */
	//Wenn Benutzer nicht eingeloggt ist, wird "menueNichtEingeloggt" ausgeführt. Solange nicht q für Quit eingegeben wird.
	private void gibMenue() throws IOException, ArtikelNichtGefundenException, ArtikelNurInEinheitenVerfuegbarException{
		do{
			if(aktuellerBenutzer != null){
				eingeloggt();
			} else {
				menueNichtEingeloggt();
			}
		} while (!eingabe.equals("q"));
	}
	
	/**
	 * Die Methode gibt das Startmenü aus, so als wenn noch kein User angemeldet ist.
	 * @throws IOException
	 */
	//Gibt Startmenue aus wenn kein Benutzer eingeloggt ist.
	//Wenn e eingegeben wird kommt das Einloggmenue und fragt nach Benutzernamen und PW.
	//Wenn r eingegeben wird kann sich eine Kunde registrieren.
	//Wenn q gedrückt wird werden die bisherigen Daten gespeichert und der eShop beendet.
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
			shopVer.speichereDaten();
			System.exit(0);
		}
	}
	
	/**
	 * Diese Methode gibt das Kundenmenü aus, wenn sich ein Kunde einloggt.
	 * @throws IOException
	 * @throws ArtikelNichtGefundenException
	 * @throws ArtikelNurInEinheitenVerfuegbarException
	 */
	//Wenn Kunde eingeloggt ist kommt Willkommenstext und Artikelliste.
	private void eingeloggt() throws IOException, ArtikelNichtGefundenException, ArtikelNurInEinheitenVerfuegbarException{
		System.out.println("Herzlich Willkommen im Shop\n" +
		"wir wuenschen einen angenehmen Aufenthalt\n" +
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
	
	/**
	 * Diese Methode gibt Menü zum registrieren eines Kunden aus.
	 * @throws IOException
	 */
	//Metode zum Registrieren eines Kunden
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
		System.out.println("Straï¿½e und Hausnr:");
		String adresse = liesEingabe();
		System.out.println("Postleitzahl");
		int plz = Integer.parseInt(liesEingabe());
		System.out.println("Ort:");
		String ort = liesEingabe();
		CUI.shopVer.fuegeUserEin(name, passwort, anrede, vorName, nachName, adresse, plz, ort);
	}
	
	/**
	 * Methode gibt die Artikelliste aus die sie über Shopverwaltung aufruft.
	 */
	private void gibArtikellisteAus() {
		shopVer.gibArtikellisteAus();
	}
	
	/**
	 * Die Methode gibt den eingeloggten benutzer mit Namen und PW wieder.
	 * @param name
	 * @param passwort
	 * @return eingeloggten Benuter
	 * @throws KennwortFalschException
	 * @throws BereitsEingeloggtException
	 */
	private User userLogin(String name, String passwort) throws KennwortFalschException{
		return shopVer.userLogin(name, passwort);
	}
	
	/**
	 * Diese Methode gibt das Menü aus wenn der User als Mitarbeiter eingeloggt ist.
	 * @throws IOException
	 * @throws ArtikelNichtGefundenException
	 */
	//Ist das Menue wenn User als Mitarbeiter eingeloggt ist
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
	
	/**
	 * Diese Methode ist zum löschen der Artikel.
	 * @throws IOException
	 * @throws ArtikelNichtGefundenException wird geworfen, wenn der Artikel nicht existiert.
	 */
	//Methode zum löschen von Artikeln.
	//In der Shopverwaltung wird "loescheArtikel" aufgerufen und Artikel wird mit Artikel ID und dem Beutzer der den Artikel gelöscht hat gespeichert
	private void artikelLoeschen() throws IOException, ArtikelNichtGefundenException{
		System.out.println("Welchen Artikel willst du loeschen?");
		int artID = Integer.parseInt(liesEingabe());
		shopVer.loescheArtikel(artID, aktuellerBenutzer);
	}
	
	/**
	 * Diese Methode ist zum löschen von Mitarbeitern gedacht.
	 * @throws IOException
	 */
	//Methode zum löschen eines Mitarbeiters
	private void benutzerLoeschen() throws IOException{
		System.out.println("Welchen Mitarbeiter willst du loeschen?");
		int userNr = Integer.parseInt(liesEingabe());
		shopVer.loescheUser(userNr, aktuellerBenutzer);
	}
	
	/**
	 * Die Methode wird aufgerufen, wenn sich ein User ausloggt.
	 * @throws IOException
	 */
	private void ausloggen() throws IOException{
		System.out.println("Auf Wiedersehen!");
		menueNichtEingeloggt();
	}
	
	/**
	 * Diese Methode wird zum erstellen eines Mitarbeiters aufgerufen.
	 * @throws IOException
	 */
	//Methode zum Erstellen eines eines Mitarbeiters
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
	
	/**
	 * Diese Methode dient zum ändern der Artikelmenge in die Artikelliste.
	 * @throws IOException
	 */
	//Methode zum ändern der Artikelmenge
	private void artikelmengeAendern() throws IOException{
		System.out.println("Artikelliste:");
		gibArtikellisteAus();
		System.out.println("Artikelnummer des zu aendernden Artikel eingeben.");
		eingabe = liesEingabe();
		int nummer = Integer.parseInt(eingabe);
		System.out.println("Wieviele moechtest du hinzufuegen?");
		eingabe = liesEingabe();
		int anzahl = Integer.parseInt(eingabe);
		try{
			shopVer.mengeAendern(nummer, anzahl, aktuellerBenutzer);
		}
		catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("Artikel wurden hinzugefuegt!");
	}
	
	/**
	 * Diese Methode wird zum Anelgen von neuen Artikeln aufgerufen.
	 * @throws IOException
	 */
	//Methode zum anlegen eines neuen Artikels
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
				System.out.println("Bitte gib die Portionsgroesse ein.");
				String portion = liesEingabe();
				packungsGroesse = Integer.parseInt(portion);
				String sPreis = liesEingabe();
				stueckPreis = Float.parseFloat(sPreis);
				shopVer.fuegeArtikelEin(artikelName, menge, preis, packungsGroesse, stueckPreis); // mehrfachartikel
			} else if (mehrfach.equals("n")) {
				shopVer.fuegeArtikelEin(artikelName, menge, preis);
	
			} else {
				throw new IOException("Bitte entscheide dich fï¿½r ja oder nein.");
			}
			System.out.println("wird angelegt!");
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Diese Methode wird aufgerufen wenn ein Kunde eingeloggt ist und zeigt das Kundenmenü.
	 * @throws IOException
	 * @throws ArtikelNichtGefundenException
	 * @throws ArtikelNurInEinheitenVerfuegbarException
	 */
	//Anzeige des Menues wenn man als Kunde eingeloggt ist.
	public void menueKunde() throws IOException, ArtikelNichtGefundenException, ArtikelNurInEinheitenVerfuegbarException{
		System.out.println("w) Zum Warenkorb\n" +
				"m) Artikelmenge im Warenkorb ï¿½ndern\n" +
				//"b) Artikelbeschreibung aufrufen\n" +
				"n) Artikel nach Namen ordnen\n" +
				"f) Artikel nach Nummern ordnen\n" +
				"c) Artikel in Warenkorb legen\n" +
				"d) Artikel aus Warenkorb\n" +
				"e) Warenkorb leeren\n" +
				"k) Zur Kasse\n" +
				"a) Ausloggen");
		//Ruft einzelen Unterpunkte im Kundenmenue auf
			eingabe = liesEingabe();
		switch(eingabe) {
			case "w": 
				shopVer.getWarenkorbInhalt(aktuellerBenutzer);
				//gibArtikellisteAus();
				break;
			case "m": 
				System.out.println("Von welchem Artikel moechtest du die Menge aendern?");
				eingabe = liesEingabe();
				int artID = Integer.parseInt(eingabe);
				System.out.println("Wieviel moechtest du hinzufuegen oder abziehen?");
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
	
	/**
	 * Diese Methode erstellt eine Rechnung aus der Shopverwaltung.
	 */
	//Methode ruft von Shopverwaltung die Methode rechnungErstellen auf
	public void zurKasse(){
		try {
			shopVer.kaufen((Kunde) aktuellerBenutzer);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Diese Methode liest Eingaben ein.
	 * @return Eingaben
	 * @throws IOException
	 */
	//Methode zum einlesen der Eingaben
	private String liesEingabe() throws IOException{
		return in.readLine();
	}

}
