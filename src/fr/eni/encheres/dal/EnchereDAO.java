/**
 * 
 */
package fr.eni.encheres.dal;

import fr.eni.echeres.BusinessException;

/**
 * 
 *
 */
public interface EnchereDAO {
	
//	public void insert(ListeCourse listeCourse) throws BusinessException;
	public void delete(int id) throws BusinessException;
//	public List<ListeCourse> selectAll() throws BusinessException;
//	public ListeCourse selectById(int id) throws BusinessException;
	public void deleteArticle(int idArticle) throws BusinessException;
	public void cocherArticle(int idArticle) throws BusinessException;
	public void decocherArticle(int idArticle) throws BusinessException;
	public void decocherListeCourse(int listeCourse) throws BusinessException;
}
