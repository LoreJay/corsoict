package corso;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import internalconnection.InterListener;
import internalconnection.Product;
import internalconnection.Product.DummyProduct;
import internalconnection.AggiuntoProdotto;

@SuppressWarnings("serial")
public class ProductSelecter extends JPanel {

	// Testo del JButton
	final static String ADD_BTN = "Aggiungi";
	
	// Elemento vuoto della lista
	final static String SELECT_STRING = "--- seleziona un prodotto ---";
	final static DummyProduct SELECT = new DummyProduct();
	
	// Lista vuota: per il costruttore senza parametri
	final static ArrayList<Product> emptyList = new ArrayList<>();

	static JComboBox<Product> cmb_products = new JComboBox<>();
	static JButton btn_add = new JButton(ADD_BTN);
	static JLabel lbl_prezzo = new JLabel();
	
	// Lista ascoltatori
	private Vector<InterListener> ascoltatori = new Vector<>();

	/**
	 * Crea il panel contentente la combo box con la lista dei prodotti e il bottone
	 * "aggiungi". Necessita della lista dei prodotti da aggiungere
	 * 
	 * @param productsList lista dei prodotti da aggiungere
	 * 
	 */

	public ProductSelecter(ArrayList<Product> productsList) {

		
		// Layout del panel (this)
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(layout);

		// JComboBox con la lista dei prodotti
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 5 , 10, 5);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		
		cmb_products.addActionListener(new ComboAction());
		((JLabel)cmb_products.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		this.addProducts(productsList);
		
		this.add(cmb_products, c);

		// Label prezzo

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		lbl_prezzo.setHorizontalAlignment(JLabel.CENTER);
		this.add(lbl_prezzo, c);

		// Bottone
		c = new GridBagConstraints();
		//c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		c.gridy = 1;
		c.anchor = GridBagConstraints.LINE_END;
		
		btn_add.addActionListener(new ButtonAction());
		this.add(btn_add, c);

	}

	/**
	 * Crea il pannello con una combo box con prodotti di prova
	 */

	public ProductSelecter() {
		this(emptyList);
	}

	/**
	 * Aggiunge gli elementi dell'ArrayList in input al JComboBox
	 * 
	 * @param productsList lista dei prodotti da aggiungere
	 */

	public void addProducts(ArrayList<Product> productsList) {

		cmb_products.addItem(SELECT);
		for (Product product : productsList) {
			cmb_products.addItem(product);
		}

		// DEBUG: aggiunge elementi se la lista � vuota
		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		if (productsList.isEmpty()) {
			for (int i = 0; i < 5; i++) {
				cmb_products.addItem(new Product("Prodotto #" + i, i*10));
			}
		}
		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<		
	}
	/**
	 * Aggiunge listener a questo elemento. Saranno aggiunti in ProgGui, saranno KeyPad e ProductList
	 * 
	 * @param listener
	 */
	
	public void addInternalListener (InterListener listener) {
		this.ascoltatori.add(listener);
	}

	/**
	 * Crea e lancia l'evento. Verrà fatto al momento del click sul bottono aggiungi
	 * @param prodottoAggiunto
	 */
	
	public void crealanciaEvento (Product prodottoAggiunto) {
		AggiuntoProdotto event = new AggiuntoProdotto(this, prodottoAggiunto);
		
		for (InterListener listener : ascoltatori)
			listener.prodottoAggiunto(event);
	}
	
	
	/**
	 * ActionListener del bottone
	 */
	
	private class ButtonAction implements ActionListener {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Product selectedProducts = (Product) cmb_products.getSelectedItem();

		// Se non viene selezionato alcun oggetto non fa niente
		if (selectedProducts.equals(SELECT))
			return;

		System.out.println("DEBUG >>> " + selectedProducts);
		// TODO: metodo per mandare la selezione al JList

		cmb_products.setSelectedIndex(0);
		
		crealanciaEvento(selectedProducts);
		
		System.out.println("DEBUGG: evento lanciato");
		
		}
	}
	
	/**
	 * Mostra il prezzo nel JLabel accanto al bottone
	 * 
	 * @author Lorenzo
	 *
	 */
	
	private class ComboAction implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			Product aux = (Product) cmb_products.getSelectedItem();
						
			String prezzo;
			
			if (aux instanceof DummyProduct)
				prezzo = "-prezzo-";
			else
				prezzo = "€ " + aux.getPrice();
			
			lbl_prezzo.setText(prezzo);
			
		}
	}

}
