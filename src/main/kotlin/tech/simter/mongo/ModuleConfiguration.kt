package tech.simter.mongo

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import org.springframework.data.mongodb.core.mapping.event.LoggingEventListener
import tech.simter.mongo.converter.OffsetDateTimeReadConverter
import tech.simter.mongo.converter.OffsetDateTimeWriteConverter
import tech.simter.mongo.converter.ZonedDateTimeReadConverter
import tech.simter.mongo.converter.ZonedDateTimeWriteConverter
import java.time.OffsetDateTime
import java.time.ZonedDateTime

private const val MODULE_PACKAGE = "tech.simter.mongo"

/**
 * All configuration for this module.
 *
 * Auto register [LoggingEventListener] and [OffsetDateTime], [ZonedDateTime] converter.
 *
 * @author RJ
 */
@Configuration("$MODULE_PACKAGE.ModuleConfiguration")
open class ModuleConfiguration {
  private val logger = LoggerFactory.getLogger(ModuleConfiguration::class.java)

  @Bean
  @ConditionalOnProperty(name = ["simter.mongo.enabled-logging-event-listener"], havingValue = "true")
  @ConditionalOnMissingBean
  open fun simterMongoLoggingEventListener(): LoggingEventListener {
    logger.warn("instance a LoggingEventListener bean for mongodb")
    return LoggingEventListener()
  }

  /**
   * Register custom [OffsetDateTime] and [ZonedDateTime] conversions.
   */
  @Bean
  @ConditionalOnMissingBean
  open fun customConversions(): MongoCustomConversions {
    logger.warn("register OffsetDateTime and ZonedDateTime converter for mongodb")
    return MongoCustomConversions(listOf(
      OffsetDateTimeReadConverter.INSTANCE,
      OffsetDateTimeWriteConverter.INSTANCE,
      ZonedDateTimeReadConverter.INSTANCE,
      ZonedDateTimeWriteConverter.INSTANCE
    ))
  }
}