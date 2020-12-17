/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import trienhk.registration.RegistrationDAO;
import trienhk.registration.RegistrationDTO;
import trienhk.registration.RegistrationErrorSignUp;

/**
 *
 * @author Treater
 */
@WebServlet(name = "SignUpServlet", urlPatterns = {"/SignUpServlet"})
public class SignUpServlet extends HttpServlet {

    private final String SIGN_UP_JSP = "createAccount.jsp";
    private final String SENDING_EMAIL = "SendingEmailServlet";
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
        String url = SIGN_UP_JSP;
        String email = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");
        String confirm = request.getParameter("txtConfirmPassword");
        String name = request.getParameter("txtName");
        boolean valid = true;
        RegistrationErrorSignUp error = new RegistrationErrorSignUp();

        if (email.length() == 0) {
            valid = false;
            error.setEmailError("Email can not be empty!");
        } else {
            String regex = "^(.+)@(.+)$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()){
                error.setEmailError("Wrong format email");
            }
        }

        if (password.length() < 4 || password.length() > 20) {
            valid = false;
            error.setPasswordError("Password length must be in range 4 - 20");
        } else {
            if (!confirm.equals(password)) {
                valid = false;
                error.setPasswordConfirmError("Confirm must be match with Password!");
            }
        }

        if (name.length() == 0) {
            valid = false;
            error.setNameError("Name can not be empty!");
        }
        try {
            if (valid) {
                RegistrationDAO dao = new RegistrationDAO();
                RegistrationDTO dto = new RegistrationDTO(email, password, name, "member", "new");
                boolean result = dao.signUp(dto);
                if (result) {
                    url = SENDING_EMAIL;
                }
            } else {
                request.setAttribute("ERROR", error);
            }

        } catch (NamingException e) {
            logger.error(e);
        } catch (SQLException ex) {
            if (ex.getMessage().contains("duplicate")) {
                valid = false;
                error.setEmailExisted("Email has been existed!");
                request.setAttribute("ERROR", error);
            } else {
            logger.error(ex);
            }

        } catch (NoSuchAlgorithmException ex) {
            logger.error(ex);
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
