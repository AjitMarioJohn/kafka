https://www.conduktor.io/kafka/how-to-start-kafka-using-docker/

<!-- Github link -->
https://github.com/conduktor/kafka-stack-docker-compose


# Docker commands
docker pull confluentinc/cp-zookeeper
docker pull confluentinc/cp-kafka
docker-compose up -d

<!-- Docker commands to pause, resume, and remove services -->
docker-compose pause
docker-compose unpause
docker-compose rm

# Kafka Topic CLI command
docker-compose exec kafka1 kafka-topics --create --bootstrap-server kafka1:9092  --replication-factor 1 --partitions 1 --topic first-topic 
docker-compose exec kafka1 kafka-topics --bootstrap-server kafka1:9092 --list
docker-compose exec kafka1 kafka-topics --bootstrap-server kafka1:9092 --topic first_topic --describe
docker-compose exec kafka1 kafka-topics --bootstrap-server kafka1:9092 --topic first_topic --delete


# Kafka Producer CLI command
docker-compose exec kafka1 kafka-console-producer --broker-list kafka1:9092 --topic first-topic
docker-compose exec kafka1 kafka-console-producer --broker-list kafka1:9092 --topic first-topic --producer-property acks=all
docker-compose exec kafka1 kafka-console-producer --broker-list kafka1:9092 --topic first-topic --producer-property acks=all --property parse.key=true --property key.separator=:
<!-- Kafka Producer CLI command with Acks, Retries, and Batch Size -->
docker-compose exec kafka1 kafka-console-producer --broker-list kafka1:9092 --topic first-topic --producer-property acks=all --producer-property retries=3 --producer-property batch.size=16384

# Kafka Consumer CLI command
docker-compose exec kafka1 kafka-console-consumer --bootstrap-server kafka1:9092 --topic first-topic
docker-compose exec kafka1 kafka-console-consumer --bootstrap-server kafka1:9092 --producer-property partitioner.class=org.apache.kafka.clients.producer.RoundRobinPartitioner --topic first-topic
docker-compose exec kafka1 kafka-console-consumer --bootstrap-server kafka1:9092 --topic first-topic --from-beginning
docker-compose exec kafka1 kafka-console-consumer --bootstrap-server kafka1:9092 --topic first-topic --from-beginning --formatter kafka.tools.DefaultMessageFormatter --property print.timestamp=true --property print.key=true --property print.value=true --property print.partition=true
<!-- Mentioning consumer group -->
docker-compose exec kafka1 kafka-console-consumer --bootstrap-server kafka1:9092 --topic first-topic --from-beginning --group first-consumer-group
<!-- Kafka Consumer CLI command with Consumer Groups and Auto-Offset Reset -->
docker-compose exec kafka1 kafka-console-consumer --bootstrap-server kafka1:9092 --topic first-topic --consumer-property group.id=first-consumer-group --consumer-property auto.offset.reset=earliest
<!-- Kafka Consumer CLI command with Partition Assignment Strategy -->
docker-compose exec kafka1 kafka-console-consumer --bootstrap-server kafka1:9092 --topic first-topic --consumer-property group.id=first-consumer-group --consumer-property partition.assignment.strategy=org.apache.kafka.clients.consumer.RoundRobinAssignor

# Kafka Consumer Group CLI
docker-compose exec kafka1 kafka-consumer-groups --bootstrap-server kafka1:9092 --list


# Monitoring and Troubleshooting Kafka

<!-- Kafka Producer and Consumer Performance Metrics CLI command -->
docker-compose exec kafka1 kafka-producer-perf-test --producer-props bootstrap.servers=kafka1:9092 --topic first-topic --num-records 1000 --record-size 1024 --throughput 10000
docker-compose exec kafka1 kafka-consumer-perf-test --bootstrap-server kafka1:9092 --topic first-topic --messages 10000

<!-- Kafka Lag CLI command -->
docker-compose exec kafka1 kafka-consumer-groups --bootstrap-server kafka1:9092 --describe --group first-consumer-group


# Reference
https://kafka.apache.org/quickstart
export KAFKA_HEAP_OPTS="-Xmx2G"

# Blog
# best practices for deciding topics name
https://cnr.sh/essays/how-paint-bike-shed-kafka-topic-naming-conventions