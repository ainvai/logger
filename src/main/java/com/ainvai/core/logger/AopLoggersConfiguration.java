package com.ainvai.core.logger;

import com.ainvai.core.logger.advice.after.returning.LogAfterReturningConfiguration;
import com.ainvai.core.logger.advice.after.throwing.LogAfterThrowingConfiguration;
import com.ainvai.core.logger.advice.before.LogBeforeConfiguration;
import com.ainvai.core.logger.message.interpolation.StringSubstitutorConfiguration;
import com.ainvai.core.logger.advice.around.LogAroundConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@EnableConfigurationProperties({AopLoggersProperties.class})
@Import({
    StringSubstitutorConfiguration.class,
    LogAfterReturningConfiguration.class,
    LogAfterThrowingConfiguration.class,
    LogAroundConfiguration.class,
    LogBeforeConfiguration.class
})
public class AopLoggersConfiguration {

}
