package fr.eni.encheres.dal;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurDAO {

	public void insertUtilisateur(Utilisateur utilisateur) throws BusinessException;
	public Utilisateur selectByEmail(String email) throws BusinessException;
	public Utilisateur selectByPseudo(String pseudo) throws BusinessException;


	
}
