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
public class HealKouzlem extends ZakladniAkce implements Akce{
    
    private final int minHeal;
    private final int maxHeal;
    private final int mana;
    
    public HealKouzlem(Item item, int minDmg, int maxDmg, int mana) {
        super(item);
        this.minHeal = minDmg;
        this.maxHeal = maxDmg;
        this.mana = mana;
    }

    @Override
    public String proved(Zivocich actor, Zivocich target) {
        Double heal = RNG.cisloMezi(minHeal, maxHeal) * actor.getIntelekt();
        target.heal(heal.intValue());
        actor.spotrebujManu(mana);
        String zprava = String.format("%s healnul %s za %d, jelikož seslal %s.", actor.getJmeno(), 
                actor.equals(target) ? "sám sebe" : target.getJmeno(),heal.intValue() ,super.getItem().getJmeno());
        return zprava;
    }

    @Override
    public int cenaProvedeni() {
        return mana;
    }
}
