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
public class VariableInstance {
    private Object value;

    public VariableInstance() {
        value = null;
    }
    
    public VariableInstance(VariableInstance original) {
        this.value = original.value;
    }
    
    public VirtualMachineStackItem getStackItem() {
        if (value == null)
        {
            return new VirtualMachineStackItem();
        } else if (value instanceof Boolean) {
            boolean unboxed = (Boolean) value;
            return new VirtualMachineStackItem(unboxed);
        } else if (value instanceof Byte) {
            byte unboxed = (Byte) value;
            return new VirtualMachineStackItem(unboxed);
        } else if (value instanceof Long) {
            long unboxed = (Long) value;
            return new VirtualMachineStackItem(unboxed);
        } else if (value instanceof String) {
            return new VirtualMachineStackItem((String) value);
        }
        // TODO: Dictionary, List, 
        throw new VirtualMachineExecutionException("Unexpected type in stack");
    }
    
    public void setFromStackItem(VirtualMachineStackItem stackItem) {
        switch (stackItem.getBaseType())
        {
            case NULL:
                value = null;
                break;
                
            case BOOLEAN:
                boolean boolVal = stackItem.getPrimitiveValue() == 0L ? false : true;
                value = new Boolean(boolVal);
                break;
                
            case BYTE:
                byte byteVal = (byte) stackItem.getPrimitiveValue();
                value = new Byte(byteVal);
                break;
                
            case INTEGER:
                value = new Long(stackItem.getPrimitiveValue());
                break;
                
            case STRING:
                value = stackItem.getReferenceValue();
                break;
                
            // TODO other types
            
            default:
                throw new VirtualMachineExecutionException("Unrecognized type in stack item creating ");
        }
        
    }
}
