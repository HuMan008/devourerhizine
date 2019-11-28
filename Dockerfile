FROM registry.petroun.com/base/java11:latest

LABEL maintainer="s@petroun.com"

RUN mkdir /application
RUN mkdir /application/config
RUN mkdir /application/logs

COPY ./build/libs/devourerhizine-0.0.1-SNAPSHOT.jar /application/app.jar

WORKDIR /application


CMD ["java", "-Dfile.encoding=UTF-8","-jar", "app.jar"]
