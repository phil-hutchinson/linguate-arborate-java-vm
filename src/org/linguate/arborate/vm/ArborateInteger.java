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

    // EQUALS / HASHCODE OVERRIDES
    final static int CLASS_HASH = 289210841;
    final static int CLASS_HASH_OFFSET = 167930687;
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        ArborateInteger other = (ArborateInteger) obj;

        return this.value == other.value;
    }
    
    @Override
    public int hashCode() {
        return (Long.hashCode(value) + CLASS_HASH_OFFSET) * CLASS_HASH;
    }
    
}
