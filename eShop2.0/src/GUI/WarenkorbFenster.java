package GUI;

import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Valueobjects.Artikel;
import Valueobjects.Kunde;
import exceptions.ArtikelNichtGefundenException;
import exceptions.ArtikelNurInEinheitenVerfuegbarException;


@SuppressWarnings("serial")
public class WarenkorbFenster extends JDialog{
	
	private JFormattedTextField anzahlText;
	private JButton abbrechenButton;
	private JButton okButton;
	private JPanel buttonPanel;
	private JLabel textLabel;
	private Artikel artikel;
	
	/**
	 * Initialisiert ein modales WarenkorbFenster-Objekt mit �bergebenem Hauptfenster und fester Gr��e.
	 * @param owner Das Hauptfenster des Warenkorbfensters.
	 */
	public WarenkorbFenster(Window owner, Artikel a){
		super(owner, "Artikel zum Warenkorb hinzuf�gen", ModalityType.APPLICATION_MODAL);
		
		this.artikel = a;
		
		this.setResizable(false);
		this.initialisieren();
	}
	
	/**
	 * Die Methode stellt das Warenkorbfenster mit allen ActionListenern und Buttons zusammen.
	 */
	private void initialisieren(){
		//setzt das Layout und initialisiert alle Komponenten des Warenkorbfensters:
		this.setLayout(new GridLayout(3,1));
		anzahlText = new JFormattedTextField(NumberFormat.getIntegerInstance());
		okButton = new JButton("Ok");
		abbrechenButton = new JButton("Abbrechen");
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,2));
		textLabel = new JLabel("Sie haben folgenden Artikel gew�hlt: " + artikel.getArtikelName() + ". Wie viel m�chten sie in den Warenkorb tun?");
		final WarenkorbFenster gui = this;
		
		//erstellt einen ActionListener f�r den Abbrechen-Button und f�gt die ActionListener den Buttons hinzu:
		ActionListener abort = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				gui.dispose();
			}
		};
		abbrechenButton.addActionListener(abort);
		okButton.addActionListener(this.okButton());
		
		//f�gt die Komponenten in das Layout ein:
		this.add(textLabel);
		this.add(anzahlText);
		buttonPanel.add(okButton);
		buttonPanel.add(abbrechenButton);
		this.add(buttonPanel);
		
		//f�gt alle Komponenten mit der optimale Gr��e zusammen
		this.pack();
	}
	
	/**
	 * Die Methode implementiert den ActionListener f�r den Ok-Button und gibt diesen zur�ck.
	 * @return Gibt den ActionListener f�r den Ok-Button zur�ck.
	 */
	private ActionListener okButton(){
		//Selbstreferenz auf das Warenkorbfenster f�r die anonyme Klasse:
		final WarenkorbFenster gui = this;
		ActionListener ok = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					int menge = Integer.parseInt(gui.anzahlText.getText());
					
					Kunde k = (Kunde)HauptFenster.benutzer;
					//k.getWarenkorb().artikelHinzufuegen(artikel, menge, artikel.getArtikelBestand());
					HauptFenster.shopVerwaltung.artikelInWarenkorb(artikel.getArtikelNummer(), menge, k);
						
					JOptionPane dialog = new JOptionPane();
					JOptionPane.showMessageDialog(WarenkorbFenster.this, "Folgender Artikel befindet sich jetzt in Ihrem Warenkorb: " + artikel.getArtikelName() + ".", "Artikel im Warenkorb", JOptionPane.INFORMATION_MESSAGE);
					dialog.setVisible(true);
					gui.dispose();
				}catch (NumberFormatException e1) {
					JOptionPane dialog = new JOptionPane();
					JOptionPane.showMessageDialog(WarenkorbFenster.this, "Bitte geben Sie eine Zahl ein.", "Error", JOptionPane.ERROR_MESSAGE);
					dialog.setVisible(true);
				} catch (ArtikelNichtGefundenException e1) {
					JOptionPane dialog = new JOptionPane();
					JOptionPane.showMessageDialog(WarenkorbFenster.this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					dialog.setVisible(true);
					return;
				} catch (ArtikelNurInEinheitenVerfuegbarException e1) {
					JOptionPane dialog = new JOptionPane();
					JOptionPane.showMessageDialog(WarenkorbFenster.this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					dialog.setVisible(true);
					return;
				}
			}
		};
		return ok;
	}
}
