<%-- 
    Document   : searchPage
    Created on : Sep 15, 2020, 8:39:33 AM
    Author     : Treater
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    </head>
    <body>
        <c:set value="${sessionScope.DTO}" var="userDTO"></c:set>
        <c:if test="${empty userDTO}">
            <c:redirect url="login.html"/>
        </c:if>
        <div class="form-inline">
            <font color="red" style="margin-left: 40%; margin-top: 1%">
            <h2>
                Welcome, ${userDTO.name}
            </h2>
            </font>  
            <div class="form-group col-1" style="position: absolute; top: 20px; right: 30px">
                <form action="DispatchServlet" method="POST">
                    <input type="submit" value="Log out" name="btAction" class="btn btn-danger"/>
                </form>  
            </div>
            <div class="form-group col-1" style="position: absolute; top: 20px; right: 200px">
                <form action="DispatchServlet">
                    <input type="submit" value="Create new post" name="btAction" class="btn btn-primary"/>
                </form>  
            </div>
            <div class="form-group col-2" style="position: absolute; top: 20px; left: 30px">
                <button type="button" data-toggle="modal" data-target="#notificationModal" class="btn btn-primary">View notification</button>
            </div>
        </div>
        <form action="DispatchServlet" class="form-inline" style="margin-top: 3%; margin-bottom: 2%">
            <div class="form-group col-5" style="margin-left: 35%">
                <input class="form-control" placeholder="Content..." type="text" name="txtSearch" value="${param.txtSearch}" style="margin-top: 1%; width: 300px"/>
            </div>
            <div class="form-group col-1" style="margin-left: -20%; padding-top: 3px">
                <input type="submit" value="Search" class="btn btn-primary" name="btAction"/>
            </div>
        </form>   

        <c:set var="currentPage" value="${requestScope.CURRENT_PAGE}"/>
        <c:set var="txtSearchValue" value="${param.txtSearch}"/>
        <c:set var="currentAmountPosts" value="${requestScope.CURRENT_AMOUNT_POSTS}"/>
        <c:set var="notiList" value="${sessionScope.LIST_NOTFICATION}"/>
        <c:if test="${not empty txtSearchValue}">
            <c:set var="listArticle" value="${requestScope.LIST_ARTICLE}"/>

            <c:if test="${not empty listArticle}" var="checkList">
                <c:forEach items="${listArticle}" var="articleDTO" varStatus="vs">
                    <form action="DispatchServlet">
                        <input type="hidden" name="txtCurrentPage" value="${currentPage}" />
                        <input type="hidden" name="txtIdPost" value="${articleDTO.id}" />
                        <input type="hidden" name="txtSearch" value="${txtSearchValue}" />
                        <input type="hidden" name="txtCurrentAmountPosts" value="${currentAmountPosts}" />
                        <div class="modal fade" id="confirmModal${vs.index}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Are you sure want to delete this post?</h5> 
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                        <input type="submit" value="Delete Article" class="btn btn-danger" name="btAction"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card mb-3" style="max-width: 70%; margin: 0 auto">
                            <div class="card-body">
                                <div class="form-row">
                                    <h4 class="card-title col-8">${articleDTO.title}</h4>
                                    <c:if test="${articleDTO.registrationEmail eq userDTO.email or userDTO.role == 'admin'}">
                                        <button type="button" data-toggle="modal" data-target="#confirmModal${vs.index}" class="btn btn-danger col-2" style="margin-left: 100px">Delete Article</button>
                                    </c:if>
                                </div>
                                </form>
                                <p class="card-text" style="margin-top: -10px"><small class="text-muted">${articleDTO.date}</small></p>
                                <p class="card-text" style="margin-bottom: 10px">${articleDTO.description}</p>
                                <a href="DispatchServlet?btAction=Load detail page&idPost=${articleDTO.id}" style="margin-left: 840px">See more detail &rarr;</a>
                                <div class="form-row">
                                    <form action="DispatchServlet">
                                        <input type="hidden" name="txtArticleID" value="${articleDTO.id}" />
                                        <input type="hidden" name="txtEmotion" value="like" />
                                        <input type="hidden" name="txtUserEmail" value="${userDTO.email}" />
                                        <input type="hidden" name="txtSearch" value="${txtSearchValue}" />
                                        <input type="hidden" name="txtCurrentPage" value="${currentPage}" />
                                        <input type="submit" value="Like" name="btAction" class="btn btn-primary"/>
                                    </form>
                                    <form action="DispatchServlet">
                                        <input type="hidden" name="txtArticleID" value="${articleDTO.id}" />
                                        <input type="hidden" name="txtEmotion" value="dislike" />
                                        <input type="hidden" name="txtUserEmail" value="${userDTO.email}" />
                                        <input type="hidden" name="txtSearch" value="${txtSearchValue}" />
                                        <input type="hidden" name="txtCurrentPage" value="${currentPage}" />
                                        <input type="submit" value="Dislike" name="btAction" class="btn btn-danger"/>
                                    </form>
                                </div>
                            </div>
                            <c:if test="${not empty articleDTO.image}">
                                <img class="card-img-bottom" src="./img/${articleDTO.image}" alt="${articleDTO.title}" width="100%" height="500px"/>
                            </c:if>
                        </div>
                    </c:forEach>
                    <c:set var="totalPage" value="${requestScope.TOTAL_PAGE}"></c:set>
                    <c:if test="${totalPage gt 1}">
                        <ul class="pagination justify-content-center">
                            <c:forEach var="page" begin="1" step="1"  end="${totalPage}">
                                <li <c:if test="${page eq currentPage}" var="checkCurrentPage">
                                        class="page-item active"
                                    </c:if>
                                    <c:if test="${!checkCurrentPage}">
                                        class="page-item"
                                    </c:if>>
                                    <a class="page-link"
                                       href="DispatchServlet?btAction=Search&page=${page}&txtSearch=${txtSearchValue}">${page}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </c:if>

                </c:if>
                <c:if test="${!checkList}">
                    <h6 class="text-center" style="margin-top: 20px"> 
                        Not Found Article
                    </h6>
                </c:if>
            </c:if>

            <div class="modal fade" id="notificationModal" tabindex="-1" role="dialog" aria-labelledby="notificationModalTitle" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLongTitle">Notification</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <c:forEach var="notiDTO" items="${notiList}">
                                <a href="DispatchServlet?btAction=Load detail page&idPost=${notiDTO.ariticleId}" style="text-decoration: none">
                                    <div>
                                        <p><h4 style="text-decoration: none">${notiDTO.registrationEmail}</h4> has ${notiDTO.type}ed your post!</p>
                                    </div>
                                    <p><small class="text-muted">${notiDTO.date}</small></p>
                                </a>
                                <hr>
                            </c:forEach>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>            

            <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    </body>
</html>
