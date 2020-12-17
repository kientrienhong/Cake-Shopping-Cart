/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.notification;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Treater
 */
public class NotificationDTO implements Serializable{
    private String registrationEmail;
    private String ariticleId;
    private String type; 
    private Timestamp date; 

    public NotificationDTO(String registrationEmail, String ariticleId, String type, Timestamp date) {
        this.registrationEmail = registrationEmail;
        this.ariticleId = ariticleId;
        this.type = type;
        this.date = date;
    }
    
    public NotificationDTO() {
    }

    public String getRegistrationEmail() {
        return registrationEmail;
    }

    public void setRegistrationEmail(String registrationEmail) {
        this.registrationEmail = registrationEmail;
    }

    public String getAriticleId() {
        return ariticleId;
    }

    public void setAriticleId(String ariticleId) {
        this.ariticleId = ariticleId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    
}
