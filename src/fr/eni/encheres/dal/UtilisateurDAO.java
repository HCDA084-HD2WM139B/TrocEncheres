package fr.eni.encheres.dal;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurDAO {

	public void insertUtilisateur(Utilisateur utilisateur) throws BusinessException;
	
}
