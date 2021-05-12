/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.model;

import java.util.ArrayList;

/**
 *
 * @author Jakub Jukl
 */
public class Lokace {
    private final String CESTA_OBRAZKU = "Obrazky/Lokace/";
    
    private final int xSourad;
    private final int ySourad;
    private final String jmeno;
    private final String popis;
    private final String jmenoObrazku;
    
    private final ArrayList<Ukol> dostupneUkoly;
    private final ArrayList<Potycka> dostupnePotycky;

    public String getJmeno() {
        return jmeno;
    }

    public String getPopis() {
        return popis;
    }

    public ArrayList<Ukol> getDostupneUkoly() {
        return dostupneUkoly;
    }

    public ArrayList<Potycka> getDostupnePotycky() {
        return dostupnePotycky;
    }
      
    public Lokace(int xSourad, int ySourad, String jmeno, String popis, String jmenoObrazku) {
        this.xSourad = xSourad;
        this.ySourad = ySourad;
        this.jmeno = jmeno;
        this.popis = popis;
        this.jmenoObrazku = CESTA_OBRAZKU + jmenoObrazku;
        
        dostupneUkoly = new ArrayList<>();
        dostupnePotycky = new ArrayList<>();
    }
    
    public void pridejUkol(Ukol ukol){
        dostupneUkoly.add(ukol);
    }
    
    public void pridejPotycku(Potycka potycka){
        dostupnePotycky.add(potycka);
    }

    public String getJmenoObrazku() {
        return jmenoObrazku;
    }

    public int getxSourad() {
        return xSourad;
    }

    public int getySourad() {
        return ySourad;
    }
    
    public boolean jsouShodne(Lokace lokace){
        return xSourad == lokace.getxSourad() && ySourad == lokace.getySourad();
    }
    
}
