package controller.profile;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import persistence.DAOFactory;

public class GeneralProfile extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//fatto nel login
		//req.getSession().setAttribute("userId", 1);
		//
		Integer idUser = null;
		User principale = null;
		if(req.getSession().getAttribute("userId") != null){
			idUser = (int) req.getSession().getAttribute("userId");
			try {
				principale = DAOFactory.getInstance().makeUserDAO().getUserById(idUser);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			req.setAttribute("canSee", true);
		}
		else if(req.getParameter("id") != null)
		{
			req.setAttribute("canSee", true);
		}
		else
		{
			req.setAttribute("canSee", false);
		}

		if(req.getParameter("id") == null || (idUser != null && Integer.parseInt(req.getParameter("id")) == idUser))
		{
			req.setAttribute("user", principale);
			req.setAttribute("friend", false);
		}
		else
		{
			int idFriend = Integer.parseInt(req.getParameter("id"));
			User friend = null;
			try {
				friend = DAOFactory.getInstance().makeUserDAO().getUserById(idFriend);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			req.setAttribute("user", friend);
			req.setAttribute("friend", true);
		}
		RequestDispatcher rd = null;
		rd = req.getRequestDispatcher("profile.jsp");
		rd.forward(req, resp);

	}

}
