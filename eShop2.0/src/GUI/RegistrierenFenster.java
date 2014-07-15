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
import javax.swing.JPasswordField;
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
	private JTextField benutzerNameText;
	private JTextField anredeText;
	private JTextField vorNameText;
	private JTextField nachNameText;
	private JPasswordField kennwortText;
	private JPanel adressPanel;
	private JTextField strasseText;
	private JTextField plzText;
	private JTextField stadtText;
	private JPanel buttonPanel;
	private JPanel aktuelleAdressePanel;
	JTextArea aktuelleAdresseText;
	private int aktion;
	
	/**
	 * Initialisiert ein RegistrierenFenster-Objekt, welches je nach gew�hlter Aktion sein Aussehen �ndert
	 * und standardm��ig nur Ok- und Abbrechen-Button beinhaltet.
	 * @param owner Das Hauptfenster des RegistrierenFensters.
	 * @param aktion Die Aktion, die ausgef�hrt werden soll. 0 f�r Kunden registrieren, 1 f�r Adresse �ndern
	 * und 2 f�r Mitarbeiter registrieren.
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
	 * und gibt dieses zur�ck.
	 * @return Gibt das RegistrierenPanel zur�ck.
	 */
	private JPanel registrierenPanelErzeugen(){
		//initialisiert alle Komponenten des RegistrierenPanel und setzt das Layout:
		registrierenPanel = new JPanel();
		registrierenPanel.setLayout(new GridLayout(5, 2, 6, 3));
		benutzerNameText = new JTextField();
		anredeText = new JTextField();
		vorNameText = new JTextField();
		nachNameText = new JTextField();
		kennwortText = new JPasswordField();
		
		//f�gt alle Komponenten in das Layout ein:
		registrierenPanel.add(new JLabel("Benutzername:"));
	    registrierenPanel.add(benutzerNameText);
	    registrierenPanel.add(new JLabel("Anrede:"));
	    registrierenPanel.add(anredeText);
	    registrierenPanel.add(new JLabel("Vorname:"));
	    registrierenPanel.add(vorNameText);
	    registrierenPanel.add(new JLabel("Nachname:"));
	    registrierenPanel.add(nachNameText);
	    registrierenPanel.add(new JLabel("Passwort:"));
	    registrierenPanel.add(kennwortText);
	    
	    return registrierenPanel;
	}
	
	/**
	 * Die Methode erzeugt das AdressPanel, welches Stra�e, Postleitzahl und Stadt einliest
	 * und das Panel zur�ckgibt.
	 * @return Gibt das AdressPanel zur�ck.
	 */
	private JPanel adressPanelErzeugen(){
		//initialisiert alle Komponenten des AdressPanels und setzt das Layout:
		adressPanel = new JPanel();
		adressPanel.setLayout(new GridLayout(3, 2, 6, 3));
		strasseText = new JTextField();
		plzText = new JTextField();
		stadtText = new JTextField();
		
		//f�gt alle Komponenten in das Layout ein:
		adressPanel.add(new JLabel("Stra�e und Hausnummer:"));
		adressPanel.add(strasseText);
		adressPanel.add(new JLabel("Postleitzahl:"));
		adressPanel.add(plzText);
		adressPanel.add(new JLabel("Stadt:"));
		adressPanel.add(stadtText);
		
		return adressPanel;
	}
	
	/**
	 * Die Methode erzeugt das Panel mit der aktuellen Adresse, welche bei der Adress�nderung ausgegeben wird
	 * und gibt das Panel zur�ck.
	 * @return Gibt das aktuelleAdressePanel zur�ck.
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
		
		//f�gt das Textfeld in das Panel ein:
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
		
		//implementiert den ActionListener f�r den Abbrechen Button:
		ActionListener abort = new ActionListener(){
	
			public void actionPerformed(ActionEvent e){
				dispose();
	        }
	    };
	    //f�gt die ActionListener zu den Buttons hinzu:
	    abbrechenButton.addActionListener(abort);
	    okButton.addActionListener(this.okListener());
		
	    //f�gt die Buttons in das Panel ein:
		buttonPanel.add(okButton);
		buttonPanel.add(abbrechenButton);
		
		//f�gt das Panel in das Layout ein:
		this.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Die Methode implementiert den ActionListener f�r den Ok-Button und gibt diesen zur�ck.
	 * Die Funktionalit�t �ndert sich je nach der im Konstruktor �bergebenen Aktion.
	 * @return Gibt den ActionListener f�r den Ok-Button zur�ck.
	 */
	private ActionListener okListener(){
		ActionListener ok = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {	
				//�ndert die Funktionalit�t des Ok-Buttons je nach Aktion:
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
	 * Die Methode implementiert das Verhalten des Ok-Buttons f�r die Aktion 0.
	 */
	private void registrieren(){
		//Deklariert und initialisiert alle einzulesenden Werte:
		String benutzerName = benutzerNameText.getText();
		String anrede = anredeText.getText();
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
			JOptionPane.showMessageDialog(RegistrierenFenster.this, "Sie m�ssen eine Postleitzahl angeben.", "Error", JOptionPane.ERROR_MESSAGE);
			dialog.setVisible(true);
			return;
		}
		
		//pr�ft, ob in jedem Feld ein Wert steht:
		if(benutzerName.length() == 0 ||
		   anrede.length() == 0 ||
		   vorName.length() == 0 ||
		   nachName.length() == 0 ||
		   kennwort.length() == 0 ||
		   strasse.length() == 0 ||
		   plzText.getText().length() == 0 ||
		   stadt.length() == 0){
			JOptionPane dialog = new JOptionPane();
			JOptionPane.showMessageDialog(RegistrierenFenster.this, "Sie m�ssen in jedes Feld einen Wert eintragen.", "Error", JOptionPane.ERROR_MESSAGE);
			dialog.setVisible(true);
			return;
		}
		
		HauptFenster.shopVerwaltung.fuegeUserEin(benutzerName, kennwort, anrede, vorName, nachName, strasse, plz, stadt);
		
		//teilt dem Kunden bei Erfolg seine neue ID mit:
		JOptionPane dialog = new JOptionPane();
		JOptionPane.showMessageDialog(RegistrierenFenster.this, "Sie k�nnen sich nun mit dem Namen " + benutzerName + " und dem von Ihnen gew�hlten Passwort anmelden.", "Kunde angelegt", JOptionPane.INFORMATION_MESSAGE);
		dialog.setVisible(true);
		this.dispose();
	}
	
	/**
	 * Die Methode implementiert das Verhalten des Ok-Buttons f�r die Aktion 1.
	 */
	/*private void adresseAendern(){
		//deklariert und initialisiert alle einzulesenden Werte:
		String strasse = strasseText.getText();
		String plz = plzText.getText();
		String stadt = stadtText.getText();
		
		//pr�ft, ob in jedem Feld ein Wert steht:
		if(strasse.length() == 0 ||
		   plz.length() == 0 ||
		   stadt.length() == 0){
			JOptionPane dialog = new JOptionPane();
			JOptionPane.showMessageDialog(RegistrierenFenster.this, "Sie m�ssen in jedes Feld einen Wert eintragen.", "Error", JOptionPane.ERROR_MESSAGE);
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
		
		//positive R�ckmeldung f�r den Kunden:
		JOptionPane dialog = new JOptionPane();
		JOptionPane.showMessageDialog(RegistrierenFenster.this, "Ihre Adresse wurde erfolgreich ge�ndert.", "Adresse ge�ndert", JOptionPane.INFORMATION_MESSAGE);
		dialog.setVisible(true);
		this.dispose();
	}*/
	
	/**
	 * Die Methode implementiert das Verhalten des Ok-Buttons f�r die Aktion 2.
	 */
	private void mitarbeiterAnlegen(){
		//deklariert und initialisiert alle einzulesenden Werte:
		String benutzerName = benutzerNameText.getText();
		String anrede = anredeText.getText();
		String vorName = vorNameText.getText();
		String nachName = nachNameText.getText();
		String kennwort = kennwortText.getText();
		
		//pr�ft, ob in jedem Feld ein Wert steht:
		if(benutzerName.length() == 0 ||
		   anrede.length() == 0 ||
		   vorName.length() == 0 ||
		   nachName.length() == 0 ||
		   kennwort.length() == 0){
			JOptionPane dialog = new JOptionPane();
			JOptionPane.showMessageDialog(RegistrierenFenster.this, "Sie m�ssen in jedes Feld einen Wert eintragen.", "Error", JOptionPane.ERROR_MESSAGE);
			dialog.setVisible(true);
			return;
		}
		
		//holt sich eine neue Mitarbeiter-ID aus der EShopVerwaltung:
		//int neueMitarbeiterId = HauptFenster.shopVerwaltung.getNeueMitarbeiterId();
		
		HauptFenster.shopVerwaltung.fuegeUserEin(benutzerName, kennwort, anrede, vorName, nachName);

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
		JOptionPane.showMessageDialog(RegistrierenFenster.this, "Der Mitarbeiter kann sich nun mit dem Benutzernamen " + benutzerName + " und dem von Ihnen gew�hlten Passwort anmelden.", "Mitarbeiter angelegt", JOptionPane.INFORMATION_MESSAGE);
		dialog.setVisible(true);
		this.dispose();
	}
	
	/**
	 * Die Methode gibt das RegistrierenPanel zur�ck.
	 * @return Gibt das RegistrierenPanel zur�ck.
	 */
	public JPanel getRegistrierenPanel(){
		return this.registrierenPanelErzeugen();
	}
	
	/**
	 * Die Methode gibt das AdressPanel zur�ck.
	 * @return Gibt das AdressPanel zur�ck.
	 */
	public JPanel getAdressPanel(){
		return this.adressPanelErzeugen();
	}
	
	/**
	 * Die Methode gibt das aktuelleAdressePanel zur�ck.
	 * @return Gibt das aktuelleAdressePanel zur�ck.
	 */
	public JPanel getAktuelleAdressePanel(){
		return this.aktuelleAdressePanelErzeugen();
	}
}
