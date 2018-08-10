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
    private final List<FunctionDefinition> funcDefs;
    private final Stack<Object> stack;
    private final Stack<FunctionInstance> functionStack;
    private FunctionInstance currentFunction;

    public VirtualMachine(List<FunctionDefinition> funcDefs) {
        this.stack = new Stack<>();
        this.funcDefs = funcDefs;
        this.functionStack = new Stack<>();
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
            
    public List<Object> execute(Object ...args) {
        FunctionDefinition startDef = funcDefs.get(0);
        PrepareArguments(startDef, args);
        
        currentFunction = getFunctionInstance(startDef);
        
        boolean stillProcessing = true;
        while (stillProcessing) {
            Instruction nextInstruction = currentFunction.getDefinition().getCode().get(currentFunction.getNextInstructionNumber());
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
                
                case INTEGER_MULTIPLY: {
                    long op2 = popInteger();
                    long op1 = popInteger();
                    long product = op1 * op2;
                    stack.push(product);
                }
                break;
                
                case INTEGER_DIVIDE: {
                    long op2 = popInteger();
                    long op1 = popInteger();
                    long integerQuotient = op1 / op2;
                    stack.push(integerQuotient);
                }
                break;
                
                case INTEGER_MODULUS: {
                    long op2 = popInteger();
                    long op1 = popInteger();
                    long remainder = op1 % op2;
                    stack.push(remainder);
                }
                break;
                
                case CALL_FUNCTION: {
                    int functionIndex = (Integer) nextInstruction.getData();
                    if (funcDefs.size() <= functionIndex) {
                        throw new VirtualMachineExecutionException("Invalid function index");
                    }
                    currentFunction.incrementNextInstructionNumber();
                    functionStack.push(currentFunction);

                    FunctionDefinition definition = funcDefs.get(functionIndex);
                    currentFunction = getFunctionInstance(definition);
                    incrementInstruction = false;
                }
                break;
                
                default:
                    throw new VirtualMachineExecutionException("Encountered unknown operation code.");
            }
            if (incrementInstruction) {
                currentFunction.incrementNextInstructionNumber();
            }
            if (currentFunction.getNextInstructionNumber() >= currentFunction.getDefinition().getCode().size()) {
                if (functionStack.empty()) {
                    stillProcessing = false;
                } else {
                    currentFunction = functionStack.pop();
                }
            }
        }
        
        return PrepareReturn();
    }

    private void PrepareArguments(FunctionDefinition startDef, Object[] args) throws VirtualMachineExecutionException, IllegalArgumentException {
        if (startDef.getInParamCount() != args.length) {
            throw new IllegalArgumentException("Primary function does not take correct amount of arguments.");
        }
        
        List<BaseType> inParams = startDef.getInParams();
        for (int argPos = 0; argPos < args.length; argPos++) {
            switch(inParams.get(argPos)) {
                case NULL:
                    throw new VirtualMachineExecutionException("Function argument type cannot be NULL.");
                    
                case INTEGER:
                    if (args[argPos] instanceof Long) {
                        stack.push(args[argPos]); // ENHANCMENT accept shorter types
                    } else {
                        throw new IllegalArgumentException("Argument is not appropriate type for method.");
                    }
                    break;
                    
                case STRING:
                    if (args[argPos] instanceof String) {
                        stack.push(args[argPos]);
                    } else {
                        throw new IllegalArgumentException("Argument is not appropriate type for method.");
                    }
                    break;
                    
                case BOOLEAN:
                    if (args[argPos] instanceof Boolean) {
                        stack.push(args[argPos]);
                    } else {
                        throw new IllegalArgumentException("Argument is not appropriate type for method.");
                    }
                    break;
                    
                case BYTE:
                    if (args[argPos] instanceof Byte) {
                        stack.push(args[argPos]);
                    } else {
                        throw new IllegalArgumentException("Argument is not appropriate type for method.");
                    }
                    break;
                    
                default:
                    throw new IllegalArgumentException("Argument of unknown type.");
            }
        }
    }
    
    private List<Object> PrepareReturn() throws VirtualMachineExecutionException, IllegalArgumentException {
        if (functionStack.size() > 0) {
            throw new VirtualMachineExecutionException("Function stack not cleared at end of execution.");
        }
        
        if (stack.size() != currentFunction.getDefinition().getOutParamCount()) {
            throw new VirtualMachineExecutionException("Wrong number of items on stack at end of execution.");
        }
        
        ArrayList<Object> returnValue = new ArrayList<>();
         
        List<BaseType> outParams = currentFunction.getDefinition().getOutParams();

        for (int argPos = 0; argPos < outParams.size(); argPos++) {
            Object currParam = stack.pop();
            switch(outParams.get(argPos)) {
                case NULL:
                    throw new VirtualMachineExecutionException("Function argument type cannot be NULL.");
                    
                case INTEGER:
                    if (currParam instanceof Long) {
                        returnValue.add(currParam);
                    } else {
                        throw new IllegalArgumentException("Stack item is not appropriate type for function result.");
                    }
                    break;
                    
                case STRING:
                    if (currParam instanceof String) {
                        returnValue.add(currParam);
                    } else {
                        throw new IllegalArgumentException("Stack item is not appropriate type for function result.");
                    }
                    break;
                    
                    
                case BOOLEAN:
                    if (currParam instanceof Boolean) {
                        returnValue.add(currParam);
                    } else {
                        throw new IllegalArgumentException("Stack item is not appropriate type for function result.");
                    }
                    break;
                    
                    
                case BYTE:
                    if (currParam instanceof Byte) {
                        returnValue.add(currParam);
                    } else {
                        throw new IllegalArgumentException("Stack item is not appropriate type for function result.");
                    }
                    break;
                    
                default:
                    throw new IllegalArgumentException("Argument of unknown type.");
            }
        }
        
        Collections.reverse(returnValue);
        return returnValue;
    }

    private Long popInteger() {
        Object stackItem = stack.pop();
        if (!(stackItem instanceof Long)) {
            throw new VirtualMachineExecutionException("Stack item was not expected type (integer)");
        }
        return (Long)stackItem;
    }
}
