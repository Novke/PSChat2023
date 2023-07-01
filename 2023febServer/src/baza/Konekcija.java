/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baza;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Korisnik
 */
public class Konekcija {
    private static Konekcija instance;
    private Connection conn;

    private Konekcija() {
        String url = "jdbc:mysql://localhost:3306/chatserver";
        try {
            conn = DriverManager.getConnection(url, "root", "");
            conn.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(Konekcija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Konekcija getInstance() {
        if (instance == null) instance = new Konekcija();
        return instance;
    }

    public Connection getConn() {
        return conn;
    }
    
    
    
    
    
}
