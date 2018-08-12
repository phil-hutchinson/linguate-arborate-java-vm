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
public class ArborateByte extends ArborateObject {
    final byte value;
    
    public ArborateByte(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
