<%-- 
    Document   : verifiedCode
    Created on : Sep 24, 2020, 3:48:26 PM
    Author     : Treater
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    </head>
    <body>
        <div class="d-flex justify-content-center" style="margin-top: 10%">
            <div class="card border-secondary w-50">
                <div class="card-header">
                    Verified Code
                </div>
                <div class="card-body">
                    <form action="DispatchServlet" method="POST">
                        <div class="form-group">
                            <input type="hidden" name="txtUserEmail" value="${param.txtEmail}" />
                            <input type="hidden" name="txtUserPassword" value="${param.txtPassword}" />
                            <input type="hidden" name="txtUserName" value="${param.txtName}" />

                            <label>Code: </label>
                            <input class="form-control col-6" type="text" name="txtCode" value="" /> 
                        </div>
                        <input type="submit" value="Activate" class="btn btn-primary" name="btAction"/>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
