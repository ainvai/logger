package com.ainvai.core.logger.message.interpolation;

import java.util.function.Supplier;

/**
 * Strategy Interface for implementation to register String {@link Supplier} to
 * {@link StringSupplierLookup}.
 *
 * @author Andy Lian
 */
public interface StringSupplierRegistrar<T> {

  void register(StringSupplierLookup stringSupplierLookup, T source);
}
