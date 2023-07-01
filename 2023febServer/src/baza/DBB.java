/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baza;

import domen.Korisnik;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Korisnik
 */
public class DBB {
    
    
    public Korisnik login(String string){
        String delovi[] = string.split(";");
        String upit = "SELECT * FROM Korisnik WHERE user = '" + delovi[0] + "' AND pass = '" + delovi[1] + "'";
        try {
            Statement st = Konekcija.getInstance().getConn().createStatement();
            ResultSet rs = st.executeQuery(upit);
            if (rs.next()) {
                return new Korisnik(rs.getString("user"), rs.getString("pass"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean dodajKorisnika(Korisnik k) {
        String upit = "INSERT INTO KORISNIK (user, pass) VALUES (?,?)";
        try {
            PreparedStatement ps = Konekcija.getInstance().getConn().prepareStatement(upit);
            ps.setString(1, k.getUser());
            ps.setString(2, k.getPass());
            
            int x = ps.executeUpdate();
            
            if (x==0) throw new SQLException("Nije doslo do promene u tabeli");
            Konekcija.getInstance().getConn().commit();
        } catch (SQLException ex) {
            Logger.getLogger(DBB.class.getName()).log(Level.SEVERE, null, ex);
            try {
                Konekcija.getInstance().getConn().rollback();
                return false;
            } catch (SQLException ex1) {
                Logger.getLogger(DBB.class.getName()).log(Level.SEVERE, null, ex1);
                return false;
            }
        }
        return true;
    }

    public boolean loginAdmin(String string, String string0) {
        String upit = "SELECT * FROM Admin WHERE user = '" + string + "' AND pass = '" + string0 + "'";
        try {
            Statement st = Konekcija.getInstance().getConn().createStatement();
            ResultSet rs = st.executeQuery(upit);
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean jelPostojiKorisnik(String s) {
        String upit = "SELECT * FROM Korisnik WHERE user = '" + s + "'";
        try {
            Statement st = Konekcija.getInstance().getConn().createStatement();
            ResultSet rs = st.executeQuery(upit);
            return rs.next();
        } catch (SQLException e){
            return true;
        }
    }
    
}
