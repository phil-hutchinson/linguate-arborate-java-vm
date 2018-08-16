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
public class VirtualMachineBooleanTest {
    
    public VirtualMachineBooleanTest() {
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

    private boolean simpleBooleanTest(List<Instruction> instructions) {
        FunctionDefinition mainFunc = new FunctionDefinition(instructions, 0, Arrays.asList(), Arrays.asList(BaseType.BOOLEAN));
        
        VirtualMachine virtualMachine = new VirtualMachine(Arrays.asList(mainFunc));
        
        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateBoolean result = (ArborateBoolean) actualValue.get(0);
        return result.getValue();
    }
    
    @Test
    public void testBooleanConstToStack() {
        boolean actualValue = simpleBooleanTest(Arrays.asList(
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true)
        ));
        
        assertEquals(true, actualValue);
    }

    @Test
    public void testBooleanEqual_f_f() {
        boolean actualValue = simpleBooleanTest(Arrays.asList(
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, false),
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, false),
            new Instruction(InstructionCode.BOOLEAN_EQUAL)
        ));
        
        assertEquals(true, actualValue);
    }

    @Test
    public void testBooleanEqual_f_t() {
        boolean actualValue = simpleBooleanTest(Arrays.asList(
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, false),
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.BOOLEAN_EQUAL)
        ));
        
        assertEquals(false, actualValue);
    }

    @Test
    public void testBooleanEqual_t_f() {
        boolean actualValue = simpleBooleanTest(Arrays.asList(
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, false),
            new Instruction(InstructionCode.BOOLEAN_EQUAL)
        ));
        
        assertEquals(false, actualValue);
    }

    @Test
    public void testBooleanEqual_t_t() {
        boolean actualValue = simpleBooleanTest(Arrays.asList(
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.BOOLEAN_EQUAL)
        ));
        
        assertEquals(true, actualValue);
    }

    @Test
    public void testBooleanNotEqual_f_f() {
        boolean actualValue = simpleBooleanTest(Arrays.asList(
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, false),
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, false),
            new Instruction(InstructionCode.BOOLEAN_NOT_EQUAL)
        ));
        
        assertEquals(false, actualValue);
    }

    @Test
    public void testBooleanNotEqual_f_t() {
        boolean actualValue = simpleBooleanTest(Arrays.asList(
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, false),
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.BOOLEAN_NOT_EQUAL)
        ));
        
        assertEquals(true, actualValue);
    }

    @Test
    public void testBooleanNotEqual_t_f() {
        boolean actualValue = simpleBooleanTest(Arrays.asList(
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, false),
            new Instruction(InstructionCode.BOOLEAN_NOT_EQUAL)
        ));
        
        assertEquals(true, actualValue);
    }

    @Test
    public void testBooleanNotEqual_t_t() {
        boolean actualValue = simpleBooleanTest(Arrays.asList(
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.BOOLEAN_NOT_EQUAL)
        ));
        
        assertEquals(false, actualValue);
    }

    @Test
    public void testBooleanAnd_f_f() {
        boolean actualValue = simpleBooleanTest(Arrays.asList(
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, false),
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, false),
            new Instruction(InstructionCode.BOOLEAN_AND)
        ));
        
        assertEquals(false, actualValue);
    }

    @Test
    public void testBooleanAnd_f_t() {
        boolean actualValue = simpleBooleanTest(Arrays.asList(
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, false),
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.BOOLEAN_AND)
        ));
        
        assertEquals(false, actualValue);
    }

    @Test
    public void testBooleanAnd_t_f() {
        boolean actualValue = simpleBooleanTest(Arrays.asList(
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, false),
            new Instruction(InstructionCode.BOOLEAN_AND)
        ));
        
        assertEquals(false, actualValue);
    }

    @Test
    public void testBooleanAnd_t_t() {
        boolean actualValue = simpleBooleanTest(Arrays.asList(
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.BOOLEAN_AND)
        ));
        
        assertEquals(true, actualValue);
    }

    @Test
    public void testBooleanOr_f_f() {
        boolean actualValue = simpleBooleanTest(Arrays.asList(
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, false),
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, false),
            new Instruction(InstructionCode.BOOLEAN_OR)
        ));
        
        assertEquals(false, actualValue);
    }

    @Test
    public void testBooleanOr_f_t() {
        boolean actualValue = simpleBooleanTest(Arrays.asList(
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, false),
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.BOOLEAN_OR)
        ));
        
        assertEquals(true, actualValue);
    }

    @Test
    public void testBooleanOr_t_f() {
        boolean actualValue = simpleBooleanTest(Arrays.asList(
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, false),
            new Instruction(InstructionCode.BOOLEAN_OR)
        ));
        
        assertEquals(true, actualValue);
    }

    @Test
    public void testBooleanOr_t_t() {
        boolean actualValue = simpleBooleanTest(Arrays.asList(
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.BOOLEAN_OR)
        ));
        
        assertEquals(true, actualValue);
    }

    @Test
    public void testBooleanNot_f() {
        boolean actualValue = simpleBooleanTest(Arrays.asList(
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, false),
            new Instruction(InstructionCode.BOOLEAN_NOT)
        ));
        
        assertEquals(true, actualValue);
    }

    @Test
    public void testBooleanNot_t() {
        boolean actualValue = simpleBooleanTest(Arrays.asList(
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.BOOLEAN_NOT)
        ));
        
        assertEquals(false, actualValue);
    }

    @Test
    public void testBooleanXor_f_f() {
        boolean actualValue = simpleBooleanTest(Arrays.asList(
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, false),
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, false),
            new Instruction(InstructionCode.BOOLEAN_XOR)
        ));
        
        assertEquals(false, actualValue);
    }

    @Test
    public void testBooleanXor_f_t() {
        boolean actualValue = simpleBooleanTest(Arrays.asList(
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, false),
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.BOOLEAN_XOR)
        ));
        
        assertEquals(true, actualValue);
    }

    @Test
    public void testBooleanXor_t_f() {
        boolean actualValue = simpleBooleanTest(Arrays.asList(
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, false),
            new Instruction(InstructionCode.BOOLEAN_XOR)
        ));
        
        assertEquals(true, actualValue);
    }

    @Test
    public void testBooleanXor_t_t() {
        boolean actualValue = simpleBooleanTest(Arrays.asList(
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.BOOLEAN_XOR)
        ));
        
        assertEquals(false, actualValue);
    }
}
