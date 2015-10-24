package com.heroes.utils;

import java.net.URI;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.DefaultResponseErrorHandler;
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
        // we will analyze the status codes in the tests as part of the application protocol, 
        // so no default error handler needed. Unfortunately this is the only way to override error default handling 
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler()
        {
            @Override
            protected boolean hasError(HttpStatus statusCode)
            {
                return false;
            }
        });
    }


    private static <T> ResponseEntity<T> get(String urn, Class<T> clazz)
    {
        return restTemplate.getForEntity(withBase(urn), clazz);
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
        return restTemplate.exchange(withBase(urn), PUT, new HttpEntity(entity), Object.class);
    }


    public static ResponseEntity put(String urn)
    {
        return restTemplate.exchange(withBase(urn), PUT, HttpEntity.EMPTY, Object.class);
    }


    public static ResponseEntity delete(String urn)
    {
        return restTemplate.exchange(withBase(urn), DELETE, HttpEntity.EMPTY, Object.class);
    }


    public static final URI withBase(String urn)
    {
        return URI.create(BASE_URL + urn);
    }
}
