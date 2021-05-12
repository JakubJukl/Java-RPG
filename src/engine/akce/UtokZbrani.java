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
public class UtokZbrani extends ZakladniAkce implements Akce {
    
    private final int minDmg;
    private final int maxDmg;
    
    public UtokZbrani(Item item, int minDmg, int maxDmg) {
        super(item);
        this.minDmg = minDmg;
        this.maxDmg = maxDmg;
    }

    @Override
    public String proved(Zivocich actor, Zivocich target) {
        Double dmg = RNG.cisloMezi(minDmg, maxDmg) * actor.getSila(); 
        target.dostalDmg(dmg.intValue());
        String zprava = actor.getJmeno() + " udělil " + target.getJmeno() + " " + dmg.intValue() + " poškození zbraní " 
                + super.getItem().getJmeno();
        return zprava;
    }

    @Override
    public int cenaProvedeni() {
        return 0; //pokud se rozhodnu implementovat energii
    }
    
}
