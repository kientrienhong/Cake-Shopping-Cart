/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.codeverify;

import java.io.Serializable;

/**
 *
 * @author Treater
 */
public class CodeVerifyDTO implements Serializable{
    private String registrationEmail; 
    private String codeVerify; 

    public CodeVerifyDTO() {
    }

    public CodeVerifyDTO(String registrationEmail, String codeVerify) {
        this.registrationEmail = registrationEmail;
        this.codeVerify = codeVerify;
    }

    public String getRegistrationEmail() {
        return registrationEmail;
    }

    public void setRegistrationEmail(String registrationEmail) {
        this.registrationEmail = registrationEmail;
    }

    public String getCodeVerify() {
        return codeVerify;
    }

    public void setCodeVerify(String codeVerify) {
        this.codeVerify = codeVerify;
    }
    
    
}
