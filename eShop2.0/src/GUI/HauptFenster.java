package GUI;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import eshop.client.domain.EShopVerwaltung;
import eshop.client.ui.gui.ArtikelPanel;
import eshop.client.ui.gui.HauptFenster;
import eshop.client.ui.gui.LagerEreignisPanel;
import eshop.client.ui.gui.VerwaltungsPanel;
import Domain.ShopVerwaltung;
import Valueobjects.Artikel;
import Valueobjects.User;


public class HauptFenster extends JFrame{
	JTabbedPane tabsPanel;
	private ArtikelPanel artikelPanel;
	private WarenkorbPanel warenkorbPanel;
	private VerwaltungsPanel verwaltungsPanel;
	private LagerEreignisPanel lagerPanel;
	
	public static User benutzer = null;
	public static ShopVerwaltung shopVerwaltung = null;
	public static Artikel artikel = null;
	
	public HauptFenster(ShopVerwaltung esv) {
		super("eShop");
		
		HauptFenster.shopVerwaltung = esv;
		
		this.initialisieren();
		this.pack();
		this.setLocationRelativeTo(this.getParent());
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void initialisieren() {
		this.setLayout(new BorderLayout());

		this.centerPanelErzeugen();
		this.northPanelErzeugen();
	}
	
	private void northPanelErzeugen(){
		//initialisiert ein VerwaltungsPanel und fügt es in das Layout ein:
		verwaltungsPanel = new VerwaltungsPanel(this, warenkorbPanel, artikelPanel, lagerPanel);
		
		this.add(verwaltungsPanel, BorderLayout.NORTH);
	}
	
	private void centerPanelErzeugen(){
		//die Bestandteile des JTabbedPane:
		tabsPanel = new JTabbedPane();
		artikelPanel = new ArtikelPanel(this);	
		lagerPanel = new LagerEreignisPanel();
		
		//fügt einen Tab zum JTabbedPane hinzu:
		tabsPanel.addTab("Artikel", artikelPanel);
		
		//fügt das JTabbedPane in das Layout ein:
		this.add(tabsPanel, BorderLayout.CENTER);
	}

}
