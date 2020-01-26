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
		RequestDispatcher rd = null;
		rd = req.getRequestDispatcher("header.jsp");
		rd.include(req, resp);
		User loggedUser = (User) req.getSession().getAttribute("user");
		User toShow = new User();
		if (req.getParameter("id") == null || Integer.parseInt(req.getParameter("id")) == loggedUser.getId()){
			toShow = loggedUser;
		}else if (req.getParameter("id") != null){
			toShow = DAOFactory.getInstance().makeUserDAO().getUserById(Integer.parseInt(req.getParameter("id")));
		}
		toShow.setFriends(DAOFactory.getInstance().makeUserDAO().getFriends(toShow));
		req.setAttribute("toShow", toShow);
		rd = req.getRequestDispatcher("profile.jsp");
		rd.include(req, resp);
		rd = req.getRequestDispatcher("footer.html");
		rd.include(req, resp);
	}

}
