FROM alpine:latest
RUN apk --update add openjdk8-jre && rm /var/cache/apk/*

ENV MS_FILE helloworld.jar
ENV MS_HOME /usr/verticles

EXPOSE 8080

COPY target/$MS_FILE $MS_HOME/

WORKDIR $MS_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar $MS_FILE"]