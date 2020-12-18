#!/bin/bash
SERVICE_NAME=nsf-demo
JAR_PATH=/dubbo/nsf-demo.jar
chmod 777 $JAR_PATH
java $NCE_JAVA_OPTS -jar $JAR_PATH
exit 0;
