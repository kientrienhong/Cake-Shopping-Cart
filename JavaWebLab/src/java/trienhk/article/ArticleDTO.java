/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.article;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Treater
 */
public class ArticleDTO implements Serializable{
    private String title; 
    private String description; 
    private String image; 
    private Timestamp date; 
    private String registrationEmail; 
    private String id; 
    private String status; 

    public ArticleDTO() {
    }

    public ArticleDTO(String title, String description, String image, Timestamp date, String registrationEmail, String id) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.date = date;
        this.registrationEmail = registrationEmail;
        this.id = id;
        this.status = "Active";
    }

    public ArticleDTO(String title, Timestamp date, String registrationEmail, String image, String description, String id) {
        this.title = title;
        this.date = date;
        this.registrationEmail = registrationEmail;
        this.image = image;
        this.description = description;
        this.id = id;
    }

    public ArticleDTO(String title, String description, String image, String registrationEmail) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.registrationEmail = registrationEmail;
    }

    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getRegistrationEmail() {
        return registrationEmail;
    }

    public void setRegistrationEmail(String registrationEmail) {
        this.registrationEmail = registrationEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
