FROM alpine:latest
RUN apk --update add openjdk8-jre && rm /var/cache/apk/*

ENV MS_FILE helloService.jar
ENV MS_HOME /usr/ms

EXPOSE 8081

COPY target/$MS_FILE $MS_HOME/

COPY entrypoint.sh /usr/local/bin/entrypoint.sh
RUN chmod +x /usr/local/bin/entrypoint.sh

WORKDIR $MS_HOME
ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
CMD ["java -jar $MS_FILE"]