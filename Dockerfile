FROM eclipse-temurin:21-jdk-alpine
RUN addgroup -S johndeere && adduser -S johndeere -G johndeere
USER johndeere:johndeere
COPY build/libs/johndeere-*-SNAPSHOT.jar johndeere.jar
EXPOSE 8080

ENTRYPOINT ["java", "-Xms100m", "-Xmx170m", "-Dspring.profiles.active=pro", "-Xlog:gc=debug:stdout", "-jar", "johndeere.jar"]
HEALTHCHECK --interval=1m --timeout=3s CMD curl -f http://localhost:8080/actuator/health/ || exit 1