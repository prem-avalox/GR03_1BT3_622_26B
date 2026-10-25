FROM tomcat:9.0-jre17-temurin-jammy
RUN rm -rf /usr/local/tomcat/webapps/*
COPY ./target/GR03_1BT3_622_26B.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]