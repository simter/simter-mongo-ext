package tech.simter.mongo.converter

import org.bson.Document
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter
import java.time.ZonedDateTime
import java.util.*

/**
 * Convert [ZonedDateTime] to [Document] when write to mongodb.
 *
 * See [Overriding Mapping with explicit Converters](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#mapping-explicit-converters).
 *
 * @author RJ
 */
@WritingConverter
enum class ZonedDateTimeWriteConverter : Converter<ZonedDateTime, Document> {
  INSTANCE;

  override fun convert(source: ZonedDateTime): Document {
    return Document("dateTime", Date.from(source.toInstant()))
      .append("offset", source.offset.toString())
      .append("zone", source.zone.toString())
  }
}