FROM openjdk:8-jdk-alpine
VOLUME /tmp
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai  /etc/localtime
ADD frame-util-0.0.1-SNAPSHOT.jar frame-util.jar
ENTRYPOINT ["java","-Xmx1024m","-Djava.security.egd=file:/dev/./urandom","-Duser.timezone=GMT+08", "-jar","/frame-util.jar"]
