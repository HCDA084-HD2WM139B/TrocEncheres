package fr.eni.encheres.test;

import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.dal.EnchereDAO;
import fr.eni.encheres.dal.EnchereDAOImpl;

public class TestEncheres {

	public static void main(String[] args) {
		System.out.println("debut");
		EnchereDAO enchereDAOimpl = new EnchereDAOImpl();
		
		List<Article> listArticles = new ArrayList<Article>();
		
		try {
			listArticles = enchereDAOimpl.selectAllSales();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Article article : listArticles) {
			System.out.println("nom article : " + article.getNomArticle());
			System.out.println("prix vente article : " + article.getPrixVente());
			System.out.println("fin enchere article : " + article.getDateFinEchere().toString());
			System.out.println("vendeur article : " + article.getVendeur().getPseudo());
		}
		
		
	}

}
