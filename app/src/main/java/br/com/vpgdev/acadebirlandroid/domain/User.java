package br.com.vpgdev.acadebirlandroid.domain;

import java.io.Serializable;
import java.util.Calendar;

import br.com.vpgdev.acadebirlandroid.utils.Utils;

/**
 * Created by Fernando on 11/07/2017.
 */

/**
 * <b>User</b><br>
 *     Classe modelo para usuário
 */
public class User  implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idUser;
    private String firstName;
    private String lastName;
    private String email;
    private String sex;
    private String username;
    private String pass;
    private String dateBirth;
    private String dateRegister;
    private String levelAccess;

    // Getters and Setters
    public Long getIdUser() {
        return idUser;
    }
    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    public String getDateBirth() {
        return dateBirth;
    }
    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }
    public String getDateRegister() {
        return dateRegister;
    }
    public void setDateRegister(String dateRegister) {
        this.dateRegister = dateRegister;
    }
    public String getLevelAccess() {
        return levelAccess;
    }
    public void setLevelAccess(String levelAccess) {
        this.levelAccess = levelAccess;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        Utils util = new Utils();
        return "=== User ===\n"
                + "Id: " + this.idUser
                + "\nFirst Name: " + this.firstName
                + "\nLast Name: " + this.lastName
                + "\nEmail: " + this.email
                + "\nSex: " + this.sex
                + "\nUsername: " + this.username
                +"\nBirth Date: " + this.dateBirth
                + "\nRegister Date: " + this.dateRegister
                + "\nLevel Access: " + this.levelAccess;
    }

}
