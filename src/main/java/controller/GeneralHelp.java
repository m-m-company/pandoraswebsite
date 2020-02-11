package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.mail.smtp.SMTPTransport;
import model.User;
import persistence.DAOFactory;
import utility.EmailSender;

@WebServlet(value = "/help")
public class GeneralHelp extends HttpServlet
{
	String to;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		getPage(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		if(req.getParameter("send").equals("false"))
			getPage(req, resp);
		else if(req.getParameter("send").equals("true"))
		{
			new Thread(new EmailSender("This email is from " + req.getParameter("userEmail") + "\n "+ req.getParameter("content"),
					req.getParameter("subject"), req.getParameter("receiver")));
		}
		resp.sendRedirect("/");
	}

	private void getPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		to = req.getParameter("emailTo");
		if(to == null)
		{
			to = "pandorasjar2019@gmail.com";
		}
		User loggedUser = null;
		if(req.getSession().getAttribute("user") != null)
		{
			loggedUser = (User) req.getSession().getAttribute("user");
			String name = loggedUser.getUsername();
			String email = loggedUser.getEmail();
			req.setAttribute("name", name);
			req.setAttribute("email", email);
		}
		req.setAttribute("emailTo", to);
		RequestDispatcher rd;
		rd = req.getRequestDispatcher("header.jsp");
		rd.include(req, resp);
		rd = req.getRequestDispatcher("help.jsp");
		rd.include(req, resp);
		rd = req.getRequestDispatcher("footer.html");
		rd.include(req, resp);
	}
}
