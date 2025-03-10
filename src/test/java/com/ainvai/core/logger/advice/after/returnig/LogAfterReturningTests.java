package com.ainvai.core.logger.advice.after.returnig;

import static org.assertj.core.api.Assertions.assertThat;

import com.ainvai.core.logger.Level;
import com.ainvai.core.logger.advice.after.returning.LogAfterReturning;
import com.ainvai.core.logger.advice.before.LogBefore;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link LogAfterReturning}.
 *
 * @author Andy Lian
 */
class LogAfterReturningThrowingTests {

  @Test
  void declaringClass_defaultValue() {
    @LogBefore
    class Local {

    }
    ;

    final LogBefore annotation = Local.class.getAnnotation(LogBefore.class);
    assertThat(annotation.declaringClass()).isEqualTo(void.class);
  }

  @Test
  void declaringClass_givenAttributeValue() {
    @LogBefore(declaringClass = Local.class)
    class Local {

    }
    ;

    final LogBefore annotation = Local.class.getAnnotation(LogBefore.class);
    assertThat(annotation.declaringClass()).isEqualTo(Local.class);
  }

  @Test
  void level_defaultValue() {
    @LogAfterReturning
    class Local {

    }
    ;

    final LogAfterReturning annotation = Local.class.getAnnotation(LogAfterReturning.class);
    Assertions.assertThat(annotation.level()).isEqualTo(Level.DEFAULT);
  }

  @Test
  void level_givenAttributeValue() {
    @LogAfterReturning(level = Level.DEBUG)
    class Local {

    }
    ;

    final LogAfterReturning annotation = Local.class.getAnnotation(LogAfterReturning.class);
    Assertions.assertThat(annotation.level()).isEqualTo(Level.DEBUG);
  }

  @Test
  void exitedMessage_defaultValue() {
    @LogAfterReturning
    class Local {

    }
    ;

    final LogAfterReturning annotation = Local.class.getAnnotation(LogAfterReturning.class);
    assertThat(annotation.exitedMessage()).isEmpty();
  }

  @Test
  void exitedMessage_givenAttributeValue() {
    @LogAfterReturning(exitedMessage = "foo")
    class Local {

    }
    ;

    final LogAfterReturning annotation = Local.class.getAnnotation(LogAfterReturning.class);
    assertThat(annotation.exitedMessage()).isEqualTo("foo");
  }
}
