#!/bin/bash
SERVICE_NAME=nsf-demo-stock-dubbo-wall
JAR_PATH=/dubbo-wall/$SERVICE_NAME".jar"
chmod +x /dubbo-wall/$SERVICE_NAME".jar"
java $NCE_JAVA_OPTS -jar $JAR_PATH
exit 0;
