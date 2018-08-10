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
public class VirtualMachineIntegerTest {
    
    public VirtualMachineIntegerTest() {
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
    public void testIntegerConstToStack() {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, -534L));
        
        List<BaseType> inParams = new ArrayList();
        List<BaseType> outParams = Arrays.asList(BaseType.INTEGER);
        FunctionDefinition mainFunc = new FunctionDefinition(instructions, 0, inParams, outParams);
        List<FunctionDefinition> allFuncs = new ArrayList<>();
        allFuncs.add(mainFunc);
        
        VirtualMachine virtualMachine = new VirtualMachine(allFuncs);
        
        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        assertEquals(-534L, actualValue.get(0));
    }
    
    @Test
    public void testIntegerAdd() {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 12L));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 17L));
        instructions.add(new Instruction(InstructionCode.INTEGER_ADD));
        
        List<BaseType> inParams = new ArrayList();
        List<BaseType> outParams = Arrays.asList(BaseType.INTEGER);
        FunctionDefinition mainFunc = new FunctionDefinition(instructions, 0, inParams, outParams);
        List<FunctionDefinition> allFuncs = new ArrayList<>();
        allFuncs.add(mainFunc);
        
        VirtualMachine virtualMachine = new VirtualMachine(allFuncs);
        
        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        assertEquals(29L, actualValue.get(0));
    }
    
    @Test
    public void testIntegerSubtract() {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 15L));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 5L));
        instructions.add(new Instruction(InstructionCode.INTEGER_SUBTRACT));
        
        List<BaseType> inParams = new ArrayList();
        List<BaseType> outParams = Arrays.asList(BaseType.INTEGER);
        FunctionDefinition mainFunc = new FunctionDefinition(instructions, 0, inParams, outParams);
        List<FunctionDefinition> allFuncs = new ArrayList<>();
        allFuncs.add(mainFunc);
        
        VirtualMachine virtualMachine = new VirtualMachine(allFuncs);
        
        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        assertEquals(10L, actualValue.get(0));
    }
    
    @Test
    public void testIntegerMultiply() {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 22L));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, -7L));
        instructions.add(new Instruction(InstructionCode.INTEGER_MULTIPLY));
        
        List<BaseType> inParams = new ArrayList();
        List<BaseType> outParams = Arrays.asList(BaseType.INTEGER);
        FunctionDefinition mainFunc = new FunctionDefinition(instructions, 0, inParams, outParams);
        List<FunctionDefinition> allFuncs = new ArrayList<>();
        allFuncs.add(mainFunc);
        
        VirtualMachine virtualMachine = new VirtualMachine(allFuncs);
        
        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        assertEquals(-154L, actualValue.get(0));
    }
    
    @Test
    public void testIntegerDivide() {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 42L));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 5L));
        instructions.add(new Instruction(InstructionCode.INTEGER_DIVIDE));
        
        List<BaseType> inParams = new ArrayList();
        List<BaseType> outParams = Arrays.asList(BaseType.INTEGER);
        FunctionDefinition mainFunc = new FunctionDefinition(instructions, 0, inParams, outParams);
        List<FunctionDefinition> allFuncs = new ArrayList<>();
        allFuncs.add(mainFunc);
        
        VirtualMachine virtualMachine = new VirtualMachine(allFuncs);
        
        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        assertEquals(8L, actualValue.get(0));
    }
    
    @Test
    public void testIntegerModulus() {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 202L));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 10L));
        instructions.add(new Instruction(InstructionCode.INTEGER_MODULUS));
        
        List<BaseType> inParams = new ArrayList();
        List<BaseType> outParams = Arrays.asList(BaseType.INTEGER);
        FunctionDefinition mainFunc = new FunctionDefinition(instructions, 0, inParams, outParams);
        List<FunctionDefinition> allFuncs = new ArrayList<>();
        allFuncs.add(mainFunc);
        
        VirtualMachine virtualMachine = new VirtualMachine(allFuncs);
        
        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        assertEquals(2L, actualValue.get(0));
    }
}
