package com.heroes.utils;

import java.net.URI;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.PUT;

/**
 *
 * @author Stanislav Tyurikov
 */
public class RestUtils
{
    private static final String BASE_URL = "http://localhost:8080/api/v1";
    private static final RestTemplate restTemplate;


    static {
        restTemplate = new RestTemplate();
        // we are going to analyze the status codes as part of the application protocol,
        // so no default error handler needed. 
        restTemplate.setErrorHandler(new DoNothingErrorHandler());
    }


    public static final URI apiURI(String urn)
    {
        return URI.create(BASE_URL + urn);
    }


    private static <T> ResponseEntity<T> get(String urn, Class<T> clazz)
    {
        return restTemplate.getForEntity(apiURI(urn), clazz);
    }


    public static <T> T getBody(String urn, Class<T> clazz)
    {
        return get(urn, clazz).getBody();
    }


    public static HttpStatus getStatus(String urn)
    {
        return get(urn, Object.class).getStatusCode();
    }


    public static ResponseEntity put(String urn, Object entity)
    {
        return restTemplate.exchange(apiURI(urn), PUT, new HttpEntity(entity), Object.class);
    }


    public static ResponseEntity put(String urn)
    {
        return restTemplate.exchange(apiURI(urn), PUT, HttpEntity.EMPTY, Object.class);
    }


    public static ResponseEntity delete(String urn)
    {
        return restTemplate.exchange(apiURI(urn), DELETE, HttpEntity.EMPTY, Object.class);
    }
}
