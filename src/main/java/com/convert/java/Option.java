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

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Represents optional values. Instances of Option are either an instance of Some or None.
 * 
 * The most idiomatic way to use an Option instance is to treat it as a collection in a for loop.
 * 
 * @author ghais.
 * 
 * @param <T>
 */
public abstract class Option<T> implements Iterable<T> {

    /**
     * Returns the option's value.
     * 
     * @return the option's value.
     */
    abstract public T get();

    /**
     * Check if the instance is some value.
     * 
     * @return true if the instance is some value, or false otherwise.
     */
    abstract public boolean isSome();

    /**
     * Return true if the instance is none.
     * 
     * @return true if the instance is none and false otherwise.
     */
    public boolean isNone() {
        return !isSome();
    }

    /**
     * Get an iterator.
     */
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private boolean hasNext = isSome();

            public boolean hasNext() {
                return hasNext;
            }

            public T next() {
                if (hasNext) {
                    hasNext = false;
                    return get();
                } else {
                    throw new NoSuchElementException();
                }
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    /**
     * Return an instance of None if T is null and Some otherwise.
     * 
     * @param t
     * @return
     */
    public static <T> Option<T> Option(T t) {
        if (null == t) {
            return None();
        }
        return Some(t);
    }

    /**
     * An Option factory which returns an instance of {@link None}
     * 
     * help reduces the verbosity of type parameterization in java.
     * 
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> Option<T> None() {
        return (Option<T>) None.NONE;
    }

    /**
     * Return an instance of Option.None of type T.
     * 
     * @param <T>
     * @param cls
     * @return
     */
    public static <T> Option<T> None(Class<? extends T> cls) {
        return None();
    }

    /**
     * An Option factory which creates Some<T>(x) if the argument is not null, and None if it is null.
     * 
     * @param <T>
     * @param <Y extends T>
     * @param value
     * @return
     */
    public static <T, Y extends T> Option<T> Some(Y value) {
        return new Option.Some<T>(checkNotNull(value));
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
        @Override
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
            if (!(obj instanceof Option)) {
                return false;
            }
            @SuppressWarnings("unchecked")
            Option<T> other = (Option<T>) obj;
            if (!other.isSome()) {
                return false;
            }
            if (!this.get().equals(other.get())) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return "Some(" + value_.toString() + ")";
        }

        @Override
        public boolean isSome() {
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
    static public final class None extends Option<Object> {

        private static final None NONE = new None();

        /**
         * Construct an instance of this object.
         */
        private None() {
        }

        /**
         * @throws UnsupportedOperationException
         */
        @Override
        public Object get() {
            throw new UnsupportedOperationException("Can't call get on None");
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
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj == null) {
                return false;
            }
            Option<?> other = (Option<?>) obj;
            if (!other.isNone()) {
                return false;
            }
            return true;
        }

        @Override
        public boolean isSome() {
            return false;
        }
    }

}
