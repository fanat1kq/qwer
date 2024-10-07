FROM openjdk:21-jdk
WORKDIR /app

ENV JAVA_OPTS="$JAVA_OPTS -XX:MaxRAMPercentage=75"
COPY ./target/files-service-1.0-SNAPSHOT.jar ./files-service.jar

EXPOSE 8084
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar files-service.jar"]