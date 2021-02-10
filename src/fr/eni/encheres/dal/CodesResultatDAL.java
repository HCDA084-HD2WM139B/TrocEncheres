/**
 * 
 */
package fr.eni.encheres.dal;

/**
 * Les codes disponibles sont entre 10000 et 19999
 */
public abstract class CodesResultatDAL {
	
	/**
	 * Echec g�n�ral quand tentative d'ajouter un objet null
	 */
	public static final int INSERT_OBJET_NULL=10000;
	
	/**
	 * Echec g�n�ral quand erreur non g�r�e � l'insertion 
	 */
	public static final int INSERT_OBJET_ECHEC=10001;
	
	/**
	 * Echec de l'insertion d'un avis � cause de la note
	 */
	public static final int INSERT_AVIS_NOTE_ECHEC=10002;
	
	/**
	 * Echec de la requete en base de données de l'utilisateur par son login
	 */
	public static final int SELECT_OBJET_NULL=10003;
	
	/**
	 * Echec de lecture en base de données de l'utilisateur
	 */
	public static final int SELECT_OBJET_ECHEC=10004;
	
	
}
