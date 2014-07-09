package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import Valueobjects.Artikel;
import Valueobjects.Kunde;
import Valueobjects.Mitarbeiter;
import exceptions.ArtikelNichtGefundenException;
import exceptions.EinlagernException;

/**
 * Klasse zur Erstellung des ArtikelPanels des Hauptfensters.
 *
 */
@SuppressWarnings("serial")
public class ArtikelPanel extends JPanel {
	private JPanel eastPanel;
	private JPanel southPanel;
	private JTable artikelTabelle;
	private JPanel centerPanel;
	private JPanel buttonPanel;
	JButton inDenWarenkorbButton;
	private ArtikelTableModel tableModel;
	private JTextArea beschreibungText;
	private int ausgewaehlteArtikelId = -1;
	private Artikel ausgewaehlterArtikel = null;
	private JTextField artikelNameText;
	private JTextField artikelBeschreibungText;
	private JFormattedTextField artikelMengeText;
	private JFormattedTextField artikelMindestbestandText;
	private JFormattedTextField artikelEinheitText;
	private JTextField artikelPreisText;
	private JButton hinzufuegenButton;
	private JButton artikelVerwaltenButton;
	private HauptFenster hauptFenster;
	
	/**
	 * Initialisiert ein ArtikelPanel-Objekt.
	 */
	public ArtikelPanel(HauptFenster hf){
		super();
		
		this.hauptFenster = hf;
		
		this.initialisieren();
	}
	
	/**
	 * Die Methode setzt das Layout des ArtikelPanels und f�gt die Hauptkomponenten ein.
	 */
	private void initialisieren(){
		this.setLayout(new BorderLayout());
		
		this.centerPanelErzeugen();
		this.eastPanelErzeugen();
	}
	
	/**
	 * Die Methode erzeugt das CenterPanel des ArtikelPanels mit der Artikel-Tabelle.
	 */
	private void centerPanelErzeugen(){
		//Komponenten des CenterPanels initialisieren:
		centerPanel = new JPanel();
		artikelTabelle = new JTable();
		tableModel = new ArtikelTableModel();
		
		//Artikel-Tabelle genau definieren und SelectionListener hinzuf�gen:
		artikelTabelle.setModel(tableModel);
		artikelTabelle.setAutoCreateRowSorter(true);
		artikelTabelle.getTableHeader().setReorderingAllowed(false);
		artikelTabelle.setFillsViewportHeight(true);
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();  
		dtcr.setHorizontalTextPosition(DefaultTableCellRenderer.RIGHT); 
		artikelTabelle.setDefaultRenderer(tableModel.getColumnClass(0), dtcr);
		artikelTabelle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		artikelTabelle.getSelectionModel().addListSelectionListener(this.beschreibungAnzeigen());
		
		//f�gt die Artikel-Tabelle in ein JScrollPane ein, um l�ngere Tabellen mit einem Scroll-Balken auszustatten:
		JScrollPane scrollPane = new JScrollPane(artikelTabelle);
		Border scrollBorder = BorderFactory.createEtchedBorder();
		scrollPane.setBorder(scrollBorder);
		
		//f�gt das JScrollPane, welches die Tabelle beinhaltet, in das CenterPanel ein:
		centerPanel.add(scrollPane);
		
		//f�gt das CenterPanel in das ArtikelPanel ein:
		this.add(centerPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Die Methode erzeugt das EastPanel des ArtikelPanels mit den Buttons und der Artikel-Beschreibung.
	 */
	private void eastPanelErzeugen(){
		//Die Komponenten des EastPanel initialisieren:
		eastPanel = new JPanel();
		buttonPanel = new JPanel();
		inDenWarenkorbButton = new JButton("In den Warenkorb");
		beschreibungText = new JTextArea();
		artikelVerwaltenButton = new JButton("Artikel verwalten");
		
		//Die Gr��e und das Layout des EastPanel festlegen:
		Dimension d = new Dimension(150, 400);
		eastPanel.setMinimumSize(d);
		eastPanel.setPreferredSize(d);
		eastPanel.setMaximumSize(d);
		eastPanel.setLayout(new GridLayout(2,1));
		
		//Die Artikel-Beschreibung formatieren:
		TitledBorder tBorderBeschreibung = BorderFactory.createTitledBorder("Beschreibung");
		beschreibungText.setBorder(tBorderBeschreibung);
		beschreibungText.setEditable(false);
		beschreibungText.setWrapStyleWord(true);
		beschreibungText.setLineWrap(true);
		beschreibungText.setOpaque(false);
		beschreibungText.setFont(new JLabel().getFont());

	    //ActionListener auf die Buttons anwenden und die Buttons hinzuf�gen:
		inDenWarenkorbButton.addActionListener(this.warenkorbListener());
		inDenWarenkorbButton.setVisible(false);
		
		artikelVerwaltenButton.addActionListener(this.artikelVerwaltenListener());
		artikelVerwaltenButton.setVisible(false);
		
		//Artikel-Beschreibung und Button-Panel in das Layout einf�gen:
		eastPanel.add(beschreibungText);
		eastPanel.add(buttonPanel);
		buttonPanel.add(inDenWarenkorbButton);
		buttonPanel.add(artikelVerwaltenButton);
		
		//EastPanel zum ArtikelPanel hinzuf�gen:
		this.add(eastPanel, BorderLayout.EAST);
	}
	
	/**
	 * Die Methode erzeugt das SouthPanel des ArtikelPanels und gibt dieses zur�ck, da es nur angezeigt wird,
	 * falls der Benutzer ein Mitarbeiter ist.
	 * @return Gibt das erzeugte SouthPanel zur�ck.
	 */
	private JPanel southPanelErzeugen(){
		//Die Komponenten des SouthPanel erzeugen und Layout setzen:
		southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(2, 6, 6, 3));
		artikelNameText = new JTextField();
		artikelBeschreibungText = new JTextField();
		artikelMengeText = new JFormattedTextField(NumberFormat.getIntegerInstance());
		artikelMindestbestandText = new JFormattedTextField(NumberFormat.getIntegerInstance());
		artikelEinheitText = new JFormattedTextField(NumberFormat.getIntegerInstance());
		artikelPreisText = new JTextField();
		hinzufuegenButton = new JButton("Hinzuf�gen");
		
		TitledBorder tBorder = BorderFactory.createTitledBorder("Artikel hinzuf�gen");
		southPanel.setBorder(tBorder);
		
		//ActionListener zum Button hinzuf�gen:
		hinzufuegenButton.addActionListener(this.artikelHinzufuegen());
		
		//Komponenten zum SouthPanel hinzuf�gen:
		southPanel.add(new JLabel("Artikelname:"));
		southPanel.add(new JLabel("Artikelbeschreibung:"));
		southPanel.add(new JLabel("Bestand:"));
		southPanel.add(new JLabel("Mindestbestand:"));
		southPanel.add(new JLabel("Verkaufseinheit:"));
		southPanel.add(new JLabel("St�ckpreis:"));
		southPanel.add(new JLabel());
		southPanel.add(artikelNameText);
		southPanel.add(artikelBeschreibungText);
		southPanel.add(artikelMengeText);
		southPanel.add(artikelMindestbestandText);
		southPanel.add(artikelEinheitText);
		southPanel.add(artikelPreisText);
		southPanel.add(hinzufuegenButton);
		
		//SouthPanel zur�ckgeben, da es nur f�r Mitarbeiter sichtbar ist:
		return southPanel;
	}
	
	/**
	 * Die Methode gibt das SouthPanel zur�ck.
	 * @return Gibt das SouthPanel zur�ck.
	 */
	public JPanel getSouthPanel(){
		return this.southPanelErzeugen();
	}
	
	/**
	 * Die Methode gibt den in der Tabelle ausgew�hlten Artikel zur�ck.
	 * @return Gibt den in der Tabelle ausgew�hlten Artikel zur�ck.
	 */
	public Artikel getAusgewaehlterArtikel() {
		return this.ausgewaehlterArtikel;
	}
	
	/**
	 * Die Methode implementiert den ListSelectionListener f�r die Tabelle und gibt diesen zur�ck,
	 * damit er der Tabelle hinzugef�gt werden kann.
	 * @return Gibt den ListSelectionListener zur�ck.
	 */
	private ListSelectionListener beschreibungAnzeigen(){
		ListSelectionListener beschreibungListener = new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				//zeigt die Beschreibung des Artikels in der ausgew�hlten Spalte an:
				int ausgewaehlteSpalte = artikelTabelle.getSelectedRow();
				ausgewaehlteArtikelId = (int)artikelTabelle.getModel().getValueAt(ausgewaehlteSpalte, 0);
				try {
					//Shopverwaltung findArtikelByNumber
					ausgewaehlterArtikel = HauptFenster.shopVerwaltung.findArtikelByNumber(ausgewaehlteArtikelId);
					beschreibungText.setText(ausgewaehlterArtikel.getBeschreibung());
						
				} catch (ArtikelNichtGefundenException e1) {
					JOptionPane dialog = new JOptionPane();
					JOptionPane.showMessageDialog(ArtikelPanel.this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					dialog.setVisible(true);
				}
				//Zeigt je nach Benutzer erst nach Auswahl einer Spalte die Buttons an:
				if(HauptFenster.benutzer == null){
					ArtikelPanel.this.inDenWarenkorbButton.setVisible(true);
					ArtikelPanel.this.artikelVerwaltenButton.setVisible(true);
				}
				if(HauptFenster.benutzer instanceof Mitarbeiter){
					ArtikelPanel.this.inDenWarenkorbButton.setVisible(false);
					ArtikelPanel.this.artikelVerwaltenButton.setVisible(true);
				}
				if(HauptFenster.benutzer instanceof Kunde){
					ArtikelPanel.this.inDenWarenkorbButton.setVisible(true);
					ArtikelPanel.this.artikelVerwaltenButton.setVisible(false);
				}
			}
		};
		return beschreibungListener;
	}
	
	/**
	 * Die Methode implementiert den ActionListener f�r den Warenkorb Button und gibt diesen zur�ck.
	 * @return Gibt den ActionListener f�r den Warenkorb Button zur�ck.
	 */
	private ActionListener warenkorbListener(){
		ActionListener warenkorbListener = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//�ffnet ein JOptionPane, falls man nicht als Kunde eingeloggt ist und beendet den Vorgang:
				if(HauptFenster.benutzer == null || HauptFenster.benutzer instanceof Mitarbeiter){
					JOptionPane dialog = new JOptionPane();
					JOptionPane.showMessageDialog(ArtikelPanel.this, "Sie m�ssen sich zuerst als Kunde einloggen, um Artikel zum Warenkorb hinzuzuf�gen.",
							"Error", JOptionPane.ERROR_MESSAGE);
					dialog.setVisible(true);
					return;
				}
				//Pr�ft, ob ein Artikel ausgew�hlt ist und �ffnet bei Erfolg ein Pop-Up, in welchem die Menge
				//abgefragt wird:
				if(ausgewaehlterArtikel != null){
					WarenkorbFenster warenkorbPlus = new WarenkorbFenster(null, ausgewaehlterArtikel);
					warenkorbPlus.setLocationRelativeTo(hauptFenster);
					warenkorbPlus.setVisible(true);
				}else{
					JOptionPane dialog = new JOptionPane();
					JOptionPane.showMessageDialog(ArtikelPanel.this,
							"Bitte w�hlen Sie zuerst einen Artikel aus!", "Error", JOptionPane.ERROR_MESSAGE);
					dialog.setVisible(true);
				}
	        }
	    };
	    return warenkorbListener;
	}
	
	/**
	 * Die Methode implementiert den ActionListener f�r den Artikel-verwalten-Button und gibt diesen zur�ck.
	 * @return Gibt den ActionListener f�r den Artikel-verwalten-Button zur�ck.
	 */
	private ActionListener artikelVerwaltenListener(){
		ActionListener artikelVerwaltenListener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//�ffnet ein JOptionPane, falls man nicht als Mitarbeiter eingeloggt ist und beendet den Vorgang:
				if(HauptFenster.benutzer == null || HauptFenster.benutzer instanceof Kunde){
					JOptionPane dialog = new JOptionPane();
					JOptionPane.showMessageDialog(ArtikelPanel.this, "Sie m�ssen sich zuerst als Mitarbeiter einloggen, um Artikel verwalten zu k�nnen.",
							"Error", JOptionPane.ERROR_MESSAGE);
					dialog.setVisible(true);
					return;
				}
				//�ffnet ein Pop-Up, in welchem die auszuf�hrende Aktion abgefragt wird:
				ArtikelVerwaltenFenster verwalten = new ArtikelVerwaltenFenster(hauptFenster, ArtikelPanel.this.getAusgewaehlterArtikel());
				verwalten.setLocationRelativeTo(hauptFenster);
				verwalten.setVisible(true);
				//teilt dem TableModel mit, dass sich die Daten ge�ndert haben:
				ArtikelPanel.this.tableModel.fireTableDataChanged();
			}
		};
		return artikelVerwaltenListener;
	}
	
	/**
	 * Die Methode implementiert den ActionListener f�r den Hinzuf�gen Button und gibt diesen zur�ck.
	 * @return Gibt den ActionListener f�r den Hinzuf�gen Button zur�ck.
	 */
	private ActionListener artikelHinzufuegen(){
		ActionListener hinzufuegenListener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//liest alle ben�tigten Daten ein und wirft ggf. Exceptions:
				//Die Texte der Exceptions wurden im Folgenden von Hand eingetragen, damit der
				//Benutzer wei�, wo er einen Fehler gemacht hat.
				String name = artikelNameText.getText();
				//String beschreibung = artikelBeschreibungText.getText();
				int menge = -1;
				try{
					menge = Integer.parseInt(artikelMengeText.getText());
				}catch(NumberFormatException e){
					JOptionPane dialog = new JOptionPane();
					JOptionPane.showMessageDialog(ArtikelPanel.this, "Sie m�ssen eine Menge angeben.", "Error", JOptionPane.ERROR_MESSAGE);
					dialog.setVisible(true);
					return;
				}
				/*int mindestBestand = -1;
				try{
					mindestBestand = Integer.parseInt(artikelMindestbestandText.getText());
				}catch(NumberFormatException e){
					JOptionPane dialog = new JOptionPane();
					JOptionPane.showMessageDialog(ArtikelPanel.this, "Sie m�ssen einen Mindestbestand angeben.", "Error", JOptionPane.ERROR_MESSAGE);
					dialog.setVisible(true);
					return;
				}*/
				int einheit = -1;
				try{
					einheit = Integer.parseInt(artikelEinheitText.getText());
				}catch(NumberFormatException e){
					JOptionPane dialog = new JOptionPane();
					JOptionPane.showMessageDialog(ArtikelPanel.this, "Sie m�ssen eine Verkaufseinheit angeben.", "Error", JOptionPane.ERROR_MESSAGE);
					dialog.setVisible(true);
					return;
				}
				float stueckPreis = -1.00f;
				try{
				 stueckPreis = Float.parseFloat(artikelPreisText.getText().replace(',', '.'));
				}catch(NumberFormatException e){
					JOptionPane dialog = new JOptionPane();
					JOptionPane.showMessageDialog(ArtikelPanel.this, "Sie m�ssen einen St�ckpreis angeben.", "Error", JOptionPane.ERROR_MESSAGE);
					dialog.setVisible(true);
					return;
				}
				
				//pr�ft, ob in jedes Feld ein Wert eingegeben wurde:
				if(name.length() == 0 ||
				   /*beschreibung.length() == 0 ||*/
				   artikelMengeText.getText().length() == 0 ||
				   /*artikelMindestbestandText.getText().length() == 0 ||*/
				   artikelEinheitText.getText().length() == 0 ||
				   artikelPreisText.getText().length() == 0){
					JOptionPane dialog = new JOptionPane();
					JOptionPane.showMessageDialog(ArtikelPanel.this, "Sie m�ssen in jedes Feld einen Wert eintragen.", "Error", JOptionPane.ERROR_MESSAGE);
					dialog.setVisible(true);
					return;
				}
				
				//holt sich die neue Artikel-Nummer aus der EShopVerwaltung:
				//int nummer = HauptFenster.shopVerwaltung.getArtikelNummer();
				//Artikel a = null;
				
				//pr�ft, ob der neue Artikel ein Massengut ist oder nicht, legt den Artikel an
				//und wirft ggf. Exceptions:
				try {
					if(einheit > 1){
						HauptFenster.shopVerwaltung.fuegeArtikelEin(name, menge, einheit*stueckPreis, einheit, stueckPreis);
						/*a = new MehrfachArtikel(artikelName, menge, d, packungsGroesse);
						HauptFenster.shopVerwaltung.artikelAnlegen(a);*/
					}else{
						/*a = new Artikel(artikelName, menge, d);
						HauptFenster.shopVerwaltung.fuegeArtikelEin(a);*/
						HauptFenster.shopVerwaltung.fuegeArtikelEin(name, menge, stueckPreis);
					}
					//HauptFenster.shopVerwaltung.lagerEreignisEinfuegen(1, HauptFenster.benutzer, a, menge);
				} catch (EinlagernException e) {
					JOptionPane dialog = new JOptionPane();
					JOptionPane.showMessageDialog(ArtikelPanel.this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					dialog.setVisible(true);
				}
				
				//speichern:
				/*try {
					HauptFenster.shopVerwaltung.artikelSpeichern();
					HauptFenster.shopVerwaltung.lagerEreignisseSpeichern();
				} catch (KonnteNichtSpeichernException e) {
					JOptionPane dialog = new JOptionPane();
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					dialog.setVisible(true);
				}*/
				
				//Bei Erfolg wird dem Nutzer mitgeteilt, dass der Artikel zum EShop hinzugef�gt wurde:
				JOptionPane dialog = new JOptionPane();
				JOptionPane.showMessageDialog(ArtikelPanel.this, "Der Artikel wurde zum EShop hinzugef�gt.", "Artikel hinzugef�gt", JOptionPane.INFORMATION_MESSAGE);
				dialog.setVisible(true);
				//teilt dem TableModel mit, dass sich die Daten ge�ndert haben:
				ArtikelPanel.this.tableModel.fireTableDataChanged();
			}
		};
		return hinzufuegenListener;
	}
}
