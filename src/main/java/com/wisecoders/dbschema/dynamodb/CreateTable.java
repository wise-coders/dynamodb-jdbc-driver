package com.wisecoders.dbschema.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CreateTable {
    private final AmazonDynamoDB amazonDynamoDB;

    public void createTable(String json) throws JsonProcessingException, IOException {
        Table table = readTable(json);
        amazonDynamoDB.createTable(getTableRequest(table));
    }

    private CreateTableRequest getTableRequest(Table table) {
        return new CreateTableRequest()
                .withTableName(table.getTableName())
                .withKeySchema(toKeySchemaElement(table.getKeySchema()))
                .withAttributeDefinitions(toAttributeDefinition(table.getAttributeDefinitions()))
                .withProvisionedThroughput(toProvisionedThroughput(table.getProvisionedThroughput()));
    }

    private Table readTable(String json) throws IOException, JsonParseException, JsonMappingException {
        return new ObjectMapper().readValue(json, Table.class);
    }

    private ProvisionedThroughput toProvisionedThroughput(ProvisionedTp provisionedTp) {
        return new ProvisionedThroughput(provisionedTp.getReadCapacityUnits(), provisionedTp.getWriteCapacityUnits());
    }

    private Collection<AttributeDefinition> toAttributeDefinition(Collection<AttributeDef> attributeDefs) {
        return attributeDefs.
                stream().
                map(this::toAttributeDefinition).
                collect(Collectors.toList());
    }

    private AttributeDefinition toAttributeDefinition(AttributeDef attributeDef) {
        return new AttributeDefinition().
                withAttributeName(attributeDef.getAttributeName()).
                withAttributeType(attributeDef.getAttributeType());
    }

    private Collection<KeySchemaElement> toKeySchemaElement(Collection<KeySchema> keySchemas) {
        return keySchemas.stream().map(this::toKeySchemaElement).collect(Collectors.toList());
    }

    private KeySchemaElement toKeySchemaElement(KeySchema keySchema) {
        return new KeySchemaElement().withAttributeName(keySchema.getAttributeName()).withKeyType(keySchema.getKeyType());
    }

    @Data
    @NoArgsConstructor
    public static class Table {
        @JsonProperty("TableName")
        private String tableName;
        @JsonProperty("KeySchema")
        private List<KeySchema> keySchema;
        @JsonProperty("AttributeDefinitions")
        private List<AttributeDef> attributeDefinitions;
        @JsonProperty("ProvisionedThroughput")
        private ProvisionedTp provisionedThroughput;
    }

    @Data
    @NoArgsConstructor
    public static class KeySchema {
        @JsonProperty("AttributeName")
        private String attributeName;
        @JsonProperty("KeyType")
        private String keyType;
    }

    @Data
    @NoArgsConstructor
    public static class AttributeDef {
        @JsonProperty("AttributeName")
        private String attributeName;
        @JsonProperty("AttributeType")
        private String attributeType;
    }

    @Data
    @NoArgsConstructor
    public static class ProvisionedTp {
        @JsonProperty("ReadCapacityUnits")
        private long readCapacityUnits;
        @JsonProperty("WriteCapacityUnits")
        private long writeCapacityUnits;
    }

}

