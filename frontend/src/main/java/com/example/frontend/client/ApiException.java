package com.example.frontend.client;

public class ApiException extends RuntimeException {
    private final int statusCode;
    private final boolean unavailable;

    public ApiException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.unavailable = statusCode == 503;
    }

    public int getStatusCode() { return statusCode; }
    public boolean getUnavailable() { return unavailable; }
}