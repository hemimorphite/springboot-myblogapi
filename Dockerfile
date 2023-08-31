FROM ubuntu:22.04
EXPOSE 8080
WORKDIR /root/myblog
ENV JAVA_HOME /root/jdk-17.0.8
ENV PATH /root/jdk-17.0.8/bin:$PATH
RUN apt update
RUN apt install -y wget
RUN wget https://download.oracle.com/java/17/archive/jdk-17.0.8_linux-x64_bin.tar.gz
RUN tar -xvzf jdk-17.0.8_linux-x64_bin.tar.gz -C /root
COPY app /root/myblog
RUN bash mvnw -Dmaven.test.skip=true install
ENTRYPOINT ["java","-jar","target/myblog-0.0.1-SNAPSHOT.jar"]
