package Domain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import persistence.FilePersistenceManager;
import persistence.PersistenceManager;
import Valueobjects.Artikel;
import Valueobjects.MehrfachArtikel;
import exceptions.ArtikelNichtGefundenException;
import exceptions.ArtikelNurInEinheitenVerfuegbarException;
import exceptions.EinlagernException;


/**
 * Artikelverwaltung
 * Zur Verwaltung aller Methoden, die etwas mit Artikeln zu tun haben, wie z.B. einfügen, löschen etc.
 * 
 */

public class ArtikelVerwaltung {
	 
	private List<Artikel> artikelBestand = new Vector<Artikel>();
	private int laufnr = 0;
	private PersistenceManager pm = new FilePersistenceManager();

	private int bestimmeNr() {
		return ++laufnr;
	}
	
	
	public ArtikelVerwaltung(){
		try {
//			this.ladeDatenArtikel();
			this.ladeDatenMehrfachArtikel();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Methode zum einfügen von einfachen Artikel. Wenn richtig eingetragen, 
	 * bekommt der Artikel eine Artikelnummer -> bestimmeNr. wird hochgezählt und der Artikelbestand wird erhöht.
	 * @param artikelName Name des einzufügenden Artikel.
	 * @param menge Menge des Artikels.
	 * @param preis Preis des Artikels.
	 * @return Artikel Gibt den neu erstellten Artikel zurück.
	 * @throws EinlagernException wird geworfen, wenn Menge oder Preis kleiner oder gleich 0 sind.
	 */
	public Artikel einfuegen(String artikelName, int menge, double preis) throws EinlagernException{ 
		if(preis<=0||menge<=0) {
			EinlagernException e = new EinlagernException();
			throw e;
		} else {
			int artikelNummer = bestimmeNr();
			Artikel einArtikel = new Artikel(artikelNummer, artikelName, menge, preis);
			artikelBestand.add(einArtikel);
			return einArtikel;
		}
	}
	
	/**
	 * Methode zum einfügen von Mehrfachartikeln.
	 * @param artikelName Name des einzufügenden Artikel.
	 * @param menge Menge des Artikels.
	 * @param preis Preis des Artikels.
	 * @param packungsGroesse Packungsgröße des Artikels.
	 * @param stueckPreis Stückpreis des Artikels.
	 * @return Artikel Gibt den neu erstellten Artikel zurück.
	 * @throws EinlagernException wird geworfen, wenn Menge oder Preis kleiner oder gleich 0 sind.
	 */
	public MehrfachArtikel einfuegen(String artikelName, int menge, double preis, int packungsGroesse, float stueckPreis) throws EinlagernException{ 
		if(preis<=0||menge<=0) {
			EinlagernException e = new EinlagernException();
			throw e;
		} else {
			int artikelNummer = bestimmeNr();
			MehrfachArtikel einArtikel = new MehrfachArtikel(artikelNummer, artikelName, menge, preis, packungsGroesse, stueckPreis);
			artikelBestand.add(einArtikel);
			return einArtikel;
		}
	}
	
	
	/**
	 * Methode zum ändern der Artikelmenge
	 * @param nummer Artikel ID
	 * @param anzahl Menge die ein-/ausgelagert werden soll.
	 * @throws ArtikelNichtGefundenException wird geworfen, wenn der Artikel nicht gefunden wird.
	 */
	public void setArtikelMenge(int nummer, int anzahl) throws ArtikelNichtGefundenException, ArtikelNurInEinheitenVerfuegbarException{
		Artikel artikel = this.findArtikelByNumber(nummer);
		boolean mehrfach = artikel instanceof MehrfachArtikel;
			
		if(mehrfach){
			int packungsGroesse = ((MehrfachArtikel)artikel).getPackungsgroesse();
			System.out.println(packungsGroesse % anzahl == 0);
			System.out.println(anzahl >= packungsGroesse);
			if((packungsGroesse % anzahl == 0) && (anzahl >= packungsGroesse)){
				System.out.println("Massengut");
				artikel.setArtikelBestand((artikel.getArtikelBestand() + anzahl));
			}else{
				throw new ArtikelNurInEinheitenVerfuegbarException(packungsGroesse);
			}
		}else{
			artikel.setArtikelBestand((artikel.getArtikelBestand() + anzahl));
		}
		
		/*Iterator<Artikel> it = artikelBestand.iterator();
		while (it.hasNext()) {
			Artikel artikel = it.next();
			if(artikel.getArtikelNummer() == nummer){
				artikel.setArtikelBestand((artikel.getArtikelBestand() + anzahl));
			} 
		}*/
	}
	
	/**
	 * Methode, die die Artikelliste übergibt.
	 * @return Gibt die Artikelliste zurück.
	 */
	public List<Artikel> getArtikelBestand() {
		return artikelBestand;
	}
	
	/**
	 * Methode zum löschen von Artikeln.
	 * @param a Artikel der gelöscht werden soll.
	 * @throws ArtikelNichtGefundenException wird geworfen, wenn es den zu löschenden Artikel gar nicht gibt.
	 */
	public void loescheArtikel(Artikel a) throws ArtikelNichtGefundenException{
		if (artikelBestand.contains(a)) {
			artikelBestand.remove(a);
		} else {
			ArtikelNichtGefundenException e = new ArtikelNichtGefundenException(a);
			throw e;
		}
	}
	
	/**
	 * Methode zum ausgeben der Artikelliste.
	 */
	public void gibArtikellisteAus(){		
		if(artikelBestand.isEmpty()) {
			System.out.println("Liste ist leer.");
		} else {
			Iterator<Artikel> it = artikelBestand.iterator();
			while (it.hasNext()) {
				Artikel artikel = it.next();
				if (!(artikel.getArtikelBestand() <= 0)) {
					System.out.println(artikel.toString());
				} else {
					artikel = null;					
				}
			}			
		}
		System.out.println(" ");
	}
	
	/**
	 * Methode findet Artikel anhand der Artikelnummer.
	 * @param artID Nummer des Artikels.
	 * @return artikel Artikel der gefunden wurde.
	 * @throws ArtikelNichtGefundenException wird geworfen, wenn es den gesuchten Artikel nicht gibt.
	 */
	public Artikel findArtikelByNumber(int artID) throws ArtikelNichtGefundenException{
		Iterator<Artikel> it = artikelBestand.iterator();
		// Artikel erstellen
		Artikel artikel = null;
		// Artikelverzeichnis durchlaufen
		while (it.hasNext()) {
			artikel = it.next();
			// gesuchte Artikel ID gefunden
			if(artID==artikel.getArtikelNummer()){
				return artikel;				
			} else if (!(artID==artikel.getArtikelNummer())&&!it.hasNext()){ // gesuchte Artikel ID nicht gefunden
				ArtikelNichtGefundenException e = new ArtikelNichtGefundenException(artID);
				throw e;
			}
		}
		return null;
	}
	
	/**
	 * Methode speichert alle Mehrfachartikel in einer Textdatei.
	 * @throws FileNotFoundException wird geworfen, wenn Datei nicht gefunden wurde.
	 * @throws IOException wenn es einen fehler beim schreiben gab.
	 */
	public void schreibeDatenMehrfachartikel() throws FileNotFoundException, IOException {		
		pm.openForWriting("Mehrfachartikel.txt");
		if (!artikelBestand.isEmpty()) {
			for (Artikel artikel : artikelBestand) {
				if (artikel instanceof MehrfachArtikel)
					pm.speichereMehrfachArtikel((MehrfachArtikel)artikel);				
			}
		}
		pm.close();
	}
	
	/**
	 * Methode speichert alle Artikel in einer Textdatei.
	 * @throws FileNotFoundException wird geworfen, wenn Datei nicht gefunden wurde.
	 * @throws IOException wenn es einen fehler beim schreiben gab.
	 */
/*	public void schreibeDatenArtikel() throws IOException  {
		pm.openForWriting("Artikel.txt");
		if (!artikelBestand.isEmpty()) {
			for (Artikel artikel : artikelBestand) {
				if (artikel instanceof Artikel)
					pm.speichereArtikel((Artikel)artikel);				
			}
		}
		pm.close();
	}*/
	/**
	 * Methode läd alle Artikel aus einer Textdatei.
	 * @throws FileNotFoundException wird geworfen, wenn Datei nicht gefunden wurde.
	 * @throws IOException wenn es einen fehler beim lesen gab.
	 * @throws ClassNotFoundException
	 */
/*	public void ladeDatenArtikel() throws FileNotFoundException, IOException, ClassNotFoundException{
		pm.openForReading("Artikel.txt");
		Artikel a;
		do {
			a = pm.ladeArtikel();
			if (a != null) {
				artikelBestand.add(a);
			}				
		} while (a != null);	
		pm.close();
		this.laufnr = this.artikelBestand.get(this.artikelBestand.size()-1).getArtikelNummer();
			
	}*/
	
	/**
	 * Methode läd alle Mehrfachartikel aus einer Textdatei.
	 * @throws FileNotFoundException wird geworfen, wenn Datei nicht gefunden wurde.
	 * @throws IOException wenn es einen fehler beim lesen gab.
	 * @throws ClassNotFoundException
	 */
	public void ladeDatenMehrfachArtikel() throws FileNotFoundException, IOException, ClassNotFoundException{
		pm.openForReading("Mehrfachartikel.txt");
		MehrfachArtikel a;
		do {
			a = pm.ladeMehrfachArtikel();
			if (a != null) {
				artikelBestand.add(a);
			}				
		} while (a != null);	
		pm.close();
		this.laufnr = this.artikelBestand.get(this.artikelBestand.size()-1).getArtikelNummer();
			
	}
	
}
