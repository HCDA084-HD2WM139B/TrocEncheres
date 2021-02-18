package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Cette classe implémente l'interface EnchereDAO afin d'utiliser les méthodes
 * de cette dernière pour effectuer des requêtes SQL.
 * 
 * @author Groupe 3
 *
 */
public class EnchereDAOImpl implements EnchereDAO {

	// Constantes des requêtes SQL
	private static final String SELECT_ARTICLE = "SELECT * FROM UTILISATEURS AS u INNER JOIN ARTICLES_VENDUS AS a ON u.no_utilisateur = a.no_utilisateur INNER JOIN CATEGORIES AS c ON a.no_categorie = c.no_categorie";
	private static final String SELECT_MAX_ENCHERE = "SELECT MAX(montant_enchere) AS best_enchere FROM ENCHERES WHERE no_article = ? GROUP BY no_article";
	private static final String SELECT_CATEGORIE = "SELECT * FROM categories";
	private static final String INSERT_ARTICLE = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, no_utilisateur, no_categorie ) VALUES (?, ?, ?, ?, ?, ?, ?);";
	private static final String INSERT_ARTICLE_RETRAIT = "INSERT INTO RETRAITS (no_article, rue, code_postal, ville) VALUES (?, ?, ?, ?);";

	private static final String SELECT_DETAIL_ARTICLE = "SELECT * FROM ARTICLES_VENDUS AS a    INNER JOIN CATEGORIES AS c ON a.no_categorie = c.no_categorie\r\n"
			+ "INNER JOIN RETRAITS AS r ON r.no_article = a.no_article\r\n"
			+ "INNER JOIN UTILISATEURS AS u ON u.no_utilisateur = a.no_utilisateur\r\n" + "WHERE a.no_article = ?;";

	private static final String SELECT_NO_ARTICLE_ENCHERES_BY_ID_START = "SELECT e.no_article FROM UTILISATEURS AS u INNER JOIN ARTICLES_VENDUS AS a ON u.no_utilisateur = a.no_utilisateur INNER JOIN CATEGORIES AS c ON a.no_categorie = c.no_categorie INNER JOIN ENCHERES AS e ON e.no_article = a.no_article WHERE e.no_utilisateur = ?";
	private static final String SELECT_NO_ARTICLE_ENCHERES_EN_COURS_BY_ID_END = "AND e.date_enchere < a.date_fin_encheres AND e.date_enchere > a.date_debut_encheres AND a.date_fin_encheres > GETDATE() AND a.date_debut_encheres < GETDATE() GROUP BY e.no_article";
	private static final String SELECT_NO_ARTICLE_ENCHERES_REMPORTES_BY_ID_END = "AND a.prix_vente = e.montant_enchere GROUP BY e.no_article";
	private static final String UPDATE_CREDIT = "UPDATE UTILISATEURS SET credit = ? WHERE no_utilisateur = ?";
	private static final String SELECT_NO_UTILISATEUR_BEST_ENCHERE = "SELECT * FROM ENCHERES AS e INNER JOIN UTILISATEURS AS u ON e.no_utilisateur = u.no_utilisateur WHERE no_article = ?";
	private static final String INSERT_ENCHERE = "INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES (?, ?, ?, ? );";
	private static final String UPDATE_ENCHERE = "UPDATE ENCHERES SET no_utilisateur = ? , date_enchere = ? , montant_enchere = ? WHERE no_article = ?";
	
	/**
	 * Méthode qui récupére la liste de toutes les catégories d'articles
	 */
	public List<Categorie> selectAllCategorie() {

		List<Categorie> ListCategories = new ArrayList<Categorie>();

		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_CATEGORIE);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Categorie categorie = new Categorie();
				categorie.setnoCategorie(rs.getInt("no_categorie"));
				categorie.setLibelle(rs.getString("libelle"));
				ListCategories.add(categorie);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ListCategories;

	}

	/**
	 * Méthode rapportant toutes les enchères en cours. Le prix de vente, d'un
	 * article en train d'être en cours d'enchère, est valorisé selon le prix de la
	 * meilleure enchère qui lui est faite ; sinon par defaut son prix de vente sera
	 * son prix initial (pour affichage).
	 * 
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

		// Connexion à la BDD & Préparation de la requete (leurs fermetures y sont
		// implicite)
		try (Connection cnx = ConnectionProvider.getConnection()) {
			// Exécution de la requete
			cnx.setAutoCommit(false);
			psmt = cnx.prepareStatement(SELECT_ARTICLE);
			rs = psmt.executeQuery();

			// tant que l'on a un article en retour
			while (rs.next()) {
				categorie = new Categorie(rs.getInt("no_categorie"), rs.getString("libelle"));
				utilisateur = new Utilisateur(rs.getInt("no_utilisateur"), rs.getString("pseudo"), rs.getString("nom"),
						rs.getString("prenom"), rs.getString("email"), rs.getString("telephone"), rs.getString("rue"),
						rs.getString("code_postal"), rs.getString("ville"), rs.getString("mot_de_passe"),
						rs.getInt("credit"), rs.getBoolean("administrateur"));
				// requete imbriqué
				// on cherche à afficher le prix de vente ou le prix d'enchere le plus élevé
				psmt = cnx.prepareStatement(SELECT_MAX_ENCHERE);
				psmt.setString(1, String.valueOf(rs.getInt("no_article")));
				rs_bis = psmt.executeQuery();
				if (rs_bis.next()) {
					// Si le prix d'une sur-enchère existe et qu'elle est superieur au prix initial
					if (rs_bis.getInt("best_enchere") > rs.getInt("prix_initial")) {
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
				article = new Article(rs.getInt("no_article"), rs.getString("nom_article"), rs.getString("description"),
						rs.getDate("date_debut_encheres"), rs.getDate("date_fin_encheres"), rs.getInt("prix_initial"),
						prixVente, categorie, utilisateur);
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

	/**
	 * Méthode permettant d'insérer un article dans la table ARTICLES_VENDUS et
	 * d'insérer une adresse dans la table RETRAITS en respectant la foreign key sur
	 * le no_article
	 */
	@Override
	public Article insertArticle(Article articleCree) throws BusinessException {

		// Si l'objet utilisateur est null, on enregistre un message d'erreur dans la
		// businessException
		if (articleCree == null) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_NULL);
			throw businessException;
		}
		// Sinon on lance la connexion
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement psmt = cnx.prepareStatement(INSERT_ARTICLE, PreparedStatement.RETURN_GENERATED_KEYS);
				PreparedStatement psmt1 = cnx.prepareStatement(INSERT_ARTICLE_RETRAIT,
						PreparedStatement.RETURN_GENERATED_KEYS)) {
			// On place l'autocommit sur false
			cnx.setAutoCommit(false);
			// On prépare la requête SQL pour insérer un utilisateur et on récupère l'ID
			// généré par l'insertion
			psmt.setString(1, articleCree.getNomArticle());
			psmt.setString(2, articleCree.getDescription());
			psmt.setDate(3, (Date) (articleCree.getDateDebutEnchere()));
			psmt.setDate(4, (Date) (articleCree.getDateFinEchere()));
			psmt.setInt(5, articleCree.getPrixInitial());
			psmt.setInt(6, articleCree.getVendeur().getNoUtilisateur());
			psmt.setInt(7, articleCree.getCategorie().getnoCategorie());
			// On execute la requétes
			psmt.executeUpdate();
			// On récupère le résultat
			ResultSet rs = psmt.getGeneratedKeys();
			if (rs.next()) {
				// On ajoute le numéro de l'article
				articleCree.setNoArticle(rs.getInt(1));
			}
			// On prépare la seconde requête SQL pour insérer les informations de retrait
			psmt1.setInt(1, articleCree.getNoArticle());
			psmt1.setString(2, articleCree.getVendeur().getRue());
			psmt1.setString(3, articleCree.getVendeur().getCodePostal());
			psmt1.setString(4, articleCree.getVendeur().getVille());
			// On execute la requétes
			psmt1.executeUpdate();
			// On récupère le résultat
			ResultSet rs1 = psmt1.getGeneratedKeys();
			if (rs1.next()) {
				// On ajoute le numéro de l'article
				// articleCree.setNoArticle(rs1.getInt(1));
			}
			// Si tout se passe bien on commit
			cnx.commit();
			// S'il y a une erreur on l'enregistre dans la businessEx
		} catch (SQLException sqle) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			throw businessException;
		}
		return null;
	}

	/*
	 * Requête qui récupère le détail d'un Article selon son Id ainsi que son
	 * vendeur et sa catégorie
	 * 
	 * @return l'article trouvé
	 */
	@Override
	public Article selectDetailArticle(int noArticle) throws BusinessException {

		Article articleTrouve = null;
		Categorie categorie = null;
		Utilisateur utilisateurVendeur = null;
		ResultSet rs = null;
		ResultSet rs_bis = null;
		Integer prixVente = null;
		PreparedStatement psmt = null;

		try // Connexion à la BDD
		(Connection cnx = ConnectionProvider.getConnection())
		// préparation de la requête (fermetures de connexion implicites)

		{
			psmt = cnx.prepareStatement(SELECT_DETAIL_ARTICLE);
			psmt.setInt(1, noArticle);
			rs = psmt.executeQuery();

			if (rs.next()) {
				categorie = new Categorie(rs.getInt("no_categorie"), rs.getString("libelle"));

				utilisateurVendeur = new Utilisateur(rs.getInt("no_utilisateur"), rs.getString("pseudo"),
						rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), rs.getString("telephone"),
						rs.getString("rue"), rs.getString("code_postal"), rs.getString("ville"),
						rs.getString("mot_de_passe"), rs.getInt("credit"), rs.getBoolean("administrateur"));

				// requete imbriqué
				// on cherche à afficher le prix de vente ou le prix d'enchere le plus élevé
				psmt = cnx.prepareStatement(SELECT_MAX_ENCHERE);
				psmt.setInt(1, (rs.getInt("no_article")));
				rs_bis = psmt.executeQuery();

				if (rs_bis.next()) {
					// Si le prix d'une sur-enchère existe et qu'elle est superieur au prix initial
					if (rs_bis.getInt("best_enchere") > rs.getInt("prix_initial")) {
						// prixVente es tvalorisé pas le prix de la sur-enchère
						prixVente = rs_bis.getInt("best_enchere");
					} else {
						// sinon le prix de vente est valorisé par son prix initial
						prixVente = rs.getInt("prix_initial");
					}
				} else {
					prixVente = rs.getInt("prix_initial");
				}
				articleTrouve = new Article(rs.getInt("no_article"), rs.getString("nom_article"),
						rs.getString("description"), rs.getDate("date_debut_encheres"), rs.getDate("date_fin_encheres"),
						rs.getInt("prix_initial"), prixVente, categorie, utilisateurVendeur);

			}
			psmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			throw businessException;
		}

		return articleTrouve;

	}

	public List<Integer> getEncheresEnCoursOuRemportesById(int noUtilisateur, int typeDeRequete)
			throws BusinessException {
		// declarations
		List<Integer> listeNumeroArticles = new ArrayList<>();
		String rqtSql = SELECT_NO_ARTICLE_ENCHERES_BY_ID_START;
		ResultSet rs = null;
		// Selection de la requete SQL
		switch (typeDeRequete) {
		case 1:
			rqtSql += " " + SELECT_NO_ARTICLE_ENCHERES_EN_COURS_BY_ID_END;
			break;
		case 2:
			rqtSql += " " + SELECT_NO_ARTICLE_ENCHERES_REMPORTES_BY_ID_END;
			break;
		default:
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}
		// Connexion à la BDD & Préparation de la requete (leurs fermetures y sont
		// implicite)
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement psmt = cnx.prepareStatement(rqtSql);) {
			// Exécution de la requete
			psmt.setInt(1, noUtilisateur);
			rs = psmt.executeQuery();
			// tant que l'on a un article en retour
			while (rs.next()) {
				listeNumeroArticles.add(rs.getInt("no_article"));
			}
			// S'il y a une erreur on l'enregistre dans la businessEx
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}
		return listeNumeroArticles;
	}

	/**
	 * Requête permettant de créditer ou débiter un Utilisateur selon son ID
	 * 
	 * @return si l'update s'est bien faite
	 */
	@Override
	public boolean updateCredit(int pCreditAcheteur, int pIdUtilisateur) throws BusinessException {
		boolean UpdateOK = false;

		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement psmt = cnx.prepareStatement(UPDATE_CREDIT)) {

			psmt.setInt(1, pCreditAcheteur);
			psmt.setInt(2, pIdUtilisateur);

			// Execution de la requête
			psmt.executeUpdate();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}

		return UpdateOK;
	}

	@Override
	public Enchere selectEnchereByIdArticle(int idArticle) throws BusinessException {
		Enchere enchere = null;
		Utilisateur utilisateur = null;
		ResultSet rs = null;

		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement psmt = cnx.prepareStatement(SELECT_NO_UTILISATEUR_BEST_ENCHERE)) {

			psmt.setInt(1, idArticle);

			// Execution de la requête
			rs = psmt.executeQuery();
			// on considere qu'il y a qu'une enchere d'ouverte
			if (rs.next()) {
				utilisateur = new Utilisateur(rs.getInt("no_utilisateur"), rs.getString("pseudo"), rs.getString("nom"),
						rs.getString("prenom"), rs.getString("email"), rs.getString("telephone"), rs.getString("rue"),
						rs.getString("code_postal"), rs.getString("ville"), rs.getString("mot_de_passe"),
						rs.getInt("credit"), rs.getBoolean("administrateur"));
				enchere = new Enchere(rs.getInt("id"), rs.getDate("date_enchere"), rs.getInt("montant_enchere"),
						utilisateur, null);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}
		return enchere;
	}


	@Override
	public void insertEnchere( int idUtilisateur, Date dateEnchere, int montant, int noArticle ) throws BusinessException {

		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement psmt = cnx.prepareStatement(INSERT_ENCHERE)) {
			
			//
			psmt.setInt(1, idUtilisateur);
			psmt.setInt(2, montant);
			psmt.setDate(3, dateEnchere);
			psmt.setInt(4, noArticle);
			
			// On execute la requétes
			psmt.executeUpdate();
			
			// S'il y a une erreur on l'enregistre dans la businessEx
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			throw businessException;
		}
	}
	
	@Override
	public void updateEnchere( int idUtilisateur, Date dateEnchere, int montant, int noArticle ) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement psmt = cnx.prepareStatement(UPDATE_ENCHERE)) {

			psmt.setInt(1, idUtilisateur);
			psmt.setDate(2, dateEnchere);
			psmt.setInt(3, montant);
			psmt.setInt(4, noArticle);

			// Execution de la requête
			psmt.executeUpdate();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}
	}
	
	

}
