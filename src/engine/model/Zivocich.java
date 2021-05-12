/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.model;

import engine.ZakladniNotifikacni;

/**
 *
 * @author Jakub Jukl
 */
public class Zivocich extends ZakladniNotifikacni {
    private final static String SMRT_ZIVOCICHA = " zemřel.";
    
    private int soucHP;
    private int maxHP;
    private int soucMana;
    private int maxMana;
    private int zlato;
    private int level;
    private String jmeno;
    private double intelekt;
    private double sila;
    
    public final static String PC_INTELEKT = "Intelekt";
    public final static String PC_SILA = "Sila";
    public final static String PC_SOUCMANA = "SoucMana";
    public final static String PC_JMENO = "Jmeno";
    public final static String PC_SOUCHP = "SoucHP";
    public final static String PC_ZLATO = "Zlato";
    public final static String PC_LEVEL = "Level";
    
    public final static double PRIDANI_PRIM_STATU = 2;
    public final static double PRIDANI_SEK_STATU = 0.5;
    public final static int KOEF_MANA_INTELEKT = 100;
    public final static int PRIDANI_HP = 20;

    public Zivocich(int maxHP, int zlato, int level, double intelekt, double sila, String jmeno) {
        this.soucHP = maxHP;
        this.maxHP = maxHP;
        this.zlato = zlato;
        this.level = level;
        this.jmeno = jmeno;
        this.intelekt = intelekt;
        this.sila = sila;

        Double mana = intelekt * KOEF_MANA_INTELEKT;
        this.maxMana = mana.intValue();
        this.soucMana = this.maxMana;
    }

    public double getIntelekt() {
        return intelekt;
    }

    private void setIntelekt(double intelekt) {
        SUPPORT.firePropertyChange(PC_INTELEKT, this.intelekt, intelekt);
        this.intelekt = intelekt;
        Double mana = intelekt * KOEF_MANA_INTELEKT;
        int maxManaZaloha = getMaxMana();
        setMaxMana(mana.intValue());
        doplnManu(getMaxMana() - maxManaZaloha);    //kolik many doplnit, aby se mu doplnil jen rozdíl
    }

    public double getSila() {
        return sila;
    }

    private void setSila(double sila) {
        SUPPORT.firePropertyChange(PC_SILA, this.sila, sila);
        this.sila = sila;
    }

    public int getSoucMana() {
        return soucMana;
    }

    private void setSoucMana(int soucMana) {
        SUPPORT.firePropertyChange(PC_SOUCMANA, this.soucMana, soucMana);
        this.soucMana = soucMana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    private void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        SUPPORT.firePropertyChange(PC_JMENO, this.jmeno, jmeno);
        this.jmeno = jmeno;
    }

    private void setSoucHP(int soucHP) {
        SUPPORT.firePropertyChange(PC_SOUCHP, this.soucHP, soucHP);
        this.soucHP = soucHP;
    }

    private void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    private void setZlato(int zlato) {
        SUPPORT.firePropertyChange(PC_ZLATO, this.zlato, zlato);
        this.zlato = zlato;
    }

    private void setLevel(int level) {
        SUPPORT.firePropertyChange(PC_LEVEL, this.level, level);
        this.level = level;
    }

    public int getSoucHP() {
        return soucHP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getZlato() {
        return zlato;
    }

    public int getLevel() {
        return level;
    }

    public void dostalDmg(int dmg) {
        int hp = soucHP - dmg;
        if (hp <= 0) {
            setSoucHP(0);
        } else {
            setSoucHP(hp);
        }
    }

    public String zpravaPriSmrti() {
        return this.jmeno + SMRT_ZIVOCICHA;
    }

    public boolean isMrtvy() {
        return this.soucHP <= 0;
    }

    public void pridejZlato(int zlato) {
        setZlato(this.zlato + zlato);
    }

    private void lvlUPZaklad() {
        setLevel(this.level + 1);
        setMaxHP(this.maxHP + PRIDANI_HP);
        healNaMax();
    }

    public void levelUP() {
        lvlUPZaklad();
        setIntelekt(this.intelekt + 1);
        setSila(this.sila + 1);
        manaNaMax();
    }

    public void levelUP(Hrac.Povolani povolani) {
        lvlUPZaklad();
        if (povolani == Hrac.Povolani.Kouzelnik) {
            setIntelekt(this.intelekt + PRIDANI_PRIM_STATU);
            setSila(this.sila + PRIDANI_SEK_STATU);
        } else {
            setIntelekt(this.intelekt + PRIDANI_SEK_STATU);
            setSila(this.sila + PRIDANI_PRIM_STATU);
        }
        manaNaMax();
    }

    public void healNaMax() {
        setSoucHP(this.maxHP);
    }

    public void heal(int heal) {
        if (heal < 0) {
            dostalDmg(-heal);
        } else {
            heal = soucHP + heal;
            if (heal >= maxHP) {
                healNaMax();
            } else {
                setSoucHP(heal);
            }
        }
    }

    public void spotrebujManu(int mana) {
        setSoucMana(this.soucMana - mana);
    }

    public void doplnManu(int mana) {
        int celkemMana = this.soucMana + mana;
        if (celkemMana >= this.maxMana) {
            manaNaMax();
        } else {
            setSoucMana(celkemMana);
        }
    }

    public void manaNaMax() {
        setSoucMana(this.maxMana);
    }

    public boolean jeDostMany(int mana) {
        return getSoucMana() >= mana;
    }

    public void vynasobIntelekt(double koeficient) {
        setIntelekt(intelekt * koeficient);
    }

    public void vynasobSilu(double koeficient) {
        setSila(sila * koeficient);
    }

}
