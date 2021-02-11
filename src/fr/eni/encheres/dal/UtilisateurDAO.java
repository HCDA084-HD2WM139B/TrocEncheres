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
	public void insertUtilisateur(Utilisateur utilisateur) throws BusinessException;
	
	// Selection d'un utilisateur par ses identifiants de connexion.
	public Utilisateur selectByLogin(String pTypeRequete, String pIdentifiant, String pMotDePasse) throws BusinessException;



	
}
