package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Categorie;

public class CategorieDAOImpl implements CategorieDAO {
	
	private static final String SELECT_CATEGORIE = "SELECT * FROM categories";
	
	//Recuperer la liste des catégories 
	public List<Categorie> selectAllCategorie() {
		
		List<Categorie> ListCategories = new ArrayList<Categorie>();
		
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_CATEGORIE);
			ResultSet rs = pstmt.executeQuery();
			
			Categorie categorie = new Categorie();
			
			while ( rs.next() )
			{
				if( rs.getInt("no_categorie") != categorie.getnoCategorie() ){
					categorie = categorieBuilder(rs);
					ListCategories.add(categorie);
				}
				
			}
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return ListCategories;
		
	}
	
	private Categorie categorieBuilder(ResultSet rs) throws SQLException {
		Categorie categorie = new Categorie();
		
		categorie.setnoCategorie(rs.getInt("no_categorie"));
		categorie.setLibelle(rs.getString("libelle"));
		
		return categorie;
	}
}
