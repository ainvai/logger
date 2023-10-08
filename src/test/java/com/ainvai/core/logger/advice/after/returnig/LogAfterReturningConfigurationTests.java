package com.ainvai.core.logger.advice.after.returnig;

import static org.assertj.core.api.Assertions.assertThat;

import com.ainvai.core.logger.AopLoggersProperties;
import com.ainvai.core.logger.advice.after.returning.LogAfterReturningAdvice;
import com.ainvai.core.logger.advice.after.returning.LogAfterReturningConfiguration;
import com.ainvai.core.logger.advice.after.returning.LogAfterReturningService;
import com.ainvai.core.logger.message.interpolation.StringSubstitutorConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

/**
 * Tests for {@link LogAfterReturningConfiguration}.
 *
 * @author Andy Lian
 */
class LogAfterReturningConfigurationTests {

  private final ApplicationContextRunner runner =
      new ApplicationContextRunner()
          .withUserConfiguration(
              StringSubstitutorConfiguration.class, LogAfterReturningConfiguration.class)
          .withBean(AopLoggersProperties.class);

  @Test
  void logAfterReturningAdviceNotNull() {
    runner.run(
        (context) -> {
          assertThat(context.getBean(LogAfterReturningAdvice.class))
              .isNotNull()
              .isExactlyInstanceOf(LogAfterReturningAdvice.class);
        });
  }

  @Test
  void logAfterReturningServiceNotNull() {
    runner.run(
        (context) -> {
          assertThat(context.getBean(LogAfterReturningService.class))
              .isNotNull()
              .isExactlyInstanceOf(LogAfterReturningService.class);
        });
  }
}
