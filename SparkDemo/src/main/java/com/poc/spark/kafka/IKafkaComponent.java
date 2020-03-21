package com.poc.spark.kafka;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public interface IKafkaComponent {

	public static final String KAFKA_TOPIC = "kafka.topic";
	public static final String KAFKA_HOST = "kafka.host";
	public static final String KAFKA_PORT = "kafka.port";
	
	public void kafkaPublisher(Dataset<String> dataset);
	public Dataset<Row> kafkaReciever() ;
}
