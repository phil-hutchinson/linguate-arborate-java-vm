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

    // EQUALS / HASHCODE OVERRIDES
    final static int CLASS_HASH = 7661081;
    final static int CLASS_HASH_OFFSET = 1;
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        ArborateByte other = (ArborateByte) obj;

        return this.value == other.value;
    }
    
    @Override
    public int hashCode() {
        return ((int)(value) + CLASS_HASH_OFFSET) * CLASS_HASH;
    }
    
}
