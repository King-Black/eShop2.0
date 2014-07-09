package GUI;

import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import Valueobjects.Rechnung;

/**
 * Klasse zur Erstellung eines Rechnungsfensters.
 *
 */
@SuppressWarnings("serial")
public class RechnungsFenster extends JDialog {
	private Rechnung rechnung;
	private JTextArea rechnungText;
	
	/**
	 * Initialisiert ein RechnungsFenster-Objekt.
	 * @param owner Das Hauptfenster, des RechnungsFensters.
	 * @param rechnung Die Rechnung, die angezeigt werden soll.
	 */
	public RechnungsFenster(Window owner, Rechnung rechnung){
		super(owner, "Rechnung");
		
		this.rechnung = rechnung;
		
		this.initialisieren();
		this.pack();
	}
	
	/**
	 * Die Methode initialisiert das Rechnungsfenster mit allen Komponenten.
	 */
	private void initialisieren(){
		//initialisiert das Textfeld und beschreibt es genau:
		rechnungText = new JTextArea();
		
		rechnungText.setEditable(false);
		rechnungText.setWrapStyleWord(true);
		rechnungText.setOpaque(false);
		rechnungText.setFont(new JLabel().getFont());
		//rechnungText.setText(rechnung.toStringGui());
		
		this.add(rechnungText);
	}
}
