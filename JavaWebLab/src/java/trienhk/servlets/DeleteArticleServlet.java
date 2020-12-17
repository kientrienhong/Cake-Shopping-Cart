/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import trienhk.article.ArticleDAO;
import trienhk.registration.RegistrationDTO;

/**
 *
 * @author Treater
 */
@WebServlet(name = "DeleteArticleServlet", urlPatterns = {"/DeleteArticleServlet"})
public class DeleteArticleServlet extends HttpServlet {

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
        String txtIdPost = request.getParameter("txtIdPost");
        String currentPage = request.getParameter("txtCurrentPage");
        String searchValue = request.getParameter("txtSearch");
        String amountPageCurrent = request.getParameter("txtCurrentAmountPosts");
        HttpSession session = request.getSession(false);

        if (session != null) {
            RegistrationDTO registrationDTO = (RegistrationDTO) session.getAttribute("DTO");
            int currentPageInterger = Integer.parseInt(currentPage);
            if (amountPageCurrent.equals("1")) {
                currentPageInterger -= 1;
            }
            String url = ERROR;

            try {
                ArticleDAO dao = new ArticleDAO();
                boolean result = false;
                if (registrationDTO.getRole().equals("admin")) {
                    result = dao.deleteViaAdmin(txtIdPost);
                } else {
                    result = dao.delete(txtIdPost, registrationDTO.getEmail());
                }
                if (result) {
                    url = "DispatchServlet?btAction=Search&page=" + currentPageInterger + "&txtSearch=" + searchValue;
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
