package com.lucasbandeira.resultados.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.core.json.JsonReadFeature;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.datatype.jsr310.JavaTimeModule;


@Configuration
public class SpringConfig {

    @Bean
    public JsonMapper jsonMapper() {

         return JsonMapper.
                builder()
                .addModule(new JavaTimeModule())
                 .changeDefaultPropertyInclusion(incl -> incl.withValueInclusion(JsonInclude.Include.NON_NULL).withContentInclusion(JsonInclude.Include.NON_NULL))
                 .build();
    }
}
