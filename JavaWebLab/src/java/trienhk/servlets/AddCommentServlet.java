/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.servlets;

import java.io.IOException;
import java.io.PrintWriter;
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
import trienhk.comment.CommentDAO;
import trienhk.comment.CommentDTO;
import trienhk.notification.NotificationDAO;
import trienhk.notification.NotificationDTO;

/**
 *
 * @author Treater
 */
@WebServlet(name = "AddCommentServlet", urlPatterns = {"/AddCommentServlet"})
public class AddCommentServlet extends HttpServlet {

    private String ERROR = "error.html";
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
        logger = Logger.getLogger(AddCommentServlet.class.getName());
        BasicConfigurator.configure();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        String comment = request.getParameter("txtComment");
        String txtIdPost = request.getParameter("txtIdPost");
        String userEmail = request.getParameter("txtUserEmail");
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        Date date = ts;
        String idComment = userEmail + "_" + date.toString();
        String url = ERROR;
        try {
            if (comment.trim().length() > 0) {
                CommentDAO dao = new CommentDAO();
                CommentDTO dto = new CommentDTO(txtIdPost, userEmail, comment, ts, idComment);
                boolean result = dao.addComment(dto);
                if (result) {
                    //check this article is commenter own
                    String[] splitString = txtIdPost.split("__");
                    if (!splitString[0].equals(userEmail)) {
                        NotificationDAO notiDAO = new NotificationDAO();
                        NotificationDTO notiDTO = new NotificationDTO(userEmail, txtIdPost, "comment", ts);
                        notiDAO.insert(notiDTO);
                    }
                    url = "DispatchServlet?btAction=Load detail page&idPost=" + txtIdPost;
                }
            } else {
                url = "DispatchServlet?btAction=Load detail page&idPost=" + txtIdPost;
            }

        } catch (NamingException ex) {
            logger.error(ex);
        } catch (SQLException ex) {
            logger.error(ex);
        } finally {
            response.sendRedirect(url);
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
