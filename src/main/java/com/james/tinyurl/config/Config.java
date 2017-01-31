package com.james.tinyurl.config;

import com.james.tinyurl.handler.TinyURLHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by haozhexu on 1/30/17.
 */
@Configuration
public class Config {

    @Bean
    public TinyURLHandler tinyURLHandler() {
        return new TinyURLHandler();
    }
}
