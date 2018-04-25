package tech.simter.mongo.converter

import org.bson.Document
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * Convert [Document] to [ZonedDateTime] when read from mongodb.
 *
 * See [Overriding Mapping with explicit Converters](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#mapping-explicit-converters).
 *
 * @author cjw
 * @author RJ
 */
@ReadingConverter
enum class ZonedDateTimeReadConverter : Converter<Document, ZonedDateTime> {
  INSTANCE;

  override fun convert(source: Document): ZonedDateTime {
    return ZonedDateTime.ofInstant(
      source.getDate("dateTime").toInstant(),
      ZoneId.of(source.getString("zone"))
    )
  }
}