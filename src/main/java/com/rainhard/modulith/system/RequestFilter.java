package com.rainhard.modulith.system;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestFilter.class);

    public static final String REQUEST_ID_HEADER = "X-Request-ID";

    private static final InheritableThreadLocal<String> currentRequestId = new InheritableThreadLocal<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {



        LOGGER.info(" Filter works: {}", request.getRequestURI());

        String requestId = request.getHeader(REQUEST_ID_HEADER);

        if(requestId == null || requestId.isBlank()){
            requestId = UUID.randomUUID().toString();
        }

        currentRequestId.set(requestId);

        LOGGER.info("Current request id: {}", currentRequestId.get());


        request.setAttribute("requestId", requestId);
        response.setHeader(REQUEST_ID_HEADER, requestId);

        try{
            filterChain.doFilter(request, response);
        }finally {
            currentRequestId.remove();

        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().startsWith("/actuator");
    }


    public static String getStringId(){
        return currentRequestId.get();
    }


}
