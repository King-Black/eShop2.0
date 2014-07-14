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
import Valueobjects.Mitarbeiter;
import Valueobjects.User;
import exceptions.ArtikelNichtGefundenException;
import exceptions.EinlagernException;

public class ArtikelVerwaltung {
	 
	private List<Artikel> artikelBestand = new Vector<Artikel>();
	private int laufnr = 0;
	private PersistenceManager pm = new FilePersistenceManager();

	private int bestimmeNr() {
		return ++laufnr;
	}
	
	public ArtikelVerwaltung(){
		try {
			this.ladeDatenArtikel();
			this.ladeDatenMehrfachArtikel();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Methode zum einfügen von einfachen Artikel.
	 * @param artikelName
	 * @param menge
	 * @param preis
	 * @return Artikel
	 * @throws EinlagernException
	 */
	//Methode zum einfügen von Artikeln.
	//Wenn Menge oder Preis kleiner 0 oder 0 ist wird eine EinlagernException geworfen.
	//Wenn richtig eingetragen, bekommt der Artikel eine Artikelnummer -> bestimmeNr. wird hochgezählt.
	//und der Artikelbestand wird erhöht.
	public Artikel einfuegen(String artikelName, int menge, double preis) throws EinlagernException{ 
		if(preis<=0||menge<=0) {
			//exception EinlagernException
			EinlagernException e = new EinlagernException();
			throw e;
		} else {
			int artikelNummer = bestimmeNr();
			Artikel einArtikel = new Artikel(artikelNummer, artikelName, menge, preis);
			artikelBestand.add(einArtikel);
			return einArtikel;
		}
		//return null;
	}
	
	/**
	 * Methode zum einfügen von Mehrfachartikeln.
	 * @param artikelName
	 * @param menge
	 * @param preis
	 * @param packungsGroesse
	 * @param stueckPreis
	 * @return Artikel
	 * @throws EinlagernException
	 */
	//Methode zum einfügen von Mehrfachartikeln
	public MehrfachArtikel einfuegen(String artikelName, int menge, double preis, int packungsGroesse, float stueckPreis) throws EinlagernException{ 
		if(preis<=0||menge<=0) {
			//exception EinlagernException
			EinlagernException e = new EinlagernException();
			throw e;
		} else {
			int artikelNummer = bestimmeNr();
			MehrfachArtikel einArtikel = new MehrfachArtikel(artikelNummer, artikelName, menge, preis, packungsGroesse, stueckPreis);
			artikelBestand.add(einArtikel);
			return einArtikel;
		}
		//return null;
	}
	
	public void setArtikelMenge(int nummer, int anzahl){
		Iterator<Artikel> it = artikelBestand.iterator();
		while  (it.hasNext()) {
			Artikel artikel = it.next();
			if(artikel.getArtikelNummer() == nummer){
				artikel.setArtikelBestand((artikel.getArtikelBestand() + anzahl));
			} 
		}
	}
	
	public List<Artikel> getArtikelBestand() {
		return artikelBestand;
	}
	/**
	 * Mathode zum löschen von Artikeln.
	 * @param a
	 * @throws ArtikelNichtGefundenException wird geworfen, wenn es den zu löschenden Artikel gar nicht gibt.
	 */
	//Methode zum löschen von Artikeln
	public void loescheArtikel(Artikel a) throws ArtikelNichtGefundenException{
		if (artikelBestand.contains(a)) {
			artikelBestand.remove(a);
		} else {
			//exception ArtikelNichtGefundenException
			ArtikelNichtGefundenException e = new ArtikelNichtGefundenException(a);
			throw e;
		}
	}
	
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
	 * @param artID
	 * @return artikel
	 * @throws ArtikelNichtGefundenException
	 */
	//Methode zum finden von Artikeln anhand der Artikelnummer
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
				//exception ArtikelIdNichtGefundenException
				ArtikelNichtGefundenException e = new ArtikelNichtGefundenException(artID);
				throw e;
			}
		}
		return null;
	}
	
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
	
	public void schreibeDatenArtikel() throws IOException  {
		pm.openForWriting("Artikel.txt");
		if (!artikelBestand.isEmpty()) {
			for (Artikel artikel : artikelBestand) {
				if (artikel instanceof Artikel)
					pm.speichereArtikel((Artikel)artikel);				
			}
		}
		pm.close();
	}
	
	public void ladeDatenArtikel() throws FileNotFoundException, IOException, ClassNotFoundException{
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
			
	}
	
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
