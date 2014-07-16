package Domain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import persistence.FilePersistenceManager;
import persistence.PersistenceManager;
import Valueobjects.Artikel;
import Valueobjects.Ereignis;
import Valueobjects.Kunde;
import Valueobjects.Mitarbeiter;
import Valueobjects.User;

/**
 * Ereignisverwaltung
 * Verwaltungsklasse, die alle Ereignismethoden verwaltet.
 */
public class EreignisVerwaltung {
	
	private List<Ereignis> protokoll = new Vector<Ereignis>();
	private PersistenceManager pm = new FilePersistenceManager();

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
	 * Methode schreibt alle Ereignisse in einer Textdatei.
	 * @throws FileNotFoundException wird geworfen, wenn Datei nicht gefunden wurde.
	 * @throws IOException wenn es einen fehler beim lesen gab.
	 * @throws ClassNotFoundException
	 */
	
	/*public void schreibeDatenEreignis() throws FileNotFoundException, IOException{
		pm.openForWriting("Ereignis.txt");
		if (!protokoll.isEmpty()) {
			for (Ereignis er : protokoll){
				if (er instanceof Ereignis)
				pm.speichereEreignis((Ereignis)er);				
			}
		}
		pm.close();
	}*/
	
	/**
	 * Methode läd alle Mitarbeiter aus einer Textdatei.
	 * @throws FileNotFoundException wird geworfen, wenn Datei nicht gefunden wurde.
	 * @throws IOException wenn es einen fehler beim lesen gab.
	 * @throws ClassNotFoundException
	 */
	/*public void ladeDatenEreignis() throws FileNotFoundException, IOException{
		pm.openForReading("Ereignis.txt");
		Ereignis er;
		do {
			er = pm.ladeEreignis();
			if (er != null) {
				protokoll.add(er);
			}				
		} while (er != null);	
		pm.close();
	}*/
	
}
