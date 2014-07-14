package Valueobjects;

import java.util.Date;
import java.util.HashMap;

/**
 * Klasse zur Repräsentation einer Rechnung.
 * @author Imke Schneider
 *
 */

public class Rechnung {

	private Kunde kunde;
	private Warenkorb warenkorb;
	private Date datum;
	private double gesamtpreis;
	
	/**
	 * Initalisiert ein Rechnungsobjekt.
	 * @param kunde Kunde, der einkauft.
	 * @param warenkorb Warenkorb der zum Kauf bestätigt wurde.
	 * @param datum Datum, an dem der Kauf stattfindet.
	 */
	public Rechnung(Kunde kunde, Warenkorb warenkorb, Date datum) {
		this.kunde = kunde;
		this.datum = datum;
		System.out.println("Rechnung: \n" +
				"Kunde: " + kunde.getVorName() + kunde.getNachName() +
				" | Datum: " + datum);
		this.warenkorb = warenkorb;
		HashMap<Artikel, Integer> wkorb = warenkorb.getInhalt();
		double gesamtpreis = 0;
		for (Artikel a : wkorb.keySet()) {
			System.out.println(a.getArtikelName() + " | Anzahl: " + wkorb.get(a) + " | Einzelpreis: " + a.getPreis() + " | Gesamtpreis: " + wkorb.get(a)*a.getPreis());
			gesamtpreis = gesamtpreis + wkorb.get(a)*a.getPreis();			
		}
		System.out.println("Gesamtpreis: " + gesamtpreis);
		this.gesamtpreis = gesamtpreis;
	}
	
	public String getAnrede() {		
		return kunde.getAnrede();
	}
	public String getVorName() {		
		return kunde.getVorName();
	}
	public String getNachName() {		
		return kunde.getNachName();
	}
	public String getAdresse() {		
		return kunde.getAdresse();
	}
	public Date getDatum() {
		return this.datum;
	}
	public double getGesamtbetrag() {
		return this.gesamtpreis;
	}
	public Warenkorb getWarenkorb(){
		return this.warenkorb;
	}
	
}
