FROM openjdk:8
EXPOSE 8080
ADD /target/LearningJWT-0.0.1-SNAPSHOT.jar LearningJWT-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "LearningJWT-0.0.1-SNAPSHOT.jar"]