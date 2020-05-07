FROM openjdk:8

ADD target/users-mysql.jar users-mysql.jar

#ADD target/user-mysql.jar user-mysql.jar
EXPOSE 8888

ENTRYPOINT ["java","-jar","users-mysql.jar"]
#ENTRYPOINT ["java","-jar","user-mysql.jar"]