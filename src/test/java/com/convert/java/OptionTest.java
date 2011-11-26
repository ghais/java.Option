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

import org.junit.Test;

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
     * Run the Option.None() method test.
     * 
     * @throws Exception
     * 
     */
    @Test
    public void testNone_2()
            throws Exception {

        Option<Integer> result = Option.None();

        assertNotNull(result);
        try {
            result.get();
            fail("Can't call get on an instance of Option.None");
        } catch (RuntimeException e) {
            // good.
        }
    }

    /**
     * Run the Option.Option(T) method test with a null value.
     * 
     * @throws Exception
     * 
     */
    @Test
    public void testOption_null()
            throws Exception {

        Option<Integer> result = Option.Option(null);

        assertNotNull(result);
        assertTrue(result.isNone());
    }

    /**
     * Run the Option.Option(T) method test with a non-null value.
     * 
     * @throws Exception
     * 
     */
    @Test
    public void testOption_notNull()
            throws Exception {

        Option<Integer> result = Option.Option(10);

        assertNotNull(result);
        assertTrue(result.isSome());
        assertEquals(new Integer(10), result.get());
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
        assertTrue(result.isSome());
        assertEquals(new Integer(10), result.get());
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

    @Test
    public void testSomeIterator() {
        Option<String> some = Option.Some("something");
        for (String x : some) {
            assertEquals("something", x);
        }
    }

    @Test
    public void testNoneIterator() {
        Option<String> some = Option.None();
        for (@SuppressWarnings("unused")
        String x : some) {
            fail("we shouldn't iterate over nothing");
        }
    }
}
