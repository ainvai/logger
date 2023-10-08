package com.ainvai.core.logger.message.interpolation;

/**
 * Used by {@link StringSubstitutor} to lookup for String value by key.
 *
 * @author Andy Lian
 */
public interface StringLookup {

  String lookup(String key);
}
