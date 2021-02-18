package fr.eni.encheres.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.EnchereManager;


/**
 * Servlet implementation class ModificationUpdateProfil
 */
@WebServlet("/modificationUpdateProfil")
public class ModificationUpdateProfil extends HttpServlet {

	private static final long serialVersionUID = 1L;

	//Constantes de paramètres
	private static final String PARAM_NEW_MOT_DE_PASSE = "new_mdp";
	private static final String PARAM_CONFIRM_MOT_DE_PASSE = "confir_mdp";
	private static final String PARAM_MOT_DE_PASSE_ACTUEL = "mdp_actuel";
	private static final String PARAM_VILLE = "ville";
	private static final String PARAM_CODE_POSTAL = "postal";
	private static final String PARAM_RUE = "rue";
	private static final String PARAM_TELEPHONE = "tel";
	private static final String PARAM_EMAIL = "email";
	private static final String PARAM_PRENOM = "prenom";
	private static final String PARAM_NOM = "nom";
	private static final String PARAM_PSEUDO = "pseudo";
	
	//Constantes de messages d'erreur
	private static final String EMAIL_KO = "Le format de l'email n'est pas correct";
	private static final String MOT_DE_PASSE_KO = "Le mot de passe ne correpond pas à la confirmation.";
	private static final String ERREUR_UN_CHAMP_MDP_VIDE = "Un des deux champs du nouveau mot de passe est vide";
	private static final String LE_NEW_MDP_IDENTIQUE_ANCIEN_MDP = "Le nouveau mot de passe est identique à l'ancien";
	private static final String LE_MDP_ENTRE_2_ET_8_CARACTERES = "Le mot de passe doit être compris entre 2 et 8 caractères";
 
	//Constantes de redirection
	private static final String REDIRECTION_SUCCESS = "/monProfil?id=";
	private static final String REDIRECTION_FAIL_UPDATE = "/modifierProfil?id=";
	
	// Constantes de valeur max & min du mot de passe
	private static final int VALEURMAX_MDP = 8;
	private static final int VALEURMIN_MDP = 2;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

				//Déclarations 
				int valeurMax = 0;
				boolean tailleChamp = true;
				boolean formatEmail = false;
				boolean erreurMotDePasse = true;
				boolean utilisateurUpdated = false;
				boolean ModifPseudoOk = false;
				
				
				List<String> listErreurs = new ArrayList<String>();

				
				//Récupération des nouveaux paramètres inscrits par l'utilisateur (pour faire la modification du profil)
				Map<String, String> parametre = new LinkedHashMap<String, String>();
				
				parametre.put(PARAM_PSEUDO, request.getParameter(PARAM_PSEUDO));
				parametre.put(PARAM_NOM, request.getParameter(PARAM_NOM));
				parametre.put(PARAM_PRENOM, request.getParameter(PARAM_PRENOM));
				parametre.put(PARAM_EMAIL, request.getParameter(PARAM_EMAIL));
				parametre.put(PARAM_TELEPHONE, request.getParameter(PARAM_TELEPHONE));
				parametre.put(PARAM_RUE, request.getParameter(PARAM_RUE));
				parametre.put(PARAM_CODE_POSTAL, request.getParameter(PARAM_CODE_POSTAL));
				parametre.put(PARAM_VILLE, request.getParameter(PARAM_VILLE));
				parametre.put(PARAM_MOT_DE_PASSE_ACTUEL, request.getParameter(PARAM_MOT_DE_PASSE_ACTUEL));		
				parametre.put(PARAM_NEW_MOT_DE_PASSE, request.getParameter(PARAM_NEW_MOT_DE_PASSE));
				parametre.put(PARAM_CONFIRM_MOT_DE_PASSE, request.getParameter(PARAM_CONFIRM_MOT_DE_PASSE));
				
				// Variable du mot de passe ( par défaut, on gardera l'ancien ) 
				String password = parametre.get(PARAM_MOT_DE_PASSE_ACTUEL);

				//Modification du type de l'id utilisateur en String pour l'intégrer dans l'URL
				int IdSession = (int) request.getSession().getAttribute("id_utilisateur");
				String sIdSession = String.valueOf(IdSession);
				
				//Appel du manager pour appeler les méthodes
				EnchereManager manager = EnchereManager.getEnchereManager();
				
				//Vérification règles métier
				for(Entry<String, String> entree : parametre.entrySet()) {
					//La taille maximum du champ est défini par la méthode valeurMax()
					valeurMax = manager.valeurMax(entree.getKey());
					// Vérification de la taille du champ qui doit être compris entre deux et valeurMax. (Le champ téléphone est exclu car il peut être null et la taille du champ ID peut-être inférieur à 2)
					if (!manager.verifierTailleChamp(entree.getValue(), valeurMax) && !entree.getKey().contains(PARAM_TELEPHONE) && !entree.getKey().contains(PARAM_NEW_MOT_DE_PASSE) && !entree.getKey().contains(PARAM_CONFIRM_MOT_DE_PASSE)) {
						listErreurs.add("Le champ " + entree.getKey() + " doit être compris entre 2 et " + valeurMax + " caractères.");
						tailleChamp = false;
					}
				}	
				
					
					// Vérifier que le pseudo a été modifié et qu'il n'existe pas déjà dans la BDD 
					try {
							// Cas où le pseudo n'existe pas en base : Return true
							if (!manager.getPseudoExiste(parametre.get(PARAM_PSEUDO))) {
								ModifPseudoOk = true;								
							} 

							// Cas où le pseudo existe en BDD mais que l'IDSession est égale à l'ID de la BDD ET que le pseudo est égal au champ "pseudo"
							else if (manager.getPseudoExiste(parametre.get(PARAM_PSEUDO)) && manager.VerifierPseudoId(parametre.get(PARAM_PSEUDO), IdSession)) {
								ModifPseudoOk = true;
							} 
							
							// Cas où le pseudo existe en BDD mais que l'ID de la BDD est différent de l'IDSession
							else if (manager.getPseudoExiste(parametre.get(PARAM_PSEUDO)) && manager.RecupererIdAvecPseudo(parametre.get(PARAM_PSEUDO)) != IdSession) {
								ModifPseudoOk = false;
								listErreurs.add("Le pseudo " + parametre.get(PARAM_PSEUDO) + " existe déjà.");
							} 
							// Autres cas 
							else  {
								ModifPseudoOk = false;
								listErreurs.add("Le pseudo " + parametre.get(PARAM_PSEUDO) + " est erronné.");
							}
							
						
					} catch (BusinessException e) {
					
					}
				

				
					// Vérification que le format de l'email est correct
					if(manager.verifFormatEmail(parametre.get(PARAM_EMAIL))) {
						formatEmail = true;

					} else {
						listErreurs.add(EMAIL_KO);
					}
					
					// Vérification que l'email n'est pas déjà utilisé 
					boolean ModifEmailOk = false;
					try {
							// Cas où l'email n'existe pas en base de données : Return true
							if (!manager.getEmailExiste(parametre.get(PARAM_EMAIL))) {
								ModifEmailOk = true;	
							} 

							// Cas où l'email existe en BDD mais que l'IDSession est égale à l'ID de la BDD ET que le pseudo est égal au champ "email"
							else if (manager.getEmailExiste(parametre.get(PARAM_EMAIL)) && manager.VerifierEmailId(parametre.get(PARAM_EMAIL), IdSession)) {
								ModifEmailOk = true;
							} 
							
							// Cas où l'email existe en BDD mais que l'ID de la BDD est différent de l'IDSession
							else if (manager.getEmailExiste(parametre.get(PARAM_EMAIL)) && manager.RecupererIdAvecEmail(parametre.get(PARAM_EMAIL)) != IdSession) {
								ModifEmailOk = false;
								listErreurs.add("L'email " + parametre.get(PARAM_EMAIL) + " existe déjà.");
							} 
							
							else  {
								ModifEmailOk = false;
								listErreurs.add("L'email " + parametre.get(PARAM_EMAIL) + " est erronné.");
							}
							
						
					} catch (BusinessException e) {
						
					}
					
					//Vérification que le nouveau mot de passe est égal à la confirmation
					try {
						// Verifier si le new_mdp et confirm_mdp sont identiques et les deux champs sont vides
						// Cas où l'utilisateur ne modifie pas le mdp ( on garde le mot de passe actuel dans la variable )
						if (manager.verifMotDePasse(parametre.get(PARAM_NEW_MOT_DE_PASSE), parametre.get(PARAM_CONFIRM_MOT_DE_PASSE)) && parametre.get(PARAM_NEW_MOT_DE_PASSE).isEmpty() && parametre.get(PARAM_CONFIRM_MOT_DE_PASSE).isEmpty()) {
							erreurMotDePasse = false;
							password = parametre.get(PARAM_MOT_DE_PASSE_ACTUEL);
							System.out.println("MDP ancien");
						} 
						
						//  Cas où un des deux champs pour remplacer le MDP est vide 
						else if (parametre.get(PARAM_NEW_MOT_DE_PASSE).isEmpty() || parametre.get(PARAM_CONFIRM_MOT_DE_PASSE).isEmpty() )  {
							erreurMotDePasse = true;
							listErreurs.add(ERREUR_UN_CHAMP_MDP_VIDE);			
							System.out.println("un champ vide");
						} 
						
						// Vérifier si le new_mdp et confirm_mdp sont identiques, que les deux champs ne sont pas vides et que la taille des champs est respectée 
						// Vérifie que le new_mdp est différent du mdp actuel
						// Cas où l'utilisateur modifie correctement son mdp ( on met le nouveau mot de passe dans la variable ) 
						else if (manager.verifMotDePasse(parametre.get(PARAM_NEW_MOT_DE_PASSE), parametre.get(PARAM_CONFIRM_MOT_DE_PASSE)) 
										&& !parametre.get(PARAM_NEW_MOT_DE_PASSE).isEmpty() && !parametre.get(PARAM_CONFIRM_MOT_DE_PASSE).isEmpty()
										&& manager.verifierTailleChamp(parametre.get(PARAM_NEW_MOT_DE_PASSE), VALEURMAX_MDP)
										&& !manager.verifMotDePasse(parametre.get(PARAM_NEW_MOT_DE_PASSE), parametre.get(PARAM_MOT_DE_PASSE_ACTUEL))) {
							
							password = parametre.get(PARAM_NEW_MOT_DE_PASSE);
							erreurMotDePasse = false; 
							System.out.println("MDP ok");
						} 
						
						// Cas où les deux champs new_mdp dont identiques mais pas compris entre 2 et 8 caractères
						else if (parametre.get(PARAM_NEW_MOT_DE_PASSE).length() < VALEURMIN_MDP || parametre.get(PARAM_NEW_MOT_DE_PASSE).length() > VALEURMAX_MDP
									|| parametre.get(PARAM_CONFIRM_MOT_DE_PASSE).length() < VALEURMIN_MDP || parametre.get(PARAM_CONFIRM_MOT_DE_PASSE).length() > VALEURMAX_MDP) {
							listErreurs.add(LE_MDP_ENTRE_2_ET_8_CARACTERES);
							System.out.println("pas compris entre 2 et 8 caracts");
						} 
						
						// Cas où le nouveau MDP est identique à l'ancien
						else if (manager.verifMotDePasse(parametre.get(PARAM_NEW_MOT_DE_PASSE), parametre.get(PARAM_MOT_DE_PASSE_ACTUEL))) {
							listErreurs.add(LE_NEW_MDP_IDENTIQUE_ANCIEN_MDP);
							System.out.println("nouveau mdp identique à l'ancien");
						} 
						// Autres cas
						else {
							listErreurs.add(MOT_DE_PASSE_KO);
							System.out.println("MDP erreur");
						}

					} catch (BusinessException e) {
						
					}
				
						// Vérification que toutes les conditions soient remplies. Si c'est le cas, l'utilisateur est modifié dans la base de données
						if (tailleChamp == true && ModifPseudoOk == true && ModifEmailOk == true && erreurMotDePasse == false && formatEmail == true) {
							try {
								manager.getUpdatedUtilisateur(IdSession, parametre.get(PARAM_PSEUDO), parametre.get(PARAM_PRENOM), parametre.get(PARAM_TELEPHONE), parametre.get(PARAM_CODE_POSTAL), password,
										parametre.get(PARAM_NOM), parametre.get(PARAM_EMAIL), parametre.get(PARAM_RUE), parametre.get(PARAM_VILLE));
								utilisateurUpdated = true;
							} catch (BusinessException e) {
							}
							
						} 

						// Si l'utilisateur est modifié, on redirige vers son profil modifié
						if(utilisateurUpdated) {
							String URLDispatcher = REDIRECTION_SUCCESS+sIdSession;
							RequestDispatcher rd = request.getRequestDispatcher(URLDispatcher);
							
							if (rd != null) {
								rd.forward(request, response);
							}
							
						//Sinon on redirige vers le formulaire de modification profil 
						} else {
							String URLDispatcherErreur = REDIRECTION_FAIL_UPDATE+sIdSession;
							// Si non, on ajoute les erreurs dans request et on retourne la page modifier profil
							request.setAttribute("erreurs", listErreurs);
							// Redirection vers la page de modifier profil:
							RequestDispatcher rd = request.getRequestDispatcher(URLDispatcherErreur);
							if (rd != null) {
								rd.forward(request, response);
							}
						}
	}

}
