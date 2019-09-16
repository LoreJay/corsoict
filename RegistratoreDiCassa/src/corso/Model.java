package corso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import internalconnection.Product;

/**
 * Modelli della tabelle del database
 * @author Lorenzo Cioni
 *
 */

public class Model {


	private PreparedStatement stmt_getProductList = null;

	private PreparedStatement stmt_addProduct = null;
	private PreparedStatement stmt_getQtaProduct = null;
	private PreparedStatement stmt_editQtaPrezzo = null;
	private PreparedStatement stmt_getPrezzo = null;

	private PreparedStatement stmt_creaScontrino = null;
	private PreparedStatement stmt_getCodice = null;
	private PreparedStatement stmt_editTotale = null;

	private PreparedStatement[] allStmt = {
			stmt_getProductList, 
			stmt_addProduct, 
			stmt_editQtaPrezzo, 
			stmt_creaScontrino,
			stmt_getQtaProduct, 
			stmt_getCodice, 
			stmt_editTotale, 
			stmt_getQtaProduct, 
			stmt_getPrezzo };

	private Connection conn = null; 
	
	// Prodotti
	private final static String QUERY_GET_PRD_LIST = "SELECT * FROM prodotti";

	// Prodotti_scontrini
	private final static String QUERY_ADD_PRODUCT = "INSERT INTO prodotti_scontrini VALUES (?, ?, ?, ?)";
	private final static String QUERY_GET_QTA = "SELECT qta FROM prodotti_scontrini WHERE codice_prodotto = (?)";
	private final static String QUERY_GET_PREZZO = "SELECT prezzo FROM prodotti_scontrini WHERE codice_prodotto = (?)";
	private final static String QUERY_UPDATE_QTAPREZZO = "UPDATE prodotti_scontrini SET qta = (?), prezzo_applicato = (?) WHERE Codice_prodotto = (?)";

	// Scontrini
	private final static String QUERY_NEW_SCONTRINO = "INSERT INTO scontrini (iva) VALUES (?)";
	private final static String QUERY_GET_ID_SCONTRINO = "SELECT id FROM scontrini ORDER BY DataOra DESC LIMIT 1";
	private final static String QUERY_EDT_TOT = "UPDATE scontrini SET totale = (?) WHERE ID = (?)";

	void prepareStatements() throws SQLException {

		conn = ConnessioneDB.getConnection();
		
		stmt_getProductList = conn.prepareStatement(QUERY_GET_PRD_LIST);

		stmt_addProduct = conn.prepareStatement(QUERY_ADD_PRODUCT);
		stmt_getQtaProduct = conn.prepareStatement(QUERY_GET_QTA);
		stmt_getPrezzo = conn.prepareStatement(QUERY_GET_PREZZO);
		stmt_editQtaPrezzo = conn.prepareStatement(QUERY_UPDATE_QTAPREZZO);

		stmt_creaScontrino = conn.prepareStatement(QUERY_NEW_SCONTRINO);
		stmt_getCodice = conn.prepareStatement(QUERY_GET_ID_SCONTRINO);
		stmt_editTotale = conn.prepareStatement(QUERY_EDT_TOT);
	}

	void close() throws SQLException {

		for (int i = 0; i < allStmt.length; i++) {
			if (allStmt[i] != null)
				allStmt[i].close();
		}

		if (conn != null) {
			conn.close();
			conn = null;
		}
	}

	/**
	 * Prende la quantià del prodotto
	 * 
	 * @return
	 * @throws SQLException 
	 */

	public int getQta(String codiceProdotto) throws SQLException {
		int qta = 0;
		
		stmt_getPrezzo.setString(1, codiceProdotto);
		
		try (ResultSet rs = this.stmt_getProductList.executeQuery()) {
			while (rs.next()) {
				qta = rs.getInt("qta");
			}
		} catch (SQLException sqle) {
			System.out.println("Errore nelle QUERY_GET_PRODUCT");
			sqle.printStackTrace();
		}

		return qta;
	}

	/**
	 * Prendere il prezzo del prodotto
	 * @return
	 * @throws SQLException 
	 */
	
	public float getPrezzo(String codiceProdotto) throws SQLException {
		float prezzo = 0;
		
		stmt_getPrezzo.setString(1, codiceProdotto);
		
		try (ResultSet rs = this.stmt_getProductList.executeQuery()) {
			while (rs.next()) {
				prezzo = rs.getInt("prezzo");
			}
		} catch (SQLException sqle) {
			System.out.println("Errore nelle QUERY_GET_PRODUCT");
			sqle.printStackTrace();
		}

		return prezzo;
	}
	
	
	/**
	 * Aggiorna la quantità del prodotto scelto
	 * 
	 * @param codice del prodotto
	 * @param newQta nuova quantità
	 * @throws SQLException
	 */

	public void aggiornaQta(String codice, int newQta, float newPrezzo) throws SQLException {

		this.stmt_editQtaPrezzo.setInt(1, newQta);
		this.stmt_editQtaPrezzo.setFloat(2, newPrezzo);
		this.stmt_editQtaPrezzo.setString(3, codice);

		this.stmt_editQtaPrezzo.executeUpdate();
	}

	/**
	 * Crea un istanza di scontrino nella tabella scontrini
	 * 
	 * @param iva
	 * @throws SQLException
	 * @return codice dello scontrino appena creato
	 */

	public int creaScontrino(float iva) throws SQLException {

		this.stmt_creaScontrino.setFloat(1, iva);

		this.stmt_creaScontrino.executeUpdate();

		int cod = 0;

		// Cerca il codice dello scontrino appena creato
		try (ResultSet rs = this.stmt_getCodice.executeQuery()) {
			cod = rs.getInt(1);
		} catch (SQLException sqle) {
			//
		}

		return cod;
	}
	
	/**
	 * 
	 */
	
	public int getId() {
		int id = 0;
		
		try (ResultSet rs = this.stmt_getCodice.executeQuery()) {
			while (rs.next()) {
				id = rs.getInt("id");
			}
		} catch (SQLException sqle) {
			System.out.println("Errore nelle QUERY_GET_PRODUCT");
			sqle.printStackTrace();
		}
		
		return id;
	}
	

	/**
	 * Aggiorna il conto totale dello scontrino selezionato
	 * 
	 * @param prezzoNuovo
	 * @param idScontrino
	 * @throws SQLException
	 */

	public void aggionraTot(float prezzoNuovo, int idScontrino) throws SQLException {

		this.stmt_editTotale.setFloat(1, prezzoNuovo);
		this.stmt_editTotale.setInt(2, idScontrino);

		this.stmt_editTotale.executeUpdate();
	}

	
	class Prodotto {
		Vector<Product> table_prodotto = new Vector<>();
		
		/**
		 * Ottieni una ArrayList di Product presentei nel database
		 * 
		 * @param codice del prodotto
		 * @return Product trovato
		 * @throws SQLException
		 */

		public ArrayList<Product> listaProdotti() throws SQLException {

			ArrayList<Product> aux = new ArrayList<>();

			try (ResultSet rs = stmt_getProductList.executeQuery()) {
				while (rs.next()) {
					aux.add(new Product(rs.getString("codice"), rs.getString("nome"), rs.getFloat("prezzo")));
				}
			} catch (SQLException sqle) {
				System.out.println("Errore nelle QUERY_GET_PRODUCT");
				sqle.printStackTrace();
			}

			return aux;
		}	
	}
	
	class ProdottoScontrino {
		Vector<Product> table_prodScontrini = new Vector<>();
		
		/**
		 * Aggiungi prodotto a prodotti_scontrini
		 * 
		 */

		public void aggiungiProdotto(int codScontrino, String codiceProdotto, int qta, float prezzo_applicato)
				throws SQLException {

			stmt_addProduct.setInt(1, codScontrino);
			stmt_addProduct.setString(2, codiceProdotto);
			stmt_addProduct.setInt(3, qta);
			stmt_addProduct.setFloat(4, prezzo_applicato);

			stmt_addProduct.executeUpdate();
		}
	}
	
	class Scontrino {
		Vector<Product> table_scontrini = new Vector<>();
	}
	
}
