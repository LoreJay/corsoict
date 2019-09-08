package corso;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.table.AbstractTableModel;

import internalconnection.AggiuntoProdotto;
import internalconnection.InterListener;
import internalconnection.Product;
import internalconnection.Product.DummyProduct;

@SuppressWarnings("serial")
public class ProductList extends JPanel implements InterListener {

	static MyTableModel myModel;
	static JTable table;
	static JScrollPane scrollPane;
	static Vector<Product> data = new Vector<>();
	static boolean isEmpty = true;
	
	public ProductList() {
		super(new GridLayout(1, 0));

		myModel = new MyTableModel();
		table = new JTable(myModel);
		
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);

		// Create the scroll pane and add the table to it.
		scrollPane = new JScrollPane(table);

		// Add the scroll pane to this panel.
		add(scrollPane);
		
		isEmpty = false;

	}

	private class MyTableModel extends AbstractTableModel {
		
		String[] columnNames = { "Nome", "Prezzo" };
		//Vector<Product> data = new Vector<>();

		@Override
		public String getColumnName(int col) {
			return columnNames[col];
		}

		@Override
		public int getRowCount() {
			return data.size();
		}

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			
			if (isEmpty) {
				return null;
			}
			
			if (columnIndex == 1) {
				return data.get(rowIndex).getPrice();
			}
			
			return data.get(rowIndex).getName();
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			// Impostazioni di default (questo Override al momento è inutile)
			return false;
			
		}

		@Override
		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}
		
	}

	public void aggiungiProdotto(Product prodotto) {
		data.add(prodotto);
		myModel.fireTableRowsInserted(data.size() - 1, data.size());
	}

	@Override
	public void prodottoAggiunto(AggiuntoProdotto event) {

		Product prodottoDaAggiungere = event.getProdottoAggiunto();
		
		aggiungiProdotto(prodottoDaAggiungere);
		System.out.println("Evento!");
		for (Product prd : data)
			System.out.println(prd);
	}
}
