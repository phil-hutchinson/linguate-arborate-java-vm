/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.arborate.vm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.List;
/**
 *
 * @author Phil Hutchinson
 */
public class VirtualMachine {
    private List<FunctionDefinition> funcDefs;
    private Stack<VirtualMachineStackItem> stack;
    private FunctionInstance currentFunction;

    public VirtualMachine(List<FunctionDefinition> funcDefs, Stack<VirtualMachineStackItem> stack) {
        this.funcDefs = funcDefs;
        this.stack = stack;
    }
    
    FunctionInstance getFunctionInstance(FunctionDefinition definition)
    {
        int inParamCount = definition.getInParamCount();
        
        if (inParamCount > stack.size()) {
            throw new VirtualMachineExecutionException("Cannot call function: not enough params."); 
        }
        
        int localVarCount = definition.getVarCount();
        if (localVarCount < 0) {
            throw new VirtualMachineExecutionException("Local variable count cannot be negative."); 
        }
        
        List<VariableInstance> localVars = new ArrayList<VariableInstance>(localVarCount);
        for (int count = 0; count < localVarCount; count++) {
            localVars.add(new VariableInstance());
        }

        return new FunctionInstance(definition, localVars);
    }
            
    public VariableInstance execute() {
        FunctionDefinition startDef = funcDefs.get(0);
        currentFunction = getFunctionInstance(startDef);
        
        if (startDef.getInParamCount() != 0) {
            throw new IllegalArgumentException("Primary function cannot take parameters (yet)");
        }
        
        int nextInstructionNumber = 0;
        boolean stillProcessing = true;
        while (stillProcessing) {
            Instruction nextInstruction = currentFunction.getDefinition().getCode().get(nextInstructionNumber);
            boolean incrementInstruction = true;
            switch(nextInstruction.getInstructionCode()) {
                case PUSH_VARIABLE_TO_STACK:
                    VariableInstance varToPush = currentFunction.getLocalVars().get((Integer)nextInstruction.getData());
                    stack.push(varToPush.getStackItem());
                    break;
                
                case POP_STACK_TO_VARIABLE:
                    VariableInstance varToPop = currentFunction.getLocalVars().get((Integer)nextInstruction.getData());
                    VirtualMachineStackItem stackItem = stack.pop();
                    varToPop.setFromStackItem(stackItem);
                    break;

                case INTEGER_TO_STACK:
                    long intToAddToStack = (Long) nextInstruction.getData();
                    VirtualMachineStackItem intToStackItem = new VirtualMachineStackItem(intToAddToStack);
                    stack.push(intToStackItem);
                    break;
                
                case INTEGER_ADD:
                    long op2 = popInteger();
                    long op1 = popInteger();
                    long sum = op1 + op2;
                    VirtualMachineStackItem sumStackitem = new VirtualMachineStackItem(sum);
                    stack.push(sumStackitem);
                    break;
                    
                case INTEGER_SUBTRACT:
                    long op2s = popInteger();
                    long op1s = popInteger();
                    long diff = op1s + op2s;
                    VirtualMachineStackItem diffStackitem = new VirtualMachineStackItem(diff);
                    stack.push(diffStackitem);
                    break;
            }
            if (incrementInstruction) {
                nextInstructionNumber++;
            }
            if (nextInstructionNumber >= currentFunction.getDefinition().getCode().size()) {
                stillProcessing = false;
            }
        }
        if (stack.size() > 0) {
            VariableInstance returnValue = new VariableInstance();
            returnValue.setFromStackItem(stack.pop());
            return returnValue;
        }
        return null;
    }
    
    private long popInteger() {
        VirtualMachineStackItem stackItem = stack.pop();
        if (stackItem.getBaseType() != BaseType.INTEGER) {
            throw new VirtualMachineExecutionException("Stack item was not expected type (integer)");
        }
        return stackItem.getPrimitiveValue();
    }
}
