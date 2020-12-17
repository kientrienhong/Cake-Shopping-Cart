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
import trienhk.emotion.EmotionDAO;
import trienhk.emotion.EmotionDTO;
import trienhk.notification.NotificationDAO;
import trienhk.notification.NotificationDTO;

/**
 *
 * @author Treater
 */
@WebServlet(name = "EmotionServlet", urlPatterns = {"/EmotionServlet"})
public class EmotionServlet extends HttpServlet {

    private final String ERROR = "error.html";
    private final String NOT_FOUND_PAGE = "notFoundArticle.html";
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String txtArticleID = request.getParameter("txtArticleID");
        String txtEmotion = request.getParameter("txtEmotion");
        String txtUserEmail = request.getParameter("txtUserEmail");
        boolean isLike = txtEmotion.equals("like");
        String currentPage = request.getParameter("txtCurrentPage");
        String searchValue = request.getParameter("txtSearch");
        String url = ERROR;
        String idEmotion = txtUserEmail + "_" + txtArticleID;
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String[] splitString = txtArticleID.split("__");

        boolean isArticleOfCurrentUser = false;
        if (splitString[0].equals(txtUserEmail)) {
            isArticleOfCurrentUser = true;
        }

        try {
            ArticleDAO articleDAO = new ArticleDAO();
            ArticleDTO articleDTO = articleDAO.findById(txtArticleID);

            if (articleDTO != null) { // check this article is deleted or not
                EmotionDAO dao = new EmotionDAO();
                EmotionDTO resultFind = dao.findById(idEmotion);
                boolean result = false;
                if (resultFind == null) { // check has user interact emotion to this article yet? NO => create new
                    EmotionDTO dto = new EmotionDTO(idEmotion, txtArticleID, txtUserEmail, isLike);
                    result = dao.insert(dto);
                    if (isArticleOfCurrentUser == false) {
                        NotificationDAO notiDAO = new NotificationDAO();
                        NotificationDTO notiDTO = new NotificationDTO(txtUserEmail, txtArticleID, txtEmotion, ts);
                        notiDAO.insert(notiDTO);
                    }

                } else { // already => update status to DELETE OR ACTIVE the remain emotion
                    if (resultFind.getStatus().equals("ACTIVE")) {
                        if (isLike == resultFind.isIsLike()) {
                            result = dao.delete(idEmotion);
                        } else {
                            result = dao.update(isLike, idEmotion);
                            if (isArticleOfCurrentUser == false) {
                                NotificationDAO notiDAO = new NotificationDAO();
                                NotificationDTO notiDTO = new NotificationDTO(txtUserEmail, txtArticleID, txtEmotion, ts);
                                notiDAO.insert(notiDTO);
                            }
                        }
                    } else {
                        result = dao.reInteractEmotion(idEmotion, isLike);
                        if (isArticleOfCurrentUser == false) {
                            NotificationDAO notiDAO = new NotificationDAO();
                            NotificationDTO notiDTO = new NotificationDTO(txtUserEmail, txtArticleID, txtEmotion, ts);
                            notiDAO.insert(notiDTO);
                        }
                    }

                }

                if (result) {
                    url = "DispatchServlet?btAction=Search&page=" + currentPage + "&txtSearch=" + searchValue;
                }
            } else {
                url = NOT_FOUND_PAGE;
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
