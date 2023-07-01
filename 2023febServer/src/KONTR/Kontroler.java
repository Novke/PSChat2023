/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package KONTR;

import baza.DBB;
import domen.Korisnik;
import domen.Poruka;
import java.util.ArrayList;
import java.util.Scanner;
import niti.PokreniServer;

/**
 *
 * @author Korisnik
 */
public class Kontroler {

    private static boolean validacijaUser(String s) {
        
        for (int i = 0; i < s.length(); i++){
            if (Character.isDigit(s.charAt(i))) return false;
        }
        return !dbb.jelPostojiKorisnik(s);
    }

    private static boolean validacijaPass(String s) {
        boolean imaBroj  = false;
        boolean imaSlovo = false;
        
        for (int i = 0; i < s.length(); i++){
            if (Character.isDigit(s.charAt(i))) imaBroj = true;
            if (Character.isLetter(s.charAt(i))) imaSlovo = true;
        }
        return imaBroj && imaSlovo;
    }

    private static void ulogujSe() {
        while (true){
            System.out.println("UNESI USER I PASS U FORMATU: user;pass");
            Scanner sc = new Scanner(System.in);
            String[] delovi = sc.nextLine().split(";");
             
            if (delovi.length==2 && dbb.loginAdmin(delovi[0], delovi[1])){
                break;
            } else System.out.println("LOS UNOS");
        }
    }
    
    private static DBB dbb;
    private static Kontroler instance;
    PokreniServer server;
    ArrayList<Korisnik> onlineKorisnici = new ArrayList<>();
    
    

    public static Kontroler getInstance() {
        if (instance==null) instance = new Kontroler();
        return instance;
    }
    
    private Kontroler(){
        dbb = new DBB();
    }

    public Korisnik login(String string) {
        return dbb.login(string);
    }
    
    
    public static void main(String[] args) {
        
        ulogujSe();
        
                 Kontroler.getInstance().server = new PokreniServer();
        while (true){
            System.out.println("1. POKRENI SERVER");
            System.out.println("2. ZAUSTAVI SERVER");
            System.out.println("3. PRIKAZI 5 PORUKA");
            System.out.println("4. DODAJ NOVOG KORISNIKA");
            System.out.println("0. UGASI SERVER");
            Scanner sc = new Scanner(System.in);
            int x = sc.nextInt();
            switch (x) {
                case 1:
                    
                 Kontroler.getInstance().server.pokreni();
                    System.out.println("POKRENUT");
                    break;
                    
                case 2:
                    
                 Kontroler.getInstance().server.zaustavi();
                    System.out.println("ZAUSTAVLJEN");
                    break;
                    
                case 3:
                    
                    System.out.println(Kontroler.getInstance().server.ispisi5());
                    break;
                    
                case 4:
                    sc.nextLine();
                    while (true){
                        System.out.println("UNESI USERNAME");
                        String user = sc.nextLine();
                        if (validacijaUser(user)){
                            while (true){
                                System.out.println("UNESI PASSWORD");
                                String pass = sc.nextLine();
                                
                                if (validacijaPass(pass)){
                                    
                                    Korisnik k = new Korisnik(user, pass);
                                    boolean uspesno = dbb.dodajKorisnika(k);
                                    if (uspesno) System.out.println("USPESNO");
                                    else System.out.println("NEUSPESNO");
                                    break;
                                } else {
                                    System.out.println("LOS UNOS, PASSWORD MORA IMATI BAR JEDAN BROJ I SLOVO");
                                }
                            }
                        } else {
                            System.out.println("LOS UNOS, USERNAME MORA IMATI SAMO SLOVA");
                            continue;
                        }
                        break;
                    }
                    break;
                case 0:
                    
                    Kontroler.getInstance().server.ugasiSve();
                    System.exit(0);
                    return;
                default:
                    System.out.println("LOS UNOS!");
                    System.out.println("PONUDJENI UNOSI");
                    break;
            }
        }
    }

    public void dodajOnlineKorisnika(Korisnik k) {
        onlineKorisnici.add(k);
    }

    public void vratiOnlineKorisnike() {
        
        server.updateOnlineKorisnike(onlineKorisnici);
    }

    public void logout(Korisnik korisnik) {
        onlineKorisnici.remove(korisnik);
        server.updateOnlineKorisnike(onlineKorisnici);
        
    }

    public void dodajPoruku(Poruka poruka) {
        server.dodajPoruku(poruka);
    }

    
    
}
