package controller.upload;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Game;
import model.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import persistence.DAOFactory;

@WebServlet(value = "/formGameUpload", name = "formGameUpload")
public class FormGameUpload extends HttpServlet {

    private String filePath;

    @Override
    public void init() {
        filePath = this.getServletContext().getRealPath(File.separator) + File.separator + "gameFiles";
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("header.jsp");
        requestDispatcher.include(req, resp);
        if (req.getSession().getAttribute("logged") == null || !(Boolean) req.getSession().getAttribute("logged")) {
            requestDispatcher = req.getRequestDispatcher("errorNotLogged.html");
        } else {
            requestDispatcher = req.getRequestDispatcher("formGameUpload.html");
        }
        requestDispatcher.include(req, resp);
        requestDispatcher = req.getRequestDispatcher("footer.html");
        requestDispatcher.include(req, resp);
    }

    private void storeFile(FileItem item, String directory) throws Exception {
        String name = item.getName();
        File file = new File(filePath + File.separator + directory + File.separator + name);
        item.write(file);
    }

    private void handleInsertGame(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String name = "";
        String description = "";
        String specifics = "";
        double price = 0;
        FileItem frontImage = null;
        ArrayList<FileItem> previewFiles = new ArrayList<>();
        ArrayList<String> externalLinks = new ArrayList<>();
        ArrayList<String> tags = new ArrayList<>();

        FileItem jarFile = null;
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
        try {
            List<FileItem> fileItems = servletFileUpload.parseRequest(req);
            for (FileItem i : fileItems) {
                if (i.getFieldName().equals("name")) {
                    name = i.getString();
                } else if (i.getFieldName().equals("description")) {
                    description = i.getString();
                } else if (i.getFieldName().equals("specifics")) {
                    specifics = i.getString();
                } else if (i.getFieldName().equals("gameFile")) {
                    jarFile = i;
                } else if (i.getFieldName().equals("frontImage")) {
                    frontImage = i;
                } else if (i.getFieldName().equals("previewFiles")) {
                    previewFiles.add(i);
                } else if (i.getFieldName().contains("link")) {
                    if (!i.getString().equals("")){
                        externalLinks.add(i.getString());
                    }
                } else if (i.getFieldName().equals("price")) {
                    price = Double.parseDouble(i.getString());
                } else if (i.getFieldName().contains("tag")) {
                    tags.add(i.getString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (DAOFactory.getInstance().makeGameDAO().getGameByName(name) != null) {
            resp.sendRedirect("/formGameUpload?gameNameAlreadyExists=true");
        } else {
            HttpSession session = req.getSession();
            int idDeveloper = ((User) session.getAttribute("user")).getId();
            String supportEmail = (String) session.getAttribute("helpEmail");
            String paymentCoords = (String) session.getAttribute("paymentCoords");
            assert frontImage != null;
            DAOFactory.getInstance().makeGameDAO().insertGame(name, frontImage.getName(), paymentCoords, supportEmail, price,
                    idDeveloper, description, specifics, tags, externalLinks);
            this.storeFile(frontImage, name);
            assert jarFile != null;
            this.storeFile(jarFile, name);
            for (FileItem file : previewFiles) {
                this.storeFile(file, name + File.separator + "previews");
            }
            resp.sendRedirect("/GameDataSheet?gameId="+DAOFactory.getInstance().makeGameDAO().getGameByName(name).getId());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            handleInsertGame(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
