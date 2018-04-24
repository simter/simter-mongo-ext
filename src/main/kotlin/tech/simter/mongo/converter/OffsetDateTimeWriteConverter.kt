package tech.simter.mongo.converter

import org.bson.Document
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter
import java.time.OffsetDateTime
import java.util.*

/**
 * Convert [OffsetDateTime] to [Document] when write to mongodb.
 *
 * See [Overriding Mapping with explicit Converters](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#mapping-explicit-converters).
 *
 * @author RJ
 */
@WritingConverter
enum class OffsetDateTimeWriteConverter : Converter<OffsetDateTime, Document> {
  INSTANCE;

  override fun convert(source: OffsetDateTime): Document {
    return Document("dateTime", Date.from(source.toInstant()))
      .append("offset", source.offset.toString())
  }
}