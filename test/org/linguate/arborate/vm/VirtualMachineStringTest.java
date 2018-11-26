/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.arborate.vm;

import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Phil Hutchinson
 */
public class VirtualMachineStringTest {
    public VirtualMachineStringTest() {
        
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

    private String simpleStringTest(List<Instruction> instructions) {
        FunctionDefinition mainFunc = new FunctionDefinition(instructions, 0, Arrays.asList(), Arrays.asList(BaseType.STRING));
        
        VirtualMachine virtualMachine = new VirtualMachine(Arrays.asList(mainFunc));
        
        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateString result = (ArborateString) actualValue.get(0);
        return result.getValue();
    }
    
    private long simpleIntReturnTest(List<Instruction> instructions) {
        FunctionDefinition mainFunc = new FunctionDefinition(instructions, 0, Arrays.asList(), Arrays.asList(BaseType.INTEGER));
        
        VirtualMachine virtualMachine = new VirtualMachine(Arrays.asList(mainFunc));
        
        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        return result.getValue();
    }
    
    @Test
    public void testStringConstToStack() {
        String actualValue = simpleStringTest(Arrays.asList(
            new Instruction(InstructionCode.STRING_TO_STACK, "asdf")
        ));
        
        assertEquals("asdf", actualValue);
    }

    @Test
    public void testStringLength() {
        long actualValue = simpleIntReturnTest(Arrays.asList(
            new Instruction(InstructionCode.STRING_TO_STACK, "qwerty"),
            new Instruction(InstructionCode.STRING_LENGTH)
        ));
        
        assertEquals(6L, actualValue);
    }

    @Test
    public void testSubstringWholeString() {
        String actualValue = simpleStringTest(Arrays.asList(
            new Instruction(InstructionCode.STRING_TO_STACK, "waterworld"),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 10L),
            new Instruction(InstructionCode.STRING_SUBSTRING)
        ));
        
        assertEquals("waterworld", actualValue);
    }

    @Test
    public void testSubstringStart() {
        String actualValue = simpleStringTest(Arrays.asList(
            new Instruction(InstructionCode.STRING_TO_STACK, "waterworld"),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 5L),
            new Instruction(InstructionCode.STRING_SUBSTRING)
        ));
        
        assertEquals("water", actualValue);
    }

    @Test
    public void testSubstringMid() {
        String actualValue = simpleStringTest(Arrays.asList(
            new Instruction(InstructionCode.STRING_TO_STACK, "waterworld"),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 1L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 2L),
            new Instruction(InstructionCode.STRING_SUBSTRING)
        ));
        
        assertEquals("at", actualValue);
    }
    
    @Test
    public void testSubstringEnd() {
        String actualValue = simpleStringTest(Arrays.asList(
            new Instruction(InstructionCode.STRING_TO_STACK, "waterworld"),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 5L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 5L),
            new Instruction(InstructionCode.STRING_SUBSTRING)
        ));
        
        assertEquals("world", actualValue);
    }
    
    @Test
    public void testSubstringGoesBeyondEnd() {
        String actualValue = simpleStringTest(Arrays.asList(
            new Instruction(InstructionCode.STRING_TO_STACK, "waterworld"),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 9L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 12L),
            new Instruction(InstructionCode.STRING_SUBSTRING)
        ));
        
        assertEquals("d", actualValue);
    }
    
    @Test
    public void testSubstringStartsAtEnd() {
        String actualValue = simpleStringTest(Arrays.asList(
            new Instruction(InstructionCode.STRING_TO_STACK, "waterworld"),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 10L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 3L),
            new Instruction(InstructionCode.STRING_SUBSTRING)
        ));
        
        assertEquals("", actualValue);
    }
    
    @Test
    public void testSubstringStartsBeyondEnd() {
        String actualValue = simpleStringTest(Arrays.asList(
            new Instruction(InstructionCode.STRING_TO_STACK, "waterworld"),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 15L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 5L),
            new Instruction(InstructionCode.STRING_SUBSTRING)
        ));
        
        assertEquals("", actualValue);
    }
    
    @Test
    public void testSubstringNegativeOffset() {
        String actualValue = simpleStringTest(Arrays.asList(
            new Instruction(InstructionCode.STRING_TO_STACK, "waterworld"),
            new Instruction(InstructionCode.INTEGER_TO_STACK, -2L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 5L),
            new Instruction(InstructionCode.STRING_SUBSTRING)
        ));
        
        assertEquals("", actualValue);
    }
    
    @Test
    public void testSubstringNegativeRequestedLength() {
        String actualValue = simpleStringTest(Arrays.asList(
            new Instruction(InstructionCode.STRING_TO_STACK, "waterworld"),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 3L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, -2L),
            new Instruction(InstructionCode.STRING_SUBSTRING)
        ));
        
        assertEquals("", actualValue);
    }
    
    @Test
    public void testSubstringZeroRequestedLength() {
        String actualValue = simpleStringTest(Arrays.asList(
            new Instruction(InstructionCode.STRING_TO_STACK, "waterworld"),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 6L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0L),
            new Instruction(InstructionCode.STRING_SUBSTRING)
        ));
        
        assertEquals("", actualValue);
    }

    @Test
    public void testConcatenate_empty_empty() {
        String actualValue = simpleStringTest(Arrays.asList(
            new Instruction(InstructionCode.STRING_TO_STACK, ""),
            new Instruction(InstructionCode.STRING_TO_STACK, ""),
            new Instruction(InstructionCode.STRING_CONCATENATE)
        ));

        assertEquals("", actualValue);
    }

    @Test
    public void testConcatenate_nonEmpty_empty() {
        String actualValue = simpleStringTest(Arrays.asList(
            new Instruction(InstructionCode.STRING_TO_STACK, "who"),
            new Instruction(InstructionCode.STRING_TO_STACK, ""),
            new Instruction(InstructionCode.STRING_CONCATENATE)
        ));

        assertEquals("who", actualValue);
    }

    @Test
    public void testConcatenate_empty_nonEmpty() {
        String actualValue = simpleStringTest(Arrays.asList(
            new Instruction(InstructionCode.STRING_TO_STACK, ""),
            new Instruction(InstructionCode.STRING_TO_STACK, "what"),
            new Instruction(InstructionCode.STRING_CONCATENATE)
        ));

        assertEquals("what", actualValue);
    }

    @Test
    public void testConcatenate_nonEmpty_nonEmpty() {
        String actualValue = simpleStringTest(Arrays.asList(
            new Instruction(InstructionCode.STRING_TO_STACK, "how "),
            new Instruction(InstructionCode.STRING_TO_STACK, "much"),
            new Instruction(InstructionCode.STRING_CONCATENATE)
        ));

        assertEquals("how much", actualValue);
    }
    
    // TODO tests for Find, Equals, Not Equals, 
}
