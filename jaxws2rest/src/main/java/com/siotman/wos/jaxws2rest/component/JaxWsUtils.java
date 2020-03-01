package com.siotman.wos.jaxws2rest.component;

import org.springframework.stereotype.Component;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JaxWsUtils {
    public static void setHeaderOnPort(BindingProvider port, final String key, final List<String> value) {
        Map<String, Object> requestContext = port.getRequestContext();

        Map<String, List<String>> headers = new HashMap<>();
        headers.put(key, value);

        requestContext.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
    }
}
