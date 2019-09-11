package internalconnection;


public class Product {

	final static String SELECT_STRING = "--- seleziona un prodotto ---";

	private String codice;
	private String nome;
	private float prezzo;
	private float prezzoApplicato;
	private int qta;

	public Product(String nome, float prezzo) {
		this.nome = nome;
		this.prezzo = prezzo;
		this.prezzoApplicato = prezzo;
		this.codice = "ZZZ";
		this.qta = 1;
	}

	public Product(String codice, String nome, float prezzo) {
		this.codice = codice;
		this.nome = nome;
		this.prezzo = prezzo;
		this.prezzoApplicato = prezzo;
		this.qta = 1;
	}
	
	public Product() {
		//DEFAULT: serve per le sottoclassi
	}

	public void updateQtaAndPrezzo() {
		this.qta++;
		this.prezzoApplicato = prezzo * qta;
		
	}
	
	public float getPrezzoApp() {
		return prezzoApplicato;
	}
	
	public float getPrezzo() {
		return prezzo;
	}
	
	public String getName() {
		return nome;
	}
	
	public String getcodice() {
		return codice;
	}

	public int getQta() {
		return qta;
	}

	
	@Override
	public String toString() {
		return "[Codice] " + this.codice + " [Nome] " + this.nome;
	}

	public static class DummyProduct extends Product {

		public DummyProduct() {
			super(SELECT_STRING, 0f);
		}

		@Override
		public String toString() {
			return SELECT_STRING;
		}
	}

}
