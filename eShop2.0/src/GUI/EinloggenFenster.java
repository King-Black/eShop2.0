package GUI;

import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import exceptions.BereitsEingeloggtException;
import exceptions.KennwortFalschException;

/**
 * Klasse zur Erstellung eines Login-Fensters.
 *
 */
@SuppressWarnings("serial")
public class EinloggenFenster extends JDialog {
	private JButton okButton;
	private JButton abbrechenButton;
	private JPasswordField passwortText;
	private JFormattedTextField idText;
	private JComboBox<String> personTypComboBox;
	private boolean erfolg = false;
	final EinloggenFenster gui = this;
	
	/**
	 * Initialisiert ein modales Login-Fenster mit fester Größe einem vorgegebenen Hauptfenster.
	 * @param owner Das Hauptfenster, des Login-Fensters.
	 */
	public EinloggenFenster(Window owner) {
		super(owner, "Einloggen", ModalityType.APPLICATION_MODAL);
		
		this.setResizable(false);
		this.initialisieren();
	}
	
	/**
	 * Initialisiert das Login-Fenster mit allen Komponenten.
	 */
	public void initialisieren() {
		//Layout setzen und Variabeln initialisieren:
		this.setLayout(new GridLayout(4, 2, 6, 3));
		personTypComboBox = new JComboBox<String>(new String[] {"Kunde", "Mitarbeiter"});
		idText = new JFormattedTextField(NumberFormat.getIntegerInstance());
		passwortText = new JPasswordField();
		okButton = new JButton("OK");
		abbrechenButton = new JButton("Abbrechen");
		
		//Komponenten ins Layout einfügen:
		this.add(new JLabel(""));
		this.add(personTypComboBox);
		this.add(new JLabel("Benutzer-ID:"));
		this.add(idText);
		this.add(new JLabel("Passwort:"));
		this.add(passwortText);
		this.add(okButton);
		this.add(abbrechenButton);
		
		//ActionListener für den Abbrechen-Button hinzufügen:
		ActionListener abort = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dispose();
	        }
	    };
	    
	    //Action- und KeyListener zu den Buttons bzw. dem Passwort-Feld hinzufügen:
	    okButton.addActionListener(this.okListener());
	    abbrechenButton.addActionListener(abort);
	    passwortText.addKeyListener(this.enterListener());
		
	    //Fenster auf optimale Größe packen:
		this.pack();
	}
	
	/**
	 * Die Methode gibt bei erfolgreichem Einloggen true zurück, andernfalls false.
	 * @return Gibt bei erfolgreichem Einloggen true zurück, andernfalls false.
	 */
	public boolean getErfolg(){
		return this.erfolg;
	}
	
	/**
	 * Die Methode implementiert einen KeyListener, mit welchem man sich per Enter-Taste in den EShop einloggen kann 
	 * und gibt diesen zurück.
	 * @return Gibt den KeyListener für die Enter-Taste zurück.
	 */
	private KeyListener enterListener(){
		KeyListener enter = new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e){
				//lässt den Nutzer seine Eingab auch per Enter-Taste bestätigen und loggt ihn ein:
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
					try {
						HauptFenster.benutzer = HauptFenster.shopVerwaltung.userLogin(Integer.parseInt(gui.idText.getText()),
								gui.personTypComboBox.getSelectedIndex(), String.valueOf(gui.passwortText.getPassword()));
						erfolg = true;
						gui.dispose();
					} catch (NumberFormatException e1) {
						JOptionPane dialog = new JOptionPane();
						JOptionPane.showMessageDialog(EinloggenFenster.this, "Bitte geben Sie eine Zahl ein.", "Error", JOptionPane.ERROR_MESSAGE);
						dialog.setVisible(true);
					} catch (KennwortFalschException e1) {
						JOptionPane dialog = new JOptionPane();
						JOptionPane.showMessageDialog(EinloggenFenster.this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						dialog.setVisible(true);
					} catch (BereitsEingeloggtException e1) {
						JOptionPane dialog = new JOptionPane();
						JOptionPane.showMessageDialog(EinloggenFenster.this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						dialog.setVisible(true);
					}				
				}
			}
			
			//Methoden, die im KeyListener enthalten sind, aber von uns nicht benötigt werden:
			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		};
		return enter;
	}

	/**
	 * Die Methode implementiert den ActionListener für den Ok-Button und gibt zurück.
	 * @return Gibt den ActionListener zurück.
	 */
	private ActionListener okListener(){
		ActionListener ok = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//loggt den Benutzer per Klick auf den OK-Button ein und gibt ggf. Fehlermeldungen aus:
				try {
					HauptFenster.benutzer = HauptFenster.shopVerwaltung.userLogin(Integer.parseInt(gui.idText.getText()),
							gui.personTypComboBox.getSelectedIndex(), String.valueOf(gui.passwortText.getPassword()));
					erfolg = true;
					gui.dispose();
				} catch (NumberFormatException e1) {
					JOptionPane dialog = new JOptionPane();
					JOptionPane.showMessageDialog(EinloggenFenster.this, "Bitte geben Sie eine Zahl ein.", "Error", JOptionPane.ERROR_MESSAGE);
					dialog.setVisible(true);
				} catch (KennwortFalschException e1) {
					JOptionPane dialog = new JOptionPane();
					JOptionPane.showMessageDialog(EinloggenFenster.this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					dialog.setVisible(true);
				} catch (BereitsEingeloggtException e1) {
					JOptionPane dialog = new JOptionPane();
					JOptionPane.showMessageDialog(EinloggenFenster.this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					dialog.setVisible(true);
				}				
			}
		};
		return ok;
	}
}
