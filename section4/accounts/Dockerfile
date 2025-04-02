#Start with a base image containing Java runtime
FROM eclipse-temurin:21-jre@sha256:d9b293682194311c34b54a9449e231c624a05c6e20a71cb044072af3df346eb8

# MAINTAINER instruction is deprecated in favor of using label
# MAINTAINER eazybytes.com
#Information around who maintains the image
LABEL "org.opencontainers.image.authors"="pblgllgs.com"

# Add the application's jar to the image
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

# execute the application
ENTRYPOINT ["java", "-jar", "accounts-0.0.1-SNAPSHOT.jar"]