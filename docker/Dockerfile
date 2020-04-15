FROM adoptopenjdk:14-jdk-openj9
RUN curl https://downloads.apache.org/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz --output apache-maven-3.6.3-bin.tar.gz \
    && curl https://downloads.apache.org/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz.sha512 --output apache-maven-3.6.3-bin.tar.gz.sha512 \
    && echo "  apache-maven-3.6.3-bin.tar.gz" >> apache-maven-3.6.3-bin.tar.gz.sha512 \
    && sha512sum -c apache-maven-3.6.3-bin.tar.gz.sha512
RUN tar xf apache-maven-3.6.3-bin.tar.gz
RUN apt-get update \
    && apt-get install -y binutils

