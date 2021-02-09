package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Utilisateur;

public class UtilisateurDAOImpl implements UtilisateurDAO {

	private static final String INSERT_UTILISATEUR = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
	
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
		try (Connection cnx = ConnectionProvider.getConnection()) {
			// On pr�pare la requ�te SQL pour ins�rer un utilisateur et on r�cup�re l'ID g�n�r� par l'insertion
			PreparedStatement psmt = cnx.prepareStatement(INSERT_UTILISATEUR, PreparedStatement.RETURN_GENERATED_KEYS);
			psmt.setString(1, utilisateur.getPseudo());
			psmt.setString(2, utilisateur.getNom());
			psmt.setString(3, utilisateur.getPrenom());
			psmt.setString(4, utilisateur.getEmail());
			psmt.setString(5, utilisateur.getTelephone());
			psmt.setString(6, utilisateur.getRue());
			psmt.setString(7, utilisateur.getCodePostal());
			psmt.setString(8, utilisateur.getVille());
			psmt.setString(9, utilisateur.getMotDePasse());
			// On execute la requ�te
			psmt.executeUpdate();
			// On r�cup�re le r�sultat
			ResultSet rs = psmt.getGeneratedKeys();
			if (rs.next()) {
				// On ajoute l'ID r�cup�rer au noUtilisateur
				utilisateur.setNoUtilisateur(rs.getInt(1));
			}
			// S'il y a une erreur on l'enregistre dans la businessException
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			throw businessException;
		}
		
	}
}
