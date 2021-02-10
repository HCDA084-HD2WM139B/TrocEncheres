package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Utilisateur;

public class UtilisateurDAOImpl implements UtilisateurDAO {

	private static final String INSERT_UTILISATEUR = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String SELECT_BY_EMAIL = "SELECT * FROM UTILISATEURS WHERE email = ? ";
	private static final String SELECT_BY_PSEUDO = "SELECT * FROM UTILISATEURS WHERE pseudo = ? ";

	
	
	/**
	 * Cette m�thode permet d'ajouter un utilisateur dans la base de donn�es ENCHERE_DB sur la table UTILISATEUR
	 */
	@Override
	public void insertUtilisateur(Utilisateur utilisateur) throws BusinessException {
		
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
	}
	
	@Override
	public Utilisateur selectByEmail(String pEmail) throws BusinessException {
		
		Utilisateur utilisateurTrouve = null;
		
		if (pEmail == null) {
			// TODO changer code erreur
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_NULL);
			throw businessException;
		}
	
		try (Connection cnx = ConnectionProvider.getConnection(); 
				PreparedStatement psmt = cnx.prepareStatement(SELECT_BY_EMAIL); ) {
			
			psmt.setString(1, pEmail);
			
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				System.out.println("user trouvé !");
				// traitement du retour
				utilisateurTrouve = new Utilisateur(
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
				utilisateurTrouve.setNoUtilisateur(rs.getInt("no_utilisateur"));
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
	public Utilisateur selectByPseudo(String pPseudo) throws BusinessException {
		
		Utilisateur utilisateurTrouve = null;
		
		if (pPseudo == null) {
			// TODO changer code erreur
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_NULL);
			throw businessException;
		}
	
		try (Connection cnx = ConnectionProvider.getConnection(); 
				PreparedStatement psmt = cnx.prepareStatement(SELECT_BY_PSEUDO); ) {
			
			psmt.setString(1, pPseudo);
			
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				System.out.println("user trouvé !");
				// traitement du retour
				utilisateurTrouve = new Utilisateur(
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
				utilisateurTrouve.setNoUtilisateur(rs.getInt("no_utilisateur"));
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
}
