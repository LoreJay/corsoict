package internalconnection;

import java.util.EventObject;

@SuppressWarnings("serial")
public class AggiuntoProdotto extends EventObject {

	private Product prodottoAggiunto = null;
	
	public AggiuntoProdotto(Object source) {
		super(source);
	}
	
	public AggiuntoProdotto(Object source, Product prodotto) {
		super(source);
		setProdotto(prodotto);
	}
	
	public void setProdotto (Product prodotto) {
		this.prodottoAggiunto = prodotto;
	}

	public Product getProdottoAggiunto() {
		return prodottoAggiunto;
	}
	
}
