package demos;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
		
		/*
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.BOTH;
		*/
		btn.addActionListener(new ProductListDemo());
		frame.add(btn);

		frame.add(list);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		frame.setPreferredSize(new Dimension(300,200));
		
		// list.prodottoAggiunto(new AggiuntoProdotto(list, new Product("prova2",
		// 10f))); //DEBUGG
		frame.pack(); // Ridimensiona il frame
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		list.prodottoAggiunto(new AggiuntoProdotto(list, new Product("provaBottoneOK", 10f))); // DEBUGG
		//frame.pack();
	}

}
