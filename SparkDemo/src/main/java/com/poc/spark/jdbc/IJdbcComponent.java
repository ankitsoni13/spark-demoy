package com.poc.spark.jdbc;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public interface IJdbcComponent {
	
	public static final String ORACLE_URL = "oracle.url";
	public static final String ORACLE_USER	= "oracle.userId";
	public static final String ORACLE_PASSWORD	= "oracle.password";
	
	public Dataset<String> readOracleDataframe();
	public void writeOracleDataframe(Dataset<Row> dataset);
}
