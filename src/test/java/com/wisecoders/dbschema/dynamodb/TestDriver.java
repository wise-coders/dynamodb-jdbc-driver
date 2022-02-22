package com.wisecoders.dbschema.dynamodb;


import com.amazonaws.util.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.Properties;

/**
 * Copyright Wise Coders GmbH https://wisecoders.com
 * Driver is used in the DbSchema Database Designer https://dbschema.com
 * Free to be used by everyone.
 * Code modifications allowed only to GitHub repository https://github.com/wise-coders/dynamodb-jdbc-driver
 */

public class TestDriver extends AbstractTestCase {

    private Connection connection;

    @Before
    public void setUp() throws ClassNotFoundException, SQLException, IOException {
        Class.forName("com.wisecoders.dbschema.dynamodb.JdbcDriver");

        final Properties properties = new Properties();
        properties.load(new FileInputStream("gradle.properties"));

        final String url = "http://localhost:8000" ;
        connection = DriverManager.getConnection(url, null, null);


    }



    @Test
    public void testCreateTable() throws Exception {
        File file = new File(TestDriver.class.getResource("CreateTable.json").getFile());
        String command = "db.createTable( " + Files.readString( Path.of( file.toURI() )) + ")";
        Statement statement = connection.createStatement();
        statement.execute(command);
        ResultSet rs = statement.getResultSet();
        printResultSet(rs);
        statement.close();
    }
}
