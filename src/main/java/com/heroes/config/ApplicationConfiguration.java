package com.heroes.config;

import com.heroes.model.HeroRepository;
import com.heroes.model.InMemoryHeroRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Stanislav Tyurikov
 */
@Configuration
public class ApplicationConfiguration
{
    @Bean
    public HeroRepository heroRepository()
    {
        return new InMemoryHeroRepository();
    }
}
