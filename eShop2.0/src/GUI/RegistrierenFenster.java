package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * Klasse zur Erstellung des RegistrierenFensters.
 *
 */
@SuppressWarnings("serial")
public class RegistrierenFenster extends JDialog {
	private JPanel registrierenPanel;
	private JButton okButton;
	private JButton abbrechenButton;
	private JTextField vorNameText;
	private JTextField nachNameText;
	private JTextField kennwortText;
	private JPanel adressPanel;
	private JTextField strasseText;
	private JTextField plzText;
	private JTextField stadtText;
	private JPanel buttonPanel;
	private JPanel aktuelleAdressePanel;
	JTextArea aktuelleAdresseText;
	private int aktion;
	
	/**
	 * Initialisiert ein RegistrierenFenster-Objekt, welches je nach gewählter Aktion sein Aussehen ändert
	 * und standardmäßig nur Ok- und Abbrechen-Button beinhaltet.
	 * @param owner Das Hauptfenster des RegistrierenFensters.
	 * @param aktion Die Aktion, die ausgeführt werden soll. 0 für Kunden registrieren, 1 für Adresse ändern
	 * und 2 für Mitarbeiter registrieren.
	 */
	public RegistrierenFenster(Window owner, int aktion){
		super(owner, "Registrieren");
		this.aktion = aktion;
		
		this.setLayout(new BorderLayout());
	
		this.buttonPanelErzeugen();
		this.setResizable(false);
		this.pack();
	}
	
	/**
	 * Die Methode erzeugt das RegistrierenPanel, welches Vor- und Nachname und das neue Passwort einliest
	 * und gibt dieses zurück.
	 * @return Gibt das RegistrierenPanel zurück.
	 */
	private JPanel registrierenPanelErzeugen(){
		//initialisiert alle Komponenten des RegistrierenPanel und setzt das Layout:
		registrierenPanel = new JPanel();
		registrierenPanel.setLayout(new GridLayout(3, 2, 6, 3));
		vorNameText = new JTextField();
		nachNameText = new JTextField();
		kennwortText = new JTextField();
		
		//fügt alle Komponenten in das Layout ein:
	    registrierenPanel.add(new JLabel("Vorname:"));
	    registrierenPanel.add(vorNameText);
	    registrierenPanel.add(new JLabel("Nachname:"));
	    registrierenPanel.add(nachNameText);
	    registrierenPanel.add(new JLabel("Passwort:"));
	    registrierenPanel.add(kennwortText);
	    
	    return registrierenPanel;
	}
	
	/**
	 * Die Methode erzeugt das AdressPanel, welches Straße, Postleitzahl und Stadt einliest
	 * und das Panel zurückgibt.
	 * @return Gibt das AdressPanel zurück.
	 */
	private JPanel adressPanelErzeugen(){
		//initialisiert alle Komponenten des AdressPanels und setzt das Layout:
		adressPanel = new JPanel();
		adressPanel.setLayout(new GridLayout(3, 2, 6, 3));
		strasseText = new JTextField();
		plzText = new JTextField();
		stadtText = new JTextField();
		
		//fügt alle Komponenten in das Layout ein:
		adressPanel.add(new JLabel("Straße und Hausnummer:"));
		adressPanel.add(strasseText);
		adressPanel.add(new JLabel("Postleitzahl:"));
		adressPanel.add(plzText);
		adressPanel.add(new JLabel("Stadt:"));
		adressPanel.add(stadtText);
		
		return adressPanel;
	}
	
	/**
	 * Die Methode erzeugt das Panel mit der aktuellen Adresse, welche bei der Adressänderung ausgegeben wird
	 * und gibt das Panel zurück.
	 * @return Gibt das aktuelleAdressePanel zurück.
	 */
	private JPanel aktuelleAdressePanelErzeugen(){
		//initialisiert alle Komponenten des Panels:
		aktuelleAdressePanel = new JPanel();
		aktuelleAdresseText = new JTextArea();
		
		//definiert das Textfeld genau:
		TitledBorder tBorderBeschreibung = BorderFactory.createTitledBorder("Aktuelle Adresse");
		aktuelleAdresseText.setBorder(tBorderBeschreibung);
		aktuelleAdresseText.setEditable(false);
		aktuelleAdresseText.setWrapStyleWord(true);
		aktuelleAdresseText.setLineWrap(true);
		aktuelleAdresseText.setOpaque(false);
		aktuelleAdresseText.setFont(new JLabel().getFont());
		
		//fügt das Textfeld in das Panel ein:
		aktuelleAdressePanel.add(aktuelleAdresseText);
		
		return aktuelleAdressePanel;
	}
	
	/**
	 * Die Methode erzeugt das ButtonPanel.
	 */
	private void buttonPanelErzeugen(){
		//initialisiert alle Komponenten des ButtonPanel:
		buttonPanel = new JPanel();
		okButton = new JButton("Ok");
		abbrechenButton = new JButton("Abbrechen");
		
		//implementiert den ActionListener für den Abbrechen Button:
		ActionListener abort = new ActionListener(){
	
			public void actionPerformed(ActionEvent e){
				dispose();
	        }
	    };
	    //fügt die ActionListener zu den Buttons hinzu:
	    abbrechenButton.addActionListener(abort);
	    okButton.addActionListener(this.okListener());
		
	    //fügt die Buttons in das Panel ein:
		buttonPanel.add(okButton);
		buttonPanel.add(abbrechenButton);
		
		//fügt das Panel in das Layout ein:
		this.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Die Methode implementiert den ActionListener für den Ok-Button und gibt diesen zurück.
	 * Die Funktionalität ändert sich je nach der im Konstruktor übergebenen Aktion.
	 * @return Gibt den ActionListener für den Ok-Button zurück.
	 */
	private ActionListener okListener(){
		ActionListener ok = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {	
				//ändert die Funktionalität des Ok-Buttons je nach Aktion:
				switch(aktion){
					case 0:
						registrieren();
						break;
					case 1:
						//adresseAendern();
						break;
					case 2:
						mitarbeiterAnlegen();
						break;
				}
			}
		};
		return ok;
	}
	
	/**
	 * Die Methode implementiert das Verhalten des Ok-Buttons für die Aktion 0.
	 */
	private void registrieren(){
		//Deklariert und initialisiert alle einzulesenden Werte:
		String vorName = vorNameText.getText();
		String nachName = nachNameText.getText();
		String kennwort = kennwortText.getText();
		String strasse = strasseText.getText();
		int plz = -1;
		String stadt = stadtText.getText();
		
		try{
			plz = Integer.parseInt(plzText.getText());
		}catch(NumberFormatException e){
			JOptionPane dialog = new JOptionPane();
			JOptionPane.showMessageDialog(RegistrierenFenster.this, "Sie müssen eine Menge angeben.", "Error", JOptionPane.ERROR_MESSAGE);
			dialog.setVisible(true);
			return;
		}
		
		//prüft, ob in jedem Feld ein Wert steht:
		if(vorName.length() == 0 ||
		   nachName.length() == 0 ||
		   kennwort.length() == 0 ||
		   strasse.length() == 0 ||
		   plzText.getText().length() == 0 ||
		   stadt.length() == 0){
			JOptionPane dialog = new JOptionPane();
			JOptionPane.showMessageDialog(RegistrierenFenster.this, "Sie müssen in jedes Feld einen Wert eintragen.", "Error", JOptionPane.ERROR_MESSAGE);
			dialog.setVisible(true);
			return;
		}
		
		//holt sich eine neue Kunden-ID aus der EShopVerwaltung und legt ein neues Adress-Objekt an:
		//int neueKundenId = HauptFenster.shopVerwaltung.getNeueKundenId();
		//Adresse adresse = new Adresse(plz, stadt, strasse);
		
		HauptFenster.shopVerwaltung.fuegeUserEin("Test", kennwort, "Herr", vorName, nachName, strasse, plz, stadt);

		//speichern:
		/*try {
			HauptFenster.shopVerwaltung.kundenSpeichern();
		} catch (KonnteNichtSpeichernException e) {
			JOptionPane dialog = new JOptionPane();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			dialog.setVisible(true);
		}*/
		
		//teilt dem Kunden bei Erfolg seine neue ID mit:
		JOptionPane dialog = new JOptionPane();
		//TODO: benutzername
		JOptionPane.showMessageDialog(RegistrierenFenster.this, "Sie können sich nun mit dem Namen " + "Test" + " und dem von Ihnen gewählten Passwort anmelden.", "Kunde angelegt", JOptionPane.INFORMATION_MESSAGE);
		dialog.setVisible(true);
		this.dispose();
	}
	
	/**
	 * Die Methode implementiert das Verhalten des Ok-Buttons für die Aktion 1.
	 */
	/*private void adresseAendern(){
		//deklariert und initialisiert alle einzulesenden Werte:
		String strasse = strasseText.getText();
		String plz = plzText.getText();
		String stadt = stadtText.getText();
		
		//prüft, ob in jedem Feld ein Wert steht:
		if(strasse.length() == 0 ||
		   plz.length() == 0 ||
		   stadt.length() == 0){
			JOptionPane dialog = new JOptionPane();
			JOptionPane.showMessageDialog(RegistrierenFenster.this, "Sie müssen in jedes Feld einen Wert eintragen.", "Error", JOptionPane.ERROR_MESSAGE);
			dialog.setVisible(true);
			return;
		}
		
		//holt sich den Benutzer des Hauptfensters und legt ein neues Adress-Objekt an:
		Kunde k = (Kunde)HauptFenster.benutzer;
		//Adresse adresse = new Adresse(plz, stadt, strasse);
		
		//gibt dem Kunden seine neue Adresse:
		k.setAdresse();

		//speichern:
		try {
			HauptFenster.shopVerwaltung.kundenSpeichern();
		} catch (KonnteNichtSpeichernException e) {
			JOptionPane dialog = new JOptionPane();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			dialog.setVisible(true);
		}
		
		//positive Rückmeldung für den Kunden:
		JOptionPane dialog = new JOptionPane();
		JOptionPane.showMessageDialog(RegistrierenFenster.this, "Ihre Adresse wurde erfolgreich geändert.", "Adresse geändert", JOptionPane.INFORMATION_MESSAGE);
		dialog.setVisible(true);
		this.dispose();
	}*/
	
	/**
	 * Die Methode implementiert das Verhalten des Ok-Buttons für die Aktion 2.
	 */
	private void mitarbeiterAnlegen(){
		//deklariert und initialisiert alle einzulesenden Werte:
		String vorName = vorNameText.getText();
		String nachName = nachNameText.getText();
		String kennwort = kennwortText.getText();
		
		//prüft, ob in jedem Feld ein Wert steht:
		if(vorName.length() == 0 ||
		   nachName.length() == 0 ||
		   kennwort.length() == 0){
			JOptionPane dialog = new JOptionPane();
			JOptionPane.showMessageDialog(RegistrierenFenster.this, "Sie müssen in jedes Feld einen Wert eintragen.", "Error", JOptionPane.ERROR_MESSAGE);
			dialog.setVisible(true);
			return;
		}
		
		//holt sich eine neue Mitarbeiter-ID aus der EShopVerwaltung:
		//int neueMitarbeiterId = HauptFenster.shopVerwaltung.getNeueMitarbeiterId();
		
		HauptFenster.shopVerwaltung.fuegeUserEin("TEST", kennwort, "Frau", vorName, nachName);

		//speichern:
		/*try {
			HauptFenster.shopVerwaltung.mitarbeiterSpeichern();
		} catch (KonnteNichtSpeichernException e) {
			JOptionPane dialog = new JOptionPane();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			dialog.setVisible(true);
		}*/
		
		//teilt dem Mitarbeiter die neue ID mit:
		JOptionPane dialog = new JOptionPane();
		//TODO: Name
		JOptionPane.showMessageDialog(RegistrierenFenster.this, "Der Mitarbeiter kann sich nun mit der ID " + "TEST" + " und dem von Ihnen gewählten Passwort anmelden.", "Mitarbeiter angelegt", JOptionPane.INFORMATION_MESSAGE);
		dialog.setVisible(true);
		this.dispose();
	}
	
	/**
	 * Die Methode gibt das RegistrierenPanel zurück.
	 * @return Gibt das RegistrierenPanel zurück.
	 */
	public JPanel getRegistrierenPanel(){
		return this.registrierenPanelErzeugen();
	}
	
	/**
	 * Die Methode gibt das AdressPanel zurück.
	 * @return Gibt das AdressPanel zurück.
	 */
	public JPanel getAdressPanel(){
		return this.adressPanelErzeugen();
	}
	
	/**
	 * Die Methode gibt das aktuelleAdressePanel zurück.
	 * @return Gibt das aktuelleAdressePanel zurück.
	 */
	public JPanel getAktuelleAdressePanel(){
		return this.aktuelleAdressePanelErzeugen();
	}
}
