FROM openjdk:11
COPY onvideo-1.0.jar /root/onvideo-1.0.jar
COPY conf /root/conf
ENTRYPOINT ["java","-jar","/root/onvideo-1.0.jar"," --spring.config.location=/root/conf/application.yml"]


