package demos;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import corso.ProductList;
import internalconnection.AggiuntoProdotto;
import internalconnection.Product;

public class ProductListDemo implements ActionListener {

	static JFrame frame = new JFrame("Lista dei prodotti");
	static ProductList list = new ProductList();
	
	public static void main(String[] args) {
		frame.setLayout(new GridLayout(1,3));

		JButton btn_add = new JButton("DEBUG>> add");
		btn_add.addActionListener(new ProductListDemo());
		btn_add.setActionCommand("ADD");
		frame.add(btn_add);

		JButton btn_update = new JButton("DEBUG>> update");
		btn_update.addActionListener(new ProductListDemo());
		frame.add(btn_update);
		
		frame.add(list);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		frame.setPreferredSize(new Dimension(500,300));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack(); // Ridimensiona il frame
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand().equals("ADD")) {
			list.aggiungiProdotto(new Product("Nome", 10f));
			return;
		}
		
		list.updateProductIfPresent(new Product ("Nome", 10f));
	}

}
