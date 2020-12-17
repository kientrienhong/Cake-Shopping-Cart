/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import trienhk.article.ArticleDAO;
import trienhk.article.ArticleDTO;
import trienhk.article.ArticleErrorCreate;

/**
 *
 * @author Treater
 */
@WebServlet(name = "CreateNewPostServlet", urlPatterns = {"/CreateNewPostServlet"})
public class CreateNewPostServlet extends HttpServlet {

    private final String CREATE_NEW_POST = "createNewPost.jsp";
    private final String ERROR_PAGE = "error.html";
    private Logger logger = null;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void init() {
        logger = Logger.getLogger(LoginServlet.class.getName());
        BasicConfigurator.configure();
    }

    private byte[] openFile(String fileName) {
        FileInputStream f = null;
        byte[] kq = null;
        try {
            f = new FileInputStream(fileName);
            if (f != null) { // lấy kích thước file
                int size = f.available();
                kq = new byte[size];
                f.read(kq); // đọc dữ liệu của f và cất vào kq 

            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            try {
                if (f != null) {
                    f.close();
                }
            } catch (Exception e) {
                logger.error(e);
            }
        }

        return kq;
    }

    private void writeImageToSource(String fileName, byte[] kq) {
        FileOutputStream f = null;

        try {
            f = new FileOutputStream(fileName);
            if (f != null && kq != null) {
                f.write(kq);
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            try {
                if (f != null) {
                    f.close();
                }
            } catch (Exception e) {
                logger.error(e);
            }
        }
    }

    public String getPath() throws UnsupportedEncodingException {

        String path = this.getClass().getClassLoader().getResource("").getPath();
        String fullPath = URLDecoder.decode(path, "UTF-8");
        String pathArr[] = fullPath.split("/build/web/WEB-INF/classes/");
        fullPath = pathArr[0];

        return fullPath;

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        String currentImagePath = getPath() + "/web/img/";
        File fileCheckList = new File(currentImagePath);
        String title = request.getParameter("txtTitle");
        String description = request.getParameter("txtDescription");
        String file = request.getParameter("filename");
        String txtUserEmail = request.getParameter("txtUserEmail");
        String url = ERROR_PAGE;
        String[] listFile = fileCheckList.list();
        boolean valid = true;
        //get name file
        int lastIndexOf = file.lastIndexOf("\\");
        String fileName = file.substring(lastIndexOf + 1);
        ArticleErrorCreate error = new ArticleErrorCreate();
        if (!fileName.contains(".jpg") && !fileName.contains(".png") && fileName.length() > 0) {
            valid = false;
            error.setImageError("Your file must be .jpg or .png");
        }

        if (description.length() == 0) {
            valid = false;
            error.setDescriptionError("Description must be filled!");
        }

        if (title.length() == 0) {
            valid = false;
            error.setTitleError("Title must be filled!");
        }

        for (int i = 0; i < listFile.length; i++) {
            if (fileName.equals(listFile[i])) {
                valid = false;
                error.setImageError("Already has this name please change name!");
                break;
            }
        }

        try {
            if (valid) {
                ArticleDAO dao = new ArticleDAO();
                Timestamp ts = new Timestamp(System.currentTimeMillis());
                Date date = ts;
                String id = txtUserEmail + "__" + date.toString();

                ArticleDTO dto = new ArticleDTO(title, description, fileName, ts, txtUserEmail, id);

                byte[] image = openFile(file);
                writeImageToSource(currentImagePath + fileName, image);
                if (dao.create(dto)) {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Create sucessfully');");
                    out.println("location='store.jsp';");
                    out.println("</script>");
                } else {
                    response.sendRedirect(url);
                }
            } else {
                request.setAttribute("ERROR", error);
                url = CREATE_NEW_POST;
                request.getRequestDispatcher(url).forward(request, response);
            }

        } catch (NamingException ex) {
            logger.error(ex);
        } catch (SQLException ex) {
            logger.error(ex);
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
