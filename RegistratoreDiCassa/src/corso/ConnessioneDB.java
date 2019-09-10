package corso;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import internalconnection.Product;

// ConnesioneDB.getConnection()
public class ConnessioneDB {
	
	static private Connection conn = null;
	private PreparedStatement stmt_getProduct = null;
	private PreparedStatement stmt_addProduct = null;
	private PreparedStatement stmt_editQta = null;
	private PreparedStatement stmt_creaScontrino = null;
	private PreparedStatement stmt_getCodice = null;
	private PreparedStatement stmt_editTotale = null;
	private PreparedStatement[] allStmt = {stmt_getProduct, stmt_addProduct, stmt_editQta, stmt_creaScontrino, stmt_getCodice, stmt_editTotale}; 
	
	private final static String QUERY_GET_PRODUCT = "SELECT * FROM prodotti";
	private final static String QUERY_ADD_PRODUCT = "INSERT INTO prodotti_scontrini VALUES (?, ?, ?, ?)";
	private final static String QUERY_EDT_QTA = "UPDATE prodotti_scontrini SET qta = (?) WHERE Codice_prodotto = (?)";
	private final static String QUERY_NEW_SCONTRINO = "INSERT INTO scontrini (iva) VALUES (?)";
	private final static String QUERY_GET_ID_SCONTRINO = "SELECT id FROM scontrini ORDER BY DataOra DESC LIMIT 1";
	private final static String QUERY_EDT_TOT = "UPDATE scontrini SET totale = (?) WHERE ID = (?)";
	
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
		stmt_getCodice = conn.prepareStatement(QUERY_GET_ID_SCONTRINO);
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
	
	/**
	 * Ottieni una ArrayList di Product presentei nel database
	 * 
	 * @param codice del prodotto
	 * @return Product trovato
	 * @throws SQLException
	 */
	
	public ArrayList<Product> listaProdotti () throws SQLException {
		
		ArrayList<Product> aux = new ArrayList<>();
		
		try (ResultSet rs = this.stmt_getProduct.executeQuery()){
			while(rs.next()) {
			aux.add(new Product (rs.getString("codice"), rs.getString("nome"), rs.getFloat("prezzo")));
			}
		} catch (SQLException sqle) {
			System.out.println("Errore nelle QUERY_GET_PRODUCT");
			sqle.printStackTrace();
		}
		
		return aux;
	}
	
	/**
	 * Aggiungi prodotto a prodotti_scontrini
	 * @throws SQLException 
	 */
	
	public void aggiungiProdotto (int codScontrino, String codiceProdotto, int qta, float prezzo_applicato) throws SQLException {
		
		this.stmt_addProduct.setInt(1, codScontrino);
		this.stmt_addProduct.setString(2, codiceProdotto);
		this.stmt_addProduct.setInt(3, qta);
		this.stmt_addProduct.setFloat(4, prezzo_applicato);
		
		this.stmt_addProduct.executeUpdate();
	}
	
	/**
	 * Aggiorna la quantità del prodotto scelto
	 * 
	 * @param codice del prodotto
	 * @param newQta nuova quantità
	 * @throws SQLException
	 */
	
	
	public void aggiornaQta (String codice, int newQta) throws SQLException {
		
		this.stmt_editQta.setInt(1, newQta);
		this.stmt_editQta.setString(2, codice);
		
		this.stmt_editQta.executeUpdate();
	}
	
	/**
	 * Crea un istanza di scontrino nella tabella scontrini
	 * 
	 * @param iva
	 * @throws SQLException
	 * @return codice dello scontrino appena creato
	 */
	
	public int creaScontrino (float iva) throws SQLException {
		
	
		this.stmt_creaScontrino.setFloat(1, iva);
		
		this.stmt_creaScontrino.executeUpdate();
		
		int cod = 0;
		
		//Cerca il codice dello scontrino appena creato
		try (ResultSet rs = this.stmt_getCodice.executeQuery()){
			cod = rs.getInt(1);
		} catch (SQLException sqle) {
			//
		}
		
		return cod;
	}
	
	/**
	 * Aggiorna il conto totale dello scontrino selezionato
	 * 
	 * @param prezzoNuovo 
	 * @param idScontrino 
	 * @throws SQLException
	 */
	
	public void aggionraTot (float prezzoNuovo, int idScontrino) throws SQLException {
		
		this.stmt_editTotale.setFloat(1, prezzoNuovo);
		this.stmt_editTotale.setInt(2, idScontrino);
		
		this.stmt_editTotale.executeUpdate();
	}
	

}
