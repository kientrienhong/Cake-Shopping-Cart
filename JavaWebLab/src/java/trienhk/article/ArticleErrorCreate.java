/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.article;

import java.io.Serializable;

/**
 *
 * @author Treater
 */
public class ArticleErrorCreate implements Serializable{
    private String titleError; 
    private String descriptionError; 
    private String imageError; 

    public ArticleErrorCreate() {
    }

    public ArticleErrorCreate(String titleError, String descriptionError, String imageError) {
        this.titleError = titleError;
        this.descriptionError = descriptionError;
        this.imageError = imageError;
    }

    public String getTitleError() {
        return titleError;
    }

    public void setTitleError(String titleError) {
        this.titleError = titleError;
    }

    public String getDescriptionError() {
        return descriptionError;
    }

    public void setDescriptionError(String descriptionError) {
        this.descriptionError = descriptionError;
    }

    public String getImageError() {
        return imageError;
    }

    public void setImageError(String imageError) {
        this.imageError = imageError;
    }
    
    
}
