package fr.eni.encheres.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ModifierProfil
 */
@WebServlet("/modifierProfil")
public class ModifierProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SERVLET_MODIF_PROFIL = "/WEB-INF/jsp/ModifierProfil.jsp";

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd; 
		rd = request.getRequestDispatcher(SERVLET_MODIF_PROFIL);
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
