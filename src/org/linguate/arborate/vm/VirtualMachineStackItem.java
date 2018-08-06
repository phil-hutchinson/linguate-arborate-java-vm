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
public class VirtualMachineStackItem {
    private BaseType baseType;
    private long primitiveValue;
    private Object referenceValue;

    public BaseType getBaseType() {
        return baseType;
    }
    
    public long getPrimitiveValue() {
        return primitiveValue;
    }

    public Object getReferenceValue() {
        return referenceValue;
    }
    
    public VirtualMachineStackItem() {
        this.baseType = BaseType.NULL;
    }
            
    public VirtualMachineStackItem(long val) {
        this.baseType = BaseType.INTEGER;
        this.primitiveValue = val;
    }

    public VirtualMachineStackItem(byte val) {
        this.baseType = BaseType.BYTE;
        this.primitiveValue = (long) val;
    }
    
    public VirtualMachineStackItem(boolean val) {
        this.baseType = BaseType.BOOLEAN;
        this.primitiveValue = val ? 1L : 0L;
    }
    
    public VirtualMachineStackItem(String val) {
        this.baseType = BaseType.STRING;
        this.referenceValue = val;
    }

    // TODO: ARRAY, DICTIONARY, FUNCTION
}
