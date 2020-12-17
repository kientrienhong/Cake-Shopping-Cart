/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.comment;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Treater
 */
public class CommentDTO implements Serializable{
    private String articleId; 
    private String registrationEmail; 
    private String contentComment; 
    private Timestamp date; 
    private String status; 
    private String id;
    public CommentDTO() {
    }

    public CommentDTO(String articleId, String registrationEmail, String contentComment, Timestamp date, String id) {
        this.articleId = articleId;
        this.registrationEmail = registrationEmail;
        this.contentComment = contentComment;
        this.date = date;
        this.status = "Active"; 
        this.id = id; 
    }

    public CommentDTO(String registrationEmail, String contentComment, Timestamp date, String id) {
        this.registrationEmail = registrationEmail;
        this.contentComment = contentComment;
        this.date = date;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getRegistrationEmail() {
        return registrationEmail;
    }

    public void setRegistrationEmail(String registrationEmail) {
        this.registrationEmail = registrationEmail;
    }

    public String getContentComment() {
        return contentComment;
    }

    public void setContentComment(String contentComment) {
        this.contentComment = contentComment;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
