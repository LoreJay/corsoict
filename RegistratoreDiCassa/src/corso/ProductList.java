package corso;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import internalconnection.AggiuntoProdotto;
import internalconnection.InterListener;
import internalconnection.Product;

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
		table.setRowSelectionAllowed(false);
		
		table.setPreferredScrollableViewportSize(new Dimension(250, 70));
		table.setFillsViewportHeight(true);

		// Create the scroll pane and add the table to it.
		scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		// Add the scroll pane to this panel.
		add(scrollPane);
		
		isEmpty = false;

	}

	private class MyTableModel extends AbstractTableModel {
		
		String[] columnNames = {"Nome", "Prezzo" };

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
				return "€ " + data.get(rowIndex).getPrezzo();
			}
			
			return data.get(rowIndex).getName();
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			// Impostazioni di default (questo Override al momento Ã¨ inutile)
			return false;
			
		}

		/*
		// Non serve
		@Override
		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}
		*/
		
	}

	public void aggiungiProdotto(Product prodotto) {
		
		
		data.add(prodotto);
		
		myModel.fireTableRowsInserted(0, data.size());
	}

	@Override
	public void prodottoAggiunto(AggiuntoProdotto event) {

		Product prodottoDaAggiungere = event.getProdottoAggiunto();
		
		aggiungiProdotto(prodottoDaAggiungere);
		
		/*
		//DEBUG >>>>
		System.out.println("Evento!");
		for (Object prd : data)
			System.out.println(prd);
		*/
	}
	
}
