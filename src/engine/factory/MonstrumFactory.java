/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.factory;

import engine.model.LootItem;
import engine.model.Monstrum;
import java.util.ArrayList;

/**
 *
 * @author Jakub Jukl
 */
public class MonstrumFactory {
    
    public static final int ID_ZIZALA = 1;
    public static final int ID_KOVBOJ = 2;
    public static final int ID_UPIR = 3;
    
    private static final ArrayList<Monstrum> herniMonstra = new ArrayList<>();
    
    public static void setMonstra(){
        Monstrum zizala = new Monstrum(ID_ZIZALA, ItemFactory.vytvorItem(ItemFactory.ID_ZUBY), 10, 10, 1, 10, 1, 1, "Žížala","Zizala.png");
        zizala.pridejLootItem(new LootItem(100, ItemFactory.vytvorItem(ItemFactory.ID_KOULE), 1));
        zizala.pridejLootItem(new LootItem(50, ItemFactory.vytvorItem(ItemFactory.ID_ZUBY), 1));
        herniMonstra.add(zizala);
        
        Monstrum kovboj = new Monstrum(ID_KOVBOJ, ItemFactory.vytvorItem(ItemFactory.ID_KOLT), 40, 20, 4, 20, 4, 4, "Clint Westwood", "Kovboj.png");
        kovboj.pridejLootItem(new LootItem(1, ItemFactory.vytvorItem(ItemFactory.ID_KOLT), 1));
        kovboj.pridejLootItem(new LootItem(30, ItemFactory.vytvorItem(ItemFactory.ID_KLOBOUK), 1));
        kovboj.pridejLootItem(new LootItem(15, ItemFactory.vytvorItem(ItemFactory.ID_WHISKEY), 2));
        herniMonstra.add(kovboj);
        
        Monstrum upir = new Monstrum(ID_UPIR, ItemFactory.vytvorItem(ItemFactory.ID_ZUBY), 200, 50, 10, 50, 10, 10, "Upír", "Upir.png");
        upir.pridejLootItem(new LootItem(25, ItemFactory.vytvorItem(ItemFactory.ID_ZUBY), 1));
        upir.pridejLootItem(new LootItem(1, ItemFactory.vytvorItem(ItemFactory.ID_UPIRI_MOREK), 1));
        upir.pridejLootItem(new LootItem(2, ItemFactory.vytvorItem(ItemFactory.ID_PLAST), 1));
        upir.pridejLootItem(new LootItem(10, ItemFactory.vytvorItem(ItemFactory.ID_LIDSKA_KONCETINA), 1));
        herniMonstra.add(upir);
    }
    
    public static Monstrum getMonstrum(int id){ //vracíme rovnou kopii monstra, protože jsem se poučil
        Monstrum kopirovane = herniMonstra.stream().filter(m -> m.getId() == id).findFirst().get();
        Monstrum kopie = new Monstrum(kopirovane.getId(), kopirovane.getZbran(), kopirovane.getMaxHP(), 
                kopirovane.getZlato(), kopirovane.getLevel(), kopirovane.getOdmenaXP(), 
                kopirovane.getIntelekt(), kopirovane.getSila(),kopirovane.getJmeno(), kopirovane.getJmenoObrazku());
        kopirovane.getLoot().forEach(loot -> {kopie.pridejLootItem(loot);});
        return kopie;
    }
}
