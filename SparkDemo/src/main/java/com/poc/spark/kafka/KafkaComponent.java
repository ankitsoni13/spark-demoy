package com.poc.spark.kafka;

import java.util.Properties;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class KafkaComponent implements IKafkaComponent{

	private SparkSession sparkSession = null;
	private Properties props;
	
	public KafkaComponent(SparkSession sparkSession, Properties props) {
		this.sparkSession = sparkSession;
		this.props = props;
	}
	
	public void kafkaPublisher(Dataset<String> dataset) {
		dataset
		  .write()
		  .format("kafka")
		  .option("kafka.bootstrap.servers", props.getProperty(KAFKA_HOST)+":"+props.getProperty(KAFKA_PORT))
		  .option("topic", props.getProperty(KAFKA_TOPIC))
		  .save();
	}
	
	
	public Dataset<Row> kafkaReciever() {
		return sparkSession
		  .read()
		  .format("kafka")
		  .option("kafka.bootstrap.servers", props.getProperty(KAFKA_HOST)+":"+props.getProperty(KAFKA_PORT))
		  .option("subscribe", props.getProperty(KAFKA_TOPIC))
		  .option("startingOffsets", "earliest")
		  .option("endingOffsets", "latest")
		  .option("header", "false")
		  .load().select("value");
	}
}
