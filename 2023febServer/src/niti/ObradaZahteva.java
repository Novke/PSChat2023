/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package niti;

import KONTR.Kontroler;
import OP.Operacije;
import domen.Korisnik;
import domen.Poruka;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import transfer.KlijentskiZahtev;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Korisnik
 */
public class ObradaZahteva extends Thread{
    
    Socket s;
    ServerskiOdgovor so = new ServerskiOdgovor();
    Korisnik k;
    private boolean radi = true;

    void setRadi(boolean radi) {
        this.radi = radi;
    }
    
    

    public ObradaZahteva(Socket s) {
        this.s = s;
        start();
    }

    public Socket getS() {
        return s;
    }
    
    
    
    public void run(){
        
        while (true){
            KlijentskiZahtev kz = primiZahtev();
            if (!radi) continue;
            switch (kz.getOperacija()) {
                case Operacije.LOGIN:
                    Korisnik k =Kontroler.getInstance().login((String)kz.getParametar());
                    so.setOdgovor(k);
                    so.setPoruka("login");
                    this.k = k;
                    Kontroler.getInstance().dodajOnlineKorisnika(k);
                    break;
                case Operacije.ONLINE:
                    Kontroler.getInstance().vratiOnlineKorisnike();
                    break;
                case Operacije.LOGOUT:
                    Kontroler.getInstance().logout((Korisnik)kz.getParametar());
                    break;
                case Operacije.SENDMSG:
                    Kontroler.getInstance().dodajPoruku((Poruka)kz.getParametar());
                    break;
                default:
                    throw new AssertionError();
            }
            posaljiOdgovor(so);
        }
    }

    private KlijentskiZahtev primiZahtev() {
        
        try {
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            return (KlijentskiZahtev) ois.readObject();
        } catch (IOException ex) {
            Logger.getLogger(ObradaZahteva.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ObradaZahteva.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void posaljiOdgovor(ServerskiOdgovor so) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(so);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ObradaZahteva.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void updateOnlineKorisnike(ArrayList<Korisnik> online) {
        ServerskiOdgovor so = new ServerskiOdgovor("online", online);
        posaljiOdgovor(so);
    }

    void ugasi() {
        ServerskiOdgovor so = new ServerskiOdgovor("stop", null);
        posaljiOdgovor(so);
    }

    void dodajPoruku(Poruka poruka) {
        if (poruka.getOD().equals(k)) return;
        if (poruka.getDO() != null && !poruka.getDO().equals(k)) return;
           ServerskiOdgovor so = new ServerskiOdgovor("poruka", poruka);
           posaljiOdgovor(so);
    }
}
