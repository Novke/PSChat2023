/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package niti;

import domen.Korisnik;
import domen.Poruka;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Korisnik
 */
public class PokreniServer extends Thread{
    
    ServerSocket ss;
    ArrayList<ObradaZahteva> obrade = new ArrayList<>();
    ArrayList<Poruka> poruke = new ArrayList<>(); 
    private boolean radi = false;
    
    int kojaPoruka = 0;
    
    public void pokreni(){
        radi = true;
        for (ObradaZahteva o : obrade) o.setRadi(radi);
    }
    
    public void zaustavi(){
        radi = false;
        for (ObradaZahteva o : obrade) o.setRadi(radi);
    }

    public PokreniServer() {
        try {
            ss = new ServerSocket(9000);
            
            start();
        } catch (IOException ex) {
            Logger.getLogger(PokreniServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void run(){
        while (true){
            try {
                Socket s = ss.accept();
                ObradaZahteva oz = new ObradaZahteva(s);
                obrade.add(oz);
            } catch (IOException ex) {
                Logger.getLogger(PokreniServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ServerSocket getSocket() {
        return ss;
    }

    public void updateOnlineKorisnike(ArrayList<Korisnik> online) {
        for (ObradaZahteva o: obrade){
            o.updateOnlineKorisnike(online);
        }
    }

   

    public String ispisi5() {
        String vrati = "";
        if (poruke.isEmpty()) return "NEMA PORUKA";
        for (int i = 0;i < 5; i++){
            if (kojaPoruka>=poruke.size()){
                kojaPoruka=0;
                break;
            }
            vrati+=poruke.get(kojaPoruka++).toString();
        }
        
        return vrati;
    }

    public void ugasiSve() {
        for (ObradaZahteva o: obrade){
            o.ugasi();
        }
        try {
            ss.close();
        } catch (IOException ex) {
            Logger.getLogger(PokreniServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void dodajPoruku(Poruka poruka) {
        
        poruke.add(poruka);
        
        for (ObradaZahteva o : obrade){
            o.dodajPoruku(poruka);
        }
        
    }
    
    
    
    
    
}
