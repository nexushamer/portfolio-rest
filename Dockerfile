FROM java:8

WORKDIR /

ADD ./target/portfolio-rest.jar /portfolio-rest.jar

EXPOSE 8088

CMD java -Dserver.port=8088 -Dspring.datasource.url=$MYSQL_URL -jar portfolio-rest.jar