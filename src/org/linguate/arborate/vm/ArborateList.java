/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.arborate.vm;

import java.util.Arrays;

/**
 *
 * @author Phil Hutchinson
 */
public class ArborateList extends ArborateObject {
    ArborateObject[] items;
    
    public ArborateList() {
        items = new ArborateObject[0];
    }
    
    private ArborateList(ArborateObject[] items) {
        this.items = items;
    }
    
    public ArborateObject getPosition(long pos) {
        if (pos < 0 || pos >= items.length ) {
            return null;
        } else {
            return items[(int)pos];
        }
    }
    
    public ArborateList setPosition(long pos, ArborateObject val) {
        if (pos < 0) {
            return this;
        } else if (pos >= items.length) {
            return this;
        } else {
            ArborateObject[] newItems = Arrays.copyOf(items, items.length);
            newItems[(int)pos] = val;
            return new ArborateList(newItems);
        }
    }
    
    public ArborateList insertPosition(long pos, ArborateObject val) {
        if (pos < 0) {
            return this;
        } else if (pos == 0) {
            // insert at start
            ArborateObject newItems[] = new ArborateObject[items.length + 1];
            newItems[0] = val;
            System.arraycopy(items, 0, newItems, 1, items.length);
            return new ArborateList(newItems);
        } else if (pos < items.length) {
            // insert in middle
            ArborateObject newItems[] = new ArborateObject[items.length + 1];
            System.arraycopy(items, 0, newItems, 0, (int)pos);
            newItems[(int)pos] = val;
            System.arraycopy(items, (int)pos, newItems, (int)pos + 1, items.length - (int)pos);
            return new ArborateList(newItems);
        } else if (pos == items.length ) {
            // insert at end
            ArborateObject newItems[] = new ArborateObject[items.length + 1];
            System.arraycopy(items, 0, newItems, 0, items.length);
            newItems[(int)pos] = val;
            return new ArborateList(newItems);
        } else {
            return this;
        }
    }

    public ArborateList removePosition(long pos) {
        if (pos < 0) {
            return this;
        } else if (pos >= items.length) {
            return this;
        } else if (pos == 0) {
            // remove from start
            ArborateObject newItems[] = new ArborateObject[items.length - 1];
            System.arraycopy(items, 1, newItems, 0, items.length);
            return new ArborateList(newItems);
        } else if (pos < items.length - 1) {
            // remove from middle
            ArborateObject newItems[] = new ArborateObject[items.length - 1];
            System.arraycopy(items, 0, newItems, 0, (int)pos);
            System.arraycopy(items, (int)pos + 1, newItems, (int)pos, items.length - 1 - (int)pos);
            return new ArborateList(newItems);
        } else {
            // remove from end
            ArborateObject newItems[] = new ArborateObject[items.length - 1];
            System.arraycopy(items, 0, newItems, 0, items.length - 1);
            return new ArborateList(newItems);
        }
    }
    
    public int getSize() {
        return items.length;
    }
}
