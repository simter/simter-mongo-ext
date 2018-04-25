# Simter MongoDB Extensions

Maven Usage  :

```xml
<dependency>
  <groupId>tech.simter</groupId>
  <artifactId>simter-mongo-ext</artifactId>
  <version>${the-version}</version>
</dependency>
```

## Converters

Name                           | Read from / Write to
-------------------------------|----------------------
[OffsetDateTimeReadConverter]  | {"dateTime": ISODate("2018-04-25T10:30:27.181Z"), "offset" : "+08:00"}
[OffsetDateTimeWriteConverter] | {"dateTime": ISODate("2018-04-25T10:30:27.181Z"), "offset" : "+08:00"}
[ZonedDateTimeReadConverter]   | {"dateTime": ISODate("2018-04-25T10:30:27.181Z"), "offset" : "+08:00", "zone": "Asia/Shanghai"}
[ZonedDateTimeWriteConverter]  | {"dateTime": ISODate("2018-04-25T10:30:27.181Z"), "offset" : "+08:00", "zone": "Asia/Shanghai"}


[OffsetDateTimeReadConverter]: https://github.com/simter/simter-mongo-ext/blob/master/src/main/kotlin/tech/simter/mongo/converter/OffsetDateTimeReadConverter.kt
[OffsetDateTimeWriteConverter]: https://github.com/simter/simter-mongo-ext/blob/master/src/main/kotlin/tech/simter/mongo/converter/OffsetDateTimeWriteConverter.kt
[ZonedDateTimeReadConverter]: https://github.com/simter/simter-mongo-ext/blob/master/src/main/kotlin/tech/simter/mongo/converter/ZonedDateTimeReadConverter.kt
[ZonedDateTimeWriteConverter]: https://github.com/simter/simter-mongo-ext/blob/master/src/main/kotlin/tech/simter/mongo/converter/ZonedDateTimeWriteConverter.kt

Usage by any one of bellows :

1. `@Import(tech.simter.mongo.ModuleConfiguration::class)`
2. `@ComponentScan("tech.simter.mongo.ModuleConfiguration")`
3. Manual register MongoCustomConversions :
    ```
    @Bean
    fun customConversions(): MongoCustomConversions {
      return MongoCustomConversions(listOf(
        OffsetDateTimeReadConverter.INSTANCE, 
        OffsetDateTimeWriteConverter.INSTANCE,
        ZonedDateTimeReadConverter.INSTANCE, 
        ZonedDateTimeWriteConverter.INSTANCE
      ))
    }
    ```