package com.ainvai.core.logger.advice.around;

import com.ainvai.core.logger.AopLoggersProperties;
import com.ainvai.core.logger.Level;
import com.ainvai.core.logger.message.interpolation.ElapsedStringSupplierRegistrar;
import com.ainvai.core.logger.message.interpolation.ElapsedTimeLimitStringSupplierRegistrar;
import com.ainvai.core.logger.message.interpolation.ExceptionStringSupplierRegistrar;
import com.ainvai.core.logger.message.interpolation.JoinPointStringSupplierRegistrar;
import com.ainvai.core.logger.message.interpolation.ReturnValueStringSupplierRegistrar;
import com.ainvai.core.logger.message.interpolation.StringSubstitutor;
import com.ainvai.core.logger.message.interpolation.StringSupplierLookup;
import com.ainvai.core.logger.util.LoggerUtil;

import java.time.Duration;
import java.util.Objects;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class LogAroundService {

  private static final Logger LOGGER = LoggerFactory.getLogger(LogAroundService.class);

  @Autowired
  private StringSubstitutor stringSubstitutor;

  @Autowired
  private JoinPointStringSupplierRegistrar joinPointStringSupplierRegistrar;

  @Autowired
  private ReturnValueStringSupplierRegistrar returnValueStringSupplierRegistrar;

  @Autowired
  private ExceptionStringSupplierRegistrar exceptionStringSupplierRegistrar;

  @Autowired
  private ElapsedStringSupplierRegistrar elapsedStringSupplierRegistrar;

  @Autowired
  private ElapsedTimeLimitStringSupplierRegistrar elapsedTimeLimitStringSupplierRegistrar;

  private final AopLoggersProperties aopLoggersProperties;

  public LogAroundService(final AopLoggersProperties aopLoggersProperties) {
    this.aopLoggersProperties = Objects.requireNonNull(aopLoggersProperties);
  }

  public Object logAround(final ProceedingJoinPoint joinPoint, final LogAround logAround)
      throws Throwable {
    final long enteringTime = System.nanoTime();

    final Logger logger = LoggerUtil.getLogger(logAround.declaringClass(), joinPoint);
    final StringSupplierLookup stringLookup = new StringSupplierLookup();

    logEnteringMessage(joinPoint, logAround, logger, stringLookup);
    final long beforeProceedTime = System.nanoTime();

    try {

      final Object returnValue = joinPoint.proceed();

      final long proceedElapsedTime = System.nanoTime() - beforeProceedTime;
      logExitedMessage(joinPoint, logAround, logger, stringLookup, returnValue);
      logElapsedTime(logAround, logger, stringLookup, proceedElapsedTime);
      logElapsedWarning(logAround, logger, stringLookup, proceedElapsedTime);

      logElapsed(enteringTime, proceedElapsedTime);

      return returnValue;

    } catch (Throwable e) {

      final long proceedElapsedTime = System.nanoTime() - beforeProceedTime;
      logExitedAbnormallyMessage(logAround, logger, stringLookup, e);
      logElapsedTime(logAround, logger, stringLookup, proceedElapsedTime);
      logElapsedWarning(logAround, logger, stringLookup, proceedElapsedTime);

      logElapsed(enteringTime, proceedElapsedTime);
      throw e;
    }
  }

  private void logElapsed(final long enteringTime, final long proceedElapsedTime) {
    LOGGER.debug(
        "[logAround] elapsed [{}]",
        Duration.ofNanos(System.nanoTime() - enteringTime - proceedElapsedTime));
  }

  private boolean isLoggerLevelDisabled(final Logger logger, final Level level) {
    return !(LoggerUtil.isEnabled(logger, level));
  }

  private boolean isIgnoredException(
      final Throwable exception, final Class<? extends Throwable>[] ignoredExceptions) {
    if (exception == null) {
      return true;
    }

    return matchesIgnoreExceptions(exception, ignoredExceptions)
        || matchesIgnoreExceptions(exception, aopLoggersProperties.getIgnoreExceptions());
  }

  private boolean matchesIgnoreExceptions(
      final Throwable exception, final Class<? extends Throwable>[] ignoredExceptions) {
    if (ignoredExceptions == null || ignoredExceptions.length == 0) {
      return false;
    }
    for (Class<? extends Throwable> ignoredException : ignoredExceptions) {
      if (ignoredException == null) {
        continue;
      }
      if (ignoredException.isInstance(exception)) {
        return true;
      }
    }
    return false;
  }

  private void logEnteringMessage(
      final ProceedingJoinPoint joinPoint,
      final LogAround annotation,
      final Logger logger,
      final StringSupplierLookup stringLookup) {
    final Level enteringLevel =
        getLevel(annotation.level(), aopLoggersProperties.getEnteringLevel());
    if (isLoggerLevelDisabled(logger, enteringLevel)) {
      return;
    }

    joinPointStringSupplierRegistrar.register(stringLookup, joinPoint);

    final String enteringMessage =
        stringSubstitutor.substitute(
            getMessage(annotation.enteringMessage(), aopLoggersProperties.getEnteringMessage()),
            stringLookup);
    LoggerUtil.log(logger, enteringLevel, enteringMessage);
  }

  private void logElapsedTime(
      final LogAround annotation,
      final Logger logger,
      final StringSupplierLookup stringLookup,
      final long elapsedTime) {
    final Level elapsedLevel = getLevel(annotation.level(), aopLoggersProperties.getElapsedLevel());
    if (isLoggerLevelDisabled(logger, elapsedLevel)) {
      return;
    }

    elapsedStringSupplierRegistrar.register(stringLookup, elapsedTime);

    final String elapsedMessage =
        stringSubstitutor.substitute(
            getMessage(annotation.elapsedMessage(), aopLoggersProperties.getElapsedMessage()),
            stringLookup);
    LoggerUtil.log(logger, elapsedLevel, elapsedMessage);
  }

  private void logElapsedWarning(
      final LogAround annotation,
      final Logger logger,
      final StringSupplierLookup stringLookup,
      final long elapsedTime) {
    final Level elapsedWarningLevel =
        getLevel(annotation.elapsedWarningLevel(), aopLoggersProperties.getElapsedWarningLevel());
    if (isLoggerLevelDisabled(logger, elapsedWarningLevel)) {
      return;
    }

    if (annotation.elapsedTimeLimit() == 0) {
      return;
    }
    final Duration elapsedTimeLimit =
        Duration.of(annotation.elapsedTimeLimit(), annotation.elapsedTimeUnit());
    if (!elapsedTimeLimit.minusNanos(elapsedTime).isNegative()) {
      return;
    }

    elapsedStringSupplierRegistrar.register(stringLookup, elapsedTime);
    elapsedTimeLimitStringSupplierRegistrar.register(stringLookup, elapsedTimeLimit);

    final String elapsedWarningMessage =
        stringSubstitutor.substitute(
            getMessage(
                annotation.elapsedWarningMessage(),
                aopLoggersProperties.getElapsedWarningMessage()),
            stringLookup);
    LoggerUtil.log(logger, elapsedWarningLevel, elapsedWarningMessage);
  }

  private void logExitedMessage(
      final ProceedingJoinPoint joinPoint,
      final LogAround annotation,
      final Logger logger,
      final StringSupplierLookup stringLookup,
      final Object returnValue) {
    final Level exitedLevel = getLevel(annotation.level(), aopLoggersProperties.getExitedLevel());
    if (isLoggerLevelDisabled(logger, exitedLevel)) {
      return;
    }

    returnValueStringSupplierRegistrar.register(stringLookup, joinPoint, returnValue);

    final String exitedMessage =
        stringSubstitutor.substitute(
            getMessage(annotation.exitedMessage(), aopLoggersProperties.getExitedMessage()),
            stringLookup);
    LoggerUtil.log(logger, exitedLevel, exitedMessage);
  }

  private void logExitedAbnormallyMessage(
      final LogAround annotation,
      final Logger logger,
      final StringSupplierLookup stringLookup,
      final Throwable exception) {
    final Level exitedAbnormallyLevel =
        getLevel(
            annotation.exitedAbnormallyLevel(), aopLoggersProperties.getExitedAbnormallyLevel());

    if (isLoggerLevelDisabled(logger, exitedAbnormallyLevel)
        || isIgnoredException(exception, annotation.ignoreExceptions())) {
      return;
    }

    exceptionStringSupplierRegistrar.register(stringLookup, exception);

    final String exitedAbnormallyMessage =
        stringSubstitutor.substitute(
            getMessage(
                annotation.exitedAbnormallyMessage(),
                aopLoggersProperties.getExitedAbnormallyMessage()),
            stringLookup);

    if (annotation.printStackTrace()) {
      LoggerUtil.logException(logger, exitedAbnormallyLevel, exitedAbnormallyMessage, exception);
    } else {
      LoggerUtil.log(logger, exitedAbnormallyLevel, exitedAbnormallyMessage);
    }
  }

  private Level getLevel(final Level level, final Level defaultLevel) {
    return level == Level.DEFAULT ? defaultLevel : level;
  }

  private String getMessage(final String message, final String defaultMessage) {
    return message.length() > 0 ? message : defaultMessage;
  }
}
