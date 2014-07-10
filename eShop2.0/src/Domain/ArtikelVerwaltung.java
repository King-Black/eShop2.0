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
			ladeDaten();
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
	
	public void schreibeDaten() throws FileNotFoundException, IOException {		
		pm.openForWriting("Artikel.txt");
		for (Artikel a : this.artikelBestand){
			pm.speichereArtikel(a);
		}
		pm.close();
//		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Artikel.txt")); 
//		// hier schleife in der dir jeweiligen objekte (artikel, user, ereignisse durchgegangen werden
//		for (Artikel a : artikelBestand){
//			
//		}
//		
//		Iterator<Artikel> it = artikelBestand.iterator();
//		// Artikel erstellen
//		Artikel artikel = null;
//		// Artikelverzeichnis durchlaufen
//		int count = 0;
//		while (it.hasNext()) {
//			artikel = it.next();
//			// artikel in Datei speichern
//			out.writeObject(artikel);
//			count ++;
//		}
//		System.out.println(count + " Artikel gespeichert.");
//		// muss aufgerufen werden, bevor der datenstrom zur eingabe verwendet werden soll
//		out.close();
	}
	
	public void ladeDaten() throws FileNotFoundException, IOException, ClassNotFoundException{
		pm.openForReading("Artikel.txt");
		Artikel a;
		do {
			a = pm.ladeArtikel();
			if (a != null) {
				artikelBestand.add(a);
			}				
		} while (a != null);	
		pm.close();
	
//		int count = 0;
//		ObjectInputStream in = new ObjectInputStream(new FileInputStream("Artikel.txt"));
//		artikelBestand.clear();
//		try {  
//			Artikel a = null;
//			for(;;) {
//				a = (Artikel) in.readObject();
//				count++;
//				artikelBestand.add(a);
//				if (a.getArtikelNummer() > this.laufnr)
//					this.laufnr = a.getArtikelNummer();
//			}
//		} catch (EOFException e) { // wg. readObject
//			System.out.println("Es wurden " + count + " Artikel geladen.");
//		} catch (IOException e) {
//			System.out.println(e);
//		} catch (ClassNotFoundException e) { // wg readObject
//			System.out.println(e);
//		} finally {
//			try {
//				if (in!=null) {
//					in.close();
//				} 
//			} catch (IOException e) {
//					e.printStackTrace();
//				}
//		}
	}
	
}
