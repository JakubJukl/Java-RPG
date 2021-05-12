/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

/**
 *
 * @author Jakub Jukl
 */
public final class RNG {
    public static int cisloMezi(int dolniMez, int horniMez){    //jednoduchá fce pro vrácení random čísla
        return (int) ((Math.random() * (horniMez + 1 - dolniMez)) + dolniMez);
    }
}
