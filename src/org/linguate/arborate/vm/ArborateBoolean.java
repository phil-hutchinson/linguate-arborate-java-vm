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
public class ArborateBoolean extends ArborateObject {
    final boolean value;
    public ArborateBoolean(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    // EQUALS / HASHCODE OVERRIDES
    final static int TRUE_HASH = 1231;
    final static int FALSE_HASH = 1237;
    final static int CLASS_HASH = 639493;
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        ArborateBoolean other = (ArborateBoolean) obj;

        return this.value == other.value;
    }
    
    @Override
    public int hashCode() {
        return value ? TRUE_HASH * CLASS_HASH : FALSE_HASH * CLASS_HASH;
    }
    
}
