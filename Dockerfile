# Image provides a container in which to run the example Hello World docker transformer.

FROM alfresco/alfresco-base-java:11.0.1-openjdk-centos-7-6784d76a7b81

ENV JAVA_OPTS=""

# Set default user information
ARG GROUPNAME=Alfresco
ARG GROUPID=1000
ARG HWUSERNAME=transform-json2pdf
ARG USERID=33004

COPY target/acme-json2pdf-transformer-${env.project_version}.jar /usr/bin

RUN ln /usr/bin/acme-json2pdf-transformer-${env.project_version}.jar /usr/bin/acme-json2pdf-transformer.jar && \
    yum clean all

RUN groupadd -g ${GROUPID} ${GROUPNAME} && \
    useradd -u ${USERID} -G ${GROUPNAME} ${HWUSERNAME} && \
    chgrp -R ${GROUPNAME} /usr/bin/acme-json2pdf-transformer.jar

EXPOSE 8090

USER ${HWUSERNAME}

ENTRYPOINT java $JAVA_OPTS -jar /usr/bin/acme-json2pdf-transformer.jar
