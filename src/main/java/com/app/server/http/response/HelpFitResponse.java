package com.app.server.http.response;

public class HelpFitResponse {
    public boolean success = true;
    public Object data;
    public int httpStatusCode = 200;
    public HelpFitResponse(Object dataParam) {
        this.data = dataParam;
    }

    public HelpFitResponse() {
    }
}

