package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Cette classe implémente l'interface EnchereDAO afin d'utiliser les méthodes de cette dernière pour effectuer des requêtes SQL.
 * @author Groupe 3
 *
 */
public class EnchereDAOImpl implements EnchereDAO {

	// Constantes des requêtes SQL
	private static final String SELECT_ARTICLE = "SELECT * FROM UTILISATEURS AS u INNER JOIN ARTICLES_VENDUS AS a ON u.no_utilisateur = a.no_utilisateur INNER JOIN CATEGORIES AS c ON a.no_categorie = c.no_categorie WHERE date_fin_encheres > GETDATE() AND date_debut_encheres <= GETDATE()";
	private static final String SELECT_MAX_ENCHERE = "SELECT MAX(montant_enchere) AS best_enchere FROM ENCHERES WHERE no_article = ? GROUP BY no_article";
	private static final String SELECT_CATEGORIE = "SELECT * FROM categories";
	
	/**
	 * Méthode qui récupére la liste de toutes les catégories d'articles
	 */
	public List<Categorie> selectAllCategorie() {
		
		List<Categorie> ListCategories = new ArrayList<Categorie>();
		
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_CATEGORIE);
			ResultSet rs = pstmt.executeQuery();
			
			while ( rs.next() )
			{
				Categorie categorie = new Categorie();
                categorie.setnoCategorie(rs.getInt("no_categorie"));
                categorie.setLibelle(rs.getString("libelle"));
                ListCategories.add(categorie);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return ListCategories;
		
	}
	
	/**
	 * Méthode rapportant toutes les enchères en cours. 
	 * Le prix de vente, d'un article en train d'être en cours d'enchère, est valorisé selon le prix de la meilleure enchère qui lui est faite ; sinon par defaut son prix de vente sera son prix initial (pour affichage).
	 * @return La liste des articles en cours d'enchères.
	 */
	@Override
	public List<Article> selectAllSales() throws BusinessException {
		// declarations
		PreparedStatement psmt = null;
		ResultSet rs = null;
		ResultSet rs_bis = null;
		List<Article> listeArticles = new ArrayList<>();
		Categorie categorie = null;
		Utilisateur utilisateur = null;
		Article article = null;
		Integer prixVente = null;
		
		// Connexion à la BDD & Préparation de la requete (leurs fermetures y sont implicite)
		try ( Connection cnx = ConnectionProvider.getConnection() ) {
			// Exécution de la requete
			cnx.setAutoCommit(false);
			psmt = cnx.prepareStatement(SELECT_ARTICLE);
			rs = psmt.executeQuery();
			
			// tant que l'on a un article en retour
			while ( rs.next() ) {
				categorie = new Categorie(
						rs.getInt("no_categorie"), 
						rs.getString("libelle")
				);
				utilisateur = new Utilisateur(
						rs.getInt("no_utilisateur"), 
						rs.getString("pseudo"), 
						rs.getString("nom"), 
						rs.getString("prenom"), 
						rs.getString("email"), 
						rs.getString("telephone"), 
						rs.getString("rue"), 
						rs.getString("code_postal"), 
						rs.getString("ville"), 
						rs.getString("mot_de_passe"), 
						rs.getInt("credit"), 
						rs.getBoolean("administrateur")
				);
				// requete imbriqué
				// on cherche à afficher le prix de vente ou le prix d'enchere le plus élevé
				psmt = cnx.prepareStatement(SELECT_MAX_ENCHERE);
				psmt.setString(1, String.valueOf(rs.getInt("no_article")) );
				rs_bis = psmt.executeQuery();
				if(rs_bis.next()) {
					// Si le prix d'une sur-enchère existe et qu'elle est superieur au prix initial
					if(rs_bis.getInt("best_enchere") > rs.getInt("prix_initial") ) {
						// prixVente es tvalorisé pas le prix de la sur-enchère
						prixVente = rs_bis.getInt("best_enchere");
					} else {
						// sinon le prix de vente est valorisé par son prix initial
						prixVente = rs.getInt("prix_initial");
					}
				} else {
					prixVente = rs.getInt("prix_initial");
				}
				// on hydrate l'instance Article
				article = new Article(
						rs.getInt("no_article"),
						rs.getString("nom_article"), 
						rs.getString("description"), 
						rs.getDate("date_debut_encheres"), 
						rs.getDate("date_fin_encheres"), 
						rs.getInt("prix_initial"), 
						prixVente, 
						categorie,
						utilisateur
				);
				listeArticles.add(article);
			}
			cnx.commit();
		// S'il y a une erreur on l'enregistre dans la businessEx
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			BusinessException businessException = new BusinessException();
			// TODO Changer code d'erreur
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		} finally {
			try {
				psmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listeArticles;
	}

}
