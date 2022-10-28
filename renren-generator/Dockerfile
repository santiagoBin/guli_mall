FROM openjdk:8
EXPOSE 8080

VOLUME /tmp
ADD renren-generator.jar  /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]


