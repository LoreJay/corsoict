package corso;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import internalconnection.AggiuntoProdotto;
import internalconnection.InterListener;
import internalconnection.Product;

@SuppressWarnings("serial")
public class ProductList extends JPanel {

	private MyTableModel myModel;
	private JTable table;
	private JScrollPane scrollPane;
	private boolean isEmpty = true;
	
	public ProductList() {
		
		super(new GridLayout(1, 0));

		myModel = new MyTableModel();
		table = new JTable(getMyModel());
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
		
		MyTableModel () {
			super();
			this.data = new Vector<Product>();
		}
		
		String[] columnNames = {"#", "Nome", "Prezzo" };
		private Vector<Product> data = new Vector<>();

		public void setData(Vector<Product> data) {
			this.data = data;
		}
		
		public Vector<Product> getData(){
			return this.data;
		}
		
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
		public Object getValueAt (int rowIndex, int columnIndex) {
			
			if (isEmpty) {
				return null;
			}
			
			//TODO
			switch (columnIndex) {
			case 0: return data.get(rowIndex).getQta();
			case 1: return data.get(rowIndex).getName();
			case 2: return data.get(rowIndex).getPrezzoApp();
			}
			
			return null;
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			// Impostazioni di default (questo Override al momento è inutile)
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

	/**
	 * Aggiunge un prodtto alla lista 
	 * 
	 * @param prodotto
	 */
	
	public void aggiungiProdotto (Product prodotto) {
		
		myModel.getData().add(prodotto);	
		myModel.fireTableRowsInserted(0, myModel.getData().size());

	}
	
	/**
	 * Incrementa 
	 * @param prodotto
	 */
	
	public boolean updateProductIfPresent (Product prodotto) {
		
		for (Product aux : myModel.getData()) {
			if (prodotto.getcodice().equals(aux.getcodice())) {
				aux.updateQtaAndPrezzo();
				myModel.fireTableRowsInserted(0, myModel.getData().size());
				return true;
			}
		}		
		
		return false;
	}
	
	/**
	 * Getter: restituisce l'elenco dei prodotti presenti nella lista
	 * 
	 * @return
	 */
	
	public MyTableModel getMyModel() {
		return myModel;
	}
	
	
}
