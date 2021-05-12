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
public class Monstrum extends Zivocich{
    private final String CESTA_OBRAZKU = "Obrazky/Monstra/";
    
    private final int id;
    private final Item zbran;
    private final int odmenaXP;
    private final ArrayList<LootItem> loot;
    private final String jmenoObrazku;

    public Monstrum(int id, Item zbran, int maxHP, int zlato, int level, int odmenaXP, double intelekt, 
            double sila, String jmeno, String jmenoObrazku) {
        super(maxHP, zlato, level, intelekt, sila, jmeno);
        this.zbran = zbran;
        this.odmenaXP = odmenaXP;
        this.id = id;
        
        if (jmenoObrazku.contains(CESTA_OBRAZKU)){
            this.jmenoObrazku = jmenoObrazku;
        }else{
            this.jmenoObrazku = CESTA_OBRAZKU + jmenoObrazku;
        }
        loot = new ArrayList<>();
    }

    public Item getZbran() {
        return zbran;
    }

    public int getOdmenaXP() {
        return odmenaXP;
    }

    public ArrayList<LootItem> getLoot() {
        return loot;
    }

    public String getJmenoObrazku() {
        return jmenoObrazku;
    }
    
    public void pridejLootItem(LootItem item) {
        loot.add(item);
    }

    public int getId() {
        return id;
    }
    
}
