package com.example.demo.DatabaseConfiguration;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Data
@Configuration
public class DatabaseConfiguration {

    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.port}")
    private Integer port;

    @Value("${spring.data.mongodb.username}")
    private String userName;

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Value("${spring.data.mongodb.password}")
    private String password;

    public static final String PROFILE_DEV = "dev";
    public static final String MONGO_HOST = "MONGO_HOST";
    public static final String MONGO_PORT = "MONGO_PORT";
    public static final String MONGO_USERNAME = "MONGO_USERNAME";
    public static final String MONGO_PASSWORD = "MONGO_PASSWORD";
    public static final String MONGO_DATABASE = "MONGO_DATABASE";

    @PostConstruct
    public void setDataFromEnvironment() {
        this.setHost(host);
        this.setPort(port);
        this.setUserName(userName);
        this.setPassword(password);
        this.setDatabase(database);
    }

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        // Set credentials
        MongoCredential credential = MongoCredential.createCredential(userName, database, password.toCharArray());
        ServerAddress serverAddress = new ServerAddress(host, port);

        // Mongo Client
        MongoClient mongoClient = new MongoClient(serverAddress, Arrays.asList(credential));

        // Mongo DB Factory
        SimpleMongoDbFactory simpleMongoDbFactory = new SimpleMongoDbFactory(mongoClient, database);

        return simpleMongoDbFactory;
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }

}