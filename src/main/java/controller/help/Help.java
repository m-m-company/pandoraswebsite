package controller.help;

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

@WebServlet(value = "/help", name = "help")
public class Help extends HttpServlet
{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		getPage(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String content = req.getParameter("content");
		String text = "This email is from " + name + "(" + email + ")\n" + content;
		String subject = req.getParameter("subject");
		new Thread(new EmailSender(text, subject,"pandorasjar2019@gmail.com")).start();
		resp.sendRedirect("/");
	}

	private void getPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String to = req.getParameter("emailTo");
		if(to == null)
		{
			to = "pandorasjar2019@gmail.com";
		}
		User loggedUser = (User) req.getSession().getAttribute("user");
		if(loggedUser != null)
		{
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
