/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.arborate.vm;

/**
 * Used as a placeholder for items deleted from collection
 * 
 * @author Phil Hutchinson
 */
class ArborateNonItem extends ArborateObject {
    private final static ArborateNonItem INSTANCE = new ArborateNonItem();
    
    private ArborateNonItem() {
    }
    
    static ArborateNonItem get() {
        return INSTANCE;
    }
}
