/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.arborate.vm;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
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
    public void testSomeMethod() {
        ArrayList<Instruction> instructions = new ArrayList<Instruction>();
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 12L));
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, 17L));
        instructions.add(new Instruction(InstructionCode.INTEGER_ADD));
        instructions.add(new Instruction(InstructionCode.POP_STACK_TO_VARIABLE, 0));
        instructions.add(new Instruction(InstructionCode.PUSH_VARIABLE_TO_STACK, 0));
        
        FunctionDefinition mainFunc = new FunctionDefinition(instructions, 1, 0, 1);
        List<FunctionDefinition> allFuncs = new ArrayList<FunctionDefinition>();
        allFuncs.add(mainFunc);
        
        Stack<VirtualMachineStackItem> stack = new Stack<VirtualMachineStackItem>();
        
        ArrayList<VariableInstance> variables = new ArrayList<VariableInstance>();
        variables.add(new VariableInstance());
        
        VirtualMachine virtualMachine = new VirtualMachine(allFuncs, stack);
        
        virtualMachine.execute();
    }
    
}
