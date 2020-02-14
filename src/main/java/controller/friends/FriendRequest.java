package controller.friends;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/friendRequest", name = "friendRequest")
public class FriendRequest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("header.jsp");
        rd.include(req, resp);
        rd = req.getRequestDispatcher("friendRequestPage.html");
        rd.include(req, resp);
        rd = req.getRequestDispatcher("footer.html");
        rd.include(req, resp);
    }
}
