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

/**
 * Represents optional values. Instances of Option are either an instance of Some or the object None.
 * 
 * The most idiomatic way to use an Option instance is to treat it as a collection or monad and use map,flatMap, filter,
 * or foreach. See tests for usage and examples.
 * 
 * @author ghais.
 * 
 * @param <T>
 */
public abstract class Option<T> {

    /**
     * Returns the option's value.
     * 
     * @return the option's value.
     */
    abstract public T get();

    /**
     * An Option factory which returns an instance of {@link None}
     * 
     * help reduces the verbosity of type parameterization in java.
     * 
     * @param <T>
     * @return
     */
    public static <T> Option<T> None() {
        return new Option.None<T>();
    }

    /**
     * An Option factory which creates Some<T>(x) if the argument is not null, and None if it is null.
     * 
     * @param <T>
     * @param value
     * @return
     */
    public static <T> Option<T> Some(T value) {
        if (null == value) {
            return Option.None();
        } else {
            return new Option.Some<T>(value);
        }
    }

    /**
     * Class Some<A> represents existing values of type A
     * 
     * @author ghais.
     * 
     * @param <T>
     */
    static public final class Some<T> extends Option<T> {

        private final T value_;

        /**
         * Creates an instance with some value.
         * 
         * @param value
         *            the object's value.
         */
        public Some(T value) {
            this.value_ = value;
        }

        /*
         * (non-Javadoc)
         * @see com.convert.java.Option#get()
         */
        public T get() {
            return value_;
        }

        /*
         * (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((value_ == null) ? 0 : value_.hashCode());
            return result;
        }

        /*
         * (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof Some)) {
                return false;
            }
            @SuppressWarnings("unchecked")
            Some<T> other = (Some<T>) obj;
            if (value_ == null) {
                if (other.value_ != null) {
                    return false;
                }
            } else if (!value_.equals(other.value_)) {
                return false;
            }
            return true;
        }

    }

    /**
     * An instance of this class represents a non existent value of type T.
     * 
     * @author ghais.
     * 
     * @param <T>
     */
    static public final class None<T> extends Option<T> {

        /**
         * Construct an instance of this object.
         */
        public None() {
        }

        /**
         * @throws UnsupportedOperationException
         */
        public T get() {
            throw new UnsupportedOperationException("Cannot resolve value on None");
        }

        /*
         * (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            return 31;
        }

        /*
         * (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }

            if (other == null) {
                return false;
            }
            if (other instanceof Option.None) {
                return true;
            }

            return false;
        }
    }

}
