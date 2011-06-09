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
     * Launch the test.
     * 
     * @param args
     *            the command line arguments
     * 
     * @generatedBy CodePro at 3/22/11 9:16 PM
     */
    public static void main(String[] args) {
        new org.junit.runner.JUnitCore().run(OptionTest.class);
    }
}
