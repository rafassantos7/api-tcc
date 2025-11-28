package com.example.levelUp.config;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

@Configuration
public class JacksonConfig {

    @Bean
    Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
    return builder -> {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      JavaTimeModule module = new JavaTimeModule();
      module.addSerializer(LocalDate.class, new LocalDateSerializer(formatter));
      module.addDeserializer(LocalDate.class, new LocalDateDeserializer(formatter));

      builder.modules(module);
      builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
      builder.timeZone(TimeZone.getTimeZone("UTC"));
      builder.simpleDateFormat("dd/MM/yyyy");

      builder.deserializerByType(String.class, new JsonDeserializer<String>() {
        @Override
        public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

          String valor = p.getValueAsString();

          return (valor != null) ? valor.trim() : null;
        }
      });

    };
  }
}
