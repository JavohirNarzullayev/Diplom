FROM amazoncorretto:11-alpine-jdk
EXPOSE 8091
ADD gui/target/Diplom.jar Diplom.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","Diplom.jar"]