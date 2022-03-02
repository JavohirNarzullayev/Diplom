FROM openjdk:11
EXPOSE 8091
ADD gui/target/Diplom.jar Dimplom.jar
ENTRYPOINT ["java","-jar","/Diplom.jar"]