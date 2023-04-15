FROM amazoncorretto:17 as builder
WORKDIR /app
ADD target/*.jar /app.jar
RUN java -Djarmode=layertools -jar /app.jar extract

FROM amazoncorretto:17
WORKDIR /app
COPY --from=builder /app/dependencies/ ./
COPY --from=builder /app/spring-boot-loader/ ./
COPY --from=builder /app/snapshot-dependencies/ ./
COPY --from=builder /app/application/ ./
EXPOSE 8080
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]