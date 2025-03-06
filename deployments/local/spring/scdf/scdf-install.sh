#!/bin/bash

docker network create tanzu

# Run RabbitMQ (user/bitnami)
docker run --name rabbitmq01  --network tanzu -d --rm -e RABBITMQ_MANAGEMENT_ALLOW_WEB_ACCESS=true -p 5672:5672 -p 5552:5552 -p 15672:15672  -p  1883:1883  bitnami/rabbitmq:4.0.4

# Start SCDF

# Download SCDF Jars (optional first time only)

mkdir -p runtime/scdf
#Download the server
if [ ! -f "runtime/scdf/spring-cloud-dataflow-server-2.11.5.jar" ]; then
  wget  --directory-prefix=runtime/scdf https://repo.maven.apache.org/maven2/org/springframework/cloud/spring-cloud-dataflow-server/2.11.5/spring-cloud-dataflow-server-2.11.5.jar
fi

#Download the skipper
if [ ! -f "runtime/scdf/spring-cloud-skipper-server-2.11.5.jar" ]; then
  wget --directory-prefix=runtime/scdf https://repo.maven.apache.org/maven2/org/springframework/cloud/spring-cloud-skipper-server/2.11.5/spring-cloud-skipper-server-2.11.5.jar
fi

#Download the shell
if [ ! -f "runtime/scdf/spring-cloud-dataflow-shell-2.11.5.jar" ]; then
    wget --directory-prefix=runtime/scdf https://repo.maven.apache.org/maven2/org/springframework/cloud/spring-cloud-dataflow-shell/2.11.5/spring-cloud-dataflow-shell-2.11.5.jar
fi


# Start Skipper
export ROOT_DIR=`pwd`
mkdir -p runtime/logs
export SPRING_APPLICATION_JSON='{"spring.cloud.stream.binders.rabbitBinder.environment.spring.rabbitmq.username":"user","spring.cloud.stream.binders.rabbitBinder.environment.spring.rabbitmq.password":"bitnami","spring.rabbitmq.username":"user","spring.rabbitmq.password":"bitnami","spring.cloud.dataflow.applicationProperties.stream.spring.rabbitmq.username" :"user","spring.cloud.dataflow.applicationProperties.stream.spring.rabbitmq.password" :"bitnami"}'
java -jar $ROOT_DIR/runtime/scdf/spring-cloud-skipper-server-2.11.5.jar  --logging.file.path=runtime/logs/skipper.log --logging.pattern.console= &

# Start Data Flow Server and Skipper
java -jar $ROOT_DIR/runtime/scdf/spring-cloud-dataflow-server-2.11.5.jar --logging.file.path=runtime/logs/scdf.log &


#Open Dashboard

#open http://localhost:9393/dashboard