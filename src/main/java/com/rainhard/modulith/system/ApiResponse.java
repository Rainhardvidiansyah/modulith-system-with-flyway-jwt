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



    //TODO: implement metadata and pagination

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


    public static <T> ApiResponse<T> success(int status, String path, String message, T data){
        System.out.println("Di sukses: " + RequestFilter.getStringId());
        return ApiResponse.<T>builder()
                .metadata(Metadata.builder()
                        .requestId(RequestFilter.getStringId())
                        .timestamp(Instant.now())
                        .status(status)
                        .path(path)
                        .message(message)
                        .build())
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> error(int status, String path, String code, String message) {
        System.out.println("Di error: " + RequestFilter.getStringId());
        return ApiResponse.<T>builder()
                .metadata(Metadata.builder()
                        .requestId(RequestFilter.getStringId())
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
