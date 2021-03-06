package GUI;

import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import exceptions.KennwortFalschException;
import exceptions.UserNichtGefundenException;

/**
 * Klasse zur Erstellung eines Login-Fensters.
 *
 */
@SuppressWarnings("serial")
public class EinloggenFenster extends JDialog {
	private JButton okButton;
	private JButton abbrechenButton;
	private JPasswordField passwortText;
	private JTextField idText;
	private boolean erfolg = false;
	final EinloggenFenster gui = this;
	
	/**
	 * Initialisiert ein modales Login-Fenster mit fester Gr��e einem vorgegebenen Hauptfenster.
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
		this.setLayout(new GridLayout(3, 2, 6, 3));
		idText = new JTextField();
		passwortText = new JPasswordField();
		okButton = new JButton("OK");
		abbrechenButton = new JButton("Abbrechen");
		
		//Komponenten ins Layout einf�gen:
		this.add(new JLabel("Benutzer-Name:"));
		this.add(idText); //�ndern
		this.add(new JLabel("Passwort:"));
		this.add(passwortText);
		this.add(okButton);
		this.add(abbrechenButton);
		
		//ActionListener f�r den Abbrechen-Button hinzuf�gen:
		ActionListener abort = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dispose();
	        }
	    };
	    
	    //Action- und KeyListener zu den Buttons bzw. dem Passwort-Feld hinzuf�gen:
	    okButton.addActionListener(this.okListener());
	    abbrechenButton.addActionListener(abort);
	    passwortText.addKeyListener(this.enterListener());
		
	    //Fenster auf optimale Gr��e packen:
		this.pack();
	}
	
	/**
	 * Die Methode gibt bei erfolgreichem Einloggen true zur�ck, andernfalls false.
	 * @return Gibt bei erfolgreichem Einloggen true zur�ck, andernfalls false.
	 */
	public boolean getErfolg(){
		return this.erfolg;
	}
	
	/**
	 * Die Methode implementiert einen KeyListener, mit welchem man sich per Enter-Taste in den EShop einloggen kann 
	 * und gibt diesen zur�ck.
	 * @return Gibt den KeyListener f�r die Enter-Taste zur�ck.
	 */
	private KeyListener enterListener(){
		KeyListener enter = new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e){
				//l�sst den Nutzer seine Eingab auch per Enter-Taste best�tigen und loggt ihn ein:
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
					try {
						HauptFenster.benutzer = HauptFenster.shopVerwaltung.userLogin(String.valueOf(gui.idText.getText()), //�ndern
								String.valueOf(gui.passwortText.getPassword()));
						erfolg = true;
						gui.dispose();
					} catch (KennwortFalschException e1) {
						JOptionPane dialog = new JOptionPane();
						JOptionPane.showMessageDialog(EinloggenFenster.this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						dialog.setVisible(true);
					} catch (UserNichtGefundenException e1) {
						JOptionPane dialog = new JOptionPane();
						JOptionPane.showMessageDialog(EinloggenFenster.this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						dialog.setVisible(true);
					} 			
				}
			}
			
			//Methoden, die im KeyListener enthalten sind, aber von uns nicht ben�tigt werden:
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
	 * Die Methode implementiert den ActionListener f�r den Ok-Button und gibt zur�ck.
	 * @return Gibt den ActionListener zur�ck.
	 */
	private ActionListener okListener(){
		ActionListener ok = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//loggt den Benutzer per Klick auf den OK-Button ein und gibt ggf. Fehlermeldungen aus:
				try {
					HauptFenster.benutzer = HauptFenster.shopVerwaltung.userLogin(String.valueOf(gui.idText.getText()),
							String.valueOf(gui.passwortText.getPassword()));
					erfolg = true;
					gui.dispose();
				} catch (KennwortFalschException e1) {
					JOptionPane dialog = new JOptionPane();
					JOptionPane.showMessageDialog(EinloggenFenster.this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					dialog.setVisible(true);
				} catch (UserNichtGefundenException e1) {
					JOptionPane dialog = new JOptionPane();
					JOptionPane.showMessageDialog(EinloggenFenster.this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					dialog.setVisible(true);
				} 				
			}
		};
		return ok;
	}
}
