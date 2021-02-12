package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Article;

/**
 * Classe Abstraite DAOFactory (couche DAL) permettant de distribuer les entitées DAO de l'applications
 * @author Groupe 3
 *
 */
public abstract class DAOFactory {

	/**
	 * Méthode permettant d'obtenir une instance de l'entité DAO Utilisateur.
	 * @return (UtilisateurDAO) instance de la classe UtilisateurDAOImpl.
	 */
	public static UtilisateurDAO getUtilisateurDAO() {
		return new UtilisateurDAOImpl();
	}
	
	/**
	 * Méthode permettant d'obtenir une instance de l'entité DAO Enchere (correspond à la catégorie article et enchères)
	 * @return (EnchereDAO) instance de la classe EnchereDAOImpl.
	 */
	public static EnchereDAO getEnchereDAO() {
		return new EnchereDAOImpl();
	}
	
}
