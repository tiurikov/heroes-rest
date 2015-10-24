package com.heroes.web;

import com.heroes.Starter;
import com.heroes.model.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;

/**
 *
 * @author Stanislav Tyurikov
 */
@WebIntegrationTest
@SpringApplicationConfiguration(classes = Starter.class)
public abstract class AbstractSystemTestBase extends AbstractTestNGSpringContextTests
{
    @Autowired
    protected HeroRepository heroRepository;


    @BeforeMethod
    protected void cleanup()
    {
        heroRepository.deleteAll();
    }
}
