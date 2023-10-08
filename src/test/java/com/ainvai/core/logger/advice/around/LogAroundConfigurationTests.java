package com.ainvai.core.logger.advice.around;

import static org.assertj.core.api.Assertions.assertThat;

import com.ainvai.core.logger.AopLoggersProperties;
import com.ainvai.core.logger.message.interpolation.StringSubstitutorConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

/**
 * Tests for {@link LogAroundConfiguration}.
 *
 * @author Andy Lian
 */
class LogAroundConfigurationTests {

  private final ApplicationContextRunner runner =
      new ApplicationContextRunner()
          .withUserConfiguration(StringSubstitutorConfiguration.class, LogAroundConfiguration.class)
          .withBean(AopLoggersProperties.class);

  @Test
  void logAroundAdviceNotNull() {
    runner.run(
        (context) -> {
          assertThat(context.getBean(LogAroundAdvice.class))
              .isNotNull()
              .isExactlyInstanceOf(LogAroundAdvice.class);
        });
  }

  @Test
  void logAroundServiceNotNull() {
    runner.run(
        (context) -> {
          assertThat(context.getBean(LogAroundService.class))
              .isNotNull()
              .isExactlyInstanceOf(LogAroundService.class);
        });
  }
}
