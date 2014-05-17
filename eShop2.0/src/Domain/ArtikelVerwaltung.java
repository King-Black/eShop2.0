package Domain;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import Valueobjects.Artikel;

public class ArtikelVerwaltung {
	
	private List<Artikel> artikelBestand = new Vector<Artikel>();
	private int laufnr = 0;

	private int bestimmeNr() {
		return ++laufnr;
	}
	
	public Artikel einfuegen(String ArtikelName, int ArtikelBestand, String Beschreibung, double Preis){ 
		if(Preis<=0||ArtikelBestand<=0) {
			//exception
		} else {
			int ArtikelNummer = bestimmeNr();
			Artikel einArtikel = new Artikel(ArtikelNummer, ArtikelName, ArtikelBestand, Beschreibung, Preis);
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
	
}
