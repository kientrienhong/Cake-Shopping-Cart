<%-- 
    Document   : CreateNewPost
    Created on : Sep 17, 2020, 7:37:07 AM
    Author     : Treater
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create new post</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    </head>
    <body>
        <c:set var="error" value="${requestScope.ERROR}"></c:set>
            <div class="d-flex justify-content-center" style="margin-top: 10%">
                <div class="card border-secondary w-50">
                    <div class="card-header">
                        Create new post
                    </div>
                    <div class="card-body">
                        <form action="DispatchServlet">
                            <input type="hidden" name="txtUserEmail" value="${sessionScope.DTO.email}" />
                        <div class="form-group">
                            <label>Title: </label>
                            <input class="form-control col-6" type="text" name="txtTitle" value="${param.txtTitle}" /> 
                        </div>
                        <c:if test="${not empty error.titleError}">
                            <div class="alert alert-danger col-6" role="alert">
                                ${error.titleError}
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label>Description: </label>
                            <input class="form-control col-6" type="text" name="txtDescription" value="${param.txtDescription}" /> 
                        </div>
                        <c:if test="${not empty error.descriptionError}">
                            <div class="alert alert-danger col-6" role="alert">
                                ${error.descriptionError}
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label>Upload file: </label>
                            <input type="file" id="myFile" name="filename"></input>
                        </div>
                        <c:if test="${not empty error.imageError}">
                            <div class="alert alert-danger col-6" role="alert">
                                ${error.imageError}
                            </div>
                        </c:if>
                        <input type="submit" value="Post Article" name="btAction" class="btn btn-primary"/>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
