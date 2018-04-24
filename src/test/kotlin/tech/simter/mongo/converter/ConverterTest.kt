package tech.simter.mongo.converter

import org.bson.Document
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.context.ApplicationContext
import org.springframework.data.mongodb.core.convert.DbRefResolver
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import org.springframework.data.mongodb.core.mapping.MongoMappingContext
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*

/**
 * See [MappingMongoConverterUnitTests](https://github.com/spring-projects/spring-data-mongodb/blob/master/spring-data-mongodb/src/test/java/org/springframework/data/mongodb/core/convert/MappingMongoConverterUnitTests.java).
 * [ZonedDateTime with MongoDB](https://stackoverflow.com/questions/41127665/zoneddatetime-with-mongodb#answer-48469443)
 * @author RJ
 */
@ExtendWith(MockitoExtension::class)
class ConverterTest {
  @Mock
  private lateinit var context: ApplicationContext
  @Mock
  private lateinit var resolver: DbRefResolver
  private lateinit var converter: MappingMongoConverter

  data class MyDto(val offsetTime: OffsetDateTime? = null)

  @BeforeEach
  fun setUp() {
    // custom converter
    val conversions = MongoCustomConversions(listOf(
      OffsetDateTimeReadConverter.INSTANCE,
      OffsetDateTimeWriteConverter.INSTANCE
    ))

    val mappingContext = MongoMappingContext()
    mappingContext.setApplicationContext(context)
    mappingContext.setSimpleTypeHolder(conversions.simpleTypeHolder)
    mappingContext.afterPropertiesSet()
    //mappingContext.getPersistentEntity(MyDto::class.java)

    // register converter
    converter = MappingMongoConverter(resolver, mappingContext)
    converter.setCustomConversions(conversions)
    converter.afterPropertiesSet()
  }

  @Test
  fun offsetDateTime_WriteNotNull() {
    val dto = MyDto(offsetTime = OffsetDateTime.now())
    val document = org.bson.Document()
    converter.write(dto, document)
    val uploadOn = document["offsetTime"] as Document
    assertTrue(dto.offsetTime!!.isEqual(OffsetDateTime.ofInstant(
      uploadOn.getDate("dateTime").toInstant(),
      ZoneOffset.of(uploadOn.getString("offset")))
    ))
  }

  @Test
  fun offsetDateTime_WriteNull() {
    val document = Document()
    converter.write(MyDto(offsetTime = null), document)
    assertNull(document["offsetTime"])
  }

  @Test
  fun offsetDateTime_ReadNotNull() {
    val now = OffsetDateTime.now()
    val document = Document("offsetTime",
      Document("dateTime", Date.from(now.toInstant())).append("offset", now.offset.toString())
    )
    val dto = converter.read(MyDto::class.java, document)
    assertTrue(dto.offsetTime!!.isEqual(now))
  }

  @Test
  fun offsetDateTime_ReadNotExists() {
    val dto = converter.read(MyDto::class.java, Document())
    assertNull(dto.offsetTime)
  }
}