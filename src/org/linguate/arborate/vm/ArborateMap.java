/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.arborate.vm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author Phil Hutchinson
 */
public class ArborateMap extends ArborateObject {
    HashMap<ArborateObject, ArborateObject> items;
    ArborateMap parent;
    int itemCount;
    int internalHashCode;
    
    public ArborateMap() {
        items = new HashMap<>();
        parent = null;
        itemCount = 0;
        internalHashCode = 0;
    }
    
    ArborateMap(ArborateMap parent) {
        this.items = new HashMap<>();
        this.parent = parent;
        this.itemCount = parent.itemCount;
        this.internalHashCode = parent.internalHashCode;
    }
    
    public boolean has(ArborateObject key) {
        if (items.containsKey(key)) {
            return items.get(key) != ArborateNonItem.get();
        } else if (parent != null) {
            return parent.has(key);
        } else {
            return false;
        }
    }

    public ArborateObject get(ArborateObject key) {
        if (items.containsKey(key)) {
            ArborateObject result = items.get(key);
            if (result == ArborateNonItem.get()) {
                return null;
            } else {
                return result;
            }
        } else if (parent != null) {
            return parent.get(key);
        } else {
            return null;
        }
    }
    
    public ArborateMap set(ArborateObject key, ArborateObject value) {
        if (key == null || value == null) {
            throw new VirtualMachineExecutionException("Cannot use null as key or value in ArborateDictionary.");
        }
        
        ArborateObject oldValue = get(key);
        
        if (value.equals(oldValue) ) {
            return this;
        } else {
            ArborateMap returnValue = new ArborateMap(this);
            returnValue.items.put(key, value);
            returnValue.internalHashCode = returnValue.internalHashCode ^ getKeyValueHashCode(key, value);
            if (oldValue == null) {
                returnValue.itemCount++;
            } else {
                returnValue.internalHashCode = returnValue.internalHashCode ^ getKeyValueHashCode(key, oldValue);
            }
            returnValue.mergeUp();
            return returnValue;
        }
    }

    public ArborateMap clear(ArborateObject key) {
        if (key == null) {
            throw new VirtualMachineExecutionException("Cannot use null as key or value in ArborateDictionary.");
        }
        
        ArborateObject oldValue = get(key);
        
        if (oldValue == null) {
            return this;
        } else {
            ArborateMap returnValue = new ArborateMap(this);
            returnValue.items.put(key, ArborateNonItem.get());
            returnValue.internalHashCode = returnValue.internalHashCode ^ getKeyValueHashCode(key, oldValue);
            returnValue.itemCount--;
            returnValue.mergeUp();
            return returnValue;
        }
    }
    
    public int getSize() {
        return itemCount;
    }
    
    private void mergeUp() {
        while (parent != null && parent.items.size() <= items.size()) {
            parent.items.entrySet().stream().forEach((entry) -> {
                ArborateObject key = entry.getKey();
                if (!this.items.containsKey(key)) {
                    this.items.put(key, entry.getValue());
                }
            });
            parent = parent.parent;
        }
    }
    
    // key/value Hash Code generation
    final static int KEY_HASH_OFFSET = 87517;
    final static int KEY_HASH = 802453;
    final static int VALUE_HASH_OFFSET = 57529;
    final static int VALUE_HASH = 5519;
    
    private int getKeyValueHashCode(ArborateObject key, ArborateObject value) {
        return (key.hashCode() + KEY_HASH_OFFSET) * KEY_HASH + (value.hashCode() + VALUE_HASH_OFFSET) * VALUE_HASH;
    }
    
    // EQUALS / HASHCODE OVERRIDES
    final static int CLASS_HASH = 37813;
    final static int CLASS_HASH_OFFSET = 42589;
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        ArborateMap other = (ArborateMap) obj;
        
        return compareToMap(other);
    }
    
    private boolean compareToMap(ArborateMap other) {
        if (itemCount != other.itemCount) {
            return false;
        }
        
        HashMap<ArborateObject, ArborateObject> packedItems = getPackedItems();
        
        boolean isSame = packedItems.entrySet().stream().allMatch(e -> 
            other.get(e.getKey()).equals(e.getValue())
        );
                
        return isSame;
    }

    private HashMap<ArborateObject, ArborateObject> getPackedItems() {
        HashMap<ArborateObject, ArborateObject> returnValue = new HashMap<>();
        HashSet<ArborateObject> keysAlreadyEncountered = new HashSet<>();
        
        ArborateMap current = this;
        while (current != null) {
            for (ArborateObject key: current.items.keySet()) {
                if (!keysAlreadyEncountered.contains(key)) {
                    keysAlreadyEncountered.add(key);
                    ArborateObject value = current.items.get(key);
                    if (value != ArborateNonItem.get()) {
                        returnValue.put(key, value);
                    }
                }
            }
            current = current.parent;
        }
        
        return returnValue;
    }
    
    @Override
    public int hashCode() {
        return (internalHashCode + CLASS_HASH_OFFSET) * CLASS_HASH;
    }

}
