#!/bin/bash
echo  "Make sure you set KAFKA_HOME, It should be a parameter but i'm so lazy to code"
$KAFKA_HOME/bin/zookeeper-server-start.sh -daemon config/zookeeper.properties
$KAFKA_HOME/bin/kafka-server-start.sh -daemon config/server.properties

