### creacion jar ###
FROM maven:3-openjdk-17-slim AS builder

WORKDIR /app
COPY ./pom.xml .
RUN mvn -e -B dependency:go-offline
COPY ./src ./src
RUN mvn clean package -Dmaven.test.skip=true

 ### fase final del imagen ###
FROM openjdk:17-slim

WORKDIR /workspace

ARG USER="app"
ARG GROUP="gapp"
ARG USERUID="2000"
ARG GROUPUID="2000"

RUN addgroup --gid $GROUPUID $GROUP
RUN adduser --uid $USERUID --gid $GROUPUID --disabled-password $USER
RUN apt-get update && apt-get -f install -y --no-install-recommends \
    iputils-ping \
    net-tools \
    curl \
    && rm -rf /var/lib/apt/lists/*

USER $USER

COPY --from=builder /app/target/provider*.jar app.jar
EXPOSE 8080
ENTRYPOINT exec java -jar /workspace/app.jar

