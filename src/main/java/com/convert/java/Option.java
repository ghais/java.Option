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

import java.util.ArrayList;
import java.util.Collection;

import com.google.common.base.Function;

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
    abstract public boolean isNone();

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
     * Return an instance of Option.None of type T.
     * 
     * @param <T>
     * @param cls
     * @return
     */
    public static <T> Option<T> None(Class<? extends T> cls) {
        return new Option.None<T>();
    }

    /**
     * An Option factory which returns an instance of {@link None}
     * 
     * help reduces the verbosity of type parameterization in java.
     * 
     * @param t
     *            thrown exception.
     * @param <T>
     * @return
     */
    public static <T> Option<T> None(Throwable t) {
        return new Option.None<T>(t);
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
        if (null == value) {
            return Option.None();
        } else {
            return new Option.Some<T>(value);
        }
    }

    /**
     * Return all the {@link Some} values.
     * 
     * @param <T>
     * @param ts
     * @return
     */
    public static <T> Collection<T> cat(Iterable<Option<T>> ts) {
        Collection<T> result = new ArrayList<T>();
        for (Option<T> t : ts) {
            if (t instanceof Some) {
                result.add(t.get());
            }
        }
        return result;
    }

    /**
     * Apply the function to all {@link Some} values.
     * 
     * @param <F>
     * @param <T>
     * @param function
     * @param values
     * @return
     */
    public static <F, T> Collection<T> map(Function<? super F, ? extends T> function, Iterable<Option<F>> values) {
        Collection<T> result = new ArrayList<T>();
        for (Option<F> value : values) {
            if (value instanceof Some) {
                result.add(function.apply(value.get()));
            }
        }
        return result;
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
            return value_.toString();
        }

        @Override
        public boolean isSome() {
            return true;
        }

        @Override
        public boolean isNone() {
            return false;
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

        private Throwable t;

        /**
         * Construct an instance of this object.
         */
        public None() {
            t = new UnsupportedOperationException("Cannot resolve value on None");
        }

        /**
         * Create an instance of None where this.get() will throw a {@link RuntimeException} caused by t.
         * 
         * @param t
         */
        public None(Throwable t) {
            this.t = t;
        }

        /**
         * @throws UnsupportedOperationException
         */
        @Override
        public T get() {
            throw new RuntimeException(this.t);
        }

        /**
         * Get the exception if any.
         * 
         * @return the current throwable t or null.
         */
        public Throwable getThrowable() {
            return this.t;
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
            @SuppressWarnings("unchecked")
            Option<T> other = (Option<T>) obj;
            if (!other.isNone()) {
                return false;
            }
            return true;
        }

        @Override
        public boolean isSome() {
            return false;
        }

        @Override
        public boolean isNone() {
            return true;
        }
    }

}
