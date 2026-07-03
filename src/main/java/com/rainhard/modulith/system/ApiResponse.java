package com.rainhard.modulith.system;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;


@AllArgsConstructor
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private Metadata metadata;
    private T data;
    private ErrorDetail error;


    @Getter
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Metadata{
        private String requestId;
        private Instant timestamp;
        private int status;
        private String path;
        private String message;
    }

    @Getter
    @Builder
    public static class ErrorDetail{
        private String code;
        private String message;
    }


    public static <T> ApiResponse<T> success(String requestId, int status, String path, String message, T data){
        return ApiResponse.<T>builder()
                .metadata(Metadata.builder()
                        .requestId(requestId)
                        .timestamp(Instant.now())
                        .status(status)
                        .path(path)
                        .message(message)
                        .build())
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> error(String requestId, int status, String path, String code, String message) {
        return ApiResponse.<T>builder()
                .metadata(Metadata.builder()
                        .requestId(requestId)
                        .timestamp(Instant.now())
                        .status(status)
                        .path(path)
                        .build())
                .error(ErrorDetail.builder()
                .code(code)
                .message(message).build())
                .build();
    }

}

//TODO: implement metadata and pagination