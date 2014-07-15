package Domain;

import java.util.HashMap;

import Valueobjects.Artikel;
import Valueobjects.Kunde;
import Valueobjects.MehrfachArtikel;
import Valueobjects.User;
import exceptions.ArtikelNurInEinheitenVerfuegbarException;
import exceptions.NichtGenugAufLagerException;
import exceptions.WarenkorbLeerException;

public class WarenkorbVerwaltung {
	
/**
 * Diese Methode gibt die Artikel im Warkenkorb wieder die der Kunde in den Warenkorb gelegt hat.
 * @param einArtikel Der Artikel der in den WK gelegt wurde.
 * @param menge Menge des Artikels der in den WK gelegt wurde.
 * @param k Der zur zeit eingeloggte Kunde der etwas in den WK legt.
 * @return Gibt eingeloggten Kunden zurück.
 * @throws NichtGenugAufLagerException wird geworfen, wenn nicht genügend Artikel des ausgeählten Produkts auf Lager sind.
 * @throws ArtikelNurInEinheitenVerfuegbarException wird geworfen, wenn es sich um einen Artikel mit bestimmter Packungsgröße handelt.
 */
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
		} else { 
			NichtGenugAufLagerException e = new NichtGenugAufLagerException(einArtikel);
			throw e;
		}
	}
	
/**
 * Die Methode wird aufgerufen, wenn ein Kunde einen Artikel aus dem Warenkorb entfernt.
 * @param artikel Artikel der aus dem Warenkorb gelöscht wird.
 * @param user Der eingeloggte User.
 * @return Eingeloggter User.
 * @throws WarenkorbLeerException wird geworfen, wenn der Warenkorb schon leer ist.
 */
	public Kunde artikelAusWarenkorb(Artikel artikel, Kunde user) throws WarenkorbLeerException{		
		user.getWarenkorb().artikelEntfernen(artikel);
		return user;
	}
	
	/**
	 * Methode ändert Menge eines Artikels im WK.
	 * @param a Der Artikel der geändert wird.
	 * @param anzahl Menge die zur alten Menge addier wird.
	 * @param user User dem der WK gehört.
	 * @return Gibt neuen WK Bestand wieder.
	 */
	public HashMap<Artikel, Integer> setArtikelMenge(Artikel a, int anzahl, Kunde user) {
		HashMap<Artikel, Integer> w = user.getWarenkorb().getInhalt();
		int alteMenge = w.remove(a);
		w.put(a, alteMenge+anzahl);
		return w;
	}
	
	/**
	 * Diese Methode gibt in einer Hashmap den Inahlt des Warenkorbs wieder.
	 * @param user User dem der WK gehört.
	 */
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
