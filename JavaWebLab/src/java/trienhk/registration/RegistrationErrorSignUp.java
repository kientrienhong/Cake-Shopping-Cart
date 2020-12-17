/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.registration;

import java.io.Serializable;

/**
 *
 * @author Treater
 */
public class RegistrationErrorSignUp implements Serializable{
    private String emailError; 
    private String passwordError; 
    private String passwordConfirmError; 
    private String nameError; 
    private String emailExisted; 

    public RegistrationErrorSignUp() {
    }

    public RegistrationErrorSignUp(String emailError, String passwordError, String passwordConfirmError, String nameError, String emailExisted) {
        this.emailError = emailError;
        this.passwordError = passwordError;
        this.passwordConfirmError = passwordConfirmError;
        this.nameError = nameError;
        this.emailExisted = emailExisted;
    }

    public String getEmailError() {
        return emailError;
    }

    public void setEmailError(String emailError) {
        this.emailError = emailError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getPasswordConfirmError() {
        return passwordConfirmError;
    }

    public void setPasswordConfirmError(String passwordConfirmError) {
        this.passwordConfirmError = passwordConfirmError;
    }

    public String getNameError() {
        return nameError;
    }

    public void setNameError(String nameError) {
        this.nameError = nameError;
    }

    public String getEmailExisted() {
        return emailExisted;
    }

    public void setEmailExisted(String emailExisted) {
        this.emailExisted = emailExisted;
    }
    
}
