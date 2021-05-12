/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.model;

import engine.akce.Akce;

/**
 *
 * @author Jakub Jukl
 */
public class Item {
    
    private final int id;
    private final String jmeno;
    private final String popis;
    private final int cena;
    private final ItemDruh druh;
    private Akce akce;
    
   
    public enum ItemDruh {
        Ruzne,
        Zbran,
        Kouzlo,
        Konzumovatelne
    }
    
    public Item(int id, String jmeno, String popis, int cena, ItemDruh druh) {
        this.id = id;
        this.jmeno = jmeno;
        this.popis = popis;
        this.cena = cena;
        this.druh = druh;
    }

    public Akce getAkce() {
        return akce;
    }

    public void setAkce(Akce akce) {
        this.akce = akce;
    }
    
     public int getId() {
        return id;
    }

    public String getJmeno() {
        return jmeno;
    }

    public String getPopis() {
        return popis;
    }

    public int getCena() {
        return cena;
    }

    public ItemDruh getDruh() {
        return druh;
    }
}
