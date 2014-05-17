package Domain;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import Valueobjects.Artikel;
import Valueobjects.MehrfachArtikel;

public class ArtikelVerwaltung {
	
	private List<Artikel> artikelBestand = new Vector<Artikel>();
	private int laufnr = 0;

	private int bestimmeNr() {
		return ++laufnr;
	}
	
	public Artikel einfuegen(String artikelName, int menge, String beschreibung, double preis){ 
		if(preis<=0||menge<=0) {
			//exception
		} else {
			int artikelNummer = bestimmeNr();
			Artikel einArtikel = new Artikel(artikelNummer, artikelName, menge, beschreibung, preis);
			artikelBestand.add(einArtikel);
			return einArtikel;
		}
		return null;
	}
	
	public MehrfachArtikel einfuegen(String artikelName, int menge, String beschreibung, double preis, int packungsGroesse){ 
		if(preis<=0||menge<=0) {
			//exception
		} else {
			int artikelNummer = bestimmeNr();
			MehrfachArtikel einArtikel = new MehrfachArtikel(artikelNummer, artikelName, menge, beschreibung, preis, packungsGroesse);
			artikelBestand.add(einArtikel);
			return einArtikel;
		}
		return null;
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
	
	public void loescheArtikel(Artikel a){
		if (artikelBestand.contains(a)) {
			artikelBestand.remove(a);
		} else {
			//exception
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
	
	public Artikel findArtikelByNumber(int artID){
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
				//exception 
			}
		}
		return null;
	}
	
}
