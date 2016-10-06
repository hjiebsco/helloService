FROM alpine:latest
RUN apk --update add openjdk8-jre && rm /var/cache/apk/*

EXPOSE 8091

COPY target/helloService.jar /usr/ms

WORKDIR /usr/ms
CMD ["java", "-Xmx512m", "-jar", "helloService.jar"]
