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
}
