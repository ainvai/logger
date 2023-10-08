package com.ainvai.core.logger.message.interpolation;

/**
 * Factory returning a {@link ToStringStrategy} candidate for the Object or
 * {@link ObjectToStringStrategy}.
 *
 * @author Andy Lian
 */
public interface ToStringStrategyFactory {

  ToStringStrategy findOrDefault(Object object);
}
