FROM openjdk:11
EXPOSE 8091
ADD gui/target/Diplom.jar Diplom.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","Diplom.jar"]