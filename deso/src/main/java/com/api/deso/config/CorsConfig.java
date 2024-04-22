package com.api.deso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("https://main.dvp8yr6clgch.amplifyapp.com");
        config.addAllowedOrigin("https://main.d3o8ra2ll7lmi.amplifyapp.com");
        config.addAllowedOrigin("https://www.nearby-event.kr");
        config.addAllowedHeader("*");
        config.setExposedHeaders(Collections.singletonList("Authorization, Id"));
        config.addExposedHeader("Authorization");
        config.addExposedHeader("Id");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
