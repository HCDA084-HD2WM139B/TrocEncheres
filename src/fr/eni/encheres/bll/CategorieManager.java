package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.CategorieDAO;
import fr.eni.encheres.dal.DAOFactory;

public class CategorieManager {
	
	private CategorieDAO categorieDAO;
	
	public CategorieManager() {
		this.categorieDAO = DAOFactory.getCategorieDAO();
	}
	
	/**
	 * Selectionne l'ensemble des categories depuis que l'application est déployée
	 * @return
	 * @throws BusinessException
	 */
	public List<Categorie> selectionnerToutesLesCategories() {
		return this.categorieDAO.selectAllCategorie();
	}

}
