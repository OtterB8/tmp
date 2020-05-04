FROM kvstore-env:1.0

WORKDIR /usr/local/dbserver
COPY target .
COPY resources resources
COPY src/main/webapp src/main/webapp

RUN mv TrackingStorage-1.0-SNAPSHOT.jar App.jar

EXPOSE 10004 11004

ENTRYPOINT ["java","-cp","App.jar:kyotocabinet.jar","-Djava.library.path=.:/usr/local/lib","com.baopdh.dbserver.Main"]

