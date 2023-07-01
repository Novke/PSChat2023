/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domen;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Korisnik
 */
public class Korisnik implements Serializable{
    
    private String user;
    private String pass;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.user);
        hash = 89 * hash + Objects.hashCode(this.pass);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Korisnik other = (Korisnik) obj;
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        return Objects.equals(this.pass, other.pass);
    }

  

    

   

    public Korisnik(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public Korisnik() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    
    
}
