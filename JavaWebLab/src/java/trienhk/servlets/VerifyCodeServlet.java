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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import trienhk.codeverify.CodeVerifyDAO;
import trienhk.registration.RegistrationDAO;
import trienhk.registration.RegistrationDTO;

/**
 *
 * @author Treater
 */
@WebServlet(name = "VerifyCodeServlet", urlPatterns = {"/VerifyCodeServlet"})
public class VerifyCodeServlet extends HttpServlet {

    private final String VERIFIED_PAGE = "verifiedCode.jsp";
    private final String LOAD_NOTIFICATION = "LoadNotificationServlet";
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
        String userEmail = request.getParameter("txtUserEmail");
        String code = request.getParameter("txtCode");
        String name = request.getParameter("txtUserName");
        String url = VERIFIED_PAGE;
        try {
            CodeVerifyDAO dao = new CodeVerifyDAO();
            boolean result = dao.checkCode(code, userEmail);
            if (result) {
                url = LOAD_NOTIFICATION;
                RegistrationDAO registrationDAO = new RegistrationDAO();
                registrationDAO.activateAccount(userEmail);
                HttpSession session = request.getSession();
                RegistrationDTO dto = new RegistrationDTO(userEmail, "***", name, "member", "Active");
                session.setAttribute("DTO", dto);
            } else {
                request.setAttribute("ERROR", "Wrong code, please input again!");
            }

        } catch (NamingException ex) {
            logger.error(ex);
        } catch (SQLException ex) {
            logger.error(ex);
        } finally {
            if(url.equals(LOAD_NOTIFICATION)){
                response.sendRedirect(url);
            } else {
                RequestDispatcher rd = request.getRequestDispatcher(url); 
                rd.forward(request, response);
            }
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
