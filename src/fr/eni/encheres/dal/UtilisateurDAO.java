package fr.eni.encheres.dal;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Interface de la couche DAL permettant de lister les méthodes (CRUD) de requetes à la base de données concernant l'entité Utilisateur.
 * @author Groupe 3
 *
 */
public interface UtilisateurDAO {

	// Insertion d'un utilisateur dans l'application.
	public Utilisateur insertUtilisateur(Utilisateur utilisateur) throws BusinessException;
	
	// Selection d'un utilisateur par ses identifiants de connexion.
	public Utilisateur selectByLogin(String pTypeRequete, String pIdentifiant, String pMotDePasse) throws BusinessException;

	// Selection de la liste des pseudo.
	public boolean selectAllPseudo(String pPseudo) throws BusinessException;
	
	// Selection de la liste des email.
	public boolean selectAllEmail(String pEmail) throws BusinessException;
	
	//Mise à jour données utilisateur.
	public Utilisateur updateUtilisateur(Utilisateur utilisateur) throws BusinessException;
	
	//Affiche un utilisateur selon son numéro d'identifiant
	public Utilisateur afficheUtilisateurbyId(int id) throws BusinessException;
	
	// archivage d'un utilisateur supprimé en fonction de son numéro d'identifiant
    public boolean deleteUserById(int id) throws BusinessException;
	
	//Retourne True si l'ID et le Pseudo correspondent (BDD)
	public boolean VerifByPseudoAndId(String pPseudo, Integer pNo_utilisateur) throws BusinessException;
	
	//Retourne True si l'ID et l'email correspondent (BDD)
	public boolean VerifByEmailAndId(String pEmail, Integer pNo_utilisateur) throws BusinessException; 
	
	//Retourne un ID avec le Pseudo 
	public Integer SelectIdWherePseudo(String pPseudo) throws BusinessException;
	
	//Retourne un ID avec le Pseudo 
	public Integer SelectIdWhereEmail(String pEmail) throws BusinessException;
	
}
