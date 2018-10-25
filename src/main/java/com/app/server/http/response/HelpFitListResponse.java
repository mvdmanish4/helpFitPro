package com.app.server.http.response;

public class HelpFitListResponse {
    public boolean success;
    public Object content;
    public HelpFitListResponseMetaData metadata;
    public HelpFitListResponse(Object content, long total, long offset, long count) {
        this.success = true;
        this.content = content;
        this.metadata = new HelpFitListResponseMetaData(total,offset,count);
    }

    public HelpFitListResponse() {
        this.success = true;
    }

    private class HelpFitListResponseMetaData {
        public long total,offset,count;
        public HelpFitListResponseMetaData(long total, long offset, long count) {
            this.total = total;
            this.offset = offset;
            this.count = count;
        }
    }
}
