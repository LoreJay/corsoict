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
		JButton btn = new JButton("DEBUGG");
		frame.setLayout(new GridLayout(1,2));
		
		btn.addActionListener(new ProductListDemo());
		frame.add(btn);

		frame.add(list);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		frame.setPreferredSize(new Dimension(300,200));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack(); // Ridimensiona il frame
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		list.prodottoAggiunto(new AggiuntoProdotto(list, new Product("provaBottoneOK", 10f))); // DEBUGG
		//frame.pack();
	}

}
