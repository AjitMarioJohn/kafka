https://www.conduktor.io/kafka/how-to-start-kafka-using-docker/

<!-- Github link -->
https://github.com/conduktor/kafka-stack-docker-compose


# Docker commands
docker pull confluentinc/cp-zookeeper
docker pull confluentinc/cp-kafka
docker-compose up -d
docker-compose exec kafka1 kafka-topics --create --bootstrap-server kafka1:9092  --replication-factor 1 --partitions 1 --topic test-topic 
docker-compose exec kafka1 kafka-console-producer --broker-list kafka1:9092 --topic test-topic
docker-compose exec kafka1 kafka-console-consumer --bootstrap-server kafka1:9092 --topic test-topic --from-beginning
