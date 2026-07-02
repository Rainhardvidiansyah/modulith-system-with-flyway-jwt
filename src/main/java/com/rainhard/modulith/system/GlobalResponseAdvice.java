package com.rainhard.modulith.system;

import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.time.Instant;


@RestControllerAdvice
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !returnType.getParameterType().equals(ApiResponse.class)
                && !returnType.getDeclaringClass().equals(GlobalExceptionHandling.class);
    }

    @Override
    public @Nullable Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        int status = ((ServletServerHttpResponse) response).getServletResponse().getStatus();

        String path = request.getURI().getPath();


        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        String requestId = (String) servletRequest.getAttribute("requestId");

        return ApiResponse.<Object>builder()
                .metadata(ApiResponse.Metadata.builder()
                        .requestId(requestId)
                        .timestamp(Instant.now())
                        .status(status)
                        .path(request.getURI().getPath())
                        .message("OK")
                        .build())
                .data(body)
                .build();
    }





}
