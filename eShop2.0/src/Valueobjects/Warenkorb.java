package Valueobjects;

import java.util.Collection;
import java.util.HashMap;

import exceptions.ArtikelNichtGefundenException;
import exceptions.WarenkorbLeerException;

public class Warenkorb {

	private HashMap<Artikel, Integer> warenkorb = new HashMap<Artikel, Integer>();
	
	public void artikelHinzufuegen(Artikel a, int gewuenschteMenge, int mengeNochDa){
		// Wenn Artikel schon im Warenkorb vorhanden dann Menge erweitern
		if (warenkorb.containsKey(a)) { // WArenkorb hat hier nichtr den gew�nschten artikel drin
			int alteMenge = warenkorb.get(a);
			if (alteMenge+gewuenschteMenge<=mengeNochDa) {
				System.out.println("Artikel im Warenkorb");
				warenkorb.put(a, alteMenge + gewuenschteMenge);	
			} else{
				//exception artikelbestand reicht nicht
			}
		} else {
		// Ansonsten neu in Liste hinzuf�gen
			warenkorb.put(a, gewuenschteMenge);
		}
	}	
	
	public void artikelEntfernen(Artikel a) throws WarenkorbLeerException{
		if(warenkorb.containsKey(a)) {
			warenkorb.remove(a);
		} else{
			//exception warenkorb ist leer
			WarenkorbLeerException e = new WarenkorbLeerException();
			throw e;
		}
	}
	
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
	
}
