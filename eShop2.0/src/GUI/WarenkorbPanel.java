package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

import Valueobjects.Kunde;
import Valueobjects.Rechnung;
import exceptions.BereitsVorhandenException;
import exceptions.NichtEingeloggtException;
import exceptions.NichtGenugAufLagerException;
import exceptions.WarenkorbLeerException;


@SuppressWarnings("serial")
public class WarenkorbPanel extends JPanel{
	private JPanel centerPanel;
	private JPanel eastPanel;
	private JTable warenkorbTabelle;
	private WarenkorbTableModel tableModel;
	private JButton kaufenButton;
	private JButton leerenButton;
	private Kunde k;
	private HauptFenster hauptFenster;
	
	public WarenkorbPanel(HauptFenster hf) {
		super();
		
		this.hauptFenster = hf;
		k = (Kunde)HauptFenster.benutzer;
		
		this.initialisieren();
	}
	
	private void initialisieren() {
		this.setLayout(new BorderLayout());
		
		this.centerPanelErzeugen();
		this.eastPanelErzeugen();
	}
	
	private void centerPanelErzeugen() {
		//Komponenten des CenterPanels initialisieren:
		centerPanel = new JPanel();
		warenkorbTabelle = new JTable();
		tableModel = new WarenkorbTableModel();
		
		//TitledBorder um die Tabelle setzen:
		TitledBorder tBorder = BorderFactory.createTitledBorder("Artikel im Warenkorb");
		centerPanel.setBorder(tBorder);
		
		//Warenkorb-Tabelle mit allen Eigenschaften definieren:
		warenkorbTabelle.setModel(tableModel);
		warenkorbTabelle.setAutoCreateRowSorter(true);
		warenkorbTabelle.getTableHeader().setReorderingAllowed(false);
		warenkorbTabelle.setFillsViewportHeight(true);
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();  
		dtcr.setHorizontalTextPosition(DefaultTableCellRenderer.RIGHT); 
		warenkorbTabelle.setDefaultRenderer(tableModel.getColumnClass(0), dtcr);
		warenkorbTabelle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//Warenkorb-Tabelle in ein JScrollPane einfügen:
		JScrollPane scrollPane = new JScrollPane(warenkorbTabelle);
		Border scrollBorder = BorderFactory.createEtchedBorder();
		scrollPane.setBorder(scrollBorder);
		
		//Komponenten zum CenterPanel hinzufügen und CenterPanel ins Layout einfügen:
		centerPanel.add(scrollPane);
		this.add(centerPanel, BorderLayout.CENTER);
	}
	
	private void eastPanelErzeugen(){
		//die Komponenten des EastPanels erzeugen:
		eastPanel = new JPanel();
		kaufenButton = new JButton("Warenkorb kaufen");
		leerenButton = new JButton("Warenkorb leeren");
		
		//die ActionListener auf die Buttons anwenden:
		kaufenButton.addActionListener(this.kaufen());
		leerenButton.addActionListener(this.leeren());
		
		//die Größe des EastPanels festlegen:
		Dimension d = new Dimension(150, 400);
		eastPanel.setMinimumSize(d);
		eastPanel.setPreferredSize(d);
		eastPanel.setMaximumSize(d);
		
		//die Buttons ins EastPanel einfügen:
		eastPanel.add(kaufenButton);
		eastPanel.add(leerenButton);
		
		//das EastPanel ins Layout einfügen:
		this.add(eastPanel, BorderLayout.EAST);
	}
	
	private ActionListener kaufen(){
		ActionListener kaufen = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//öffnet ein JOptionPane mit der Frage nach Kaufbestätigung:
				JOptionPane dialog = new JOptionPane();
				int aktion = JOptionPane.showConfirmDialog(WarenkorbPanel.this,
						"Möchten Sie den Warenkorb jetzt kaufen?", "Warenkorb kaufen", JOptionPane.YES_NO_OPTION);
				dialog.setVisible(true);
				
				//falls ja ausgewählt wurde:
				if(aktion == JOptionPane.YES_OPTION){
					//Artikel kaufen, Rechnung erstellen, Warenkorb leeren und Tabelle updaten
					//und ggf. Fehlermeldungen anzeigen:
					try {
						Rechnung rechnung = HauptFenster.shopVerwaltung.kaufen(k);
						RechnungsFenster rf = new RechnungsFenster(hauptFenster, rechnung);
						rf.setLocationRelativeTo(hauptFenster);
						rf.setVisible(true);
						
						k.getWarenkorb().leeren();
						WarenkorbPanel.this.tableModel.fireTableDataChanged();
					} catch (NichtEingeloggtException e) {
						JOptionPane fehler = new JOptionPane();
						JOptionPane.showMessageDialog(WarenkorbPanel.this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						fehler.setVisible(true);
						return;
					} catch (WarenkorbLeerException e) {
						JOptionPane fehler = new JOptionPane();
						JOptionPane.showMessageDialog(WarenkorbPanel.this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						fehler.setVisible(true);
						return;
					} catch (NichtGenugAufLagerException e) {
						JOptionPane fehler = new JOptionPane();
						JOptionPane.showMessageDialog(WarenkorbPanel.this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						fehler.setVisible(true);
						return;
					} catch (BereitsVorhandenException e) {
						JOptionPane fehler = new JOptionPane();
						JOptionPane.showMessageDialog(WarenkorbPanel.this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						fehler.setVisible(true);
						return;
					} /*catch (FalscherWertException e) {
						JOptionPane fehler = new JOptionPane();
						JOptionPane.showMessageDialog(WarenkorbPanel.this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						fehler.setVisible(true);
						return;
					}*//*Wo soll die Exception geworfen werden?*/
				}
					
				//speichern:
			/*	try {
					HauptFenster.shopVerwaltung.artikelSpeichern();
					HauptFenster.shopVerwaltung.lagerEreignisseSpeichern();
				} catch (KonnteNichtSpeichernException e) {
					JOptionPane dialog2 = new JOptionPane();
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					dialog2.setVisible(true);
				}*/
			}
		};
		return kaufen;
	}
	
	/**
	 * Die Methode implementiert den ActionListener für den Leeren Button und gibt diesen zurück.
	 * @return Gibt den ActionListener für den Leeren Button zurück.
	 */
	private ActionListener leeren(){
		ActionListener leeren = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//öffnet ein JOptionPane mit der Frage nach Leerungsbestätigung:
				JOptionPane dialog = new JOptionPane();
				int aktion = JOptionPane.showConfirmDialog(WarenkorbPanel.this,
						"Möchten Sie den Warenkorb jetzt leeren?", "Warenkorb leeren", JOptionPane.YES_NO_OPTION);
				dialog.setVisible(true);

				//falls ja ausgewählt wurde:
				if(aktion == JOptionPane.YES_OPTION){
					//wenn keine Artikel im Warenkorb sind Fehlermeldung:
					if(k.getWarenkorb().getAnzahlPositionen() == 0){
						JOptionPane fehler = new JOptionPane();
						JOptionPane.showMessageDialog(WarenkorbPanel.this, "Es befinden sich keine Artikel zum Löschen in Ihrem Warenkorb!", "Error", JOptionPane.ERROR_MESSAGE);
						fehler.setVisible(true);
						return;
					}else{
						//ansonsten Warenkorb leeren und Tabelle updaten:
						try {
							k.getWarenkorb().leeren();
						} catch (WarenkorbLeerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						WarenkorbPanel.this.tableModel.fireTableDataChanged();
					}
				}
			}
		};
		return leeren;
	}

}
