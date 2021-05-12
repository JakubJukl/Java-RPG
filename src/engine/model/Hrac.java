/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.model;

import engine.factory.ItemFactory;
import java.util.ArrayList;

/**
 *
 * @author Jakub Jukl
 */
public class Hrac extends Zivocich {
    private final static int MNOZSTVI_MARGOT = 10;
    private final static String SMRT_HRACE = "Zemřel si strašlivou smrtí.";
    private final static String JMENO_NOVEHO_HRACE = "Hráč";
    
    private int xp;
    private final ArrayList<MnozstviItemu> inventar;
    private final ArrayList<Ukol> prijateUkoly;
    private final ArrayList<Ukol> splneneUkoly;
    private Item soucZbran;
    private Povolani povolani;
    
    public final static String PC_POVOLANI = "Povolani";
    public final static String PC_XP = "XP";
    public final static String PC_ZBRAN = "Zbran";
    public final static String PC_KONZUM = "Konzum";
    public final static String PC_KOUZLO = "Kouzlo";
    public final static String PC_INVENTAR = "Inventar";
    

    public enum Povolani {
        Valecnik,
        Kouzelnik
    }
    
    public Hrac(int maxHP, int zlato, int level, int xp, double intelekt, double sila, String jmeno) {
        super(maxHP, zlato, level, intelekt, sila, jmeno);
        this.xp = xp;
        
        inventar = new ArrayList<>();
        prijateUkoly = new ArrayList<>();
        splneneUkoly = new ArrayList<>();
        povolani = Povolani.Valecnik;
    }

    public Povolani getPovolani() {
        return povolani;
    }

    public void setPovolani(Povolani povolani) {
        SUPPORT.firePropertyChange(PC_POVOLANI, this.povolani, povolani);
        this.povolani = povolani;
    }

    private void setXp(int xp) {
        SUPPORT.firePropertyChange(PC_XP, this.xp, xp);
        this.xp = xp;
    }

    public int getXp() {
        return xp;
    }

    public ArrayList<MnozstviItemu> getInventar() {
        return inventar;
    }

    public ArrayList<Ukol> getPrijateUkoly() {
        return prijateUkoly;
    }

    public ArrayList<Ukol> getSplneneUkoly() {
        return splneneUkoly;
    }

    public Item getSoucZbran() {
        return soucZbran;
    }
    
    public void setSoucZbran(Item soucZbran) {
        this.soucZbran = soucZbran;
    }

    public void pridejXp(int xp) {  //setXP je private, protože hra nemusí nikdy nastavovat přesně XP, jen přidávat
        int totalXp = this.xp + xp;
        if (totalXp >= (getLevel() * 100)) {
            totalXp = totalXp - (getLevel() * 100);
            levelUP(this.povolani);
        }
        setXp(totalXp);
    }

    public void pridejItem(MnozstviItemu item) {    //přidá kopii itemu do inventáře
        MnozstviItemu kopie = new MnozstviItemu(item.item, item.mnozstvi);

        if (inventar.stream().filter(i -> i.item.getId() == kopie.item.getId()).findFirst().orElse(null) == null) {
            inventar.add(kopie);
        } else {
            inventar.stream().filter(i -> i.item.getId() == kopie.item.getId()).findFirst().get().mnozstvi += kopie.mnozstvi;
        }
        notifikace(kopie);
    }

    public void odeberItem(MnozstviItemu item) {    //odebere jeden item
        odeberItem(item, 1);
    }

    public void odeberItem(MnozstviItemu item, int mnozstvi) {  //odebere více itemů
        inventar.stream().filter(i -> i.item.getId() == item.item.getId()).findFirst().get().mnozstvi -= mnozstvi;
        if (item.mnozstvi == 0) {
            inventar.remove(item);
        }
        notifikace(item);
    }

    private void notifikace(MnozstviItemu item) {   //jaký item se přidal? 
        switch (item.item.getDruh()) {              //jaký cbo musím upozornit, aby se aktualizoval? 
            case Zbran ->
                SUPPORT.firePropertyChange(PC_ZBRAN, "", "a");
            case Konzumovatelne ->
                SUPPORT.firePropertyChange(PC_KONZUM, "", "a");
            case Kouzlo ->
                SUPPORT.firePropertyChange(PC_KOUZLO, "", "a");
            default -> {
            }
        }
        SUPPORT.firePropertyChange(PC_INVENTAR, "", "a");
    }

    public void prijmiUkol(Ukol ukol) {
        prijateUkoly.add(ukol);
    }

    public void splnUkol(Ukol ukol) {
        prijateUkoly.remove(ukol);
        splneneUkoly.add(ukol);
        pridejXp(ukol.getOdmenaXp());
        pridejZlato(ukol.getOdmenaZlato());
        if (!ukol.getOdmenaItem().isEmpty()) {
            ukol.getOdmenaItem().forEach(i -> pridejItem(i));
        }
    }

    public static Hrac ZakladniHrac() {     //vytvoření čerstvého hráče, ukládání do souboru je možnost v budoucnosti
        Hrac hrac = new Hrac(20, 3, 1, 0, 1, 2, JMENO_NOVEHO_HRACE);

        //hrac.PridejItem(new MnozstviItemu(ItemFactory.VytvorItem(ItemFactory.ID_MEC_OSUDU), 1));
        
        hrac.pridejItem(new MnozstviItemu(ItemFactory.vytvorItem(ItemFactory.ID_MARGOT), MNOZSTVI_MARGOT));
        hrac.pridejItem(new MnozstviItemu(ItemFactory.vytvorItem(ItemFactory.ID_KLACEK), 1));
        hrac.pridejItem(new MnozstviItemu(ItemFactory.vytvorItem(ItemFactory.ID_OHNIVA_KOULE), 1));
        hrac.pridejItem(new MnozstviItemu(ItemFactory.vytvorItem(ItemFactory.ID_HEALOVACI_VLNA), 1));
        return hrac;
    }

    @Override
    public String zpravaPriSmrti() {
        return SMRT_HRACE;
    }

    public void pridejOdmeny(Monstrum monstrum) {
        pridejXp(monstrum.getOdmenaXP());
        pridejZlato(monstrum.getZlato());
    }
    

}
