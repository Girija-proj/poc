# poc

Kafka POC

Steps for starting the project :

Producer Consumer 
D:\Girija\kafka_2.13-2.4.1\bin\windows>kafka-console-producer.bat --broker-list
localhost:9092 --topic test

D:\Girija\kafka_2.13-2.4.1\bin\windows>kafka-console-consumer.bat --bootstrap-se
rver localhost:9092 --topic test --from-beginning

List of topic :
D:\Girija\kafka_2.13-2.4.1\bin\windows>kafka-topics.bat --list --bootstrap-list
localhost:9092

Bootstrap server :
D:\Girija\kafka_2.13-2.4.1\bin\windows>kafka-server-start.bat server.properties
 
Zookeeper :
D:\Girija\kafka_2.13-2.4.1\bin\windows>zookeeper-server-start.bat zookeeper.prop
erties
