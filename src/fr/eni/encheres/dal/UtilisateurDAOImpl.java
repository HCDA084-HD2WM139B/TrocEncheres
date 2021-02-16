package fr.eni.encheres.dal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Classe de la DAL implémentant l'interface UtilisateurDAO permettant de réaliser les requetes (CRUD) à l'entité utilisateur d'une Base de données SQL Server.
 * @author Groupe 3
 *
 */
public class UtilisateurDAOImpl implements UtilisateurDAO {

	// Constantes des requêtes SQL
	private static final String INSERT_UTILISATEUR = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String INSERT_CREDIT = "UPDATE UTILISATEURS SET credit = ? WHERE no_utilisateur = ?";
	private static final String UPDATE_UTILISATEUR = "UPDATE UTILISATEURS SET pseudo =?, nom =?, prenom =?, email=?, telephone=?, rue=?, code_postal=?, ville=?, mot_de_passe=? WHERE no_utilisateur = ?";
	private static final String SELECT_UTILISATEUR_BY_ID = "SELECT * FROM UTILISATEURS WHERE no_utilisateur = ?";
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
			// On prépare la requête SQL pour insérer un utilisateur et on récupère l'ID généré par l'insertion
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
						// On prépare la requête SQL pour insérer un utilisateur et on récupère l'ID généré par l'insertion
						psmt.setInt(1, 100);
						psmt.setInt(2, utilisateur.getNoUtilisateur());
						// On execute la requête
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
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}	
		return utilisateurTrouve;
	}
	
	
	/**
	 * Méthode qui recherche un utilisateur avec l'ID ET le Pseudo correspondant ou non
	 * @param pPseudo (String) Le pseudo de l'utilisateur.
	 * @param pNo_utilisateur (Integer) Le numéro de l'utilisateur.
	 * @return (Boolean) si un utilisateur correspond à l'ID et au Pseudo.
	 */
	
	public boolean VerifByPseudoAndId(String pPseudo, Integer pNo_utilisateur) throws BusinessException {
		boolean verif = false; 
		if (pPseudo == null && pNo_utilisateur == null ) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_NULL);
			throw businessException;
		}
		
		// on établit la requete SQL en fonction du pseudo et du numéro
		String sql = "SELECT * FROM UTILISATEURS WHERE pseudo = ? AND no_utilisateur = ?";
		// Connexion à la BDD & Préparation de la requete (leurs fermetures y sont implicite)
		try (Connection cnx = ConnectionProvider.getConnection(); PreparedStatement psmt = cnx.prepareStatement(sql) ) {
			psmt.setString(1, pPseudo);
			psmt.setInt(2, pNo_utilisateur);
			// Exécution de la requete
			ResultSet rs = psmt.executeQuery();
			// Si la requête fonctionne : on renvoie true
			if (rs.next()) {
				verif = true;
			}
			
		// S'il y a une erreur on l'enregistre dans la businessEx
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}	
		
		return verif;
	}
	
	/**
	 * Méthode qui recherche un utilisateur avec l'ID ET le Pseudo correspondant ou non
	 * @param pEmail (String) L'email de l'utilisateur.
	 * @param pNo_utilisateur (Integer) Le numéro de l'utilisateur.
	 * @return (Boolean) si un utilisateur correspond à l'ID et à l'email.
	 */
	
	public boolean VerifByEmailAndId(String pEmail, Integer pNo_utilisateur) throws BusinessException {
		boolean verif = false; 
		if (pEmail == null && pNo_utilisateur == null ) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_NULL);
			throw businessException;
		}
		
		// on établit la requete SQL en fonction du pseudo et du numéro
		String sql = "SELECT * FROM UTILISATEURS WHERE email = ? AND no_utilisateur = ?";
		// Connexion à la BDD & Préparation de la requete (leurs fermetures y sont implicite)
		try (Connection cnx = ConnectionProvider.getConnection(); PreparedStatement psmt = cnx.prepareStatement(sql) ) {
			psmt.setString(1, pEmail);
			psmt.setInt(2, pNo_utilisateur);
			// Exécution de la requete
			ResultSet rs = psmt.executeQuery();
			// Si la requête fonctionne : on renvoie true
			if (rs.next()) {
				verif = true;
			}
			
		// S'il y a une erreur on l'enregistre dans la businessEx
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}	
		
		return verif;
	}
	
	/**
	 * Méthode qui retourne un identifiant selon le pseudo
	 * @param pPseudo (String) Le pseudo de l'utilisateur.
	 * @return (Integer) la requête retourne le numéro de l'utilisateur.
	 */
	
	public Integer SelectIdWherePseudo(String pPseudo) throws BusinessException {
		Integer verif = null; 
		if (pPseudo == null ) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_NULL);
			throw businessException;
		}
		
		// on récupère l'ID avec la requete SQL en fonction du pseudo
		String sql = "SELECT no_utilisateur FROM UTILISATEURS WHERE pseudo = ?";
		// Connexion à la BDD & Préparation de la requete (leurs fermetures y sont implicite)
		try (Connection cnx = ConnectionProvider.getConnection(); PreparedStatement psmt = cnx.prepareStatement(sql) ) {
			psmt.setString(1, pPseudo);
			// Exécution de la requete
			ResultSet rs = psmt.executeQuery();
			// Si la requête fonctionne on insère l'ID dans notre variable
			if (rs.next()) {
				verif = rs.getInt("no_utilisateur");
			}
			
		// S'il y a une erreur on l'enregistre dans la businessEx
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}	
		
		return verif;
	}
	
	/**
	 * Méthode qui retourne un identifiant selon le pseudo
	 * @param pEmail (String) L'email de l'utilisateur.
	 * @return (Integer) la requête retourne le numéro de l'utilisateur.
	 */
	
	public Integer SelectIdWhereEmail(String pEmail) throws BusinessException {
		Integer verif = null; 
		if (pEmail == null ) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_NULL);
			throw businessException;
		}
		
		// on récupère l'ID avec la requete SQL en fonction du pseudo
		String sql = "SELECT no_utilisateur FROM UTILISATEURS WHERE email = ?";
		// Connexion à la BDD & Préparation de la requete (leurs fermetures y sont implicite)
		try (Connection cnx = ConnectionProvider.getConnection(); PreparedStatement psmt = cnx.prepareStatement(sql) ) {
			psmt.setString(1, pEmail);
			// Exécution de la requete
			ResultSet rs = psmt.executeQuery();
			// Si la requête fonctionne on insère l'ID dans notre variable
			if (rs.next()) {
				verif = rs.getInt("no_utilisateur");
			}
			
		// S'il y a une erreur on l'enregistre dans la businessEx
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}	
		
		return verif;
	}
	
	/**
	 * Méthode sélectionnant toute la liste des pseudos des utilisateurs enregistrés en base de données 
	 * afin de vérifier si l'utilisateur à inscrire ne choisisse pas un pseudo déjà existant.
	 * 
	 */
	@Override
	public boolean selectAllPseudo(String pPseudo) throws BusinessException {
		boolean pseudo = false;
		// Vérification des paramètres (le type doit etre correct !)
		if (pPseudo == null ) {
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
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}	
		return pseudo;
	}
	
	/**
	 * Méthode sélectionnant toute la liste des emails des utilisateurs enregistrés en base de données 
	 * afin de vérifier si l'utilisateur à inscrire ne choisisse pas un email déjà existant.
	 * 
	 */
	@Override
	public boolean selectAllEmail(String pEmail) throws BusinessException {
		boolean email = false;
		// Vérification des paramètres (le type doit etre correct !)
		if (pEmail == null ) {
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
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}	
		return email;
	}

	/**
	 * Méthode permettant de mettre à jour le profil d'un utilisateur.
	 */
	//TODO écrire les commentaires 
	@Override
	public Utilisateur updateUtilisateur(Utilisateur utilisateur) {
		
		try { 
		// Connexion à la BDD 
		Connection cnx = ConnectionProvider.getConnection(); 
		// préparation de la requête (fermetures de connexion implicites)
		PreparedStatement psmt = cnx.prepareStatement(UPDATE_UTILISATEUR);
				
		psmt.setString(1, utilisateur.getPseudo());
		psmt.setString(2, utilisateur.getNom());
		psmt.setString(3, utilisateur.getPrenom());
		psmt.setString(4, utilisateur.getEmail());
		psmt.setString(5, utilisateur.getTelephone());
		psmt.setString(6, utilisateur.getRue());
		psmt.setString(7, utilisateur.getCodePostal());
		psmt.setString(8, utilisateur.getVille());
		psmt.setString(9, utilisateur.getMotDePasse());
		psmt.setInt(10, utilisateur.getNoUtilisateur());
		
		//exécution de la requête 
		psmt.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Méthode retournant un utilisateur par son numéro d'identifiant
	 */
	//TODO écrire les commentaires 
	@Override
	public Utilisateur afficheUtilisateurbyId(int id) {
				
		Utilisateur utilisateurTrouve = null;
		try {
			// Connexion à la BDD
			Connection cnx = ConnectionProvider.getConnection();
			// préparation de la requête (fermetures de connexion implicites)
			PreparedStatement psmt = cnx.prepareStatement(SELECT_UTILISATEUR_BY_ID);
			
			psmt.setInt(1, id);
			
			ResultSet rs = psmt.executeQuery();
			
			if(rs.next()) {
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

		} catch (SQLException e) {
			
			//TODO ajouter code d'erreur
			e.printStackTrace();
		}
		return utilisateurTrouve;
	}

	
	
	/**
	 * Méthode exécutant la procédure stockée servant à la suppression et l'archivage d'un utilisateur. 
	 * La procédure stockée va enregistrer une copie de l'utilisateur dans la table "Archives_utilisateurs" ; 
	 * puis annuler les enchères concernant l'utilisateur (acheteur) ; 
	 * puis supprimer l'utilisateur (vendeur) avec tous ses articles qui lui sont affectés mais aussi les enchères affectés à ces mêmes articles ; 
	 * et enfin supprimer les enchères préalablement annulées le concernant.
	 * 
	 * @param (int) id ; le numero d'utilisateur en base de données.
	 * @return (boolean) True si la procédure stockée execute correctement l'archivage et la suppression, sinon False.
	 * @throws BuisnessException.
	 */
	@Override
    public boolean deleteUserById(int id) throws BusinessException {
        boolean result = false;
       
        // Vérification des paramètres
        if ( id <= 0 ) {
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_NULL);
            throw businessException;
        }
        // on appel la procédure stockée :
        String sql = "{call Delete_Utilisateur(?,?)}";
       
        // Connexion à la BDD & Préparation de la requete (leurs fermetures y sont implicites)
        try (Connection cnx = ConnectionProvider.getConnection(); CallableStatement csmt = cnx.prepareCall(sql) ) {
        	// transaction (début)
            cnx.setAutoCommit(false);
            // enregistrement du parametre de sortie comme Integer :
            csmt.registerOutParameter("Result", java.sql.Types.INTEGER);
            // on valorise le parametre de la procédure :
            csmt.setInt(1, id);
            // on execute la requete :
            csmt.execute();
            // on recupere si le traitement c'est bien déroulé
            Integer reponse = csmt.getInt(2);
            // Si la procédure stockée nous renvoi 1, c'est qu'elle a réussi la suppression et l'archivage !
            if (reponse == 1) {
                result = true;
                // transaction (fin)
                cnx.commit();
            } else {
            	// transaction (annulation)
            	cnx.rollback();
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
            throw businessException;
        }
        return result;
    }
	
}
