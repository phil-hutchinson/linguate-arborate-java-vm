/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.arborate.vm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Phil Hutchinson
 */
public class VirtualMachineBaselineTest {
    
    public VirtualMachineBaselineTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testVariable() {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 3L));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 5L));
        instructions.add(new Instruction(InstructionCode.INTEGER_ADD));
        instructions.add(new Instruction(InstructionCode.STACK_TO_VARIABLE, 0L));

        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 1L));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 2L));
        instructions.add(new Instruction(InstructionCode.INTEGER_ADD));

        instructions.add(new Instruction(InstructionCode.VARIABLE_TO_STACK, 0L));
        
        instructions.add(new Instruction(InstructionCode.INTEGER_SUBTRACT));
        
        List<BaseType> inParams = new ArrayList<>();
        List<BaseType> outParams = Arrays.asList(BaseType.INTEGER);
        FunctionDefinition mainFunc = new FunctionDefinition(instructions, 1, inParams, outParams);
        List<FunctionDefinition> allFuncs = new ArrayList<>();
        allFuncs.add(mainFunc);
        
        VirtualMachine virtualMachine = new VirtualMachine(allFuncs);
        
        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger actual = (ArborateInteger) actualValue.get(0);
        assertEquals(-5L, actual.getValue());
    }
    
    @Test
    public void testFunctionCall() {
        ArrayList<Instruction> mainInstructions = new ArrayList<>();
        mainInstructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 20L));
        mainInstructions.add(new Instruction(InstructionCode.CALL_FUNCTION, 1L));
        mainInstructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 30L));
        mainInstructions.add(new Instruction(InstructionCode.CALL_FUNCTION, 1L));
        mainInstructions.add(new Instruction(InstructionCode.INTEGER_ADD));
        List<BaseType> mainInParams = new ArrayList<>();
        List<BaseType> mainOutParams = Arrays.asList(BaseType.INTEGER);
        FunctionDefinition mainFunc = new FunctionDefinition(mainInstructions, 0, mainInParams, mainOutParams);
        
        ArrayList<Instruction> subInstructions = new ArrayList<>();
        subInstructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 5L));
        subInstructions.add(new Instruction(InstructionCode.INTEGER_SUBTRACT));
        List<BaseType> subInParams = new ArrayList<>();
        List<BaseType> subOutParams = Arrays.asList(BaseType.INTEGER);
        FunctionDefinition subFunc = new FunctionDefinition(subInstructions, 0, subInParams, subOutParams);
        
        List<FunctionDefinition> allFuncs = new ArrayList<>();
        allFuncs.add(mainFunc);
        allFuncs.add(subFunc);
        
        VirtualMachine virtualMachine = new VirtualMachine(allFuncs);
        
        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger actual = (ArborateInteger) actualValue.get(0);
        assertEquals(40L, actual.getValue()); 
    }

    @Test
    public void testOneInParam() {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 20L));
        instructions.add(new Instruction(InstructionCode.INTEGER_ADD));
        List<BaseType> inParams = Arrays.asList(BaseType.INTEGER);
        List<BaseType> outParams = Arrays.asList(BaseType.INTEGER);
        FunctionDefinition func = new FunctionDefinition(instructions, 0, inParams, outParams);
        
        List<FunctionDefinition> allFuncs = new ArrayList<>();
        allFuncs.add(func);
        
        VirtualMachine virtualMachine = new VirtualMachine(allFuncs);
        
        List<Object> actualValue = virtualMachine.execute(45L);
        assertEquals(1, actualValue.size());
        ArborateInteger actual = (ArborateInteger) actualValue.get(0);
        assertEquals(65L, actual.getValue());
    }

    @Test
    public void testMultiInParam() {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Instruction(InstructionCode.INTEGER_ADD));
        instructions.add(new Instruction(InstructionCode.INTEGER_SUBTRACT));
        List<BaseType> inParams = Arrays.asList(BaseType.INTEGER, BaseType.INTEGER, BaseType.INTEGER);
        List<BaseType> outParams = Arrays.asList(BaseType.INTEGER);
        FunctionDefinition func = new FunctionDefinition(instructions, 0, inParams, outParams);
        
        List<FunctionDefinition> allFuncs = new ArrayList<>();
        allFuncs.add(func);
        
        VirtualMachine virtualMachine = new VirtualMachine(allFuncs);
        
        List<Object> actualValue = virtualMachine.execute(15L, 50L, 100L);
        assertEquals(1, actualValue.size());
        ArborateInteger actual = (ArborateInteger) actualValue.get(0);
        assertEquals(-135L, actual.getValue());
    }

    @Test
    public void testMultiOutParam() {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Instruction(InstructionCode.STACK_TO_VARIABLE, 0L));
        instructions.add(new Instruction(InstructionCode.STACK_TO_VARIABLE, 1L));
        instructions.add(new Instruction(InstructionCode.VARIABLE_TO_STACK, 1L));
        instructions.add(new Instruction(InstructionCode.VARIABLE_TO_STACK, 0L));
        instructions.add(new Instruction(InstructionCode.INTEGER_ADD));
        instructions.add(new Instruction(InstructionCode.VARIABLE_TO_STACK, 1L));
        instructions.add(new Instruction(InstructionCode.VARIABLE_TO_STACK, 0L));
        instructions.add(new Instruction(InstructionCode.INTEGER_SUBTRACT));
        
        List<BaseType> inParams = Arrays.asList(BaseType.INTEGER, BaseType.INTEGER);
        List<BaseType> outParams = Arrays.asList(BaseType.INTEGER, BaseType.INTEGER);
        FunctionDefinition func = new FunctionDefinition(instructions, 2, inParams, outParams);
        
        List<FunctionDefinition> allFuncs = new ArrayList<>();
        allFuncs.add(func);
        
        VirtualMachine virtualMachine = new VirtualMachine(allFuncs);
        
        List<Object> actualValue = virtualMachine.execute(25L, 3L);
        assertEquals(2, actualValue.size());
        ArborateInteger actual1 = (ArborateInteger) actualValue.get(0);
        ArborateInteger actual2 = (ArborateInteger) actualValue.get(1);
        assertEquals(28L, actual1.getValue());
        assertEquals(22L, actual2.getValue());
    }

    @Test
    public void testMultiOutInSub() {
        ArrayList<Instruction> mainInstructions = new ArrayList<>();
        mainInstructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 20L));
        mainInstructions.add(new Instruction(InstructionCode.CALL_FUNCTION, 1L));
        mainInstructions.add(new Instruction(InstructionCode.INTEGER_SUBTRACT));
        List<BaseType> mainInParams = new ArrayList<>();
        List<BaseType> mainOutParams = Arrays.asList(BaseType.INTEGER);
        FunctionDefinition mainFunc = new FunctionDefinition(mainInstructions, 0, mainInParams, mainOutParams);
        
        ArrayList<Instruction> subInstructions = new ArrayList<>();
        subInstructions.add(new Instruction(InstructionCode.STACK_TO_VARIABLE, 0L));
        subInstructions.add(new Instruction(InstructionCode.VARIABLE_TO_STACK, 0L));
        subInstructions.add(new Instruction(InstructionCode.VARIABLE_TO_STACK, 0L));
        subInstructions.add(new Instruction(InstructionCode.INTEGER_ADD));
        subInstructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 7L));
        subInstructions.add(new Instruction(InstructionCode.VARIABLE_TO_STACK, 0L));
        subInstructions.add(new Instruction(InstructionCode.INTEGER_ADD));
        List<BaseType> subInParams = new ArrayList<>();
        List<BaseType> subOutParams = Arrays.asList(BaseType.INTEGER, BaseType.INTEGER);
        FunctionDefinition subFunc = new FunctionDefinition(subInstructions, 1, subInParams, subOutParams);
        
        List<FunctionDefinition> allFuncs = new ArrayList<>();
        allFuncs.add(mainFunc);
        allFuncs.add(subFunc);
        
        VirtualMachine virtualMachine = new VirtualMachine(allFuncs);
        
        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger actual = (ArborateInteger) actualValue.get(0);
        assertEquals(13L, actual.getValue());
    }

    @Test
    public void testExitFunction() {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 515L));
        instructions.add(new Instruction(InstructionCode.EXIT_FUNCTION));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 1515L));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 2515L));
        instructions.add(new Instruction(InstructionCode.INTEGER_ADD));
        instructions.add(new Instruction(InstructionCode.INTEGER_ADD));
        
        List<BaseType> inParams = Arrays.asList();
        List<BaseType> outParams = Arrays.asList(BaseType.INTEGER);
        FunctionDefinition func = new FunctionDefinition(instructions, 0, inParams, outParams);
        
        List<FunctionDefinition> allFuncs = new ArrayList<>();
        allFuncs.add(func);
        
        VirtualMachine virtualMachine = new VirtualMachine(allFuncs);
        
        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger actual = (ArborateInteger) actualValue.get(0);
        assertEquals(515L, actual.getValue());
    }

    @Test
    public void testExitFunctionInsideFunction() {
        ArrayList<Instruction> mainInstructions = new ArrayList<>();
        mainInstructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 30L));
        mainInstructions.add(new Instruction(InstructionCode.CALL_FUNCTION, 1L));
        List<BaseType> mainInParams = new ArrayList<>();
        List<BaseType> mainOutParams = Arrays.asList(BaseType.INTEGER);
        FunctionDefinition mainFunc = new FunctionDefinition(mainInstructions, 0, mainInParams, mainOutParams);
        
        ArrayList<Instruction> subInstructions = new ArrayList<>();
        subInstructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 17L));
        subInstructions.add(new Instruction(InstructionCode.INTEGER_ADD));
        subInstructions.add(new Instruction(InstructionCode.EXIT_FUNCTION));
        subInstructions.add(new Instruction(InstructionCode.STACK_TO_VARIABLE, 0));
        subInstructions.add(new Instruction(InstructionCode.VARIABLE_TO_STACK, 0));
        subInstructions.add(new Instruction(InstructionCode.VARIABLE_TO_STACK, 0));
        subInstructions.add(new Instruction(InstructionCode.INTEGER_ADD));
        subInstructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 7L));
        subInstructions.add(new Instruction(InstructionCode.VARIABLE_TO_STACK, 0));
        subInstructions.add(new Instruction(InstructionCode.INTEGER_ADD));
        List<BaseType> subInParams = new ArrayList<>();
        List<BaseType> subOutParams = Arrays.asList(BaseType.INTEGER, BaseType.INTEGER);
        FunctionDefinition subFunc = new FunctionDefinition(subInstructions, 1, subInParams, subOutParams);
        
        List<FunctionDefinition> allFuncs = new ArrayList<>();
        allFuncs.add(mainFunc);
        allFuncs.add(subFunc);
        
        VirtualMachine virtualMachine = new VirtualMachine(allFuncs);
        
        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger actual = (ArborateInteger) actualValue.get(0);
        assertEquals(47L, actual.getValue());
    }

    @Test
    public void testBranch() {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 100L));
        instructions.add(new Instruction(InstructionCode.BRANCH, 6L));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 1515L));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 2515L));
        instructions.add(new Instruction(InstructionCode.INTEGER_ADD));
        instructions.add(new Instruction(InstructionCode.INTEGER_SUBTRACT));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 200L));
        instructions.add(new Instruction(InstructionCode.INTEGER_ADD));
        
        List<BaseType> inParams = Arrays.asList();
        List<BaseType> outParams = Arrays.asList(BaseType.INTEGER);
        FunctionDefinition func = new FunctionDefinition(instructions, 0, inParams, outParams);
        
        List<FunctionDefinition> allFuncs = new ArrayList<>();
        allFuncs.add(func);
        
        VirtualMachine virtualMachine = new VirtualMachine(allFuncs);
        
        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger actual = (ArborateInteger) actualValue.get(0);
        assertEquals(300L, actual.getValue());
    }

    @Test
    public void testBranchTrueWhenTrue() {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 200L));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 100L));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 50L));
        instructions.add(new Instruction(InstructionCode.INTEGER_GREATER_THAN));
        instructions.add(new Instruction(InstructionCode.BRANCH_TRUE, 8L));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 22L));
        instructions.add(new Instruction(InstructionCode.INTEGER_ADD));
        instructions.add(new Instruction(InstructionCode.EXIT_FUNCTION));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 200L));
        instructions.add(new Instruction(InstructionCode.INTEGER_ADD));
        
        List<BaseType> inParams = Arrays.asList();
        List<BaseType> outParams = Arrays.asList(BaseType.INTEGER);
        FunctionDefinition func = new FunctionDefinition(instructions, 0, inParams, outParams);
        
        List<FunctionDefinition> allFuncs = new ArrayList<>();
        allFuncs.add(func);
        
        VirtualMachine virtualMachine = new VirtualMachine(allFuncs);
        
        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger actual = (ArborateInteger) actualValue.get(0);
        assertEquals(400L, actual.getValue());
    }

    @Test
    public void testBranchTrueWhenFalse() {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 200L));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 50L));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 100L));
        instructions.add(new Instruction(InstructionCode.INTEGER_GREATER_THAN));
        instructions.add(new Instruction(InstructionCode.BRANCH_TRUE, 8L));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 22L));
        instructions.add(new Instruction(InstructionCode.INTEGER_ADD));
        instructions.add(new Instruction(InstructionCode.EXIT_FUNCTION));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 200L));
        instructions.add(new Instruction(InstructionCode.INTEGER_ADD));
        
        List<BaseType> inParams = Arrays.asList();
        List<BaseType> outParams = Arrays.asList(BaseType.INTEGER);
        FunctionDefinition func = new FunctionDefinition(instructions, 0, inParams, outParams);
        
        List<FunctionDefinition> allFuncs = new ArrayList<>();
        allFuncs.add(func);
        
        VirtualMachine virtualMachine = new VirtualMachine(allFuncs);
        
        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger actual = (ArborateInteger) actualValue.get(0);
        assertEquals(222L, actual.getValue());
    }

    @Test
    public void testBranchFalseWhenTrue() {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 200L));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 100L));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 50L));
        instructions.add(new Instruction(InstructionCode.INTEGER_GREATER_THAN));
        instructions.add(new Instruction(InstructionCode.BRANCH_FALSE, 8L));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 22L));
        instructions.add(new Instruction(InstructionCode.INTEGER_ADD));
        instructions.add(new Instruction(InstructionCode.EXIT_FUNCTION));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 200L));
        instructions.add(new Instruction(InstructionCode.INTEGER_ADD));
        
        List<BaseType> inParams = Arrays.asList();
        List<BaseType> outParams = Arrays.asList(BaseType.INTEGER);
        FunctionDefinition func = new FunctionDefinition(instructions, 0, inParams, outParams);
        
        List<FunctionDefinition> allFuncs = new ArrayList<>();
        allFuncs.add(func);
        
        VirtualMachine virtualMachine = new VirtualMachine(allFuncs);
        
        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger actual = (ArborateInteger) actualValue.get(0);
        assertEquals(222L, actual.getValue());
    }

    @Test
    public void testBranchFalseWhenFalse() {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 200L));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 50L));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 100L));
        instructions.add(new Instruction(InstructionCode.INTEGER_GREATER_THAN));
        instructions.add(new Instruction(InstructionCode.BRANCH_FALSE, 8L));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 22L));
        instructions.add(new Instruction(InstructionCode.INTEGER_ADD));
        instructions.add(new Instruction(InstructionCode.EXIT_FUNCTION));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 200L));
        instructions.add(new Instruction(InstructionCode.INTEGER_ADD));
        
        List<BaseType> inParams = Arrays.asList();
        List<BaseType> outParams = Arrays.asList(BaseType.INTEGER);
        FunctionDefinition func = new FunctionDefinition(instructions, 0, inParams, outParams);
        
        List<FunctionDefinition> allFuncs = new ArrayList<>();
        allFuncs.add(func);
        
        VirtualMachine virtualMachine = new VirtualMachine(allFuncs);
        
        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger actual = (ArborateInteger) actualValue.get(0);
        assertEquals(400L, actual.getValue());
    }
    
    @Test
    public void testRecursiveWithConditionalBranch() {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Instruction(InstructionCode.STACK_TO_VARIABLE, 1L));
        instructions.add(new Instruction(InstructionCode.STACK_TO_VARIABLE, 0L));
        instructions.add(new Instruction(InstructionCode.VARIABLE_TO_STACK, 1L));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK , 0L));
        instructions.add(new Instruction(InstructionCode.INTEGER_EQUAL));
        instructions.add(new Instruction(InstructionCode.BRANCH_FALSE, 8L));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK , 1L));
        instructions.add(new Instruction(InstructionCode.EXIT_FUNCTION));
        instructions.add(new Instruction(InstructionCode.VARIABLE_TO_STACK, 0L));
        instructions.add(new Instruction(InstructionCode.VARIABLE_TO_STACK, 0L));
        instructions.add(new Instruction(InstructionCode.VARIABLE_TO_STACK, 1L));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK , 1L));
        instructions.add(new Instruction(InstructionCode.INTEGER_SUBTRACT , 1L));
        instructions.add(new Instruction(InstructionCode.CALL_FUNCTION, 0L));
        instructions.add(new Instruction(InstructionCode.INTEGER_MULTIPLY));
        
        List<BaseType> inParams = Arrays.asList(BaseType.INTEGER, BaseType.INTEGER);
        List<BaseType> outParams = Arrays.asList(BaseType.INTEGER);
        FunctionDefinition func = new FunctionDefinition(instructions, 2, inParams, outParams);
        
        List<FunctionDefinition> allFuncs = new ArrayList<>();
        allFuncs.add(func);
        
        VirtualMachine virtualMachine = new VirtualMachine(allFuncs);
        
        List<Object> actualValue = virtualMachine.execute(3L, 5L);
        assertEquals(1, actualValue.size());
        ArborateInteger actual = (ArborateInteger) actualValue.get(0);
        assertEquals(243L, actual.getValue());
    }
}
