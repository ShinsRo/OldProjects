package com.siotman.batchwos.wsclient.lamr.domain;

public enum TARGET_DB_TYPE {
    WOS("request-api-wos-template.xml"),
    JCR("request-api-jcr-template.xml"),
    DRCI("request-api-drci-template.xml");

    private String resourceFileName;

    TARGET_DB_TYPE(String resourceFileName) {
        this.resourceFileName = resourceFileName;
    }

    public String getResourceFileName() { return resourceFileName; }
}
