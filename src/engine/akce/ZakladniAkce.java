/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.akce;

import engine.model.Item;

/**
 *
 * @author Jakub Jukl
 */
public class ZakladniAkce { //každá akce se nějak váže na item, ať už útok itemem, kouzlo je item, nebo konzumace itemu
    private final Item item;
    
    ZakladniAkce(Item item){
        this.item = item;
    }

    public Item getItem() {
        return item;
    }
}
