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

    private long simpleIntTest(List<Instruction> instructions) {
        FunctionDefinition mainFunc = new FunctionDefinition(instructions, 0, Arrays.asList(), Arrays.asList(BaseType.INTEGER));
        
        VirtualMachine virtualMachine = new VirtualMachine(Arrays.asList(mainFunc));
        
        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        return (long) actualValue.get(0);
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
    
    @Test
    public void testIntegerShiftLeft0() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 25398L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0L),
            new Instruction(InstructionCode.INTEGER_SHIFT_LEFT)
        ));
        
        assertEquals(25398L, actualValue);
    }

    @Test
    public void testIntegerShiftLeft1() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 14L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 1L),
            new Instruction(InstructionCode.INTEGER_SHIFT_LEFT)
        ));
        
        assertEquals(28L, actualValue);
    }
    
    @Test
    public void testIntegerShiftLeftMulti() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, -52L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 7L),
            new Instruction(InstructionCode.INTEGER_SHIFT_LEFT)
        ));
        
        assertEquals(-6656L, actualValue);
    }
    
    @Test
    public void testIntegerShiftLeftOverflow() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 16803354902528L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 24L),
            new Instruction(InstructionCode.INTEGER_SHIFT_LEFT)
        ));
        
        assertEquals(5212353618727927808L, actualValue);
    }
    
    @Test
    public void testIntegerShiftLeftSignSwitch() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 9223372036854775807L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 1L),
            new Instruction(InstructionCode.INTEGER_SHIFT_LEFT)
        ));
        
        assertEquals(-2L, actualValue);
    }
    
    @Test
    public void testIntegerShiftLeft63() {
          long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 2353453456347L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 63L),
            new Instruction(InstructionCode.INTEGER_SHIFT_LEFT)
        ));
        
        assertEquals(-9223372036854775808L, actualValue);
    }
    
    @Test
    public void testIntegerShiftLeft64() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, -34634634534534L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 64L),
            new Instruction(InstructionCode.INTEGER_SHIFT_LEFT)
        ));
        
        assertEquals(-34634634534534L, actualValue);
    }
    
    @Test
    public void testIntegerShiftLeft65() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 1905L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 65L),
            new Instruction(InstructionCode.INTEGER_SHIFT_LEFT)
        ));
        
        assertEquals(3810L, actualValue);
    }

    @Test
    public void testIntegerShiftRightArith0() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 346346346L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0L),
            new Instruction(InstructionCode.INTEGER_SHIFT_RIGHT_ARITHMETIC)
        ));
        
        assertEquals(346346346L, actualValue);
    }

    @Test
    public void testIntegerShiftRightArith1() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 93L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 1L),
            new Instruction(InstructionCode.INTEGER_SHIFT_RIGHT_ARITHMETIC)
        ));
        
        assertEquals(46L, actualValue);
    }
    
    @Test
    public void testIntegerShiftRightArithMulti() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, -34649L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 8L),
            new Instruction(InstructionCode.INTEGER_SHIFT_RIGHT_ARITHMETIC)
        ));
        
        assertEquals(-136L, actualValue);
    }
    
    @Test
    public void testIntegerShiftRightArith63() {
          long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, -9223372036854775808L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 63L),
            new Instruction(InstructionCode.INTEGER_SHIFT_RIGHT_ARITHMETIC)
        ));
        
        assertEquals(-1L, actualValue);
    }
    
    @Test
    public void testIntegerShiftRightArith64() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, -46929394L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 64L),
            new Instruction(InstructionCode.INTEGER_SHIFT_RIGHT_ARITHMETIC)
        ));
        
        assertEquals(-46929394L, actualValue);
    }
    
    @Test
    public void testIntegerShiftRightArith65() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 8000L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 65L),
            new Instruction(InstructionCode.INTEGER_SHIFT_RIGHT_ARITHMETIC)
        ));
        
        assertEquals(4000L, actualValue);
    }




    @Test
    public void testIntegerShiftRightLogical0() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 101023543L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0L),
            new Instruction(InstructionCode.INTEGER_SHIFT_RIGHT_LOGICAL)
        ));
        
        assertEquals(101023543L, actualValue);
    }

    @Test
    public void testIntegerShiftRightLogical1() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 1205L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 1L),
            new Instruction(InstructionCode.INTEGER_SHIFT_RIGHT_LOGICAL)
        ));
        
        assertEquals(602L, actualValue);
    }
    
    @Test
    public void testIntegerShiftRightLogicalMulti() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, -373745474575347L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 32L),
            new Instruction(InstructionCode.INTEGER_SHIFT_RIGHT_LOGICAL)
        ));
        
        assertEquals(4294880276L, actualValue);
    }
    
    @Test
    public void testIntegerShiftRightLogical63() {
          long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, -9223372036854775808L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 63L),
            new Instruction(InstructionCode.INTEGER_SHIFT_RIGHT_LOGICAL)
        ));
        
        assertEquals(1L, actualValue);
    }
    
    @Test
    public void testIntegerShiftRightLogical64() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 373744121L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 64L),
            new Instruction(InstructionCode.INTEGER_SHIFT_RIGHT_LOGICAL)
        ));
        
        assertEquals(373744121L, actualValue);
    }
    
    @Test
    public void testIntegerShiftRightLogical65() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, -12000L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 65L),
            new Instruction(InstructionCode.INTEGER_SHIFT_RIGHT_LOGICAL)
        ));
        
        assertEquals(9223372036854769808L, actualValue);
    }

    @Test
    public void testIntegerRotateLeft0() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 39054379430L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0L),
            new Instruction(InstructionCode.INTEGER_ROTATE_LEFT)
        ));
        
        assertEquals(39054379430L, actualValue);
    }

    @Test
    public void testIntegerRotateLeft1() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 252352L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 1L),
            new Instruction(InstructionCode.INTEGER_ROTATE_LEFT)
        ));
        
        assertEquals(504704L, actualValue);
    }

    @Test
    public void testIntegerRotateLeftMulti() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0xA463932B49BC3F9AL),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 8L),
            new Instruction(InstructionCode.INTEGER_ROTATE_LEFT)
        ));
        
        assertEquals(0x63932B49BC3F9AA4L, actualValue);
    }

    @Test
    public void testIntegerRotateLeft63() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0x000000008537A9A0L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 63L),
            new Instruction(InstructionCode.INTEGER_ROTATE_LEFT)
        ));
        
        assertEquals(0x00000000429BD4D0L, actualValue);
    }

    @Test
    public void testIntegerRotateLeft64() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, -24362235L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 64L),
            new Instruction(InstructionCode.INTEGER_ROTATE_LEFT)
        ));
        
        assertEquals(-24362235L, actualValue);
    }

    @Test
    public void testIntegerRotateLeft65() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 12L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 65L),
            new Instruction(InstructionCode.INTEGER_ROTATE_LEFT)
        ));
        
        assertEquals(24L, actualValue);
    }

    @Test
    public void testIntegerRotateRight0() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, -20639345L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0L),
            new Instruction(InstructionCode.INTEGER_ROTATE_RIGHT)
        ));
        
        assertEquals(-20639345L, actualValue);
    }

    @Test
    public void testIntegerRotateRight1() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0x84700627000FFFF1L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 1L),
            new Instruction(InstructionCode.INTEGER_ROTATE_RIGHT)
        ));
        
        assertEquals(0xC23803138007FFF8L, actualValue);
    }

    @Test
    public void testIntegerRotateRightMulti() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0xBC7A4F9036A657A2L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 12L),
            new Instruction(InstructionCode.INTEGER_ROTATE_RIGHT)
        ));
        
        assertEquals(0x7A2BC7A4F9036A65L, actualValue);
    }

    @Test
    public void testIntegerRotateRight63() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 2861L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 63L),
            new Instruction(InstructionCode.INTEGER_ROTATE_RIGHT)
        ));
        
        assertEquals(5722L, actualValue);
    }

    @Test
    public void testIntegerRotateRight64() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 23456369934064L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 64L),
            new Instruction(InstructionCode.INTEGER_ROTATE_RIGHT)
        ));
        
        assertEquals(23456369934064L, actualValue);
    }

    @Test
    public void testIntegerRotateRight65() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 50000L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 65L),
            new Instruction(InstructionCode.INTEGER_ROTATE_RIGHT)
        ));
        
        assertEquals(25000L, actualValue);
    }

    @Test
    public void testIntegerBitwiseAnd() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0x395AA638548395FDL),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0x593B4A8500B1547DL),
            new Instruction(InstructionCode.INTEGER_BITWISE_AND)
        ));
        
        assertEquals(0x191A02000081147DL, actualValue);
    }

    @Test
    public void testIntegerBitwiseAndAllSet() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0x3453B7A9F345B3A4L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0xFFFFFFFFFFFFFFFFL),
            new Instruction(InstructionCode.INTEGER_BITWISE_AND)
        ));
        
        assertEquals(0x3453B7A9F345B3A4L, actualValue);
    }

    @Test
    public void testIntegerBitwiseAndAllClear() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0x3453B7A9F345B3A4L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0x0000000000000000L),
            new Instruction(InstructionCode.INTEGER_BITWISE_AND)
        ));
        
        assertEquals(0x0000000000000000L, actualValue);
    }

    @Test
    public void testIntegerBitwiseOr() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0x345B02A730BF37A8L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0x8B74EF2A8400F47CL),
            new Instruction(InstructionCode.INTEGER_BITWISE_OR)
        ));
        
        assertEquals(0xBF7FEFAFB4BFF7FCL, actualValue);
    }

    @Test
    public void testIntegerBitwiseOrAllSet() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0x934FB7ACE934F800L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0xFFFFFFFFFFFFFFFFL),
            new Instruction(InstructionCode.INTEGER_BITWISE_OR)
        ));
        
        assertEquals(0xFFFFFFFFFFFFFFFFL, actualValue);
    }

    @Test
    public void testIntegerBitwiseOrAllClear() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0x5938B748A9300AFAL),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0x0000000000000000L),
            new Instruction(InstructionCode.INTEGER_BITWISE_OR)
        ));
        
        assertEquals(0x5938B748A9300AFAL, actualValue);
    }

    @Test
    public void testIntegerBitwiseNot() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0x345B02A730BF37A8L),
            new Instruction(InstructionCode.INTEGER_BITWISE_NOT)
        ));
        
        assertEquals(0xCBA4FD58CF40C857L, actualValue);
    }

    @Test
    public void testIntegerBitwiseNotAllSet() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0xFFFFFFFFFFFFFFFFL),
            new Instruction(InstructionCode.INTEGER_BITWISE_NOT)
        ));
        
        assertEquals(0x0000000000000000L, actualValue);
    }

    @Test
    public void testIntegerBitwiseNotAllClear() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0x0000000000000000L),
            new Instruction(InstructionCode.INTEGER_BITWISE_NOT)
        ));
        
        assertEquals(0xFFFFFFFFFFFFFFFFL, actualValue);
    }

    @Test
    public void testIntegerBitwiseXor() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0xF9A948BA73CC56A1L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0x7B6A58938DC930A1L),
            new Instruction(InstructionCode.INTEGER_BITWISE_XOR)
        ));
        
        assertEquals(0x82C31029FE056600L, actualValue);
    }

    @Test
    public void testIntegerBitwiseXorAllSet() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0x3456AB353ABF235AL),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0xFFFFFFFFFFFFFFFFL),
            new Instruction(InstructionCode.INTEGER_BITWISE_XOR)
        ));
        
        assertEquals(0xCBA954CAC540DCA5L, actualValue);
    }

    @Test
    public void testIntegerBitwiseXorAllClear() {
        long actualValue = simpleIntTest(Arrays.asList(
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0x4564599B292A40F1L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0x0000000000000000L),
            new Instruction(InstructionCode.INTEGER_BITWISE_XOR)
        ));
        
        assertEquals(0x4564599B292A40F1L, actualValue);
    }
}
