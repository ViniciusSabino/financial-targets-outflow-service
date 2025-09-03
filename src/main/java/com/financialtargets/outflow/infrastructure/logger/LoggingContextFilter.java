package com.financialtargets.outflow.infrastructure.logger;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class LoggingContextFilter extends OncePerRequestFilter {
    private static final String REQUEST_ID = "requestId";
    private static final String SERVICE_NAME = "service";
    private static final String ENV_NAME = "environment";
    @Value("${server.info.app.name}")
    private String serviceName;
    @Value("${spring.profiles.active}")
    private String environment;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String requestId = request.getHeader(REQUEST_ID);
            if (requestId == null || requestId.isBlank()) {
                requestId = UUID.randomUUID().toString();
            }

            MDC.put(REQUEST_ID, requestId);
            MDC.put(SERVICE_NAME, serviceName);
            MDC.put(ENV_NAME, environment);

            response.addHeader(REQUEST_ID, requestId);
            response.addHeader(SERVICE_NAME, serviceName);
            response.addHeader(ENV_NAME, environment);

            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}
