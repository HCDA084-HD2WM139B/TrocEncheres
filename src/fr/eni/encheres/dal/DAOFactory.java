package fr.eni.encheres.dal;

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
	
	public static CategorieDAO getCategorieDAO() {
		return new CategorieDAOImpl();
	}
	
}
