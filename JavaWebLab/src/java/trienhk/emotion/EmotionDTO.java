/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.emotion;

import java.io.Serializable;

/**
 *
 * @author Treater
 */
public class EmotionDTO implements Serializable{
    private String id;
    private String articleId; 
    private String registrationEmail; 
    private boolean isLike; 
    private String status; 
    public EmotionDTO(String id, String articleId, String registrationEmail, boolean isLike) {
        this.articleId = articleId;
        this.registrationEmail = registrationEmail;
        this.isLike = isLike;
        this.id = id;
    }

    public EmotionDTO(String registrationEmail, boolean isLike) {
        this.registrationEmail = registrationEmail;
        this.isLike = isLike;
    }

    public EmotionDTO(String id, boolean isLike, String status) {
        this.id = id;
        this.isLike = isLike;
        this.status = status;
    }

    public EmotionDTO() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public boolean isIsLike() {
        return isLike;
    }

    public void setIsLike(boolean isLike) {
        this.isLike = isLike;
    }
    
    
}
