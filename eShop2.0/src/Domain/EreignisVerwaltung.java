package Domain;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import Valueobjects.Artikel;
import Valueobjects.Ereignis;
import Valueobjects.User;

/**
 * Ereignisverwaltung
 * Verwaltungsklasse, die alle Ereignismethoden verwaltet.
 */
public class EreignisVerwaltung {
	
	private List<Ereignis> protokoll = new Vector<Ereignis>();

	/**
	 * Methode, die ein Ereignis protokolliert.
	 * @param derWars Artikel der bearbeitet wurde.
	 * @param artikelBestand Gibt Anzahl an die ein-/ausgelagert wurde. Bzw wie viel von einem Artikel vorhanden ist.
	 * @param aktion Aktion die Ausgeführt wird. (Ein-/Auslagern).
	 * @param nutzer Nutzer, der das Lagerereignis ausführt.
	 */
	public void ereignisEinfuegen(Date datum, Artikel derWars, int artikelBestand, String aktion, User nutzer) {
		Ereignis ereignis = new Ereignis(datum, derWars, artikelBestand, aktion, nutzer);
		protokoll.add(ereignis);
	}

	/**
	 * Methode, die die Liste aller Ereignisse ausgibt.
	 */
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
	
	/**
	 * Methode, die die Liste sortiert nach Artikeln und Tagen.
	 * @param a Artikel die sortiert werden.
	 * @return sortierte liste
	 */
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
	
	/**
	 * Methode, die die Protokollliste übergibt.
	 * @return Protokollliste.
	 */
	public List<Ereignis> gibProtokollListe() {
		return protokoll;
	}
	
	/**
	 * Methode läd alle Ereignisse aus einer Textdatei.
	 * @throws FileNotFoundException wird geworfen, wenn Datei nicht gefunden wurde.
	 * @throws IOException wenn es einen fehler beim lesen gab.
	 * @throws ClassNotFoundException
	 */
	/*public void ladeDaten() throws FileNotFoundException, IOException, ClassNotFoundException{
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
	
	/**
	 * Methode speichert alle Ereignisse in einer Textdatei.
	 * @throws FileNotFoundException wird geworfen, wenn Datei nicht gefunden wurde.
	 * @throws IOException wenn es einen fehler beim schreiben gab.
	 
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
	}*/
	
}
