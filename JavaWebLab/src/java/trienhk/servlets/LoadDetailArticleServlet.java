/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
import trienhk.comment.CommentDAO;
import trienhk.comment.CommentDTO;
import trienhk.emotion.EmotionDAO;
import trienhk.emotion.EmotionDTO;

/**
 *
 * @author Treater
 */
@WebServlet(name = "LoadDetailArticleServlet", urlPatterns = {"/LoadDetailArticleServlet"})
public class LoadDetailArticleServlet extends HttpServlet {

    private Logger logger = null;
    private final String DETAIL_ARTICLE = "detailArticle.jsp";
    private final String NOT_FOUND_PAGE = "notFoundArticle.html";
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
        String idPost = request.getParameter("idPost");
        String url = NOT_FOUND_PAGE;
        try {
            ArticleDAO articleDAO = new ArticleDAO();
            ArticleDTO articleDTO = articleDAO.findById(idPost);

            if (articleDTO != null) {
                CommentDAO commentDAO = new CommentDAO();
                List<CommentDTO> listComment = commentDAO.loadListByArticleId(idPost);
                EmotionDAO emotionDAO = new EmotionDAO();
                List<EmotionDTO> listEmotion = emotionDAO.loadListByArticleId(idPost);
                List<EmotionDTO> listLike = new ArrayList<>();
                List<EmotionDTO> listDislike = new ArrayList<>();

                if (listComment == null) {
                    listComment = new ArrayList<>();
                }

                if (listEmotion != null) {
                    for (EmotionDTO dto : listEmotion) {
                        if (dto.isIsLike()) {
                            listLike.add(dto);
                        } else {
                            listDislike.add(dto);
                        }
                    }
                }

                Collections.reverse(listLike);
                Collections.reverse(listDislike);

                request.setAttribute("ARTICLE_DETAILS", articleDTO);
                request.setAttribute("LIST_COMMENT", listComment);
                request.setAttribute("LIST_LIKE", listLike);
                request.setAttribute("LIST_DISLIKE", listDislike);
                
                url = DETAIL_ARTICLE;
            } 
        } catch (NamingException ex) {
            logger.error(ex);
        } catch (SQLException ex) {
            logger.error(ex);
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
