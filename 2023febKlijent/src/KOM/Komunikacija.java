/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package KOM;

import OP.Operacije;
import domen.Korisnik;
import domen.Poruka;
import forme.KlijentskaForma;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import niti.Prijem;
import transfer.KlijentskiZahtev;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Korisnik
 */
public class Komunikacija {
    private static Komunikacija instance;
    Socket s;
    KlijentskaForma kf;
    Prijem prijem;

    public static Komunikacija getInstance() {
        if (instance == null) instance = new Komunikacija();
        return instance;
    }

    public void setKf(KlijentskaForma kf) {
        this.kf = kf;
    }
    
    
    
    private Komunikacija(){
        try {
            s = new Socket("localhost", 9000);
        } catch (IOException ex) {
            Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void posaljiZahtev(KlijentskiZahtev kz){
        
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(s.getOutputStream());
        oos.writeObject(kz);
        oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ServerskiOdgovor primiOdgovor(){
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

    public Korisnik login(String string) {
        KlijentskiZahtev kz = new KlijentskiZahtev(Operacije.LOGIN, string);
        posaljiZahtev(kz);
        ServerskiOdgovor so = primiOdgovor();
        return (Korisnik) so.getOdgovor();
        
    }

    public void pokreniPrijem() {
        prijem = new Prijem(s);
    }

    public void updateOnlineKorisnike(ArrayList<Korisnik> onlineKorisnici) {
        kf.updateOnlineKorisnike(onlineKorisnici);
    }

    public void vratiOnlineKorisnike() {
        KlijentskiZahtev kz = new KlijentskiZahtev(Operacije.ONLINE, null);
        posaljiZahtev(kz);
    }

    public void logout(Korisnik ulogovani) {
        KlijentskiZahtev kz = new KlijentskiZahtev(Operacije.LOGOUT, ulogovani);
        posaljiZahtev(kz);
    }

    public void ugasi() {
        kf.dispose();
        System.exit(0);
    }

    public void posaljiPoruku(Poruka por) {
        KlijentskiZahtev kz = new KlijentskiZahtev(Operacije.SENDMSG, por);
        posaljiZahtev(kz);
    }

    public void stiglaPoruka(Poruka poruka) {
        kf.stiglaPoruka(poruka);
    }
    
    
}
