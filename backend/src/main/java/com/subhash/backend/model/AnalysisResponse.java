package com.subhash.backend.model;

public class AnalysisResponse {

    private String message;

    public AnalysisResponse() {
    }

    public AnalysisResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
