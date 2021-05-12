/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author Jakub Jukl
 */
public class ZakladniNotifikacni {  //základní notifikační třída, kterou implementují pak další třídy
    protected final PropertyChangeSupport SUPPORT = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        SUPPORT.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        SUPPORT.removePropertyChangeListener(propertyName, listener);
    }
}
