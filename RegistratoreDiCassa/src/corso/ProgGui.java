package corso;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import internalconnection.Product;

@SuppressWarnings("serial")
public class ProgGui extends JFrame {

	public static void main (String[] args) {
		//Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
	
	//Variabili
	int codiceScontrino; //codice dello scontrino attualmente in uso
	
	
	//Widgets
	KeyPad keypad = new KeyPad();
	ProductList list = new ProductList();
	ProductSelector select = new ProductSelector();
	JLabel tot = new JLabel("TOTALE €");	
	
	ProgGui(){
		
		// Connessioni fra i widget
		select.addInternalListener(list); //aggiungo list agli ascoltatori di select		
		
		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints constr = new GridBagConstraints();
		this.setLayout(grid);
		
		// Label Totale 
		constr.gridx = 0;
		constr.gridy = 0;
		constr.gridwidth = 2;
		
		tot.setHorizontalAlignment(JLabel.CENTER);
		tot.setVerticalAlignment(JLabel.CENTER);
		tot.setPreferredSize(new Dimension(300, 50));
		this.add(tot, constr);
		
		
		// ProductSelecter
		
		constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 1;
		constr.gridwidth = 2;
		constr.weightx = 0.5;
		
		select.setPreferredSize(new Dimension(300, 90));
		this.add(select, constr);
		
		// KeyPad
		constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 2;
		constr.gridwidth = 1;
		constr.weightx = 0.5;

		
		keypad.setPreferredSize(new Dimension(150, 150));
		this.add(keypad, constr);
		
		// Print button
		constr = new GridBagConstraints();
		JButton btn_print = new JButton("Print");
		
		constr.fill = GridBagConstraints.NONE;
		constr.gridx = 1;
		constr.gridy = 2;
		constr.gridwidth = 1;
		constr.anchor = GridBagConstraints.CENTER;
		constr.insets = new Insets(20, 20, 20, 20);
		
		btn_print.setPreferredSize(new Dimension(100, 50));
		this.add(btn_print, constr);
		
		// List
		constr = new GridBagConstraints();
		constr.gridx = 2;
		constr.gridy = 0;
		constr.gridheight = 3;
		constr.fill = GridBagConstraints.BOTH;
		constr.weightx = 1;
		constr.weighty = 1;
		list.setOpaque(true);
		
		// Database
		this.aggiungiProdottiSelector();
		this.creaScontrino();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(list, constr);
		this.setVisible(true);
		this.pack();
	}
	

	public static void createAndShowGUI() {
		
		ProgGui frame = new ProgGui();	
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);
		frame.pack();
	}

	/**
	 * Crea uno scontrino vuoto a cui verranno aggiunti i prodotti
	 */
	
	private void creaScontrino () {
		
		ConnessioneDB database = null;
		
		try {
			database = new ConnessioneDB();
			codiceScontrino = database.creaScontrino(Settings.IVA);
		} catch (SQLException sqle) {
			System.out.println("Errore di connessione al database");
			sqle.printStackTrace();
		} finally {
			try {
				database.close();
			} catch (SQLException e) {
				System.out.println("ERRORE nella chiusura del database!");
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Prende dal database la lista dei prodotti e la carica nella combo box di ProductSelector
	 */
	
	private void aggiungiProdottiSelector () {
		
		ConnessioneDB database = null;
		ArrayList<Product> prodotti = new ArrayList<>();
		
		try {
			database = new ConnessioneDB();
			prodotti = database.listaProdotti();
		} catch (SQLException sqle) {
			System.out.println("Errore di connessione al database");
			sqle.printStackTrace();
		} finally {
			try {
				database.close();
			} catch (SQLException e) {
				System.out.println("ERRORE nella chiusura del database!");
				e.printStackTrace();
			}
		}
		
		select.addProducts(prodotti);
		
	}
	
}
