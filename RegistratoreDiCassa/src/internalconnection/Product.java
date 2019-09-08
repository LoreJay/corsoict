package internalconnection;


public class Product {

	final static String SELECT_STRING = "--- seleziona un prodotto ---";

	private String productName;
	private float price;

	public Product(String nome, float prezzo) {
		this.productName = nome;
		this.price = prezzo;
	}

	public Product() {
		//DEFAULT: serve per le sottoclassi
	}

	public float getPrice() {
		return price;
	}
	
	public String getName() {
		return productName;
	}

	@Override
	public String toString() {
		return "Nome: " + this.productName;
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
