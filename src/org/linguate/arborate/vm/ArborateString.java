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
public class ArborateString extends ArborateObject {
    final String value;
    
    public ArborateString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    // EQUALS / HASHCODE OVERRIDES
    final static int CLASS_HASH = 24971;
    final static int CLASS_HASH_OFFSET = 27541;
    final static int NULL_HASH = 95009;
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        ArborateString other = (ArborateString) obj;
        
        if (this.hashCode() != other.hashCode()) {
            return false;
        }

        return (this.value == null ? other.value == null : this.value.equals(other.value));
    }
    
    @Override
    public int hashCode() {
        return (value == null) ? NULL_HASH : (value.hashCode() + CLASS_HASH_OFFSET) * CLASS_HASH;
    }
    
}
