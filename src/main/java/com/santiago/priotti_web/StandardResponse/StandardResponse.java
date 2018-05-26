package com.santiago.priotti_web.StandardResponse;

public class StandardResponse<T> {

    private StatusResponse status;
    private String message;
    private T data;

    public StandardResponse(StatusResponse status) {
        this.status = status;
    }

    public StandardResponse(StatusResponse status, String message) {
        this.status = status;
        this.message = message;
    }

    public StandardResponse(StatusResponse status, T data) {
        this.status = status;
        this.data = data;
    }

}
