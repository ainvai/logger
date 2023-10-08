package com.ainvai.core.logger;

import static org.assertj.core.api.Assertions.assertThat;

import com.ainvai.core.logger.advice.after.returning.LogAfterReturningConfiguration;
import com.ainvai.core.logger.advice.after.throwing.LogAfterThrowingConfiguration;
import com.ainvai.core.logger.advice.around.LogAroundConfiguration;
import com.ainvai.core.logger.advice.before.LogBeforeConfiguration;
import com.ainvai.core.logger.message.interpolation.StringSubstitutorConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

/**
 * Tests for {@link AopLoggersConfiguration}.
 *
 * @author Andy Lian
 */
class AopLoggersConfigurationTests {

  private final ApplicationContextRunner runner =
      new ApplicationContextRunner().withUserConfiguration(AopLoggersConfiguration.class);

  @Test
  void aopLoggersPropertiesNotNull() {
    runner.run(
        (context) -> {
          assertThat(context.getBean(AopLoggersProperties.class))
              .isNotNull()
              .isExactlyInstanceOf(AopLoggersProperties.class);
        });
  }

  @Test
  void stringSubstitutorConfigurationNotNull() {
    runner.run(
        (context) -> {
          assertThat(context.getBean(StringSubstitutorConfiguration.class))
              .isNotNull()
              .isExactlyInstanceOf(StringSubstitutorConfiguration.class);
        });
  }

  @Test
  void logAfterReturningConfigurationNotNull() {
    runner.run(
        (context) -> {
          assertThat(context.getBean(LogAfterReturningConfiguration.class))
              .isNotNull()
              .isExactlyInstanceOf(LogAfterReturningConfiguration.class);
        });
  }

  @Test
  void logAfterThrowingConfigurationNotNull() {
    runner.run(
        (context) -> {
          assertThat(context.getBean(LogAfterThrowingConfiguration.class))
              .isNotNull()
              .isExactlyInstanceOf(LogAfterThrowingConfiguration.class);
        });
  }

  @Test
  void logAroundConfigurationNotNull() {
    runner.run(
        (context) -> {
          assertThat(context.getBean(LogAroundConfiguration.class))
              .isNotNull()
              .isExactlyInstanceOf(LogAroundConfiguration.class);
        });
  }

  @Test
  void logBeforeConfigurationNotNull() {
    runner.run(
        (context) -> {
          assertThat(context.getBean(LogBeforeConfiguration.class))
              .isNotNull()
              .isExactlyInstanceOf(LogBeforeConfiguration.class);
        });
  }
}
