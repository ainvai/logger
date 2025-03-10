package com.ainvai.core.logger.advice.before;

import com.ainvai.core.logger.Level;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Log before entering the target method.
 *
 * @author Andy Lian
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface LogBefore {

  /**
   * @return Class name used as Logger's category name
   */
  Class<?> declaringClass() default void.class;

  /**
   * @return Log Level for entering message
   */
  Level level() default Level.DEFAULT;

  /**
   * @return Entering message template
   */
  String enteringMessage() default "";
}
