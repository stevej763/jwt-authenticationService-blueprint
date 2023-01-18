package com.steve.authenticationService.configuration.mongo;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mongo")
public class MongoProperties {

    private final String database;
    private final String port;
    private final String host;

    public MongoProperties(String database, String port, String host) {
        this.database = database;
        this.port = port;
        this.host = host;
    }

    public String getDatabase() {
        return database;
    }

    public String getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }
}
