package corso;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

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
	
	
	KeyPad keypad = new KeyPad();
	ProductList list = new ProductList();
	ProductSelecter select = new ProductSelecter();
	
	//TableDemo list = new TableDemo();
	
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
		
		/*
		JPanel pnl_aux = new JPanel();
		pnl_aux.setLayout(new GridBagLayout());
		GridBagConstraints constr_aux = new GridBagConstraints();
		System.out.println(btn_print.getPreferredSize());
		System.out.println(btn_print.getSize());
		btn_print.setPreferredSize(new Dimension(50, 50));
		btn_print.setSize(new Dimension(50,50));
		
		
		pnl_aux.add(btn_print,constr_aux);
		 */
		
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
		
		this.add(list, constr);
		
		this.setVisible(true);
		
	}
	

	public static void createAndShowGUI() {
		
		ProgGui frame = new ProgGui();	
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);
		frame.pack();
	}

}
