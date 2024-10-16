FROM openjdk:21-jdk
WORKDIR /app

ENV JAVA_OPTS="$JAVA_OPTS -XX:MaxRAMPercentage=75"
COPY ./target/ideas-0.0.1-SNAPSHOT.jar ./ideas-service.jar

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar ideas-service.jar"]