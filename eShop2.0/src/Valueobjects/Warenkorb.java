package Valueobjects;

import java.util.Collection;
import java.util.HashMap;

import exceptions.NichtGenugAufLagerException;
import exceptions.WarenkorbLeerException;

public class Warenkorb {

	private HashMap<Artikel, Integer> warenkorb = new HashMap<Artikel, Integer>();
	
	public void artikelHinzufuegen(Artikel a, int gewuenschteMenge, int mengeNochDa) throws NichtGenugAufLagerException{
		// Wenn Artikel schon im Warenkorb vorhanden dann Menge erweitern
		if (warenkorb.containsKey(a)) { // WArenkorb hat hier nichtr den gew�nschten artikel drin
			int alteMenge = warenkorb.get(a);
			if (alteMenge+gewuenschteMenge<=mengeNochDa) {
				System.out.println("Artikel im Warenkorb");
				warenkorb.put(a, alteMenge + gewuenschteMenge);	
			} else{
				//exception artikelbestand reicht nicht
				NichtGenugAufLagerException e = new NichtGenugAufLagerException(a);
				throw e;
			}
		} else {
		// Ansonsten neu in Liste hinzufuegen
			warenkorb.put(a, gewuenschteMenge);
		}
	}	
	//Artikel aus Warenkorb entfernen
	public void artikelEntfernen(Artikel a) throws WarenkorbLeerException{
		if(warenkorb.containsKey(a)) {
			warenkorb.remove(a);
		} else{
			//exception warenkorb ist leer
			WarenkorbLeerException e = new WarenkorbLeerException();
			throw e;
		}
	}
	//Warenkorb leeren
	public void leeren() throws WarenkorbLeerException{
		if(!warenkorb.isEmpty()){
			warenkorb.clear();
		} else {
			//exception warenkorb ist leer
			WarenkorbLeerException e = new WarenkorbLeerException();
			throw e;
		}
	}
	
	public HashMap<Artikel, Integer> getInhalt(){
		return warenkorb;
	}
	
	public void setWarenkorb(HashMap<Artikel, Integer> w) {
		warenkorb = w;
	}

	public Collection<Position> getPositionen() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getAnzahlPositionen() {
		//TODO Positionen z�hlen
		return -1;
	}
	
}
