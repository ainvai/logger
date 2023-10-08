package com.ainvai.core.logger.advice.after.throwing;

import com.ainvai.core.logger.AopLoggersProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class LogAfterThrowingConfiguration {

  @Bean
  public LogAfterThrowingAdvice logAfterThrowingAdvice() {
    return new LogAfterThrowingAdvice();
  }

  @Bean
  public LogAfterThrowingService logAfterThrowingService(
      final AopLoggersProperties aopLoggersProperties) {
    return new LogAfterThrowingService(aopLoggersProperties);
  }
}
