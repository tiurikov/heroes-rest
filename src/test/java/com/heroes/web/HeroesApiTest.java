package com.heroes.web;

import com.heroes.Starter;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

import static com.heroes.web.HeroesApi.SERVICE_IS_UP;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 * @author Stanislav Tyurikov
 */
@WebIntegrationTest
@SpringApplicationConfiguration(classes = Starter.class)
public class HeroesApiTest extends AbstractTestNGSpringContextTests
{
    private static final String PING_URL = "http://localhost:8080/api/ping";
    private static final RestTemplate restTemplate = new RestTemplate();

    @Test
    public void checkServiceIsUp()
    {
        ResponseEntity<String> response = restTemplate.getForEntity(PING_URL, String.class);
        assertThat(response.getBody(), is(SERVICE_IS_UP));
    }
}
