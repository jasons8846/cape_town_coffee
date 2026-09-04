package com.jasons.coffeewiki.configs;

import com.jasons.coffeewiki.entities.ProductDynamo;
import com.jasons.coffeewiki.entities.dynamodb.Company;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DynamoDBClientConfig {

    @Value("${aws.region}")
    private String awsRegion;

    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
                .region(Region.of(awsRegion))
                .build();
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(
            DynamoDbClient dynamoDbClient) {

        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }

    @Bean
    public DynamoDbTable<ProductDynamo> productTable(
            DynamoDbEnhancedClient enhancedClient) {

        return enhancedClient.table(
                "Products",
                TableSchema.fromBean(ProductDynamo.class)
        );
    }

    @Bean
    public DynamoDbTable<Company> companyTable(
            DynamoDbEnhancedClient enhancedClient) {

        return enhancedClient.table(
                "Companies",
                TableSchema.fromBean(Company.class)
        );
    }

}
