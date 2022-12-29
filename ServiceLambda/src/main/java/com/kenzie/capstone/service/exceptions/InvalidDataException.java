package com.kenzie.capstone.service.exceptions;

import java.util.HashMap;
import java.util.Map;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String msg) { super(msg);}

    public Map<String, Object> errorPayload() {
        Map<String, Object> erroryPayload = new HashMap<>();
        erroryPayload.put("errorType", "invalid_data");
        erroryPayload.put("message", this.getMessage());
        return erroryPayload;
    }
}
