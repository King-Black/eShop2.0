package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Valueobjects.Kunde;
import Valueobjects.Mitarbeiter;

/**
 * Klasse zur Erstellung des VerwaltungsPanels.
 *
 */
@SuppressWarnings("serial")
public class VerwaltungsPanel extends JPanel {
	private JPanel verwaltungsPanel;
	private JButton einloggenButton;
	private JButton ausloggenButton;
	private HauptFenster hauptFenster;
	private WarenkorbPanel warenkorbPanel;
	private ArtikelPanel artikelPanel;
	private LagerEreignisPanel lagerPanel;
	private JButton registrierenButton;
	private JButton adresseAendernButton;
	private JButton mitarbeiterAnlegenButton;
	
	/**
	 * Initialisiert ein VerwaltunsPanel-Objekt.
	 * @param hf Das Hauptfenster der GUI.
	 * @param wp Das WarenkorbPanel der GUI.
	 * @param ap Das ArtikelPanel der GUI.
	 * @param lep Das LagerEreignisPanel der GUI.
	 */
	public VerwaltungsPanel(HauptFenster hf, WarenkorbPanel wp, ArtikelPanel ap, LagerEreignisPanel lep){
		super();
		
		this.hauptFenster = hf;
		this.warenkorbPanel = wp;
		this.artikelPanel = ap;
		this.lagerPanel = lep;
		
		this.initialisieren();
	}
	
	/**
	 * Die Methode initialisiert die Komponenten des VerwaltungsPanel.
	 */
	private void initialisieren(){
		//Komponenten des VerwaltungsPanel initialisieren:
		verwaltungsPanel = new JPanel();
		einloggenButton = new JButton("Einloggen");
		ausloggenButton = new JButton("Ausloggen");
		registrierenButton = new JButton("Registrieren");
		adresseAendernButton = new JButton("Adresse ändern");
		mitarbeiterAnlegenButton = new JButton("Mitarbeiter anlegen");
		
	    //ActionListener der Buttons hinzufügen und Sichtbarkeit setzen:
		einloggenButton.addActionListener(this.einloggen());
		einloggenButton.setVisible(true);

		ausloggenButton.addActionListener(this.ausloggen());
		ausloggenButton.setVisible(false);
		
		registrierenButton.addActionListener(this.registrieren());
		registrierenButton.setVisible(true);
		
		//adresseAendernButton.addActionListener(this.adresseAendern());
		adresseAendernButton.setVisible(false);
		
		mitarbeiterAnlegenButton.addActionListener(this.mitarbeiterAnlegen());
		mitarbeiterAnlegenButton.setVisible(false);
		
		//fügt die Buttons zum VerwaltungsPanel hinzu:
		verwaltungsPanel.add(ausloggenButton);
		verwaltungsPanel.add(einloggenButton);
		verwaltungsPanel.add(registrierenButton);
		verwaltungsPanel.add(adresseAendernButton);
		verwaltungsPanel.add(mitarbeiterAnlegenButton);
		
		//fügt das VerwaltungsPanelPanel in das Layout ein:
		this.add(verwaltungsPanel, BorderLayout.NORTH);
	}
	
	/**
	 * Die Methode implementiert den ActionListener für den Einloggen Button und gibt diesen zurück.
	 * @return Gibt den ActionListener für den Einloggen Button zurück.
	 */
	private ActionListener einloggen(){
		ActionListener einloggen = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//erstellt ein neues Einloggen Fenster:
				EinloggenFenster login = new EinloggenFenster(hauptFenster);
				login.setLocationRelativeTo(hauptFenster);
				login.setVisible(true);
				//bei erfolgreichem Login ausführen:
				if(login.getErfolg()) {
					hauptFenster.setTitle("EShop - " + HauptFenster.benutzer.getVorName() +
							" " + HauptFenster.benutzer.getNachName());
					einloggenButton.setVisible(false);
					ausloggenButton.setVisible(true);
					registrierenButton.setVisible(false);
					//falls der eingeloggte Benutzer ein Kunde ist:
					if(HauptFenster.benutzer != null && HauptFenster.benutzer instanceof Kunde){
						warenkorbPanel = new WarenkorbPanel(VerwaltungsPanel.this.hauptFenster);
						hauptFenster.tabsPanel.addTab("Warenkorb", warenkorbPanel);
						adresseAendernButton.setVisible(true);
						hauptFenster.pack();
					}
					//falls der eingeloggte Benutzer ein Mitarbeiter ist:
					if(HauptFenster.benutzer != null && HauptFenster.benutzer instanceof Mitarbeiter){
						artikelPanel.inDenWarenkorbButton.setVisible(false);
						artikelPanel.add(artikelPanel.getSouthPanel(), BorderLayout.SOUTH);
						mitarbeiterAnlegenButton.setVisible(true);
						hauptFenster.tabsPanel.addTab("Lagerereignisse", lagerPanel);
						hauptFenster.pack();
					}
				}else{
					einloggenButton.setVisible(true);
				}
	        }
	    };
	    return einloggen;
	}
	
	/**
	 * Die Methode implementiert den ActionListener für den Ausloggen Button und gibt diesen zurück.
	 * @return Gibt den ActionListener für den Ausloggen Button zurück.
	 */
	private ActionListener ausloggen(){
		ActionListener ausloggen = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//versetzt das VerwaltungsPanel wieder in den Ursprungszustand:
				JOptionPane logout = new JOptionPane();
				JOptionPane.showMessageDialog(hauptFenster, "Sie haben sich erfolgreich abgemeldet!",
						"Logout", JOptionPane.INFORMATION_MESSAGE);
				logout.setVisible(true);
				ausloggenButton.setVisible(false);
				einloggenButton.setVisible(true);
				registrierenButton.setVisible(true);
				hauptFenster.setTitle("EShop");
				hauptFenster.tabsPanel.remove(1);
				//falls der noch(!) eingeloggte Benutzer ein Mitarbeiter ist:
				if(HauptFenster.benutzer != null && HauptFenster.benutzer instanceof Mitarbeiter){
					artikelPanel.remove(2);
					mitarbeiterAnlegenButton.setVisible(false);
				}
				//falls der noch(!) eingeloggte Benutzer ein Kunde ist:
				if(HauptFenster.benutzer != null && HauptFenster.benutzer instanceof Kunde){
					adresseAendernButton.setVisible(false);
				}
				hauptFenster.pack();
				//HauptFenster.shopVerwaltung.ausloggen(HauptFenster.benutzer);
				HauptFenster.benutzer = null;
				}
		};
		return ausloggen;
	}

	/**
	 * Die Methode implementiert den ActionListener für den Registrieren Button und gibt diesen zurück.
	 * @return Gibt den ActionListener für den RegistrierenButton zurück.
	 */
	private ActionListener registrieren(){
		ActionListener registrieren = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//erzeugt ein neues RegistrierenFenster und übergibt diesem die Aktion:
				RegistrierenFenster registrierenFenster = new RegistrierenFenster(hauptFenster, 0);
				registrierenFenster.add(new JLabel("Nur Kunden können sich selbst registrieren!"), BorderLayout.NORTH);
				
				//stellt die benötigten Komponenten des RegistrierenFensters zusammen:
				JPanel panel = new JPanel();
				panel.setLayout(new GridLayout(2, 1, 6, 3));
				panel.add(registrierenFenster.getRegistrierenPanel());
				panel.add(registrierenFenster.getAdressPanel());
				registrierenFenster.add(panel, BorderLayout.CENTER);
				registrierenFenster.pack();
				registrierenFenster.setVisible(true);
				registrierenFenster.setLocationRelativeTo(hauptFenster);
			}
		};
		return registrieren;
	}
	
	/**
	 * Die Methode implementiert den ActionListener für den Adresse ändern Button und gibt diesen zurück.
	 * @return Gibt den ActionListener für den Adresse ändern Button zurück.
	 */
	/*private ActionListener adresseAendern() {
		ActionListener adresseAendern = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//erzeugt ein neues RegistrierenFenster mit der übergebenen Aktion 1:
				RegistrierenFenster registrierenFenster = new RegistrierenFenster(hauptFenster, 1);
				//prüft, ob der eingeloggte Benutzer ein Kunde ist und stellt die Komponenten
				//des RegistrierenFensters zusammen:
				if(HauptFenster.benutzer != null && HauptFenster.benutzer instanceof Kunde){
					Kunde k = (Kunde)HauptFenster.benutzer;
					registrierenFenster.setTitle("Adresse ändern");
					registrierenFenster.add(registrierenFenster.getAktuelleAdressePanel(), BorderLayout.NORTH);
					registrierenFenster.aktuelleAdresseText.setText(k.getAdresse());
					registrierenFenster.add(registrierenFenster.getAdressPanel(), BorderLayout.CENTER);
				}
				registrierenFenster.pack();
				registrierenFenster.setVisible(true);
				registrierenFenster.setLocationRelativeTo(hauptFenster);
			}
		};
		return adresseAendern;
	}*/
	
	/**
	 * Die Methode implementiert den ActionListener für den Mitarbeiter anlegen Button und gibt diesen zurück.
	 * @return Gibt den ActionListener für den Mitarbeiter anlegen Button zurück.
	 */
	private ActionListener mitarbeiterAnlegen() {
		ActionListener mitarbeiterAnlegen = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//erzeugt ein neues RegistrierenFenster mit der übergebenen Aktion 2:
				RegistrierenFenster registrierenFenster = new RegistrierenFenster(hauptFenster, 2);
				//prüft, ob der eingeloggte Benutzer ein Mitarbeiter ist und stellt die Komponenten
				//des RegistrierenFensters zusammen:
				if(HauptFenster.benutzer != null && HauptFenster.benutzer instanceof Mitarbeiter){
					registrierenFenster.setTitle("Mitarbeiter anlegen");
					registrierenFenster.add(registrierenFenster.getRegistrierenPanel());
				}
				registrierenFenster.pack();
				registrierenFenster.setVisible(true);
				registrierenFenster.setLocationRelativeTo(hauptFenster);
			}
		};
		return mitarbeiterAnlegen;
	}
}
