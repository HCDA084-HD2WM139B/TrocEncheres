package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Categorie;

public class EnchereDAOImpl {
	
	private static final String SELECT_ARTICLE = "SELECT * FROM articles";
	
	//Recuperer la liste des articles
	public List<Categorie> selectAllArticle() {
		
		List<Categorie> ListCategories = new ArrayList<Categorie>();
		
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ARTICLE);
			ResultSet rs = pstmt.executeQuery();
			
			while ( rs.next() )
			{

			}
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return ListCategories;
		
	}
}
