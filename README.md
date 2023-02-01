# Kafka Taxi

Prerequisites: Configure a Kafka cluster using Docker with the following parameters: 
* Number of brokers - 3 
* Number of partitions - 3 
* Replication factor - 2
* observe the Kafka broker logs to see how leaders/replicas for every partition are assigned

Task:
I. Implement a pair of "at least once" producer and "at most once" consumer. 
1. no web application required 
2. write an integration test using the Kafka Containers library

II. Implement a taxi application. The application should consist of:
1. API which
* accepts vehicle signals
* validates that every signal carries a valid vehicle id and 2d coordinates
* puts the signals into the “input” Kafka topic
2. Kafka broker
3. 3 consumers which
* poll the signals from the “input” topic
* calculate the distance traveled by every unique vehicle so far
* store the latest distance information per vehicle in another “output” topic
4. separate consumer that polls the “output” topic and logs the results in realtime