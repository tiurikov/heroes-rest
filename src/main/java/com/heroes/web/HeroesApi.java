package com.heroes.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Stanislav Tyurikov
 */
@RestController
@RequestMapping("/api/v1")
public class HeroesApi
{
    public static final String SERVICE_IS_UP = "service is up";
    
    @RequestMapping("/ping")
    public String ping()
    {
        return SERVICE_IS_UP;
    }
}
