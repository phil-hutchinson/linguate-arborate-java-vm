/*
 * To change this license header: choose License Headers in Project Properties.
 * To change this template file: choose Tools | Templates
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
    private final Stack<ArborateObject> stack;
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
        
        List<ArborateObject> localVars = new ArrayList<>(localVarCount);
        for (int count = 0; count < localVarCount; count++) {
            localVars.add(null);
        }

        return new FunctionInstance(definition, localVars);
    }
    
    public List<Object> execute(Object ...args) {
        return executeByNumber(0, args);
    }
            
    public List<Object> executeByNumber(int funcNumber, Object ...args) {
        FunctionDefinition startDef = funcDefs.get(funcNumber);
        PrepareArguments(startDef, args);
        
        currentFunction = getFunctionInstance(startDef);
        
        boolean stillProcessing = true;
        while (stillProcessing) {
            Instruction nextInstruction = currentFunction.getDefinition().getCode().get(currentFunction.getNextInstructionNumber());
            boolean incrementInstruction = true;
            switch(nextInstruction.getInstructionCode()) {
                case VARIABLE_TO_STACK: {
                    long varPos = (Long)nextInstruction.getData();
                    ArborateObject varToPush = currentFunction.getLocalVars().get((int)varPos);
                    stack.push(varToPush);
                }
                break;
                
                case STACK_TO_VARIABLE: {
                    long varPos = (Long)nextInstruction.getData();
                    ArborateObject poppedStackItem = stack.pop();
                    currentFunction.getLocalVars().set((int)varPos, poppedStackItem);
                }
                break;

                case INTEGER_TO_STACK: {
                    long val = (long) nextInstruction.getData();
                    ArborateInteger intToPush = new ArborateInteger(val) ;
                    stack.push(intToPush);
                }
                break;
                
                case INTEGER_ADD: {
                    long op2 = popInteger();
                    long op1 = popInteger();
                    ArborateInteger sum = new ArborateInteger(op1 + op2);
                    stack.push(sum);
                }
                break;
                    
                case INTEGER_SUBTRACT: {
                    long op2 = popInteger();
                    long op1 = popInteger();
                    ArborateInteger diff = new ArborateInteger(op1 - op2);
                    stack.push(diff);
                }
                break;
                
                case INTEGER_MULTIPLY: {
                    long op2 = popInteger();
                    long op1 = popInteger();
                    ArborateInteger product = new ArborateInteger(op1 * op2);
                    stack.push(product);
                }
                break;
                
                case INTEGER_DIVIDE: {
                    long op2 = popInteger();
                    long op1 = popInteger();
                    ArborateInteger integerQuotient = new ArborateInteger(op1 / op2);
                    stack.push(integerQuotient);
                }
                break;
                
                case INTEGER_MODULUS: {
                    long op2 = popInteger();
                    long op1 = popInteger();
                    ArborateInteger remainder = new ArborateInteger(op1 % op2);
                    stack.push(remainder);
                }
                break;
                
                case INTEGER_SHIFT_LEFT: {
                    long op2 = popInteger();
                    long op1 = popInteger();
                    ArborateInteger shifted = new ArborateInteger(op1 << op2);
                    stack.push(shifted);
                }
                break;
                
                case INTEGER_SHIFT_RIGHT_ARITHMETIC: {
                    long op2 = popInteger();
                    long op1 = popInteger();
                    ArborateInteger shifted = new ArborateInteger(op1 >> op2);
                    stack.push(shifted);
                }
                break;
                
                case INTEGER_SHIFT_RIGHT_LOGICAL: {
                    long op2 = popInteger();
                    long op1 = popInteger();
                    ArborateInteger shifted = new ArborateInteger(op1 >>> op2);
                    stack.push(shifted);
                }
                break;
                
                case INTEGER_ROTATE_LEFT: {
                    long op2 = popInteger();
                    long op1 = popInteger();
                    long rot = Long.rotateLeft(op1, (int)op2);
                    stack.push(new ArborateInteger(rot));
                }
                break;
                
                case INTEGER_ROTATE_RIGHT: {
                    long op2 = popInteger();
                    long op1 = popInteger();
                    long rot = Long.rotateRight(op1, (int)op2);
                    stack.push(new ArborateInteger(rot));
                }
                break;
                
                case INTEGER_BITWISE_AND: {
                    long op2 = popInteger();
                    long op1 = popInteger();
                    ArborateInteger result = new ArborateInteger(op1 & op2);
                    stack.push(result);
                }
                break;
                
                case INTEGER_BITWISE_OR: {
                    long op2 = popInteger();
                    long op1 = popInteger();
                    ArborateInteger result = new ArborateInteger(op1 | op2);
                    stack.push(result);
                }
                break;
                
                case INTEGER_BITWISE_NOT: {
                    long op = popInteger();
                    ArborateInteger result = new ArborateInteger(~op);
                    stack.push(result);
                }
                break;
                
                case INTEGER_BITWISE_XOR: {
                    long op2 = popInteger();
                    long op1 = popInteger();
                    ArborateInteger result = new ArborateInteger(op1 ^ op2);
                    stack.push(result);
                }
                break;
                
                case INTEGER_EQUAL: {
                    long op2 = popInteger();
                    long op1 = popInteger();
                    ArborateBoolean result = new ArborateBoolean(op1 == op2);
                    stack.push(result);
                }
                break;
                
                case INTEGER_NOT_EQUAL: {
                    long op2 = popInteger();
                    long op1 = popInteger();
                    ArborateBoolean result = new ArborateBoolean(op1 != op2);
                    stack.push(result);
                }
                break;
                
                case INTEGER_GREATER_THAN: {
                    long op2 = popInteger();
                    long op1 = popInteger();
                    ArborateBoolean result = new ArborateBoolean(op1 > op2);
                    stack.push(result);
                }
                break;
                
                case INTEGER_LESS_THAN: {
                    long op2 = popInteger();
                    long op1 = popInteger();
                    ArborateBoolean result = new ArborateBoolean(op1 < op2);
                    stack.push(result);
                }
                break;
                
                case INTEGER_GREATER_EQUAL: {
                    long op2 = popInteger();
                    long op1 = popInteger();
                    ArborateBoolean result = new ArborateBoolean(op1 >= op2);
                    stack.push(result);
                }
                break;
                
                case INTEGER_LESS_EQUAL: {
                    long op2 = popInteger();
                    long op1 = popInteger();
                    ArborateBoolean result = new ArborateBoolean(op1 <= op2);
                    stack.push(result);
                }
                break;
                
                case BOOLEAN_TO_STACK: {
                    boolean val = (boolean) nextInstruction.getData();
                    ArborateBoolean boolToPush = new ArborateBoolean(val) ;
                    stack.push(boolToPush);
                }
                break;
                
                case BOOLEAN_EQUAL: {
                    boolean op2 = popBoolean();
                    boolean op1 = popBoolean();
                    ArborateBoolean result = new ArborateBoolean(op1 == op2);
                    stack.push(result);
                }
                break;
                
                case BOOLEAN_NOT_EQUAL: {
                    boolean op2 = popBoolean();
                    boolean op1 = popBoolean();
                    ArborateBoolean result = new ArborateBoolean(op1 != op2);
                    stack.push(result);
                }
                break;
                
                case BOOLEAN_AND: {
                    boolean op2 = popBoolean();
                    boolean op1 = popBoolean();
                    ArborateBoolean result = new ArborateBoolean(op1 && op2);
                    stack.push(result);
                }
                break;
                
                case BOOLEAN_OR: {
                    boolean op2 = popBoolean();
                    boolean op1 = popBoolean();
                    ArborateBoolean result = new ArborateBoolean(op1 || op2);
                    stack.push(result);
                }
                break;
                
                case BOOLEAN_NOT: {
                    boolean op = popBoolean();
                    ArborateBoolean result = new ArborateBoolean(!op);
                    stack.push(result);
                }
                break;
                
                case BOOLEAN_XOR: {
                    boolean op2 = popBoolean();
                    boolean op1 = popBoolean();
                    ArborateBoolean result = new ArborateBoolean(op1 ^ op2);
                    stack.push(result);
                }
                break;
                
                case STRING_TO_STACK: {
                    String val = (String) nextInstruction.getData();
                    ArborateString stringToPush = new ArborateString(val) ;
                    stack.push(stringToPush);
                }
                break;
                
                case STRING_LENGTH: {
                    String op1 = popString();
                    ArborateInteger result = new ArborateInteger((long)op1.length());
                    stack.push(result);
                }
                break;
                
                case STRING_SUBSTRING: {
                    long op3 = popInteger();
                    long op2 = popInteger();
                    String op1 = popString();
                    int len = op1.length();
                    if (op2 < 0 || op3 < 0 || len < op2 ) {
                        stack.push(new ArborateString(""));
                    } else {
                        int endPos = Math.min((int)(op2 + op3), len);
                        String result = op1.substring((int)op2, endPos);
                        stack.push(new ArborateString(result));
                    }
                }
                break;
                
                case STRING_CONCATENATE: {
                    String op2 = popString();
                    String op1 = popString();
                    String result = op1.concat(op2);
                    stack.push(new ArborateString(result));
                }
                break;
                
                case STRING_FIND: {
                    int op3 = (int) popInteger();
                    String op2 = popString();
                    String op1 = popString();
                    int len = op1.length();
                    if (op3 < 0) {
                        stack.push(new ArborateInteger(-1L));
                    } else {
                        long pos = (long) op1.indexOf(op2, op3);
                        stack.push(new ArborateInteger(pos));
                    }
                }
                break;
                
                case STRING_EQUAL: {
                    String op2 = popString();
                    String op1 = popString();
                    ArborateBoolean result = new ArborateBoolean(op1.equals(op2));
                    stack.push(result);
                }
                break;
                
                case STRING_NOT_EQUAL: {
                    String op2 = popString();
                    String op1 = popString();
                    ArborateBoolean result = new ArborateBoolean(!op1.equals(op2));
                    stack.push(result);
                }
                break;
                
                
                case MAP_TO_STACK: {
                    ArborateMap val = (ArborateMap) nextInstruction.getData();
                    stack.push(val);
                }
                break;
                
                case MAP_HAS: {
                    ArborateObject op2 = popObject();
                    ArborateMap op1 = popMap();
                    ArborateBoolean result = new ArborateBoolean(op1.has(op2));
                    stack.push(result);
                }
                break;
                
                case MAP_GET: {
                    ArborateObject op2 = popObject();
                    ArborateMap op1 = popMap();
                    ArborateObject result = op1.get(op2);
                    stack.push(result);
                }
                break;
                
                case MAP_SET: {
                    ArborateObject op3 = popObject();
                    ArborateObject op2 = popObject();
                    ArborateMap op1 = popMap();
                    ArborateMap result = op1.set(op2, op3);
                    stack.push(result);
                }
                break;
                
                case MAP_CLEAR: {
                    ArborateObject op2 = popObject();
                    ArborateMap op1 = popMap();
                    ArborateMap result = op1.clear(op2);
                    stack.push(result);
                }
                break;
                
                case MAP_EQUAL: {
                    ArborateMap op2 = popMap();
                    ArborateMap op1 = popMap();
                    ArborateBoolean result = new ArborateBoolean(op1.equals(op2));
                    stack.push(result);
                }
                break;
                
                case MAP_NOT_EQUAL: {
                    ArborateMap op2 = popMap();
                    ArborateMap op1 = popMap();
                    ArborateBoolean result = new ArborateBoolean(!op1.equals(op2));
                    stack.push(result);
                }
                break;
                
                case MAP_SIZE: {
                    ArborateMap op1 = popMap();
                    ArborateInteger result = new ArborateInteger((long)op1.getSize());
                    stack.push(result);
                }
                break;
                
                case LIST_EMPTY_TO_STACK: {
                    ArborateList newList = new ArborateList();
                    stack.push(newList);
                }
                
                break;
                
                case LIST_GET_POSITION: {
                    int op2 = (int) popInteger();
                    ArborateList op1 = popList();
                    ArborateObject result = op1.getPosition(op2);
                    stack.push(result);
                }
                break;
                
                case LIST_SET_POSITION: {
                    ArborateObject op3 = popObject();
                    int op2 = (int) popInteger();
                    ArborateList op1 = popList();
                    ArborateList result = op1.setPosition(op2, op3);
                    stack.push(result);
                }
                break;
                
                case LIST_INSERT_POSITION: {
                    ArborateObject op3 = popObject();
                    int op2 = (int) popInteger();
                    ArborateList op1 = popList();
                    ArborateList result = op1.insertPosition(op2, op3);
                    stack.push(result);
                }
                break;
                
                case LIST_REMOVE_POSITION: {
                    int op2 = (int) popInteger();
                    ArborateList op1 = popList();
                    ArborateList result = op1.removePosition(op2);
                    stack.push(result);
                }
                break;
                
//                case LIST_EQUAL: {
//                    
//                }
//                break;
//                
//                case LIST_NOT_EQUAL: {
//                    
//                }
//                break;
                
                case LIST_SIZE: {
                    ArborateList op1 = popList();
                    long result = op1.getSize();
                    stack.push(new ArborateInteger(result));
                }
                break;
                
                
                case CALL_FUNCTION: {
                    long functionIndex = (Long) nextInstruction.getData();
                    if (funcDefs.size() <= functionIndex) {
                        throw new VirtualMachineExecutionException("Invalid function index");
                    }
                    currentFunction.incrementNextInstructionNumber();
                    functionStack.push(currentFunction);

                    FunctionDefinition definition = funcDefs.get((int)functionIndex);
                    currentFunction = getFunctionInstance(definition);
                    incrementInstruction = false;
                }
                break;
                
                case EXIT_FUNCTION: {
                    if (functionStack.empty()) {
                        stillProcessing = false;
                    } else {
                        currentFunction = functionStack.pop();
                    }
                    incrementInstruction = false;
                }
                break;
                
                case BRANCH:  {
                    incrementInstruction = false;
                    performBranch(nextInstruction);
                }
                break;
                
                case BRANCH_TRUE: {
                    boolean flag = popBoolean();
                    if (flag) {
                        incrementInstruction = false;
                        performBranch(nextInstruction);
                    }
                }
                break;
                
                case BRANCH_FALSE: {
                    boolean flag = popBoolean();
                    if (!flag) {
                        incrementInstruction = false;
                        performBranch(nextInstruction);
                    }
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

    private void performBranch(Instruction nextInstruction) throws VirtualMachineExecutionException {
        long newNextInstruction = (Long)(nextInstruction.getData());
        if (currentFunction.getDefinition().getCode().size() <= newNextInstruction) {
            throw new VirtualMachineExecutionException("Branch identified invalid instruction nubmer.");
        }
        currentFunction.setNextInstructionNumber((int) newNextInstruction);
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
                    if (args[argPos] instanceof ArborateInteger) {
                        stack.push((ArborateInteger)args[argPos]); 
                    } else if (args[argPos] instanceof Long) {
                        long val = (Long) args[argPos];
                        stack.push(new ArborateInteger(val)); // ENHANCMENT accept shorter types
                    } else {
                        throw new IllegalArgumentException("Argument is not appropriate type for method.");
                    }
                    break;
                    
                case STRING:
                    if (args[argPos] instanceof ArborateString) {
                        stack.push((ArborateString)args[argPos]); 
                    } else if (args[argPos] instanceof String) {
                        String val = (String) args[argPos];
                        stack.push(new ArborateString(val)); // ENHANCMENT accept shorter types
                    } else {
                        throw new IllegalArgumentException("Argument is not appropriate type for method.");
                    }
                    break;
                    
                case BOOLEAN:
                    if (args[argPos] instanceof ArborateBoolean) {
                        stack.push((ArborateBoolean)args[argPos]); 
                    } else if (args[argPos] instanceof Boolean) {
                        boolean val = (Boolean) args[argPos];
                        stack.push(new ArborateBoolean(val)); // ENHANCMENT accept shorter types
                    } else {
                        throw new IllegalArgumentException("Argument is not appropriate type for method.");
                    }
                    break;
                    
                case BYTE:
                    if (args[argPos] instanceof ArborateByte) {
                        stack.push((ArborateByte)args[argPos]); 
                    } else if (args[argPos] instanceof Byte) {
                        byte val = (Byte) args[argPos];
                        stack.push(new ArborateByte(val)); // ENHANCMENT accept shorter types
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
                    
                case OBJECT:
                    if (currParam == null || currParam instanceof ArborateObject) {
                        returnValue.add(currParam);
                    } else {
                        throw new IllegalArgumentException("Stack item is not appropriate type for function result.");
                    }
                    break;
                    
                    
                    
                case INTEGER:
                    if (currParam instanceof ArborateInteger) {
                        returnValue.add(currParam);
                    } else {
                        throw new IllegalArgumentException("Stack item is not appropriate type for function result.");
                    }
                    break;
                    
                case STRING:
                    if (currParam instanceof ArborateString) {
                        returnValue.add(currParam);
                    } else {
                        throw new IllegalArgumentException("Stack item is not appropriate type for function result.");
                    }
                    break;
                    
                    
                case BOOLEAN:
                    if (currParam instanceof ArborateBoolean) {
                        returnValue.add(currParam);
                    } else {
                        throw new IllegalArgumentException("Stack item is not appropriate type for function result.");
                    }
                    break;
                    
                    
                case BYTE:
                    if (currParam instanceof ArborateByte) {
                        returnValue.add(currParam);
                    } else {
                        throw new IllegalArgumentException("Stack item is not appropriate type for function result.");
                    }
                    break;
                
                case DICTIONARY:
                    if (currParam instanceof ArborateMap) {
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

    private ArborateObject popObject() {
        ArborateObject stackItem = stack.pop();
        return stackItem;
    }

    private long popInteger() {
        ArborateObject stackItem = stack.pop();
        if (!(stackItem instanceof ArborateInteger)) {
            throw new VirtualMachineExecutionException("Stack item was not expected type (integer)");
        }
        return ((ArborateInteger)stackItem).getValue();
    }

    private boolean popBoolean() {
        ArborateObject stackItem = stack.pop();
        if (!(stackItem instanceof ArborateBoolean)) {
            throw new VirtualMachineExecutionException("Stack item was not expected type (boolean)");
        }
        return ((ArborateBoolean)stackItem).getValue();
    }
    
    private String popString() {
        ArborateObject stackItem = stack.pop();
        if (!(stackItem instanceof ArborateString)) {
            throw new VirtualMachineExecutionException("Stack item was not expected type (string)");
        }
        return ((ArborateString)stackItem).getValue();
    }

    private ArborateMap popMap() {
        ArborateObject stackItem = stack.pop();
        if (!(stackItem instanceof ArborateMap)) {
            throw new VirtualMachineExecutionException("Stack item was not expected type (map)");
        }
        return (ArborateMap)stackItem;
    }

    private ArborateList popList() {
        ArborateObject stackItem = stack.pop();
        if (!(stackItem instanceof ArborateList)) {
            throw new VirtualMachineExecutionException("Stack item was not expected type (list)");
        }
        return (ArborateList)stackItem;
    }
}
