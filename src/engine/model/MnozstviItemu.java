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
public class MnozstviItemu {
    public Item item;
    public int mnozstvi;

    public MnozstviItemu(Item item, int mnozstvi) {
        this.item = item;
        this.mnozstvi = mnozstvi;
    }
    
    
    
    @Override
    public String toString(){
        return item.getJmeno();
    }
}
