# SparkDemo

SparkDemo is an small POC that uses sparks with  kafka & Jdbc connection.
So basically whole program runs as Spark Java application. The application reads the DB Table USER_INFO(USER_ID NUMBER, USER_NAME VARCHAR2) and push the records in JSON format on apache kafka queue. Then a Kafka receiver received that data and push them back to new table USER_INFO_2.

## Installation
1. Create Kafka topic.\
2. Edit the checked-in spark-config.properties with the required details.
3. Import the checked-in project as Maven project on your desired IDE.
4. Run ApplicationMain.java with following JVM args:-

-DPROP_FILE=C:\Users\ankit\eclipse-workspace\SparkDemo\src\main\resources\app-config.properties -Dhadoop.home.dir=C:\hadoop-3.0.0

NOTE:- You need to put the dependency of JDBC driver in the pom as I am using oracle, i used the offline jar in classpath.
