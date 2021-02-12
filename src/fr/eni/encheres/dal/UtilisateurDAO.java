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
	public void updateUtilisateur(Utilisateur utilisateur);
	
	public Utilisateur afficheUtilisateurbyId(int id);
	
}
