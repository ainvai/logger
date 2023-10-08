package com.ainvai.core.logger.advice.after.returning;

import com.ainvai.core.logger.AopLoggersProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class LogAfterReturningConfiguration {

  @Bean
  public LogAfterReturningAdvice logAfterReturningAdvice() {
    return new LogAfterReturningAdvice();
  }

  @Bean
  public LogAfterReturningService logAfterReturningService(
      final AopLoggersProperties aopLoggersProperties) {
    return new LogAfterReturningService(aopLoggersProperties);
  }
}
