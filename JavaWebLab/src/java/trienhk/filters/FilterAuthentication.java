/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.filters;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import trienhk.registration.RegistrationDTO;
import trienhk.servlets.AddCommentServlet;

/**
 *
 * @author Treater
 */
@WebFilter(filterName = "FilterAuthentication", urlPatterns = {"/*"})
public class FilterAuthentication implements Filter {

    private static final boolean debug = true;
    private final String LOGIN = "login.html";
    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    private Logger logger = null;

    public FilterAuthentication() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("FilterAuthentication:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
	/*
         for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
         String name = (String)en.nextElement();
         String values[] = request.getParameterValues(name);
         int n = values.length;
         StringBuffer buf = new StringBuffer();
         buf.append(name);
         buf.append("=");
         for(int i=0; i < n; i++) {
         buf.append(values[i]);
         if (i < n-1)
         buf.append(",");
         }
         log(buf.toString());
         }
         */
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("FilterAuthentication:DoAfterProcessing");
        }

	// Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
	/*
         for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
         String name = (String)en.nextElement();
         Object value = request.getAttribute(name);
         log("attribute: " + name + "=" + value.toString());

         }
         */
        // For example, a filter might append something to the response.
	/*
         PrintWriter respOut = new PrintWriter(response.getWriter());
         respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        try {
            logger = Logger.getLogger(AddCommentServlet.class.getName());
            BasicConfigurator.configure();
            String url = LOGIN;
            HttpServletRequest req = (HttpServletRequest) request;
            HttpSession session = req.getSession(false);
            String uri = req.getRequestURI();
            String btAction = req.getParameter("btAction");
            boolean isNeededAuthenBtAction = false;

            if (btAction != null) { // check paramters
                if (!btAction.equals("Login") && !btAction.equals("Sign up") && !btAction.equals("Activate") && !btAction.equals("Sending Email")) {
                    isNeededAuthenBtAction = true;
                }
            }

            int lastIndex = uri.lastIndexOf("/");
            String resource = uri.substring(lastIndex + 1);
            if (resource.length() > 0) {
                if (!uri.contains("login") && !uri.contains("invalid") && !uri.contains("Login") 
                        && !resource.equals("DispatchServlet") && !resource.contains("createAccount") 
                        && !resource.contains("SignUp") && !resource.contains("verifiedCode") || isNeededAuthenBtAction) {
                    if (session == null) {
                        req.getRequestDispatcher(url).forward(request, response);
                    } else {
                        RegistrationDTO dto = (RegistrationDTO) session.getAttribute("DTO");
                        if (dto == null) {
                            ((HttpServletResponse) response).sendRedirect(url);
                        } else {
                            url = resource;
                            if (uri.contains("createNewPost")) {
                                if (!dto.getRole().equals("member")) {
                                    url = LOGIN;
                                }
                            }

                            if (btAction != null) {
                                if (btAction.contains("Post") || btAction.equals("Create new post") 
                                        || btAction.equals("Comment") || btAction.equals("Like") 
                                        || btAction.equals("Dislike")) {
                                    if (!dto.getRole().equals("member")) {
                                        url = LOGIN;
                                    }
                                }
                            }
                            req.getRequestDispatcher(url).forward(request, response);
                        }
                    }
                } else {
                    url = resource;
                    req.getRequestDispatcher(url).forward(request, response);
                }
            } else {
                chain.doFilter(request, response);
            }
        } catch (Throwable t) {
            logger.error(t);
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("FilterAuthentication:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("FilterAuthentication()");
        }
        StringBuffer sb = new StringBuffer("FilterAuthentication(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
