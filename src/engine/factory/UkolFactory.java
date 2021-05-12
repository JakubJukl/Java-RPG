/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.factory;

import engine.model.MnozstviItemu;
import engine.model.Ukol;
import java.util.ArrayList;

/**
 *
 * @author Jakub Jukl
 */
public class UkolFactory {
    private static final ArrayList<Ukol> herniUkoly = new ArrayList<>();
    
    public final static int ID_ZIZALY = 1;
    private final static int POCET_ZIZALY_QI_1 = 10;
    public final static int ID_KOVBOJOVE = 2;
    private final static int POCET_KOVBOJOVE_QI_1 = 30;
    public final static int ID_UPIRI = 3;
    private final static int POCET_UPIRI_QI_1 = 8;
    
    
    public static void setUkoly(){
        Ukol zizaly = new Ukol(ID_ZIZALY, "Zab žížaly", "Už dlouho mě sužovali, tak je zabij a přines mi " 
                + POCET_ZIZALY_QI_1 + " jejich koulí " + "jako důkaz.", 100, 50);
        zizaly.pridejOdmenuItem(new MnozstviItemu(ItemFactory.vytvorItem(ItemFactory.ID_ZIZALI_BOBEK),1));
        zizaly.pridejPotrebnyItem(new MnozstviItemu(ItemFactory.vytvorItem(ItemFactory.ID_KOULE),POCET_ZIZALY_QI_1));
        herniUkoly.add(zizaly);
        
        Ukol kovbojove = new Ukol(ID_KOVBOJOVE, "Pro hrst dolarů", "Ty kovbojové tu dělaj samou neplechu. Pár se jich "
                + "zbav, aby se to tu uklidnilo a sežeň mi " + POCET_KOVBOJOVE_QI_1 + " kovbojských klobouků.", 1000, 500);
        kovbojove.pridejPotrebnyItem(new MnozstviItemu(ItemFactory.vytvorItem(ItemFactory.ID_KLOBOUK), POCET_KOVBOJOVE_QI_1));
        kovbojove.pridejOdmenuItem(new MnozstviItemu(ItemFactory.vytvorItem(ItemFactory.ID_WHISKEY), 10));
        herniUkoly.add(kovbojove);
        
        Ukol upiri = new Ukol(ID_UPIRI, "Interview s upírem", "Rád bych se s rodinou dostal skrz hřbitov, ale je plný upírů. "
                + "Přines nám " + POCET_UPIRI_QI_1 + " upířích plášťů, abychom se mohli přestrojit.", 10000, 5000);
        upiri.pridejPotrebnyItem(new MnozstviItemu(ItemFactory.vytvorItem(ItemFactory.ID_PLAST), POCET_UPIRI_QI_1));
        upiri.pridejOdmenuItem(new MnozstviItemu(ItemFactory.vytvorItem(ItemFactory.ID_MEC_OSUDU), 1));
        herniUkoly.add(upiri);
    }
    
    public static Ukol getUkol(int id){ //můžeme vracet rovnou úkol, jelikož je jen jeden hráč, takže stačí, když ten úkol bude existovat
        return herniUkoly.stream().filter(u -> u.getId() == id).findFirst().orElse(null);   //jen jednou
    }
}
