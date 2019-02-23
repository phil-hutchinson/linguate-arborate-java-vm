/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.arborate.vm;

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
}
