FROM codecentric/springboot-maven3-centos
#FROM openshift/base-centos7

MAINTAINER ranganathan.sevuganchetty@sabre.com

# Define environment variables.
ENV MULE_HOME=/tmp/mule
ENV MULE_VERSION=3.9.0
#ENV TZ=America/Chicago

#timezone
#RUN echo ${TZ} > /etc/timezone

#USER root

# Mule Community Edition installation
RUN wget https://repository.mulesoft.org/nexus/content/repositories/releases/org/mule/distributions/mule-standalone/${MULE_VERSION}/mule-standalone-${MULE_VERSION}.tar.gz
#COPY ./mule-standalone-3.9.0.tar.gz .
RUN cd /tmp && tar xvzf ~/mule-standalone-${MULE_VERSION}.tar.gz
RUN rm ~/mule-standalone-${MULE_VERSION}.tar.gz
RUN ln -s /tmp/mule-standalone-${MULE_VERSION} ${MULE_HOME}
RUN mkdir /tmp/mule/.mule
RUN touch /tmp/mule/conf/wrapper-additional.conf
RUN touch /tmp/mule/logs/mule.log



# run as non-root user
#RUN chown -R 1001:1001 $MULE_HOME
RUN chmod -R 775 ${MULE_HOME}
RUN chmod -R 775 ${MULE_HOME}/conf/
RUN chmod -R 775 ${MULE_HOME}/logs
#RUN chmod -R 775 ${MULE_HOME}/bin/mule-start.sh
RUN chmod -R 775 ${MULE_HOME}/.mule

COPY ./target/grpc-mule-schedule-client-1.0.0-SNAPSHOT.zip ${MULE_HOME}/apps/

# Openshift runtime user
#USER 1001

CMD  [ "sh", "-c", "/tmp/mule/bin/mule ${JAVA_OPTS}"]


# Default http port
EXPOSE 8085