package com.sample.salesforce.functions.utility.functional;

/**
 * Functional interface similar to {@link java.util.function.Supplier} but allows throwing checked exceptions.
 */
@FunctionalInterface
public interface ThrowingSupplier<T> {
    T get() throws Exception;
}