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
public class UtokKouzlem extends ZakladniAkce implements Akce{
    
    private final int minDmg;
    private final int maxDmg;
    private final int mana;
    
    public UtokKouzlem(Item item, int minDmg, int maxDmg, int mana) {
        super(item);
        this.minDmg = minDmg;
        this.maxDmg = maxDmg;
        this.mana = mana;
    }

    @Override
    public String proved(Zivocich actor, Zivocich target) {
        Double dmg = RNG.cisloMezi(minDmg, maxDmg) * actor.getIntelekt();
        String zprava;
        target.dostalDmg(dmg.intValue());
        actor.spotrebujManu(mana);
        if(actor.equals(target)){   //pokud zapomeneme dát správný target 
            zprava = actor.getJmeno() + " se ve svém zmatení sám zranil kouzlem " + super.getItem().getJmeno() + ", které způsobilo " + dmg 
                + " poškození." ;
        }else{
            zprava = actor.getJmeno() + " seslal kouzlo " + super.getItem().getJmeno() + ", které způsobilo " + dmg.intValue()
                + " poškození." ;
        }     
        return zprava;
    }

    @Override
    public int cenaProvedeni() {
        return mana;
    }
    
}
