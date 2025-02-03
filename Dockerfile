# Estágio de construção
FROM maven:3.9.9-amazoncorretto-21 AS builder

WORKDIR /app
COPY pom.xml ./
RUN mvn dependency:go-offline -B

COPY src ./src

# Build com supressão de avisos SVE
RUN MAVEN_OPTS="-XX:UseSVE=0" mvn clean package -DskipTests

# Estágio de execução
FROM amazoncorretto:21-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]