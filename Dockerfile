ARG MSVC_NAME=algorithm-totp

FROM openjdk:21-slim as builder

ARG MSVC_NAME

WORKDIR /app/$MSVC_NAME


COPY ./$MSVC_NAME/pom.xml /app


COPY ./$MSVC_NAME/.mvn ./.mvn
COPY ./$MSVC_NAME/mvnw .
COPY ./$MSVC_NAME/pom.xml .


RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
#RUN ./mvnw dependency:go-offline
COPY ./$MSVC_NAME/src ./src

RUN ./mvnw clean package -DskipTests



ENTRYPOINT [ "java", "-jar","algorithm-totp-0.0.1-SNAPSHOT.jar"]


RUN ./mvnw clean package -DskipTests

FROM openjdk:21-slim


ARG MSVC_NAME

WORKDIR /app

RUN mkdir ./logs

ARG TARGET_FOLDER=/app/$MSVC_NAME/target
COPY --from=builder $TARGET_FOLDER/algorithm-totp-0.0.1-SNAPSHOT.jar .
ARG PORT_APP=8070
ENV PORT $PORT_APP
EXPOSE $PORT
#CMD sleep 20 && java -jar msvc-usuarios-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "algorithm-totp-0.0.1-SNAPSHOT.jar"]