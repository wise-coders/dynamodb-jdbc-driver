package com.wisecoders.dbschema.dynamodb;


import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;


/**
 * Minimal implementation of the JDBC standards for the Cassandra database.
 * This is customized for DbSchema database designer.
 * Connect to the database using a URL like :
 * jdbc:cassandra://host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[keyspace][?options]]
 * The URL excepting the jdbc: prefix is passed as it is to the Cassandra native Java driver.
 *
 * Copyright Wise Coders GmbH. The Cassandra JDBC driver is build to be used with DbSchema Database Designer https://dbschema.com
 * Free to use by everyone, code modifications allowed only to
 * the public repository https://github.com/wise-coders/cassandra-jdbc-driver
 */


public class JdbcDriver implements Driver {
    private static final String RETURN_NULL_STRINGS_FROM_INTRO_QUERY_KEY = "cassandra.jdbc.return.null.strings.from.intro.query";

    static {
        try {
            DriverManager.registerDriver(new JdbcDriver());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Connect to the database using a URL like :
     * jdbc:cassandra://host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[keyspace][?options]]
     * The URL excepting the jdbc: prefix is passed as it is to the Cassandra native Java driver.
     */
    public Connection connect(String url, Properties info) throws SQLException {
        if (url != null && acceptsURL(url)) {
            return new DynamoDbConnection( url, info );
        }
        return null;
    }


    /**
     * URLs accepted are of the form: jdbc:cassandra://host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[keyspace][?options]]
     */
    @Override
    public boolean acceptsURL(String url) {
        return url.startsWith("http");
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) {
        return null;
    }

    String getVersion() {
        return "1.3.4";
    }

    @Override
    public int getMajorVersion() {
        return 1;
    }

    @Override
    public int getMinorVersion() {
        return 3;
    }

    @Override
    public boolean jdbcCompliant() {
        return true;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException();
    }

}


