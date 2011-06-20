/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.convert.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;

/**
 * The class <code>OptionTest</code> contains tests for the class <code>{@link Option}</code>.
 * 
 * @author ghais
 */
public class OptionTest {

    /**
     * Run the Option.None() method test.
     * 
     * @throws Exception
     * 
     */
    @Test
    public void testNone_1()
            throws Exception {

        Option<Object> result = Option.None();

        assertNotNull(result);
        try {
            result.get();
            fail("Can't call get on an instance of Option.None");
        } catch (RuntimeException e) {
            // good.
        }
    }

    /**
     * Test the Option.None(Throwable t) function.
     */
    @Test
    public void testNone_2() {
        Option<Object> result = Option.None(new NumberFormatException());

        assertNotNull(result);
        try {
            result.get();
            fail("Can't call get on an instance of Option.None");
        } catch (RuntimeException e) {
            Throwable t = e.getCause();
            assertTrue(t instanceof NumberFormatException);
        }
    }

    /**
     * Run the Option.Some(T) method test with a null value.
     * 
     * @throws Exception
     * 
     */
    @Test
    public void testSome_null()
            throws Exception {

        Option<Object> result = Option.Some(null);

        assertNotNull(result);
        assertTrue(result instanceof Option.None);
    }

    /**
     * Run the Option.Some(T) method test with some value.
     * 
     * @throws Exception
     * 
     */
    @Test
    public void testSome_2()
            throws Exception {

        Option<Integer> result = Option.Some(10);

        assertNotNull(result);
        assertTrue(result instanceof Option.Some);
        assertEquals(new Integer(10), result.get());
    }

    /**
     * Test the Option.cat function.
     */
    @Test
    public void testCat() {
        ArrayList<Option<Integer>> values = new ArrayList<Option<Integer>>();
        assertTrue(Option.cat(values).isEmpty());

        values.add(new Option.None<Integer>());
        assertTrue(Option.cat(values).isEmpty());

        values.add(new Option.None<Integer>());
        assertTrue(Option.cat(values).isEmpty());

        values.add(Option.Some(1));
        int result = Iterables.getOnlyElement(Option.cat(values));
        assertEquals(1, result);

        values.add(Option.Some(2));
        Iterable<Integer> ints = Option.cat(values);
        boolean foundOne = false;
        boolean foundTwo = false;
        for (Integer i : ints) {
            switch (i) {
            case 1:
                foundOne = true;
                break;
            case 2:
                foundTwo = true;
                break;
            default:
                fail("Neither 1 nor 2. Actual Value" + i);
            }
        }
        if (!(foundOne && foundTwo)) {
            fail("Missing a value");
        }
    }

    /**
     * Test the map function.
     */
    @Test
    public void testMap_1() {
        class TestObject {

            String string = "initialValue";

            int integer = 0;

            TestObject other = null;
        }

        Collection<Option<TestObject>> values = new ArrayList<Option<TestObject>>();

        Function<TestObject, String> changeObject = new Function<TestObject, String>() {

            public String apply(TestObject input) {
                input.string = "changed";
                input.integer = 1;
                input.other = new TestObject();
                return input.string;
            }
        };

        Collection<String> result = Option.map(changeObject, values);
        assertTrue(result.isEmpty());

        values.add(Option.None(TestObject.class));
        result = Option.map(changeObject, values);
        assertTrue(result.isEmpty());

        TestObject obj1 = new TestObject();
        values.add(Option.Some(obj1));

        result = Option.map(changeObject, values);
        assertEquals("changed", Iterables.getOnlyElement(result));
        assertEquals("changed", obj1.string);
        assertEquals(1, obj1.integer);
        assertNotNull(obj1.other);
    }

    /**
     * Test that Some.iSome() is true and None.isSome() is false
     */
    @Test
    public void testIsSome() {
        Option<String> some = Option.Some("some");
        assertTrue(some.isSome());

        Option<String> none = Option.None();
        assertFalse(none.isSome());
    }

    /**
     * Test that Some.iNone() is false and None.isNone() is true.
     */
    @Test
    public void testIsNone() {
        Option<String> some = Option.Some("some");
        assertFalse(some.isNone());

        Option<String> none = Option.None();
        assertTrue(none.isNone());
    }

    /**
     * Launch the test.
     * 
     * @param args
     *            the command line arguments
     * 
     */
    public static void main(String[] args) {
        new org.junit.runner.JUnitCore().run(OptionTest.class);
    }
}
