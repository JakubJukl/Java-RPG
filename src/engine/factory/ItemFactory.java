/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.factory;

import engine.akce.HealKouzlem;
import engine.akce.RegeneraceKonzumem;
import engine.akce.UtokKouzlem;
import engine.akce.UtokZbrani;
import engine.model.*;
import java.util.ArrayList;

/**
 *
 * @author Jakub Jukl
 */
public class ItemFactory {

    private static final ArrayList<Item> herniItemy = new ArrayList<>();
    
    public static final int ID_MEC_OSUDU = 1;
    public static final int ID_ZUBY = 2;
    public static final int ID_KOLT = 3;
    public static final int ID_KLACEK = 4;
    
    public static final int ID_MARGOT = 500;
    public static final int ID_WHISKEY = 501;
    public static final int ID_LIDSKA_KONCETINA = 502;
    
    public static final int ID_KOULE = 1000;
    public static final int ID_ZIZALI_BOBEK = 1001;
    public static final int ID_KLOBOUK = 1002;
    public static final int ID_UPIRI_MOREK = 1003;
    public static final int ID_PLAST = 1004;
    
    public static final int ID_OHNIVA_KOULE =1500;
    public static final int ID_HEALOVACI_VLNA = 2000;
    
    public static void setItemy(){
        vytvorZbran(ID_MEC_OSUDU, "Meč osudu", "Meč, který prozrazuje osud, avšak podle něj je osudem všech jejich násilná smrt.", 
                0, 999, 999);
        vytvorZbran(ID_ZUBY, "Zuby", "Píchají.", 0, 2, 3);
        vytvorZbran(ID_KOLT, "Kolt", "Tenhle kolt visel proklatě nízko.", 100, 7, 15);
        vytvorZbran(ID_KLACEK, "Klacek", "S tímhle si někdo sundával výkaly z boty.",0, 1, 3);
        
        vytvorKonzumovatelne(ID_MARGOT, "Margot", "Moje oblíbená", 29, 30);
        vytvorKonzumovatelne(ID_WHISKEY, "Whiskey", "Její účinky jsou ti neznámé", 4, -5);
        vytvorKonzumovatelne(ID_LIDSKA_KONCETINA, "Lidská končetina", "Jak to asi může chutnat", 0, 30);
        
        vytvorRuzne(ID_KOULE, "Koule", "Je to kulaté a je to chlupaté. Co je to?", 2);
        vytvorRuzne(ID_ZIZALI_BOBEK, "Žížalí bobek", "Je to malé, hnědé a smrdí to. Co je to?", 3);
        vytvorRuzne(ID_KLOBOUK, "Klobouk", "Lepší seženeš jen na východě.", 10);
        vytvorRuzne(ID_UPIRI_MOREK, "Upíří morek", "Na dálném východě, by ti za to ruce utrhli.", 1000);
        vytvorRuzne(ID_PLAST, "Plášť", "Škoda, že je neumíš nosit.", 200);
        
        vytvorKouzlo(ID_OHNIVA_KOULE, "Ohnivá koule", "Tohle ještě nikdo neuhasil.", 10, 3, 5, 10, false);
        vytvorKouzlo(ID_HEALOVACI_VLNA, "Healovací vlna", "Uhasí i ohnivou kouli.", 10, 8, 10,10, true);
    }
    
    public static Item vytvorItem(int id) { //najde item a vrátí ho, tam už si ho příslušná fce zkopíruje
        return herniItemy.stream().filter(item -> item.getId() == id).findFirst().get();
    }
    
    private static void vytvorZbran(int id, String jmeno, String popis, int cena, int minDmg, int maxDmg){
        Item zbran = new Item(id, jmeno, popis, cena, Item.ItemDruh.Zbran);
        zbran.setAkce(new UtokZbrani(zbran, minDmg, maxDmg));
        herniItemy.add(zbran);
    }
    
    private static void vytvorKouzlo(int id, String jmeno, String popis, int cena, int minDmg, int maxDmg, 
            int mana, boolean heal){
        Item kouzlo = new Item(id, jmeno, popis, cena, Item.ItemDruh.Kouzlo);
        if (heal){
           kouzlo.setAkce(new HealKouzlem(kouzlo, minDmg, maxDmg, mana));
        }else{
            kouzlo.setAkce(new UtokKouzlem(kouzlo, minDmg, maxDmg, mana));
        }
        herniItemy.add(kouzlo);
    }
    
    private static void vytvorRuzne(int id, String jmeno, String popis, int cena){    
        herniItemy.add(new Item(id, jmeno, popis, cena, Item.ItemDruh.Ruzne));
    }
    
    private static void vytvorKonzumovatelne(int id, String jmeno, String popis, int cena, int heal){
        Item konzum = new Item(id, jmeno, popis, cena, Item.ItemDruh.Konzumovatelne);
        konzum.setAkce(new RegeneraceKonzumem(konzum, heal));
        herniItemy.add(konzum);
    }

}
