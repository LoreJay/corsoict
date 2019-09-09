package corso;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import internalconnection.Product;

// ConnesioneDB.getConnection()
public class ConnessioneDB {
	
	static private Connection conn = null;
	private PreparedStatement stmt_getProduct = null;
	private PreparedStatement stmt_addProduct = null;
	private PreparedStatement stmt_editQta = null;
	private PreparedStatement stmt_creaScontrino = null;
	private PreparedStatement stmt_editTotale = null;
	private PreparedStatement[] allStmt = {stmt_getProduct, stmt_addProduct, stmt_editQta, stmt_creaScontrino, stmt_editTotale}; 
	
	private final static String QUERY_GET_PRODUCT = "SELECT * FROM prodotti WHERE prodotti.codice = (?)";
	private final static String QUERY_ADD_PRODUCT = "INSERT INTO prodotti_scontrini VALUES (?, ?, ?, ?)";
	private final static String QUERY_EDT_QTA = "UPDATE prodotti_scontrini VALUSE qta = (?) WHERE Codice_prodotto = (?)";
	private final static String QUERY_NEW_SCONTRINO = "INSERT INTO scontrini VALUES (?, 0, ?, ?)";
	private final static String QUERY_EDT_TOT = "UPDATE scontrini VALUES totale = (?) WHERE ID = (?)";
	
	public ConnessioneDB() throws SQLException {
		conn = getConnection();
		prepareStatements();
	}
	
	static Connection getConnection() throws SQLException {
		if (conn != null) {
			return conn;
		}
		
		// jdbc:mysql://db_host/db_name
		
		String url= String.format("jdbc:mysql://%s/%s", Settings.DB_HOST, Settings.DB_NAME);
		conn = DriverManager.getConnection(url, Settings.DB_USER, Settings.DB_PASS);  //apertura connessione
		return conn;
	}
	
	void prepareStatements() throws SQLException {
		
		stmt_getProduct = conn.prepareStatement(QUERY_GET_PRODUCT);
		stmt_addProduct = conn.prepareStatement(QUERY_ADD_PRODUCT);
		stmt_editQta = conn.prepareStatement(QUERY_EDT_QTA);
		stmt_creaScontrino = conn.prepareStatement(QUERY_NEW_SCONTRINO);
		stmt_editTotale = conn.prepareStatement(QUERY_EDT_TOT);
	}
	
	void close() throws SQLException{
		
		for (int i = 0; i < allStmt.length; i++) {
			if (allStmt[i] != null)
				allStmt[i].close();
		}
		
		if(conn != null) {
			conn.close();
			conn = null;
		}
	}
	
	public Product cercaProdotto (String codice) throws SQLException {
		
		Product aux = null;
		this.stmt_getProduct.setString(1, codice);
		
		try (ResultSet rs = this.stmt_getProduct.executeQuery()){
			aux = new Product (rs.getString("codice"), rs.getString("nome"), rs.getFloat("prezzo"));
		} catch (SQLException sqle) {
			System.out.println("Errore nelle QUERY_GET_PRODUCT");
			sqle.printStackTrace();
		}
		
		return aux;
	}
	
	/**
	 * Aggiungi prodotto a prodotti_scontrini
	 */
	
	public void aggiungiProdotto (int codScontrino, String codiceProdotto, int qta, float prezzo_applicato) {
		
	}
	
	public void aggiornaQta (String codice, int newQta) throws SQLException {
		
		this.stmt_editQta.setInt(1, newQta);
		this.stmt_editQta.setString(2, codice);
		
		this.stmt_editQta.executeUpdate();
	}
	
	public void creaScontrino (int codice, float iva) throws SQLException {
		
		this.stmt_creaScontrino.setInt(1, codice);
		this.stmt_creaScontrino.setFloat(3, iva);
		
		this.stmt_creaScontrino.executeUpdate();
	}
	

}
