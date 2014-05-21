package Domain;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import Valueobjects.Artikel;
import Valueobjects.Ereignis;
import Valueobjects.User;

public class EreignisVerwaltung {
	
	private List<Ereignis> protokoll = new Vector<Ereignis>();

	public void ereignisEinfuegen(User akteur, Artikel derWars, int artikelBestand, String aktion) {
		Ereignis ereignis = new Ereignis(akteur, derWars, artikelBestand, aktion);
		protokoll.add(ereignis);
	}

	public void gibProtokollAus() {
		if(protokoll.isEmpty()) {
			System.out.println("Liste ist leer.");
		} else {
			Iterator<Ereignis> it = protokoll.iterator();
			while (it.hasNext()) {
				Ereignis ereignis = it.next();
				System.out.println(ereignis.toString());
			}			
		}
		System.out.println(" ");
	}
	
	public List<Ereignis> gibEreignisseNachArtikelUndTagen(Artikel a) {
		// anzahl tage wird nicht benutzt
			List<Ereignis> liste = new Vector<Ereignis>();

			Calendar heute = Calendar.getInstance();
			Calendar ereignis = new GregorianCalendar();		
			
			// hier werden alle ereignisse aus protokoll durchgegangen
			for(Ereignis e:protokoll) {
				ereignis.setTime(e.getDate());                      // zweiter Zeitpunkt
				long zeitVergangen = ereignis.getTime().getTime() - heute.getTime().getTime();  // Differenz in ms
				long inTagen = Math.round( (double)zeitVergangen / (24. * 60.*60.*1000.) ); // Zeit Differenz in Tagen
				// wenn ereignis in dem zeitraum liegt, in die liste
				// bedingung bewirkt das nichts, das laenger als 30 tage zurueck liegt in die liste gepackt wird
				if(inTagen>30){
					return liste;
				} else {
					if(e.getArtikel().getArtikelNummer()==a.getArtikelNummer()) {
						liste.add(e);
					} 
				}
			}	
			return liste;
				
		
	}
	
	public List<Ereignis> gibProtokollListe() {
		return protokoll;
	}


}
