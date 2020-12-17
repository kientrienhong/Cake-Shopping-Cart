/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Treater
 */
@WebServlet(name = "DispatchServlet", urlPatterns = {"/DispatchServlet"})
public class DispatchServlet extends HttpServlet {
    private final String LOGIN_SERVLET = "LoginServlet";
    private final String SIGN_UP_SERVLET = "SignUpServlet"; 
    private final String ERROR = "error.html";
    private final String LOG_OUT_SERVLET = "LogOutServlet";
    private final String CREATE_NEW_POST_PAGE = "createNewPost.jsp";
    private final String CREATE_NEW_POST_SERVLET = "CreateNewPostServlet";
    private final String DELETE_ARTICLE_SERVLET = "DeleteArticleServlet";
    private final String LOAD_DETAIL_ARTICLE_SERVLET = "LoadDetailArticleServlet";
    private final String ADD_COMMENT = "AddCommentServlet"; 
    private final String EMOTION_SERVLET = "EmotionServlet"; 
    private final String LOAD_NOTIFICATION = "LoadNotificationServlet"; 
    private final String DELETE_COMMENT = "DeleteCommentServlet";
    private final String VERIFIED_CODE = "VerifyCodeServlet";
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String btAction = request.getParameter("btAction");
        String url = ERROR; 
        try {
            if(btAction.equals("Login")){
                url = LOGIN_SERVLET; 
            } else if (btAction.equals("Sign up")){
                url = SIGN_UP_SERVLET; 
            } else if (btAction.equals("Log out")){
                url = LOG_OUT_SERVLET;
            } else if (btAction.equals("Search")){
                url = LOAD_NOTIFICATION; 
            } else if (btAction.equals("Create new post")){
                url = CREATE_NEW_POST_PAGE;
            } else if (btAction.equals("Post Article")){
                url = CREATE_NEW_POST_SERVLET;
            } else if(btAction.equals("Delete Article")){
                url = DELETE_ARTICLE_SERVLET;
            } else if(btAction.equals("Load detail page")){
                url = LOAD_DETAIL_ARTICLE_SERVLET;
            } else if(btAction.equals("Comment")){
                url = ADD_COMMENT;
            } else if(btAction.equals("Like") || btAction.equals("Dislike")){
                url = EMOTION_SERVLET; 
            } else if (btAction.equals("Delete Comment")){
                url = DELETE_COMMENT;
            } else if (btAction.equals("Activate")){
                url = VERIFIED_CODE;
            }
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url); 
            rd.forward(request, response);
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
