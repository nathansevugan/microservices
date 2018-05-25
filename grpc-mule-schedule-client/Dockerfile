FROM openshift/base-centos7
#
MAINTAINER ramon.resma@gmail.com

# Define environment variables.
ENV BUILD_DATE=05252018
ENV MULE_HOME=/opt/mule
ENV MULE_VERSION=3.9.0
#ENV MULE_MD5=09aa1448536c9273562eded1a9236609
ENV TZ=America/Chicago

RUN echo ${TZ} > /etc/timezone

# we need some tools from yum
RUN yum update -y \
    && yum install -y java-1.8.0-openjdk-devel maven zip \
    && yum clean all -y

COPY mule-standalone-${MULE_VERSION}.zip /opt
RUN cd /opt && unzip mule-standalone-${MULE_VERSION}.zip && ln -s /opt/mule-standalone-${MULE_VERSION} ${MULE_HOME}

# Define mount points.
#VOLUME ["${MULE_HOME}/logs", "${MULE_HOME}/conf", "${MULE_HOME}/apps", "${MULE_HOME}/domains"]

# Define working directory.
WORKDIR ${MULE_HOME}

#COPY ./target/arch-summit.1.0-1.0.0-SNAPSHOT-mule-application*.jar ${MULE_HOME}/apps/

RUN ls ${MULE_HOME}/apps/


# run as non-root user
RUN chown -R 1001:0 $MULE_HOME && \
    chmod -R g+wrx $MULE_HOME

# Openshift runtime user
USER 1001

CMD [ "/opt/mule/bin/mule"]

# engage
#CMD exec $MULE_HOME/bin/mule $MULE_OPTS_APPEND

# Default http port
EXPOSE 8081
EXPOSE 8082