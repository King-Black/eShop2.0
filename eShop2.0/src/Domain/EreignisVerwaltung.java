package Domain;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import Valueobjects.Artikel;
import Valueobjects.Ereignis;

public class EreignisVerwaltung {
	
	private List<Ereignis> protokoll = new Vector<Ereignis>();

	public void ereignisEinfuegen(Artikel derWars, int artikelBestand, String aktion) {
		Ereignis ereignis = new Ereignis(derWars, artikelBestand, aktion);
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

	public void ladeDaten() throws FileNotFoundException, IOException, ClassNotFoundException{
		int count = 0;
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("Ereignisse.txt"));
		protokoll.clear();
		try {  
			Ereignis er = null;
			for(;;) {
				er = (Ereignis) in.readObject();
				count++;
				protokoll.add(er);
			}
		} catch (EOFException e) { // wg. readObject
			System.out.println("Es wurden " + count + " Ereignisse geladen.");
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) { // wg readObject
			System.out.println(e);
		} finally {
			try {
				if (in!=null) {
					in.close();
				} 
			} 
			catch (IOException e) {
					e.printStackTrace();
			}
		}
	}
	
	public void schreibeDaten() throws FileNotFoundException, IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Ereignisse.txt")); 
		// hier schleife in der dir jeweiligen objekte (artikel, user, ereignisse durchgegangen werden
		
		Iterator<Ereignis> it = protokoll.iterator();
		// Artikel erstellen
		Ereignis er = null;
		// Ereignisse durchlaufen
		int count = 0;
		while (it.hasNext()) {
			er = it.next();
			// Ereignis in Datei speichern
			out.writeObject(er);
			count ++;
		}
		System.out.println(count + " Ereignisse gespeichert.");
		// muss aufgerufen werden, bevor der datenstrom zur eingabe verwendet werden soll
		out.close();
	}
	
}
