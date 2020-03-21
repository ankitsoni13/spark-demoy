package com.poc.spark.jdbc;

import java.util.Properties;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public class JdbcComponent implements IJdbcComponent{
	
	private SparkSession sparkSession=null;
	private Properties props=null;
	
	private static final String QUERY = "SELECT USER_ID,USER_NAME from USER_INFO";
	
	public JdbcComponent(SparkSession sparkSession, Properties props) {
		this.sparkSession = sparkSession;
		this.props = props;
	}

	public Dataset<String> readOracleDataframe(){
		
		 StructField[] structFields = new StructField[]{
		            new StructField("USER_ID", DataTypes.createDecimalType(), true, Metadata.empty()),
		            new StructField("USER_NAME", DataTypes.StringType, true, Metadata.empty())
		    };

		    StructType structType = new StructType(structFields);
		    
		return sparkSession.read()
		  .format("jdbc")
		  .option("url",props.getProperty(ORACLE_URL))
		  .option("query", QUERY)
		  .option("user", props.getProperty(ORACLE_USER))
		  .option("password", props.getProperty(ORACLE_PASSWORD))
		  .load().toJSON()
		  ;
	}
	
	

	
	public void writeOracleDataframe(Dataset<Row> dataset){
		dataset.write()
		  .format("jdbc")
		  .mode(SaveMode.Append)
		  .option("url",props.getProperty(ORACLE_URL))
		  .option("dbtable", "USER_INFO_2")
		  .option("user", props.getProperty(ORACLE_USER))
		  .option("password", props.getProperty(ORACLE_PASSWORD))
		  .save();
		  
	}

}
