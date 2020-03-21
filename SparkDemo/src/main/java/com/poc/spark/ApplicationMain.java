package com.poc.spark;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import com.poc.spark.jdbc.IJdbcComponent;
import com.poc.spark.jdbc.JdbcComponent;
import com.poc.spark.kafka.IKafkaComponent;
import com.poc.spark.kafka.KafkaComponent;




public class ApplicationMain {

	public static final String JSON_SCHEMA	= "json.schema";
	
	static SparkSession sparkSession =null;
    static JavaSparkContext jctx = null;
    static SparkContext ctx =null;
	static Properties props = null;
	
	static 
	{
		try {
			props =new Properties();
	    	props.load(new FileReader(new File(System.getProperty("PROP_FILE"))));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
		
	
	public void process(IKafkaComponent kafkaComponent,IJdbcComponent jdbcComponent) {
		Dataset<String> oracleData =jdbcComponent.readOracleDataframe();
		kafkaComponent.kafkaPublisher(oracleData);
		Dataset<Row> receivedData =kafkaComponent.kafkaReciever();
		//jdbcComponent.writeOracleDataframe(receivedData);		
		receivedData.show();
	}
	
    public static void main(String[] args) {
		// TODO Auto-generated method stub
		
    	sparkSession = SparkSession
    			.builder()
    			.appName("Java Spark SQL basic example")
    			.config("spark.master", "local")
    			.getOrCreate();
    	jctx = new JavaSparkContext(sparkSession.sparkContext());
    	ctx = sparkSession.sparkContext();
    	
    	ApplicationMain main = new ApplicationMain();
    	IKafkaComponent kafkaComponent = new KafkaComponent(sparkSession, props);
    	IJdbcComponent jdbcComponent = new JdbcComponent(sparkSession, props);
    	main.process(kafkaComponent,jdbcComponent);
    	
	}

}
