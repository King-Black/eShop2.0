package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

import exceptions.NichtGefundenException;
import Valueobjects.Artikel;
import Valueobjects.LagerEreignis;

/**
 * Klasse zur Erstellung des LagerEreignisPanels.
 *
 */
@SuppressWarnings("serial")
public class LagerEreignisPanel extends JPanel {
	private JPanel northPanel;
	private JComboBox<String> artikelAuswahlBox;
	private JButton anzeigenButton;
	private JTable lagerEreignisTabelle;
	private JPanel centerPanel;
	private LagerEreignisTableModel tableModel;
	private int ausgewaehlteArtikelId;
	
	/**
	 * Initialisiert ein LagerEreignisPanel-Objekt.
	 */
	public LagerEreignisPanel(){
		super();
		
		this.initialisieren();
	}
	
	/**
	 * Die Methode setzt das Layout des LagerEreignisPanels und fügt die Hauptkomponenten ein.
	 */
	private void initialisieren(){
		this.setLayout(new BorderLayout());
		
		this.northPanelErzeugen();
		this.centerPanelErzeugen();
	}
	
	/**
	 * Die Methode erzeugt das NorthPanel mit allen Komponenten.
	 */
	private void northPanelErzeugen(){
		//alle Komponenten des NorthPanel initialisieren und ActionListener zum AnzeigenButton hinzufügen:
		northPanel = new JPanel();
		artikelAuswahlBox = new JComboBox<String>(new String[] {"Alle anzeigen"});
		for(Artikel a: HauptFenster.shopVerwaltung.getArtikel()){
			artikelAuswahlBox.addItem(a.getArtikelName());
		}
		anzeigenButton = new JButton("Anzeigen");
		anzeigenButton.addActionListener(this.anzeigenListener());
		
		//alle Komponenten ins Layout einfügen:
		northPanel.add(new JLabel("Welchen Artikel möchten Sie sich anzeigen lassen?"));
		northPanel.add(artikelAuswahlBox);
		northPanel.add(anzeigenButton);
		
		//NorthPanel ins LagerEreignisPanel einfügen:
		this.add(northPanel, BorderLayout.NORTH);
	}
	
	/**
	 * Die Methode implementiert den ActionListener für den Anzeigen-Button und gibt diesen zurück.
	 * @return Gibt den ActionListener für den AnzeigenButton zurück.
	 */
	private ActionListener anzeigenListener() {
		//Selbstreferenz zur Nutzung in der anonymen Klasse:
		final LagerEreignisPanel gui = this;
		ActionListener anzeigen = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//sucht den ausgewählten Artikel im EShop und erstellt eine neue Collection mit
				//den passenden Lagerereignissen:
				try {
					String ausgewaehlterArtikel = (String)artikelAuswahlBox.getSelectedItem();
					for(Artikel a: HauptFenster.eShopVerwaltung.getArtikel()){
						if(a.getArtikelName().equals(ausgewaehlterArtikel)){
							ausgewaehlteArtikelId = a.getId();
						}
					}
					Artikel a = HauptFenster.shopVerwaltung.findeArtikel(gui.ausgewaehlteArtikelId);
					// 1. alle Ereignisse durchgehen und gucken, welche Ereignisse davon die gleiche
					// ID haben wie der eben gerade gefundene Artikel, der jetzt in a steht.
					// 2. alle gefundenen Ereignisse zu diesem Artikel in einer extra Liste zwischenspeichern
					// 3. dann tablemodel initialisieren und im Konstruktor die neue Collection übergeben
					// 4. dann tablemodel setzen
					// 5. im table model getrow nicht mehr getAlleEreignisse aufrufen sondern nurnoch die aus der
					// neuen Collection die dem tablemodel Konstruktor übergeben wurden
					Collection<LagerEreignis> ereignisse = new ArrayList<LagerEreignis>();
					for(LagerEreignis erg: HauptFenster.shopVerwaltung.getLagerEreignisse()){						
						if(erg.getPosition().getArtikel().getId() == a.getId()){
							ereignisse.add(erg);
						}
					}
					if(artikelAuswahlBox.getSelectedIndex() == 0){
						ereignisse = HauptFenster.shopVerwaltung.getLagerEreignisse();
					}
					tableModel = new LagerEreignisTableModel(ereignisse);
					lagerEreignisTabelle.setModel(tableModel);
					gui.tableModel.fireTableDataChanged();
				} catch (NichtGefundenException e) {
					JOptionPane dialog = new JOptionPane();
					JOptionPane.showMessageDialog(LagerEreignisPanel.this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					dialog.setVisible(true);
				}
			}
		};
		return anzeigen;
	}
	
	/**
	 * Die Methode erzeugt das CenterPanel mit allen Komponenten.
	 */
	private void centerPanelErzeugen(){
		//initialisiert alle Komponenten des CenterPanel:
		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(1, 1));
		lagerEreignisTabelle = new JTable();
		tableModel = new LagerEreignisTableModel(HauptFenster.shopVerwaltung.getLagerEreignisse());
		
		//genaue Definition der LagerEreignisTabelle:
		lagerEreignisTabelle.setModel(tableModel);
		lagerEreignisTabelle.getTableHeader().setReorderingAllowed(false);
		lagerEreignisTabelle.setFillsViewportHeight(true);
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();  
		dtcr.setHorizontalTextPosition(DefaultTableCellRenderer.RIGHT); 
		lagerEreignisTabelle.setDefaultRenderer(tableModel.getColumnClass(2), dtcr);
		
		//fügt die LagerEreignisTabelle in ein JScrollPane ein:
		JScrollPane scrollPane = new JScrollPane(lagerEreignisTabelle);
		Border scrollBorder = BorderFactory.createEtchedBorder();
		scrollPane.setBorder(scrollBorder);
		
		//fügt die JScrollPane in das CenterPanel ein:
		centerPanel.add(scrollPane);
		
		//fügt das CenterPanel in das LagerEreignisPanel ein:
		this.add(centerPanel, BorderLayout.CENTER);
	}
}
