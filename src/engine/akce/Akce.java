/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.akce;

import engine.model.Zivocich;


/**
 *
 * @author Jakub Jukl
 */
public interface Akce { //interface, abych si ho mohl vyzkoušet
    
    String proved(Zivocich actor, Zivocich target);
    
    int cenaProvedeni();
}
