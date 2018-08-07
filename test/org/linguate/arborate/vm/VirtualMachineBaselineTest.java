/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.arborate.vm;

import java.util.ArrayList;
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
    public void testIntegerAdd() {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 12L));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 17L));
        instructions.add(new Instruction(InstructionCode.INTEGER_ADD));
        
        FunctionDefinition mainFunc = new FunctionDefinition(instructions, 1, 0, 0);
        List<FunctionDefinition> allFuncs = new ArrayList<>();
        allFuncs.add(mainFunc);
        
        VirtualMachine virtualMachine = new VirtualMachine(allFuncs);
        
        Object actualValue = virtualMachine.execute();
        assertEquals(actualValue, 29L);
    }
    
    @Test
    public void testIntegerSubtract() {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 15L));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 5L));
        instructions.add(new Instruction(InstructionCode.INTEGER_SUBTRACT));
        
        FunctionDefinition mainFunc = new FunctionDefinition(instructions, 1, 0, 0);
        List<FunctionDefinition> allFuncs = new ArrayList<>();
        allFuncs.add(mainFunc);
        
        VirtualMachine virtualMachine = new VirtualMachine(allFuncs);
        
        Object actualValue = virtualMachine.execute();
        assertEquals(actualValue, 10L);
    }
    
    @Test
    public void testVariable() {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 3L));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 5L));
        instructions.add(new Instruction(InstructionCode.INTEGER_ADD));
        instructions.add(new Instruction(InstructionCode.STACK_TO_VARIABLE, 0));

        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 1L));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 2L));
        instructions.add(new Instruction(InstructionCode.INTEGER_ADD));

        instructions.add(new Instruction(InstructionCode.VARIABLE_TO_STACK, 0));
        
        instructions.add(new Instruction(InstructionCode.INTEGER_SUBTRACT));
        
        FunctionDefinition mainFunc = new FunctionDefinition(instructions, 1, 0, 1);
        List<FunctionDefinition> allFuncs = new ArrayList<>();
        allFuncs.add(mainFunc);
        
        VirtualMachine virtualMachine = new VirtualMachine(allFuncs);
        
        Object actualValue = virtualMachine.execute();
        assertEquals(actualValue, -5L);
    }
    
    @Test
    public void testFunctionCall() {
        ArrayList<Instruction> mainInstructions = new ArrayList<>();
        mainInstructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 20L));
        mainInstructions.add(new Instruction(InstructionCode.CALL_FUNCTION, 1));
        mainInstructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 30L));
        mainInstructions.add(new Instruction(InstructionCode.CALL_FUNCTION, 1));
        mainInstructions.add(new Instruction(InstructionCode.INTEGER_ADD));
        FunctionDefinition mainFunc = new FunctionDefinition(mainInstructions, 1, 0, 1);
        
        ArrayList<Instruction> subInstructions = new ArrayList<>();
        subInstructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 5L));
        subInstructions.add(new Instruction(InstructionCode.INTEGER_SUBTRACT));
        FunctionDefinition subFunc = new FunctionDefinition(subInstructions, 1, 0, 1);
        
        List<FunctionDefinition> allFuncs = new ArrayList<>();
        allFuncs.add(mainFunc);
        allFuncs.add(subFunc);
        
        VirtualMachine virtualMachine = new VirtualMachine(allFuncs);
        
        Object actualValue = virtualMachine.execute();
        assertEquals(actualValue, 40L);
    }
}
