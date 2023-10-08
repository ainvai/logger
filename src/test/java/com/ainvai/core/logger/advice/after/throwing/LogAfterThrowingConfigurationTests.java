package com.ainvai.core.logger.advice.after.throwing;

import static org.assertj.core.api.Assertions.assertThat;

import com.ainvai.core.logger.AopLoggersProperties;
import com.ainvai.core.logger.message.interpolation.StringSubstitutorConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

/**
 * Tests for {@link LogAfterThrowingConfiguration}.
 *
 * @author Andy Lian
 */
class LogAfterThrowingConfigurationTests {

  private final ApplicationContextRunner runner =
      new ApplicationContextRunner()
          .withUserConfiguration(
              StringSubstitutorConfiguration.class, LogAfterThrowingConfiguration.class)
          .withBean(AopLoggersProperties.class);

  @Test
  void logAfterThrowingAdviceNotNull() {
    runner.run(
        (context) -> {
          assertThat(context.getBean(LogAfterThrowingAdvice.class))
              .isNotNull()
              .isExactlyInstanceOf(LogAfterThrowingAdvice.class);
        });
  }

  @Test
  void logAfterThrowingServiceNotNull() {
    runner.run(
        (context) -> {
          assertThat(context.getBean(LogAfterThrowingService.class))
              .isNotNull()
              .isExactlyInstanceOf(LogAfterThrowingService.class);
        });
  }
}
