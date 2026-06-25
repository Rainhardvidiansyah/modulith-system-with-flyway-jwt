package com.rainhard.modulith.system;


import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private boolean status;

    private String message;

    private T data;

    public ApiResponse(boolean status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

}
