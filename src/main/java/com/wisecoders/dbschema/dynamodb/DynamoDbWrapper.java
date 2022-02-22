package com.wisecoders.dbschema.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;

import java.io.IOException;

public class DynamoDbWrapper {

    private final AmazonDynamoDB dynamoDB;

    public DynamoDbWrapper(AmazonDynamoDB dynamoDB){
        this.dynamoDB = dynamoDB;
    }

    public void createTable( String json ) throws IOException {
        dynamoDB.createTable( new CreateTable().getTableRequest( json ));
    }


}
