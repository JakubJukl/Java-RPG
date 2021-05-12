/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.model;

/**
 *
 * @author Jakub Jukl
 */
public class Potycka {
    private final Monstrum monstrum;
    private final int sance;

    public Potycka(Monstrum monstrum, int sance) {
        this.monstrum = monstrum;
        this.sance = sance;
    }

    public Monstrum getMonstrum() {
        return monstrum;
    }

    public int getSance() {
        return sance;
    }

}
