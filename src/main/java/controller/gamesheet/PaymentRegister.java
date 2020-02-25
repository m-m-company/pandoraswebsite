package controller.gamesheet;

import model.User;
import persistence.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value="/paymentSuccess", name = "paymentSuccess")
public class PaymentRegister extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idUser = ((User) req.getSession().getAttribute("user")).getId();
        int idGame = Integer.parseInt(req.getParameter("idGame"));
        double price = Double.parseDouble(req.getParameter("price"));
        if(DAOFactory.getInstance().makePurchaseDAO().insertNewPurchase(idUser, idGame, price)){
            resp.setStatus(201);
        }
        else{
            resp.setStatus(403);
        }
    }

}
