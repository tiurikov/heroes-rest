package com.heroes.utils;

import java.io.IOException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

/**
 *
 * @author Stanislav Tyurikov
 */
public class DoNothingErrorHandler extends DefaultResponseErrorHandler
{
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException
    {
        return false;
    }
}
