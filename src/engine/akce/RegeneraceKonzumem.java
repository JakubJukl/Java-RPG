/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.akce;

import engine.RNG;
import engine.model.Item;
import engine.model.Zivocich;

/**
 *
 * @author Jakub Jukl
 */
public class RegeneraceKonzumem extends ZakladniAkce implements Akce {
    private final int heal;
    
    public RegeneraceKonzumem(Item item, int heal) {
        super(item);
        this.heal = heal;
    }

    @Override
    public String proved(Zivocich actor, Zivocich target) {
        target.heal(heal);
        String zprava = String.format("%s healnul %s za %d, protože použil %s.", actor.getJmeno(), 
                actor.equals(target) ? "sám sebe" : target.getJmeno(),heal ,super.getItem().getJmeno());
        return zprava;
    }

    @Override
    public int cenaProvedeni() {
       return 0;    //příprava, pokud bych se rozhodl implementovat poplatek zlata za použití itemu
    }
}
