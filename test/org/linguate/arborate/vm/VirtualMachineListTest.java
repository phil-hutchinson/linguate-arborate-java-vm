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
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 *
 * @author Phil Hutchinson
 */
public class VirtualMachineListTest {
    public VirtualMachineListTest() {
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
    
    private ArborateObject simpleListTest(List<Instruction> instructions) {
        return simpleListTest(instructions, 0);
    }

    private ArborateObject simpleListTest(List<Instruction> instructions, int numVariables) {
        FunctionDefinition mainFunc = new FunctionDefinition(instructions, numVariables, Arrays.asList(), Arrays.asList(BaseType.OBJECT));
        
        VirtualMachine virtualMachine = new VirtualMachine(Arrays.asList(mainFunc));
        
        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateObject result = (ArborateObject) actualValue.get(0);
        return result;
    }
    
    @Test
    public void testObjectSizeEmtpy() {
        ArborateList testList = new ArborateList();

        assertEquals(0, testList.getSize());
    }

    @Test
    public void testObjectSizeNonEmtpy() {
        ArborateList testList = new ArborateList();
        ArborateObject value1 = new ArborateBoolean(true);
        ArborateObject value2 = new ArborateString("abc");
        
        testList = testList.insertPosition(0, value1);
        testList = testList.insertPosition(1, value2);
                
        assertEquals(2, testList.getSize());
    }
    
    @Test
    public void testObjectSizeAfterSet() {
        ArborateList testList = new ArborateList();
        ArborateObject value1 = new ArborateInteger(3);
        ArborateObject value2 = new ArborateString("newval");
        
        testList = testList.insertPosition(0, value1);
        testList = testList.setPosition(0, value2);
                
        assertEquals(1, testList.getSize());
    }
    
    @Test
    public void testObjectSizeAfterRemoved() {
        ArborateList testList = new ArborateList();
        ArborateObject value = new ArborateString("temporary");
        
        testList = testList.insertPosition(0, value);
        testList = testList.removePosition(0);
                
        assertEquals(0, testList.getSize());
    }
    
    @Test
    public void testSimpleSetGet() {
        ArborateList testList = new ArborateList();
        ArborateObject value = new ArborateString("baz");
        
        testList = testList.insertPosition(0, value);
        
        ArborateObject actualContainer = testList.getPosition(0);
        String actualValue = ((ArborateString)actualContainer).value;
        
        assertEquals("baz", actualValue);
    }
    
    @Test
    public void testReplacedSetGet() {
        ArborateList testList = new ArborateList();
        ArborateObject value1 = new ArborateString("val");
        ArborateObject value2 = new ArborateString("newval");
        
        testList = testList.insertPosition(0, value1);
        testList = testList.setPosition(0, value2);
        
        ArborateObject actualContainer = testList.getPosition(0);
        String actualValue = ((ArborateString)actualContainer).value;
        
        assertEquals("newval", actualValue);
    }

    @Test
    public void testImmutableSetGet() {
        ArborateObject value1 = new ArborateString("foo");
        ArborateObject value2 = new ArborateString("bar");
        
        ArborateList testList1 = (new ArborateList()).insertPosition(0, value1);
        ArborateList testList2 = testList1.setPosition(0, value2);
        
        ArborateObject actualContainer1 = testList1.getPosition(0);
        String actualValue1 = ((ArborateString)actualContainer1).value;
        ArborateObject actualContainer2 = testList2.getPosition(0);
        String actualValue2 = ((ArborateString)actualContainer2).value;
        
        assertEquals("foo", actualValue1);
        assertEquals("bar", actualValue2);
    }

    @Test
    public void testSimpleSetRemoveSize() {
        ArborateList testList = new ArborateList();
        ArborateObject value = new ArborateString("bar");
        
        testList = testList.insertPosition(0, value);
        testList = testList.removePosition(0);
        
        int finalSize = testList.getSize();
        
        assertEquals(0, finalSize);
    }
    
    @Test
    public void testImmutableSetRemoveGet() {
        ArborateList testList = new ArborateList();
        ArborateObject value = new ArborateString("bar");
        
        testList = testList.insertPosition(0, value);
        ArborateList testList2 = testList.removePosition(0);
        
        ArborateObject actualContainer1 = testList.getPosition(0);
        String actualValue1 = ((ArborateString)actualContainer1).value;
        int actualSizeNew = testList2.getSize();
        
        assertEquals("bar", actualValue1);
        assertEquals(0, actualSizeNew);
    }
    
    @Test
    public void testCompoundTest() {
        ArborateList testList1 = new ArborateList();
        
        testList1 = testList1.insertPosition(0, new ArborateString("foo")); // "foo"
        testList1 = testList1.insertPosition(1, new ArborateBoolean(false)); // "foo", false
        testList1 = testList1.insertPosition(2, new ArborateString("33")); // "foo", false, "33"
        testList1 = testList1.removePosition(0); // false, "33"
        
        ArborateList testList2 = testList1.setPosition(1, new ArborateInteger(33)); // false, 33
        
        ArborateList testList3 = testList1.insertPosition(1, new ArborateBoolean(true)); // false, true, 33
        testList3 = testList3.insertPosition(0, new ArborateString("bar")); // "bar", false, true, 33
        testList3 = testList3.removePosition(0); // false, true, 33
        testList3 = testList3.removePosition(2); // false, true
        
        testList1 = testList1.insertPosition(2, new ArborateString("abc")); // false, "33", "abc"
        testList1 = testList1.setPosition(1, new ArborateString("what")); // false, "what", "abc"
        testList1 = testList1.insertPosition(0, new ArborateString("33")); // "33", false, "what", "abc"
        
        assertEquals(4, testList1.getSize());
        assertEquals("33", ((ArborateString)testList1.getPosition(0)).getValue());
        assertEquals(false, ((ArborateBoolean)testList1.getPosition(1)).getValue());
        assertEquals("what", ((ArborateString)testList1.getPosition(2)).getValue());
        assertEquals("abc", ((ArborateString)testList1.getPosition(3)).getValue());


        assertEquals(2, testList2.getSize());
        assertEquals(false, ((ArborateBoolean)testList2.getPosition(0)).getValue());
        assertEquals(33, ((ArborateInteger)testList2.getPosition(1)).getValue());
        
        assertEquals(2, testList3.getSize());
        assertEquals(false, ((ArborateBoolean)testList3.getPosition(0)).getValue());
        assertEquals(true, ((ArborateBoolean)testList3.getPosition(1)).getValue());
        
    }

    @Test
    public void testListOperationEmptyToStack() {
        ArborateObject val = simpleListTest(Arrays.asList(
            new Instruction(InstructionCode.LIST_EMPTY_TO_STACK)
        ));
        
        assertThat(val, instanceOf(ArborateList.class));
        assertEquals(0, ((ArborateList)val).getSize());
        
    }
    
    @Test
    public void testListOperationInsert() {
        ArborateList list = (ArborateList)simpleListTest(Arrays.asList(
            new Instruction(InstructionCode.LIST_EMPTY_TO_STACK),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0L),
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.LIST_INSERT_POSITION)
        ));
        
        assertEquals(1, list.getSize());
        ArborateBoolean actualValue = (ArborateBoolean)list.getPosition(0);
        assertEquals(true, actualValue.getValue());
    }
    
    @Test
    public void testListOperationSet() {
        ArborateList list = (ArborateList)simpleListTest(Arrays.asList(
            new Instruction(InstructionCode.LIST_EMPTY_TO_STACK),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0L),
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.LIST_INSERT_POSITION),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0L),
            new Instruction(InstructionCode.STRING_TO_STACK, "blue"),
            new Instruction(InstructionCode.LIST_SET_POSITION)
        ));
        
        assertEquals(1, list.getSize());
        ArborateString actualValue = (ArborateString)list.getPosition(0);
        assertEquals("blue", actualValue.getValue());
    }
    
    @Test
    public void testListOperationGet() {
        ArborateString actualValue = (ArborateString)simpleListTest(Arrays.asList(
            new Instruction(InstructionCode.LIST_EMPTY_TO_STACK),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0L),
            new Instruction(InstructionCode.STRING_TO_STACK, "aaa"),
            new Instruction(InstructionCode.LIST_INSERT_POSITION),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 1L),
            new Instruction(InstructionCode.STRING_TO_STACK, "bbb"),
            new Instruction(InstructionCode.LIST_INSERT_POSITION),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 2L),
            new Instruction(InstructionCode.STRING_TO_STACK, "ccc"),
            new Instruction(InstructionCode.LIST_INSERT_POSITION),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 1L),
            new Instruction(InstructionCode.LIST_GET_POSITION)
        ));
        
        assertEquals("bbb", actualValue.getValue());
    }
    
    @Test
    public void testListOperationRemove() {
        ArborateList list = (ArborateList)simpleListTest(Arrays.asList(
            new Instruction(InstructionCode.LIST_EMPTY_TO_STACK),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 100L),
            new Instruction(InstructionCode.LIST_INSERT_POSITION),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 1L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 200L),
            new Instruction(InstructionCode.LIST_INSERT_POSITION),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0L),
            new Instruction(InstructionCode.LIST_REMOVE_POSITION)
        ));
        
        assertEquals(1, list.getSize());
        ArborateInteger actualValue = (ArborateInteger)list.getPosition(0);
        assertEquals(200L, actualValue.getValue());
    }
    
    @Test
    public void testListOperationSize() {
        ArborateInteger actualValue = (ArborateInteger)simpleListTest(Arrays.asList(
            new Instruction(InstructionCode.LIST_EMPTY_TO_STACK),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 123L),
            new Instruction(InstructionCode.LIST_INSERT_POSITION),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 123L),
            new Instruction(InstructionCode.LIST_INSERT_POSITION),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 0L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 123L),
            new Instruction(InstructionCode.LIST_INSERT_POSITION),
            new Instruction(InstructionCode.LIST_SIZE)
        ));
        
        assertEquals(3L, actualValue.getValue());
    }
    
//    LIST_EQUAL,
//    LIST_NOT_EQUAL,
}
