FROM tyurikov/centos-java8:latest
MAINTAINER "Stanislav Tyurikov"

# add jar
ADD /libs/${jar.archiveName} /

# expose ports
EXPOSE 8080

# setup startup command
CMD ["bash", "-c", "java -jar /${jar.archiveName}"]