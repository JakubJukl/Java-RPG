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
public class LootItem extends MnozstviItemu{    //šance na loot
    private final int sance;

    public LootItem(int sance, Item item, int mnozstvi) {
        super(item, mnozstvi);
        this.sance = sance;
    }

    public int getSance() {
        return sance;
    }
}
