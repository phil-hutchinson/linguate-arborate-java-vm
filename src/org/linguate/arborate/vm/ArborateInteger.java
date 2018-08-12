/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.arborate.vm;

/**
 *
 * @author Phil Hutchinson
 */
public class ArborateInteger extends ArborateObject {
    final private long value;
    
    public ArborateInteger(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
