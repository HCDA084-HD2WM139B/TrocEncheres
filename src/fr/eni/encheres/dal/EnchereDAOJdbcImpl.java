/**
 * 
 */
package fr.eni.encheres.dal;

import fr.eni.encheres.BusinessException;

/**
 * @author Joach
 *
 */
public class EnchereDAOJdbcImpl implements EnchereDAO {

//	private static final String INSERT="INSERT INTO AVIS(description, note) VALUES(?,?);";
//	
//	@Override
//	public void insert(Avis avis) throws BusinessException {
//		if(avis==null)
//		{
//			BusinessException businessException = new BusinessException();
//			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_NULL);
//			throw businessException;
//		}
//		
//		try(Connection cnx = ConnectionProvider.getConnection())
//		{
//			PreparedStatement pstmt = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
//			pstmt.setString(1, avis.getDescription());
//			pstmt.setInt(2, avis.getNote());
//			pstmt.executeUpdate();
//			ResultSet rs = pstmt.getGeneratedKeys();
//			if(rs.next())
//			{
//				avis.setIdentifiant(rs.getInt(1));
//			}
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			BusinessException businessException = new BusinessException();
//			if(e.getMessage().contains("CK_AVIS_note"))
//			{
//				businessException.ajouterErreur(CodesResultatDAL.INSERT_AVIS_NOTE_ECHEC);
//			}
//			else
//			{
//				businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
//			}
//			throw businessException;
//		}	
//	}

}
