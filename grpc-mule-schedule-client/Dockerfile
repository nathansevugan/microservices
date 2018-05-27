FROM codingtony/java

MAINTAINER ranganathan.sevuganchetty@sabre.com

# Define environment variables.
ENV MULE_HOME=/opt/mule
ENV MULE_VERSION=3.9.0
ENV TZ=America/Chicago

#timezone
RUN echo ${TZ} > /etc/timezone

# Download Java 8
RUN apt-get install -y software-properties-common \
 && add-apt-repository ppa:webupd8team/java -y \
 && apt-get update \
 && apt-get install -y zip \
 && apt-get install -y curl \
 && apt-get install -y htop \
 && apt-get install -y jq \
 && echo oracle-java7-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections \
 && apt-get install -y oracle-java8-installer \
 && apt-get install oracle-java8-set-default \
 && apt-get clean

# Mule Community Edition installation
RUN wget https://repository.mulesoft.org/nexus/content/repositories/releases/org/mule/distributions/mule-standalone/${MULE_VERSION}/mule-standalone-${MULE_VERSION}.tar.gz
RUN cd /opt && tar xvzf ~/mule-standalone-${MULE_VERSION}.tar.gz
RUN rm ~/mule-standalone-${MULE_VERSION}.tar.gz
RUN ln -s /opt/mule-standalone-${MULE_VERSION} ${MULE_HOME}

COPY ./target/grpc-mule-schedule-client-1.0.0-SNAPSHOT.zip ${MULE_HOME}/apps/

CMD [ "/opt/mule/bin/mule" ]

# Default http port
EXPOSE 8085