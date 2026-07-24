package com.mambunveasna.student_management_api.exception;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String message;

    public ErrorResponse(LocalDateTime timestamp, int status, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public int getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }
}
