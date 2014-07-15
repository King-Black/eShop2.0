package GUI;

import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Valueobjects.Artikel;
import exceptions.ArtikelNichtGefundenException;

/**
 * Klasse zur Erstellung eines Artikel-Verwaltungs-Fensters.
 *
 */
@SuppressWarnings("serial")
public class ArtikelVerwaltenFenster extends JDialog {
	private JComboBox<String> aktionWaehlenComboBox;
	private Artikel artikel;
	private JTextField neuerWertText;
	private int gewaehlterIndex = 0;
	private JLabel neuerWertLabel;
	private JButton okButton;
	private JButton abbrechenButton;
	
	/**
	 * Initialisiert ein ArtikelVerwaltenFenster-Objekt.
	 * @param owner Das Fenster, von welchem aus das Fenster aufgerufen wird.
	 * @param a Der Artikel, der bearbeitet werden soll.
	 */
	public ArtikelVerwaltenFenster(Window owner, Artikel a){
		super(owner, "Artikel verwalten", ModalityType.APPLICATION_MODAL);
		this.artikel = a;
		
		this.setResizable(false);
		this.initialisieren();
		this.pack();
	}
	
	/**
	 * Die Methode initialisiert das ArtikelVerwaltenFenster mit allen Eigenschaften,
	 * wie z.B. Layout und Buttons.
	 */
	private void initialisieren() {
		//Komponenten des ArtikelVerwaltenFensters initialisieren und Layout setzen:
		this.setLayout(new GridLayout(4, 2, 6, 3));
		aktionWaehlenComboBox = new JComboBox<String>(new String[] {"Artikel auslagern",
				"Artikel einlagern", "Artikelpreis ändern"});
		neuerWertText = new JTextField();
		final ArtikelVerwaltenFenster gui = this;
		neuerWertLabel = new JLabel("Wie viel möchten Sie auslagern?");
		okButton = new JButton("Ok");
		abbrechenButton = new JButton("Abbrechen");
		
		//ItemListener für die JComboBox implementieren und hinzufügen:
		ItemListener aktionWaehlen = new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				gui.gewaehlterIndex = aktionWaehlenComboBox.getSelectedIndex();
				
				if(gewaehlterIndex == 0){
					gui.neuerWertLabel.setText("Wie viel möchten Sie auslagern?");
				}else if(gewaehlterIndex == 1){
					gui.neuerWertLabel.setText("Wie viel möchten Sie einlagern?");
				}else if(gewaehlterIndex == 2){
					gui.neuerWertLabel.setText("Wie viel soll der Artikel kosten?");
				}
			}
		};
		aktionWaehlenComboBox.addItemListener(aktionWaehlen);
		
		//ActionListener für den Ok-Button implementieren und hinzufügen:
		ActionListener ok = new ActionListener(){
			public void actionPerformed(ActionEvent e){		
				if(gewaehlterIndex == 0){
					//Auslagern
					gui.artikelAuslagern();
				}else if(gewaehlterIndex == 1){
					//Einlagern
					gui.artikelEinlagern();
				}else if(gewaehlterIndex == 2){
					//Preis ändern
					gui.preisAendern();
				}
				gui.dispose();
	        }
	    };
	    okButton.addActionListener(ok);
		
	    //ActionListener für den Abbrechen-Button implementieren und hinzufügen:
		ActionListener abort = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dispose();
	        }
	    };
	    abbrechenButton.addActionListener(abort);
		
	    //Komponenten ins Layout einfügen:
		this.add(new JLabel("gewählter Artikel:"));
		this.add(new JLabel(artikel.getArtikelName()));
		this.add(new JLabel("Was möchten Sie tun?"));
		this.add(aktionWaehlenComboBox);
		this.add(neuerWertLabel);
		this.add(neuerWertText);
		this.add(okButton);
		this.add(abbrechenButton);
	}
	
	/**
	 * Die Methode lagert Artikel GUI-spezifisch aus dem EShop aus.
	 */
	private void artikelAuslagern(){
		//versucht Artikel auszulagern, legt ein Lagerereignis an und öffnet ggf. Fehlermeldungen:
		try{
			int auslagern = Integer.parseInt(neuerWertText.getText());
			if(artikel.getArtikelBestand() < auslagern){
				JOptionPane dialog = new JOptionPane();
				JOptionPane.showMessageDialog(ArtikelVerwaltenFenster.this, "Es ist nicht genug auf Lager.", "Error", JOptionPane.ERROR_MESSAGE);
				dialog.setVisible(true);
				return;
			} else {
				HauptFenster.shopVerwaltung.mengeAendern(artikel.getArtikelNummer(), -auslagern, HauptFenster.benutzer);
			}
		}catch(NumberFormatException e){
			JOptionPane dialog = new JOptionPane();
			JOptionPane.showMessageDialog(ArtikelVerwaltenFenster.this, "Bitte geben Sie eine Zahl ein.", "Error", JOptionPane.ERROR_MESSAGE);
			dialog.setVisible(true);
			return;
		} catch (ArtikelNichtGefundenException e) {
			JOptionPane dialog = new JOptionPane();
			JOptionPane.showMessageDialog(ArtikelVerwaltenFenster.this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			dialog.setVisible(true);
			return;
		}
		
		//Wird bei erfolgreichem Auslagern angezeigt:
		JOptionPane dialog = new JOptionPane();
		JOptionPane.showMessageDialog(ArtikelVerwaltenFenster.this, "Der Artikel wurde erfolgreich ausgelagert.", "Artikel ausgelagert", JOptionPane.INFORMATION_MESSAGE);
		dialog.setVisible(true);
	}
	
	/**
	 * Die Methode lagert Artikel GUI-spezifisch in den EShop ein.
	 */
	private void artikelEinlagern() {
		//versucht Artikel einzulagern, legt ein Lagerereignis an und öffnet ggf. Fehlermeldungen:
		try{
			int einlagern = Integer.parseInt(neuerWertText.getText());
			HauptFenster.shopVerwaltung.mengeAendern(artikel.getArtikelNummer(), einlagern, HauptFenster.benutzer);
		}catch(NumberFormatException e){
			JOptionPane dialog = new JOptionPane();
			JOptionPane.showMessageDialog(ArtikelVerwaltenFenster.this, "Bitte geben Sie eine Zahl ein.", "Error", JOptionPane.ERROR_MESSAGE);
			dialog.setVisible(true);
			return;
		} catch (ArtikelNichtGefundenException e) {
			JOptionPane dialog = new JOptionPane();
			JOptionPane.showMessageDialog(ArtikelVerwaltenFenster.this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			dialog.setVisible(true);
			return;
		}
		
		//Wird bei erfolgreichem Einlagern angezeigt:
		JOptionPane dialog = new JOptionPane();
		JOptionPane.showMessageDialog(ArtikelVerwaltenFenster.this, "Der Artikel wurde erfolgreich eingelagert.", "Artikel eingelagert", JOptionPane.INFORMATION_MESSAGE);
		dialog.setVisible(true);
	}
	
	/**
	 * Die Methode ändert den Artikelpreis.
	 */
	private void preisAendern() {
		//versucht den Preis zu ändern und wirft ggf. eine Exception:
		try{
			float preis = Float.parseFloat(neuerWertText.getText().replace(',', '.'));
			artikel.setPreis(preis);
		}catch(NumberFormatException e1){
			JOptionPane dialog = new JOptionPane();
			JOptionPane.showMessageDialog(ArtikelVerwaltenFenster.this, "Bitte geben Sie eine Zahl ein.", "Error", JOptionPane.ERROR_MESSAGE);
			dialog.setVisible(true);
			return;
		}
		
		//Wird bei erfolgreichem Preis ändern angezeigt:
		JOptionPane dialog = new JOptionPane();
		JOptionPane.showMessageDialog(ArtikelVerwaltenFenster.this, "Der Preis wurde erfolgreich geändert.", "Preis geändert", JOptionPane.INFORMATION_MESSAGE);
		dialog.setVisible(true);
	}
}