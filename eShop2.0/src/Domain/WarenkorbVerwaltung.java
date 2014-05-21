package Domain;

import java.util.HashMap;

import Valueobjects.Artikel;
import Valueobjects.Kunde;
import Valueobjects.MehrfachArtikel;
import Valueobjects.User;
import exceptions.ArtikelNurInEinheitenVerfuegbarException;
import exceptions.NichtGenugAufLagerException;

public class WarenkorbVerwaltung {
	
	public Kunde artikelInWarenkorb(Artikel einArtikel, int menge, Kunde k) throws NichtGenugAufLagerException, ArtikelNurInEinheitenVerfuegbarException{		
		if(menge<=einArtikel.getArtikelBestand()){
			if (einArtikel instanceof MehrfachArtikel) {
				MehrfachArtikel b = (MehrfachArtikel) einArtikel;
				int packungsGroesse = b.getPackungsgroesse();
				if (menge % packungsGroesse == 0) {
					k.getWarenkorb().artikelHinzufuegen(einArtikel, menge, einArtikel.getArtikelBestand());
					return k;
				} else {
					ArtikelNurInEinheitenVerfuegbarException x = new ArtikelNurInEinheitenVerfuegbarException(packungsGroesse);
					throw x;
				}
			} else {
				k.getWarenkorb().artikelHinzufuegen(einArtikel, menge, einArtikel.getArtikelBestand());
				return k;
			}
		} else { // gewollte Menge ist gr��er als die vorhandene Menge
			//exception NichtGenugAufLagerException
			NichtGenugAufLagerException e = new NichtGenugAufLagerException(einArtikel, menge);
			throw e;
		}
		//return k;
	}

	public Kunde artikelAusWarenkorb(Artikel artikel, Kunde user){		
		user.getWarenkorb().artikelEntfernen(artikel);
		return user;
	}
	
	public HashMap<Artikel, Integer> setArtikelMenge(Artikel a, int anzahl, Kunde user) {
		HashMap<Artikel, Integer> w = user.getWarenkorb().getInhalt();
		int alteMenge = w.remove(a);
		w.put(a, alteMenge+anzahl);
		return w;
	}
	
	public void getWarenkorbInhalt(User user){
		HashMap<Artikel, Integer> warenkorb = ((Kunde) user).getWarenkorb().getInhalt();
		//wenn warenkorb leer ist
		if(warenkorb.isEmpty()){
			System.out.println("Warenkorb ist leer.");
		}
		else{
		//die einzelnen Elemente aus der HashMap durchgehen und ausgeben
			for(Artikel key : warenkorb.keySet())
		    {
		      System.out.print(key.getArtikelNummer() + " | " + key.getArtikelName() + " | " + key.getPreis() + " | Anzahl: " + warenkorb.get(key) + " | Gesamtpreis: " + key.getPreis()*warenkorb.get(key) + "\n");
		    }
		}
	}
	
}
