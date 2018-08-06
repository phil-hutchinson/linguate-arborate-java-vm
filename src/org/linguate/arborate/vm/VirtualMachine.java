/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.arborate.vm;

import java.util.ArrayList;
import java.util.Stack;
import java.util.List;
/**
 *
 * @author Phil Hutchinson
 */
public class VirtualMachine {
    private final List<FunctionDefinition> funcDefs;
    private final Stack<Object> stack;
    private FunctionInstance currentFunction;

    public VirtualMachine(List<FunctionDefinition> funcDefs) {
        this.stack = new Stack<>();
        this.funcDefs = funcDefs;
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
        
        List<Object> localVars = new ArrayList<>(localVarCount);
        for (int count = 0; count < localVarCount; count++) {
            localVars.add(null);
        }

        return new FunctionInstance(definition, localVars);
    }
            
    public Object execute() {
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
                case VARIABLE_TO_STACK: {
                    int varPos = (Integer)nextInstruction.getData();
                    Object varToPush = currentFunction.getLocalVars().get(varPos);
                    stack.push(varToPush);
                }
                break;
                
                case STACK_TO_VARIABLE: {
                    int varPos = (Integer)nextInstruction.getData();
                    Object poppedStackItem = stack.pop();
                    currentFunction.getLocalVars().set(varPos, poppedStackItem);
                }
                break;

                case INTEGER_TO_STACK: {
                    Long intToPush = (Long) nextInstruction.getData();
                    stack.push(intToPush);
                }
                break;
                
                case INTEGER_ADD: {
                    long op2 = popInteger();
                    long op1 = popInteger();
                    long sum = op1 + op2;
                    stack.push(sum);
                }
                break;
                    
                case INTEGER_SUBTRACT: {
                    long op2 = popInteger();
                    long op1 = popInteger();
                    long diff = op1 - op2;
                    stack.push(diff);
                }
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
            Object returnValue = stack.pop();
            return returnValue;
        }
        return null;
    }
    
    private Long popInteger() {
        Object stackItem = stack.pop();
        if (!(stackItem instanceof Long)) {
            throw new VirtualMachineExecutionException("Stack item was not expected type (integer)");
        }
        return (Long)stackItem;
    }
}
