package controller.profile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import persistence.DAOFactory;

@WebServlet(value = "/profile", name = "profile")
public class Profile extends HttpServlet{

	private boolean setUserToShow(HttpServletRequest req) {
		User loggedUser = (User) req.getSession().getAttribute("user");
		boolean returnValue = true;
		boolean isFriend = false;
		User toShow = new User();
		if (req.getParameter("id") == null || Integer.parseInt(req.getParameter("id")) == loggedUser.getId()){
			toShow = loggedUser;
		}else if (req.getParameter("id") != null){
			ArrayList<User> friends = DAOFactory.getInstance().makeUserDAO().getFriends(loggedUser.getId());
			for (User friend: friends) {
				if (friend.getId() == Integer.parseInt(req.getParameter("id"))) {
					isFriend = true;
					break;
				}
			}
			returnValue = false;
			toShow = DAOFactory.getInstance().makeUserDAO().getUserById(Integer.parseInt(req.getParameter("id")));
		}
		req.setAttribute("toShow", toShow);
		req.setAttribute("isFriend", isFriend);
		return returnValue;
	}

	private void takeData(HttpServletRequest req, User user, byte[] image) {
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
		try {
			List<FileItem> fileItems = servletFileUpload.parseRequest(req);
			for (FileItem f : fileItems){
				switch (f.getFieldName()){
					case "profileImage":
						user.setImage(true);
						image = f.get();
						break;

					case "username":
						user.setUsername(f.getString());
						break;

					case "email":
						user.setEmail(f.getString());
						break;

					case "password":
						user.setPassword(f.getString());
						break;

					case "description":
						user.setDescription(f.getString());

					default:
						break;
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher("header.jsp");
		rd.include(req, resp);
		if ((Boolean)req.getSession().getAttribute("logged") == null || !(Boolean)req.getSession().getAttribute("logged")) {
			rd = req.getRequestDispatcher("errorNotLogged.html");
			rd.include(req,resp);
			rd = req.getRequestDispatcher("footer.html");
			rd.include(req, resp);
			return;
		}
		if (setUserToShow(req))
			rd = req.getRequestDispatcher("profile.jsp");
		else
			rd = req.getRequestDispatcher("friendPage.jsp");
		rd.include(req, resp);
		rd = req.getRequestDispatcher("footer.html");
		rd.include(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User) req.getSession().getAttribute("user");
		byte[] image = null;
		takeData(req, user, image);
		DAOFactory.getInstance().makeUserDAO().changeUserDetails(user, image);
		resp.sendRedirect("/profile");
	}

}
