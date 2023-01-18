package com.steve.authenticationService.configuration.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.UuidRepresentation;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@ConfigurationPropertiesScan("com.steve.authenticationService.configuration.mongo")
public class MongoConfiguration {

    MongoProperties mongoProperties;

    public MongoConfiguration(MongoProperties mongoProperties) {
        this.mongoProperties = mongoProperties;
    }

    @Bean
    public MongoTemplate userDao() {
        return new MongoTemplate(mongoClient(), mongoProperties.getDatabase());
    }

    public MongoClient mongoClient() {
        ConnectionString connectionString = getConnectionString();
        MongoClientSettings mongoClientSettings = mongoClientSettings(connectionString);

        return MongoClients.create(mongoClientSettings);
    }

    private MongoClientSettings mongoClientSettings(ConnectionString connectionString) {
        return MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .build();
    }

    private ConnectionString getConnectionString() {
        System.out.println(mongoProperties.getHost());
        String hostname = mongoProperties.getHost();
        String port = mongoProperties.getPort();
        return new ConnectionString("mongodb://" + hostname + ":" + port);
    }
}
