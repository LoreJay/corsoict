package internalconnection;

import java.util.EventListener;

public interface InterListener extends EventListener {
	
	/**
	 * Azione effettuata quando viene recepito un evento di tipo AggiuntoProdotto
	 * @param event
	 */
	
	public void prodottoAggiunto (AggiuntoProdotto event);
}
