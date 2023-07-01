/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package niti;

import KOM.Komunikacija;
import domen.Korisnik;
import domen.Poruka;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Korisnik
 */
public class Prijem extends Thread{
    
    Socket s;

    public Prijem(Socket s) {
        this.s = s;
        start();
    }
    
    public void run(){
        while (true){
            ServerskiOdgovor so = primiOdgovor();
            if (so!=null){
                switch (so.getPoruka()) {
                    case "online":
                    ArrayList<Korisnik> onlineKorisnici = (ArrayList<Korisnik>) so.getOdgovor();
                    Komunikacija.getInstance().updateOnlineKorisnike(onlineKorisnici);
                    break;
                    case "login": break;
                    case "stop":
                        Komunikacija.getInstance().ugasi();
                        break;
                    case "poruka":
                        Komunikacija.getInstance().stiglaPoruka((Poruka)so.getOdgovor());
                        break;
                    default:
                    throw new AssertionError();
                }
            }
        }
    }

    private ServerskiOdgovor primiOdgovor() {
        try {
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            return (ServerskiOdgovor) ois.readObject();
        } catch (IOException ex) {
            Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    
    
}
