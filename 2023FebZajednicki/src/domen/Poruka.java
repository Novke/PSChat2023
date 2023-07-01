/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domen;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Korisnik
 */
public class Poruka implements Serializable{
    
    private Korisnik OD;
    private Korisnik DO;
    private String tekst;
    private Date datum;

    @Override
    public String toString() {
        String pom = "SVIH";
        if (DO != null) pom = DO.getUser();
        return "OD " + OD.getUser() + ", DO " + pom + ": " + tekst + " {" + datum.getHours() + ":" + datum.getMinutes() + '}';
    }
    
    

    public Poruka() {
    }

    public Poruka(Korisnik OD, String tekst, Date datum) {
        this.OD = OD;
        this.tekst = tekst;
        this.datum = datum;
    }

    public Poruka(Korisnik OD, Korisnik DO, String tekst, Date datum) {
        this.OD = OD;
        this.DO = DO;
        this.tekst = tekst;
        this.datum = datum;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Korisnik getOD() {
        return OD;
    }

    public void setOD(Korisnik OD) {
        this.OD = OD;
    }

    public Korisnik getDO() {
        return DO;
    }

    public void setDO(Korisnik DO) {
        this.DO = DO;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }
    
    
    
}
