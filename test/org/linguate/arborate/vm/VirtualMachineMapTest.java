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
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
/**
 *
 * @author Phil Hutchinson
 */
public class VirtualMachineMapTest {
    
    public VirtualMachineMapTest() {
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

    private ArborateMap emptyMap() {
        return new ArborateMap();
    }
    
    private ArborateObject simpleMapTest(List<Instruction> instructions) {
        return simpleMapTest(instructions, 0);
    }

    private ArborateObject simpleMapTest(List<Instruction> instructions, int numVariables) {
        FunctionDefinition mainFunc = new FunctionDefinition(instructions, numVariables, Arrays.asList(), Arrays.asList(BaseType.OBJECT));
        
        VirtualMachine virtualMachine = new VirtualMachine(Arrays.asList(mainFunc));
        
        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateObject result = (ArborateObject) actualValue.get(0);
        return result;
    }
    
    @Test
    public void testObjectCreate() {
        ArborateMap testMap = new ArborateMap();

        assertThat(testMap, instanceOf(ArborateMap.class));
    }
    
    @Test
    public void testObjectSizeEmtpy() {
        ArborateMap testMap = new ArborateMap();

        assertEquals(0, testMap.getSize());
    }

    @Test
    public void testObjectSizeNonEmtpy() {
        ArborateMap testMap = new ArborateMap();
        ArborateObject key1 = new ArborateInteger(3);
        ArborateObject value1 = new ArborateBoolean(true);
        ArborateObject key2 = new ArborateString("something");
        ArborateObject value2 = new ArborateString("foo");
        
        testMap = testMap.set(key1, value1);
        testMap = testMap.set(key2, value2);
                
        assertEquals(2, testMap.getSize());
    }
    
    @Test
    public void testObjectSizeReplaced() {
        ArborateMap testMap = new ArborateMap();
        ArborateObject key = new ArborateInteger(3);
        ArborateObject value1 = new ArborateBoolean(true);
        ArborateObject value2 = new ArborateString("foo");
        
        testMap = testMap.set(key, value1);
        testMap = testMap.set(key, value2);
                
        assertEquals(1, testMap.getSize());
    }
    
    @Test
    public void testObjectSizeCleared() {
        ArborateMap testMap = new ArborateMap();
        ArborateObject key = new ArborateInteger(3);
        ArborateObject value1 = new ArborateBoolean(true);
        
        testMap = testMap.set(key, value1);
        testMap = testMap.clear(key);
                
        assertEquals(0, testMap.getSize());
    }
    
    @Test
    public void testSimpleSetGet() {
        ArborateMap testMap = new ArborateMap();
        ArborateObject key = new ArborateString("foo");
        ArborateObject value = new ArborateString("bar");
        ArborateObject retrieveKey = new ArborateString("foo");
        
        testMap = testMap.set(key, value);
        
        ArborateObject actualContainer = testMap.get(retrieveKey);
        String actualValue = ((ArborateString)actualContainer).value;
        
        assertEquals("bar", actualValue);
    }
    
    @Test
    public void testReplacedSetGet() {
        ArborateMap testMap = new ArborateMap();
        ArborateObject key = new ArborateString("foo");
        ArborateObject value1 = new ArborateString("bar");
        ArborateObject value2 = new ArborateString("newval");
        ArborateObject retrieveKey = new ArborateString("foo");
        
        testMap = testMap.set(key, value1);
        testMap = testMap.set(key, value2);
        
        ArborateObject actualContainer = testMap.get(retrieveKey);
        String actualValue = ((ArborateString)actualContainer).value;
        
        assertEquals("newval", actualValue);
    }
    
    @Test
    public void testImmutableSetGet() {
        ArborateMap testMap1 = new ArborateMap();
        ArborateObject key = new ArborateString("foo");
        ArborateObject value1 = new ArborateString("bar");
        ArborateObject value2 = new ArborateString("newval");
        ArborateObject retrieveKey = new ArborateString("foo");
        
        testMap1 = testMap1.set(key, value1);
        ArborateMap testMap2 = testMap1.set(key, value2);
        
        ArborateObject actualContainer1 = testMap1.get(retrieveKey);
        String actualValue1 = ((ArborateString)actualContainer1).value;
        ArborateObject actualContainer2 = testMap2.get(retrieveKey);
        String actualValue2 = ((ArborateString)actualContainer2).value;
        
        assertEquals("bar", actualValue1);
        assertEquals("newval", actualValue2);
        
    }

    @Test
    public void testSimpleSetClearGet() {
        ArborateMap testMap = new ArborateMap();
        ArborateObject key = new ArborateString("foo");
        ArborateObject value = new ArborateString("bar");
        
        testMap = testMap.set(key, value);
        testMap = testMap.clear(key);
        
        ArborateObject actualContainer = testMap.get(key);
        
        assertEquals(null, actualContainer);
    }
    
    @Test
    public void testImmutableSetClearGet() {
        ArborateMap testMap1 = new ArborateMap();
        ArborateObject key = new ArborateString("foo");
        ArborateObject value = new ArborateString("bar");
        ArborateObject retrieveKey = new ArborateString("foo");
        
        testMap1 = testMap1.set(key, value);
        ArborateMap testMap2 = testMap1.clear(key);
        
        ArborateObject actualContainer1 = testMap1.get(retrieveKey);
        String actualValue1 = ((ArborateString)actualContainer1).value;
        ArborateObject actualContainer2 = testMap2.get(retrieveKey);
        
        assertEquals("bar", actualValue1);
        assertEquals(null, actualContainer2);
    }
    
    @Test
    public void testInternalObjectDepth7() {
        ArborateMap testMap = new ArborateMap();
        ArborateObject key1 = new ArborateString("foo1");
        ArborateObject key2 = new ArborateString("foo2");
        ArborateObject key3 = new ArborateString("foo3");
        ArborateObject key4 = new ArborateString("foo4");
        ArborateObject key5 = new ArborateString("foo5");
        ArborateObject key6 = new ArborateString("foo6");
        ArborateObject key7 = new ArborateString("foo7");
        ArborateObject value = new ArborateString("bar1");
        
        testMap = testMap.set(key1, value);
        testMap = testMap.set(key2, value);
        testMap = testMap.set(key3, value);
        testMap = testMap.set(key4, value);
        testMap = testMap.set(key5, value);
        testMap = testMap.set(key6, value);
        testMap = testMap.set(key7, value);

        boolean isDepth3 = testMap.parent != null 
                && testMap.parent.parent != null 
                && testMap.parent.parent.parent == null;
        assertEquals(true, isDepth3);
    }
    
    @Test
    public void testInternalObjectDepth8() {
        ArborateMap testMap = new ArborateMap();
        ArborateObject key1 = new ArborateString("foo1");
        ArborateObject key2 = new ArborateString("foo2");
        ArborateObject key3 = new ArborateString("foo3");
        ArborateObject key4 = new ArborateString("foo4");
        ArborateObject key5 = new ArborateString("foo5");
        ArborateObject key6 = new ArborateString("foo6");
        ArborateObject key7 = new ArborateString("foo7");
        ArborateObject key8 = new ArborateString("foo8");
        ArborateObject value = new ArborateString("bar1");
        
        testMap = testMap.set(key1, value);
        testMap = testMap.set(key2, value);
        testMap = testMap.set(key3, value);
        testMap = testMap.set(key4, value);
        testMap = testMap.set(key5, value);
        testMap = testMap.set(key6, value);
        testMap = testMap.set(key7, value);
        testMap = testMap.set(key8, value);

        boolean isDepth1 = testMap.parent == null;
        assertEquals(true, isDepth1);
    }
    
    @Test 
    public void testEqualityChecksWhenEqual() {
        ArborateMap map1 = new ArborateMap();
        map1 = map1.set(new ArborateString("foo"), new ArborateString("whatever"));
        map1 = map1.set(new ArborateString("foo"), new ArborateBoolean(true));
        map1 = map1.set(new ArborateString("baz"), new ArborateString("baz"));
        map1 = map1.set(new ArborateByte((byte)100), new ArborateInteger(1000L));
        map1 = map1.clear(new ArborateString("baz"));
        
        ArborateMap map2 = new ArborateMap();
        map2 = map2.set(new ArborateByte((byte)100), new ArborateBoolean(false));
        map2 = map2.set(new ArborateString("foo"), new ArborateBoolean(true));
        map2 = map2.clear(new ArborateString("foo"));
        map2 = map2.set(new ArborateString("foo"), new ArborateBoolean(true));
        map2 = map2.set(new ArborateByte((byte)100), new ArborateInteger(1000L));
        
        assertEquals(true, map1.internalHashCode == map2.internalHashCode);
        assertEquals(true, map1.hashCode() == map2.hashCode());
        assertEquals(map1, map2);
    }
            
    @Test 
    public void testEqualityChecksWhenNotEqual() {
        ArborateMap map1 = new ArborateMap();
        map1 = map1.set(new ArborateString("foo"), new ArborateString("whatever"));
        map1 = map1.set(new ArborateString("foo"), new ArborateBoolean(true));
        map1 = map1.set(new ArborateString("baz"), new ArborateString("baz"));
        map1 = map1.set(new ArborateByte((byte)100), new ArborateInteger(1000L));
        
        ArborateMap map2 = new ArborateMap();
        map2 = map2.set(new ArborateByte((byte)100), new ArborateBoolean(false));
        map2 = map2.set(new ArborateString("foo"), new ArborateBoolean(true));
        map2 = map2.clear(new ArborateString("foo"));
        map2 = map2.set(new ArborateString("foo"), new ArborateBoolean(true));
        map2 = map2.set(new ArborateByte((byte)100), new ArborateInteger(1000L));
        
        assertNotEquals(map1, map2);
    }
    
    @Test
    public void testCompoundTest() {
        ArborateMap map1 = new ArborateMap();
        ArborateString foo = new ArborateString("foo");
        map1 = map1.set(foo, new ArborateString("bar"));
        map1 = map1.set(new ArborateString("footwo"), new ArborateString("bartwo"));
        map1 = map1.set(new ArborateString("footoo"), new ArborateString("bartoo"));
        
        ArborateMap map2 = map1.set(new ArborateInteger(10000L), new ArborateInteger(-1000L));
        map2 = map2.set(foo, new ArborateInteger(0));
        
        ArborateMap map3 = map1.clear(foo);
        map3 = map3.clear(new ArborateString("footoo"));
        map3 = map3.clear(new ArborateString("mysteryitem"));
        
        ArborateMap map4 = map1.set(new ArborateString("what"), new ArborateString("ever"));
        map4 = map4.set(new ArborateByte((byte)49), new ArborateBoolean(true));
        map4 = map4.set(foo, foo);
        
        ArborateMap map5 = (new ArborateMap()).set(new ArborateString("in"), map4);
        
        assertEquals(3, map1.getSize());
        assertEquals(4, map2.getSize());
        assertEquals(1, map3.getSize());
        assertEquals(5, map4.getSize());
        assertEquals(1, map5.getSize());
        assertEquals("bar", ((ArborateString)map1.get(foo)).value);
        assertEquals(null, map3.get(foo));
        assertEquals(foo, map4.get(foo));
        assertEquals(map4, map5.get(new ArborateString("in")));
    }
    
    @Test
    public void testMapOperationEmptyToStack() {
        ArborateObject actualValue = simpleMapTest(Arrays.asList(
            new Instruction(InstructionCode.MAP_TO_STACK, emptyMap())
        ));
        
        assertEquals(new ArborateMap(), actualValue);
        
    }

    @Test
    public void testMapOperationHasExists() {
        ArborateObject actualValue = simpleMapTest(Arrays.asList(
            new Instruction(InstructionCode.MAP_TO_STACK, emptyMap()),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 50L),
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 50L),
            new Instruction(InstructionCode.MAP_HAS)
        ));
        
        assertEquals(true, ((ArborateBoolean)actualValue).value);
    }

    @Test
    public void testMapOperationHasDoesNotExist() {
        ArborateObject actualValue = simpleMapTest(Arrays.asList(
            new Instruction(InstructionCode.MAP_TO_STACK, emptyMap()),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 50L),
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 100L),
            new Instruction(InstructionCode.MAP_HAS)
        ));
        
        assertEquals(false, ((ArborateBoolean)actualValue).getValue());
    }

    @Test
    public void testMapOperationGetExists() {
        ArborateObject actualValue = simpleMapTest(Arrays.asList(
            new Instruction(InstructionCode.MAP_TO_STACK, emptyMap()),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 50L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 1234L),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 50L),
            new Instruction(InstructionCode.MAP_GET)
        ));
        
        assertEquals(1234L, ((ArborateInteger)actualValue).getValue());
    }

    @Test
    public void testMapOperationGetDoesNotExist() {
        ArborateObject actualValue = simpleMapTest(Arrays.asList(
            new Instruction(InstructionCode.MAP_TO_STACK, emptyMap()),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 50L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 1234L),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 100L),
            new Instruction(InstructionCode.MAP_GET)
        ));
        
        assertEquals(null, actualValue);
    }
    
    @Test
    public void testMapOperationSet() {
        ArborateObject actualValue = simpleMapTest(Arrays.asList(
            new Instruction(InstructionCode.MAP_TO_STACK, emptyMap()),
            new Instruction(InstructionCode.INTEGER_TO_STACK, -123L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 654321L),
            new Instruction(InstructionCode.MAP_SET)
        ));
        
        ArborateMap actualMap = (ArborateMap) actualValue;
        
        assertEquals(1, actualMap.getSize());
        ArborateInteger actualInteger = (ArborateInteger) actualMap.get(new ArborateInteger(-123L));
        assertEquals(654321L, actualInteger.getValue());
    }
    
    @Test
    public void testMapOperationSetTwice() {
        ArborateObject actualValue = simpleMapTest(Arrays.asList(
            new Instruction(InstructionCode.MAP_TO_STACK, emptyMap()),
            new Instruction(InstructionCode.INTEGER_TO_STACK, -123L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 654321L),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.INTEGER_TO_STACK, -123L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 2L),
            new Instruction(InstructionCode.MAP_SET)
        ));
        
        ArborateMap actualMap = (ArborateMap) actualValue;
        
        assertEquals(1, actualMap.getSize());
        ArborateInteger actualInteger = (ArborateInteger) actualMap.get(new ArborateInteger(-123L));
        assertEquals(2L, actualInteger.getValue());
    }
    
    @Test
    public void testMapOperationSetImmutable() {
        ArborateObject actualValue = simpleMapTest(Arrays.asList(
            new Instruction(InstructionCode.MAP_TO_STACK, emptyMap()),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 999L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 10L),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.STACK_TO_VARIABLE, 0L),
            new Instruction(InstructionCode.VARIABLE_TO_STACK, 0L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 999L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 2623L),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.STACK_TO_VARIABLE, 1L),
            new Instruction(InstructionCode.VARIABLE_TO_STACK, 0L)
        ), 2);
        
        ArborateMap actualMap = (ArborateMap) actualValue;
        
        assertEquals(1, actualMap.getSize());
        ArborateInteger actualInteger = (ArborateInteger) actualMap.get(new ArborateInteger(999L));
        assertEquals(10L, actualInteger.getValue());
    }
    
    @Test
    public void testMapOperationClear() {
        ArborateObject actualValue = simpleMapTest(Arrays.asList(
            new Instruction(InstructionCode.MAP_TO_STACK, emptyMap()),
            new Instruction(InstructionCode.INTEGER_TO_STACK, -123L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 654321L),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.INTEGER_TO_STACK, -123L),
            new Instruction(InstructionCode.MAP_CLEAR)
        ));
        
        ArborateMap actualMap = (ArborateMap) actualValue;
        
        assertEquals(0, actualMap.getSize());
        assertEquals(null, actualMap.get(new ArborateInteger(-123L)));
    }
    
    @Test
    public void testMapOperationClearImmutable() {
        ArborateObject actualValue = simpleMapTest(Arrays.asList(
            new Instruction(InstructionCode.MAP_TO_STACK, emptyMap()),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 999L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 44L),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.STACK_TO_VARIABLE, 0L),
            new Instruction(InstructionCode.VARIABLE_TO_STACK, 0L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 999L),
            new Instruction(InstructionCode.MAP_CLEAR),
            new Instruction(InstructionCode.STACK_TO_VARIABLE, 1L),
            new Instruction(InstructionCode.VARIABLE_TO_STACK, 0L)
        ), 2);
        
        ArborateMap actualMap = (ArborateMap) actualValue;
        
        assertEquals(1, actualMap.getSize());
        ArborateInteger actualInteger = (ArborateInteger) actualMap.get(new ArborateInteger(999L));
        assertEquals(44L, actualInteger.getValue());
    }
    
    @Test
    public void testMapOperationEqualWhenEqual() {
        ArborateObject actualValue = simpleMapTest(Arrays.asList(
            new Instruction(InstructionCode.MAP_TO_STACK, emptyMap()),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 500L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 1L),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 1000L),
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.MAP_TO_STACK, emptyMap()),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 1000L),
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 500L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 1L),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.MAP_EQUAL)
        ));
        
        ArborateBoolean actualBoolean = (ArborateBoolean) actualValue;
        
        assertEquals(true, actualBoolean.getValue());
    }
    
    @Test
    public void testMapOperationNotEqualWhenNotEqual() {
        ArborateObject actualValue = simpleMapTest(Arrays.asList(
            new Instruction(InstructionCode.MAP_TO_STACK, emptyMap()),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 500L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 1L),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 1000L),
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.MAP_TO_STACK, emptyMap()),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 1000L),
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.MAP_EQUAL)
        ));
        
        ArborateBoolean actualBoolean = (ArborateBoolean) actualValue;
        
        assertEquals(false, actualBoolean.getValue());
    }
    
    @Test
    public void testMapOperationNotEqualWhenEqual() {
        ArborateObject actualValue = simpleMapTest(Arrays.asList(
            new Instruction(InstructionCode.MAP_TO_STACK, emptyMap()),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 500L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 1L),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 1000L),
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.MAP_TO_STACK, emptyMap()),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 1000L),
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 500L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 1L),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.MAP_NOT_EQUAL)
        ));
        
        ArborateBoolean actualBoolean = (ArborateBoolean) actualValue;
        
        assertEquals(false, actualBoolean.getValue());
    }
    
    @Test
    public void testMapOperationEqualWhenNotEqual() {
        ArborateObject actualValue = simpleMapTest(Arrays.asList(
            new Instruction(InstructionCode.MAP_TO_STACK, emptyMap()),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 500L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 1L),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 1000L),
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.MAP_TO_STACK, emptyMap()),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 1000L),
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.MAP_NOT_EQUAL)
        ));
        
        ArborateBoolean actualBoolean = (ArborateBoolean) actualValue;
        
        assertEquals(true, actualBoolean.getValue());
    }
    
    @Test
    public void testMapOperationSizeWhenEmpty() {
        ArborateObject actualValue = simpleMapTest(Arrays.asList(
            new Instruction(InstructionCode.MAP_TO_STACK, emptyMap()),
            new Instruction(InstructionCode.MAP_SIZE)
        ));
        
        ArborateInteger actualInteger = (ArborateInteger) actualValue;
        
        assertEquals(0L, actualInteger.getValue());
    }

    @Test
    public void testMapOperationSizeWithItems() {
        ArborateObject actualValue = simpleMapTest(Arrays.asList(
            new Instruction(InstructionCode.MAP_TO_STACK, emptyMap()),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 1000L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 250L),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 2000L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 10L),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 3000L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 250L),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 4000L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 255L),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.MAP_SIZE)
        ));
        
        ArborateInteger actualInteger = (ArborateInteger) actualValue;
        
        assertEquals(4L, actualInteger.getValue());
    }

    @Test
    public void testMapOperationSizeWithOverwrites() {
        ArborateObject actualValue = simpleMapTest(Arrays.asList(
            new Instruction(InstructionCode.MAP_TO_STACK, emptyMap()),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 1000L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 250L),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 2000L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 10L),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 1000L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 250L),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 2000L),
            new Instruction(InstructionCode.BOOLEAN_TO_STACK, true),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.MAP_SIZE)
        ));
        
        ArborateInteger actualInteger = (ArborateInteger) actualValue;
        
        assertEquals(2L, actualInteger.getValue());
    }

    @Test
    public void testMapOperationSizeWithClear() {
        ArborateObject actualValue = simpleMapTest(Arrays.asList(
            new Instruction(InstructionCode.MAP_TO_STACK, emptyMap()),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 1000L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 250L),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 2000L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 10L),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 3000L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 250L),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 2000L),
            new Instruction(InstructionCode.MAP_CLEAR),
            new Instruction(InstructionCode.MAP_SIZE)
        ));
        
        ArborateInteger actualInteger = (ArborateInteger) actualValue;
        
        assertEquals(2L, actualInteger.getValue());
    }
    
    @Test
    public void testMapReturnedFromFunction() {
        ArborateObject actualValue = simpleMapTest(Arrays.asList(
            new Instruction(InstructionCode.MAP_TO_STACK, emptyMap()),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 10L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 20L),
            new Instruction(InstructionCode.MAP_SET),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 40L),
            new Instruction(InstructionCode.INTEGER_TO_STACK, 80L),
            new Instruction(InstructionCode.MAP_SET)
        ));
    
        ArborateMap actualMap = (ArborateMap) actualValue;
        
        assertEquals(2L, actualMap.getSize());
    }
}
