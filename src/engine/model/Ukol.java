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
public class Ukol {

    private final int id;
    private final String jmeno;
    private final String popis;
    private final int odmenaXp;
    private final int odmenaZlato;
    private final ArrayList<MnozstviItemu> odmenaItem;
    private final ArrayList<MnozstviItemu> potrebnyItem;
    private boolean splnenaKriteria;
    private Lokace lokaceOdevzdani;
    
    public Ukol(int id, String jmeno, String popis, int odmenaXp, int odmenaZlato) {
        this.id = id;
        this.jmeno = jmeno;
        this.popis = popis;
        this.odmenaXp = odmenaXp;
        this.odmenaZlato = odmenaZlato;
        
        odmenaItem = new ArrayList<>();
        potrebnyItem = new ArrayList<>();
        splnenaKriteria = false;
        lokaceOdevzdani = null;
    }

    public Lokace getLokaceOdevzdani() {
        return lokaceOdevzdani;
    }

    public void setLokaceOdevzdani(Lokace lokaceOdevzdani) {
        this.lokaceOdevzdani = lokaceOdevzdani;
    }

    public boolean isSplnenaKriteria() {
        return splnenaKriteria;
    }

    public void setSplnenaKriteria(boolean splnenaKriteria) {
        this.splnenaKriteria = splnenaKriteria;
    }

    public ArrayList<MnozstviItemu> getPotrebnyItem() {
        return potrebnyItem;
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

    public int getOdmenaXp() {
        return odmenaXp;
    }

    public int getOdmenaZlato() {
        return odmenaZlato;
    }

    public ArrayList<MnozstviItemu> getOdmenaItem() {
        return odmenaItem;
    }
    
    public void pridejOdmenuItem(MnozstviItemu item) {
        odmenaItem.add(item);
    }
    
    public void pridejPotrebnyItem(MnozstviItemu item) {
        potrebnyItem.add(item);
    }
    
    public void splnUkol(Hrac hrac) {
        hrac.splnUkol(this);
        hrac.pridejXp(odmenaXp);
        hrac.pridejZlato(odmenaZlato);
        
        if (!odmenaItem.isEmpty()){
            odmenaItem.forEach(item -> {
            hrac.pridejItem(item);
            });
        }
        
    }
    
}
