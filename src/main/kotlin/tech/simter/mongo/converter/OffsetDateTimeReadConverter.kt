package tech.simter.mongo.converter

import org.bson.Document
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import java.time.OffsetDateTime
import java.time.ZoneOffset

/**
 * Convert [Document] to [OffsetDateTime] when read from mongodb.
 *
 * See [Overriding Mapping with explicit Converters](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#mapping-explicit-converters).
 *
 * @author RJ
 */
@ReadingConverter
enum class OffsetDateTimeReadConverter : Converter<Document, OffsetDateTime> {
  INSTANCE;

  override fun convert(source: Document): OffsetDateTime {
    return OffsetDateTime.ofInstant(
      source.getDate("dateTime").toInstant(),
      ZoneOffset.of(source.getString("offset"))
    )
  }
}