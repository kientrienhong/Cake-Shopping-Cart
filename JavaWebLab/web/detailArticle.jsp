<%-- 
    Document   : detailArticle
    Created on : Sep 19, 2020, 5:02:36 PM
    Author     : Treater
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detail Article</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    </head>
    <body>
        <c:set var="listComment" value="${requestScope.LIST_COMMENT}"/>
        <c:set var="listLike" value="${requestScope.LIST_LIKE}"/>
        <c:set var="listDisLike" value="${requestScope.LIST_DISLIKE}"/>
        <c:set var="articleDTO" value = "${requestScope.ARTICLE_DETAILS}"/>
        <c:set value="${sessionScope.DTO}" var="userDTO"></c:set>

            <div class="d-flex justify-content-center w-80" style="margin-top: 2%" >
                <div class="card border-secondary" style="width: 40%">
                    <div class="card-header">
                        Detail Article
                    </div>
                    <div class="card-body">
                        <div class="form-row">
                            <h6 class="col">Title: </h6>
                            <p class="col" style="margin-left: -10%">${articleDTO.title}</p>
                    </div>
                    <div class="form-row">
                        <h6 class="col" >Created By:  </h6>
                        <p class="col" style="margin-left: -10%">${articleDTO.registrationEmail}</p>
                    </div>
                    <div class="form-row">
                        <h6 class="col" >Description:  </h6>
                        <p class="col" style="margin-left: -10%">${articleDTO.description}</p>
                    </div>
                    <c:if test ="${not empty articleDTO.image}">
                        <div class="form-group">
                            <h6>Image:  </h6>
                            <img src="./img/${articleDTO.image}" alt="${articleDTO.image}" style="width: 200px; height: 200px; margin-left: 30%"/>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>

        <nav>
            <div class="nav nav-tabs" id="nav-tab" role="tablist">
                <a class="nav-item nav-link active" id="nav-home-tab" data-toggle="tab" href="#comment" role="tab" aria-controls="nav-comment" aria-selected="true">Comment (${listComment.size()})</a>
                <a class="nav-item nav-link" id="nav-profile-tab" data-toggle="tab" href="#like" role="tab" aria-controls="nav-like" aria-selected="false">Like (${listLike.size()})</a>
                <a class="nav-item nav-link" id="nav-contact-tab" data-toggle="tab" href="#dislike" role="tab" aria-controls="nav-dislike" aria-selected="false">Dislike (${listDisLike.size()})</a>
            </div>
        </nav>
        <div class="tab-content" id="nav-tabContent">
            <div class="tab-pane fade show active" id="comment" role="tabpanel" aria-labelledby="nav-comment-tab">
                <form action="DispatchServlet" class="form-inline" style="margin-top: 3%; margin-bottom: 2%">
                    <input type="hidden" name="txtIdPost" value="${param.idPost}" />
                    <input type="hidden" name="txtUserEmail" value="${sessionScope.DTO.email}" />
                    <div class="form-group col-5" style="margin-left: 35%">
                        <input class="form-control" placeholder="Content..." type="text" name="txtComment" value="${param.txtComment}" style="margin-top: 1%; margin-bottom: 1% ;width: 300px"/>
                    </div>
                    <div class="form-group col-1" style="margin-left: -20%; padding-top: 3px">
                        <input type="submit" value="Comment" class="btn btn-primary" name="btAction"/>
                    </div>
                </form>   

                <c:forEach items="${listComment}" var="commentDTO" varStatus="vs">
                    <form action="DispatchServlet" method="POST">
                        <input type="hidden" name="txtCommentId" value="${commentDTO.id}" />
                        <input type="hidden" name="txtArticleId" value="${param.idPost}" />
                        <div class="modal fade" id="confirmModal${vs.index}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Are you sure want to delete this comment?</h5> 
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                        <input type="submit" value="Delete Comment" class="btn btn-danger" name="btAction"/>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="card mb-3" style="max-width: 70%; margin: 0 auto; position: relative">
                            <div class="card-body">
                                <div class="form-row">
                                    <h4 class="card-title col-5">${commentDTO.registrationEmail}</h4>
                                    <p class="card-text col-7" style="margin-bottom: 10px">: ${commentDTO.contentComment}</p>
                                    <c:if test="${userDTO.email eq commentDTO.registrationEmail}">
                                        <button type="button" data-toggle="modal" data-target="#confirmModal${vs.index}" class="btn btn-danger col-2" style="position: absolute; top: 15px; right: 20px">Delete Comment</button>
                                    </c:if>
                                </div>
                                <p class="card-text" style="margin-top: -10px"><small class="text-muted">${commentDTO.date}</small></p>
                            </div>
                        </div>
                    </form>
                </c:forEach>
            </div>
            <div class="tab-pane fade" id="like" role="tabpanel" aria-labelledby="nav-like-tab">
                <c:forEach items="${listLike}" var="likeDTO">
                    <div class="card mb-3" style="max-width: 70%; margin: 1% auto">
                        <div class="card-body">
                            <div class="form-row">
                                <h4 class="card-title col-8">${likeDTO.registrationEmail}</h4>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="tab-pane fade" id="dislike" role="tabpanel" aria-labelledby="nav-dislike-tab">
                <c:forEach items="${listDisLike}" var="dislikeDTO">
                    <div class="card mb-3" style="max-width: 70%; margin: 1% auto">
                        <div class="card-body">
                            <div class="form-row">
                                <h4 class="card-title col-8">${dislikeDTO.registrationEmail}</h4>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div> 
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    </body>
</html>
