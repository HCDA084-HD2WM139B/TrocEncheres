package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Classe de la DAL implémentant l'interface UtilisateurDAO permettant de réaliser les requetes (CRUD) à l'entité utilisateur d'une Base de données SQL Server.
 * @author Groupe 3
 *
 */
public class UtilisateurDAOImpl implements UtilisateurDAO {

	
	// Constantes
	private static final String INSERT_UTILISATEUR = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String INSERT_CREDIT = "UPDATE UTILISATEURS SET credit = ? WHERE no_utilisateur = ?";
	// le nom exact des colonnes dans la table utilisateur en BDD :
	private static final String COLONNE_EMAIL = "email";
	private static final String COLONNE_PSEUDO = "pseudo";

	

	/**
	 * Méthode permettant d'ajouter un utilisateur, passé en paramètre, dans la base de données sur la table UTILISATEUR.
	 * @param utilisateur (Utilisateur) L'utilisateur à enregistrer.
	 */
	@Override
	public Utilisateur insertUtilisateur(Utilisateur utilisateur) throws BusinessException {
		// Si l'objet utilisateur est null, on enregistre un message d'erreur dans la businessException
		if (utilisateur == null) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_NULL);
			throw businessException;
		}
		// Sinon on lance la connexion
		try (Connection cnx = ConnectionProvider.getConnection(); 
				PreparedStatement psmt = cnx.prepareStatement(INSERT_UTILISATEUR, PreparedStatement.RETURN_GENERATED_KEYS); ) {
			// On pr�pare la requ�te SQL pour ins�rer un utilisateur et on r�cup�re l'ID g�n�r� par l'insertion
			psmt.setString(1, utilisateur.getPseudo());
			psmt.setString(2, utilisateur.getNom());
			psmt.setString(3, utilisateur.getPrenom());
			psmt.setString(4, utilisateur.getEmail());
			psmt.setString(5, utilisateur.getTelephone());
			psmt.setString(6, utilisateur.getRue());
			psmt.setString(7, utilisateur.getCodePostal());
			psmt.setString(8, utilisateur.getVille());
			psmt.setString(9, utilisateur.getMotDePasse());
			psmt.setInt(10, utilisateur.getCredit());
			psmt.setBoolean(11, false);
			// On execute la requ�te
			psmt.executeUpdate();
			// On r�cup�re le r�sultat
			ResultSet rs = psmt.getGeneratedKeys();
			if (rs.next()) {
				// On ajoute l'ID r�cup�rer au noUtilisateur
				utilisateur.setNoUtilisateur(rs.getInt(1));
			}
			// S'il y a une erreur on l'enregistre dans la businessEx
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			throw businessException;
		}
		
		if (utilisateur.getNoUtilisateur() <= 100) {
					try (Connection cnx = ConnectionProvider.getConnection(); 
							PreparedStatement psmt = cnx.prepareStatement(INSERT_CREDIT, PreparedStatement.RETURN_GENERATED_KEYS); ) {
						// On pr�pare la requ�te SQL pour ins�rer un utilisateur et on r�cup�re l'ID g�n�r� par l'insertion
						psmt.setInt(1, 100);
						psmt.setInt(2, utilisateur.getNoUtilisateur());
						// On execute la requ�te
						psmt.executeUpdate();
						// S'il y a une erreur on l'enregistre dans la businessEx
					} catch (SQLException sqle) {
						sqle.printStackTrace();
						BusinessException businessException = new BusinessException();
						businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
						throw businessException;
					}
		}
		
		return null;
	}
	
	
	/**
	 * Méthode retournant un utilisateur en fonction d'un identifiant, d'un mot de passe, et du type d'identifiant passés en paramètre.
	 * @param pTypeRequete (String) Le type d'identifiant correspondant au nom exact de la colonne en table Utilisateur de la Base de données.
	 * @param pIdentifiant (String) Le login valide saisie par l'utilisateur.
	 * @param pMotDePasse (String) Le mot de passe valide saisie par l'utilisateur.
	 * @return (Utilisateur) un utilisateur si les identifiants existent en base de données, sinon null.
	 */
	@Override
	public Utilisateur selectByLogin(String pTypeRequete, String pIdentifiant, String pMotDePasse) throws BusinessException {
		Utilisateur utilisateurTrouve = null;
		// Vérification des paramètres (le type doit etre correct !)
		if (pIdentifiant == null || pTypeRequete == null || pMotDePasse == null || ( !pTypeRequete.equals(COLONNE_EMAIL) && !pTypeRequete.equals(COLONNE_PSEUDO) ) ) {
			// TODO changer code erreur
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_NULL);
			throw businessException;
		}
		// on établit la requete SQL en fonction du type d'identifiant
		String sql = "SELECT * FROM UTILISATEURS WHERE " + pTypeRequete + " = ? AND mot_de_passe = ?";
		// Connexion à la BDD & Préparation de la requete (leurs fermetures y sont implicite)
		try (Connection cnx = ConnectionProvider.getConnection(); PreparedStatement psmt = cnx.prepareStatement(sql) ) {
			psmt.setString(1, pIdentifiant);
			psmt.setString(2, pMotDePasse);
			// Exécution de la requete
			ResultSet rs = psmt.executeQuery();
			// Si la requete retourne une ligne (on considère une seule réponse car l'email et le pseudo sont uniques en BDD)
			if (rs.next()) {
				// on hydrate notre nouvelle utilisateur
				utilisateurTrouve = new Utilisateur(
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
						rs.getBoolean("administrateur"));
			}
		// S'il y a une erreur on l'enregistre dans la businessEx
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			BusinessException businessException = new BusinessException();
			//TODO Changer code d'erreur
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}	
		return utilisateurTrouve;
	}
	
	@Override
	public boolean selectAllPseudo(String pPseudo) throws BusinessException {
		boolean pseudo = false;
		// Vérification des paramètres (le type doit etre correct !)
		if (pPseudo == null ) {
			// TODO changer code erreur
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_NULL);
			throw businessException;
		}
		
		// on établit la requete SQL en fonction du type d'identifiant
		String sql = "SELECT * FROM UTILISATEURS WHERE pseudo = ?";
		// Connexion à la BDD & Préparation de la requete (leurs fermetures y sont implicite)
		try (Connection cnx = ConnectionProvider.getConnection(); PreparedStatement psmt = cnx.prepareStatement(sql) ) {
			psmt.setString(1, pPseudo);
			// Exécution de la requete
			ResultSet rs = psmt.executeQuery();
			// Si la requete retourne une ligne (on considère une seule réponse car l'email et le pseudo sont uniques en BDD)
			if (rs.next()) {
				pseudo = true;
			}
			
		// S'il y a une erreur on l'enregistre dans la businessEx
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			BusinessException businessException = new BusinessException();
			//TODO Changer code d'erreur
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}	
		return pseudo;
	}
	
	@Override
	public boolean selectAllEmail(String pEmail) throws BusinessException {
		boolean email = false;
		// Vérification des paramètres (le type doit etre correct !)
		if (pEmail == null ) {
			// TODO changer code erreur
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_NULL);
			throw businessException;
		}
		// on établit la requete SQL en fonction du type d'identifiant
		String sql = "SELECT * FROM UTILISATEURS WHERE email = ?";
		// Connexion à la BDD & Préparation de la requete (leurs fermetures y sont implicite)
		try (Connection cnx = ConnectionProvider.getConnection(); PreparedStatement psmt = cnx.prepareStatement(sql) ) {
			psmt.setString(1, pEmail);
			// Exécution de la requete
			ResultSet rs = psmt.executeQuery();
			// Si la requete retourne une ligne (on considère une seule réponse car l'email et le pseudo sont uniques en BDD)
			if (rs.next()) {
				email = true;
			}
			
		// S'il y a une erreur on l'enregistre dans la businessEx
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			BusinessException businessException = new BusinessException();
			//TODO Changer code d'erreur
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}	
		return email;
	}

}
