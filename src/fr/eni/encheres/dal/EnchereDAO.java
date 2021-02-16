package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;

/**
 * Interface de la couche DAL permettant de lister les méthodes (CRUD) des requetes à la base de données concernant l'entité enchères (articles, retrait, et encheres).
 * @author Groupe 3
 *
 */
public interface EnchereDAO {
	
	// Selection de toutes les enchères.
	public List<Article> selectAllSales() throws BusinessException;
	
	// Sélection de tous les articles
	public List<Categorie> selectAllCategorie() throws BusinessException;
	
    // Création d'un article à vendre
	public Article insertArticle(Article artcileCree) throws BusinessException;


}
