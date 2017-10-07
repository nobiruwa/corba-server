FROM debian:sid

RUN apt-get update

# ENV
## Locale
## https://hub.docker.com/r/library/debian/ => Locales
ENV LANG C.UTF-8

# RUN
## install Java 8
RUN apt-get update -y && apt-get install -y openjdk-8-jdk gradle
## install others
RUN apt-get update -y && apt-get install -y git
## create an user of which the user id is 1000.
RUN useradd -s /bin/bash -c '' -d /home/user -u 1000 -m user

# WORKDIR
WORKDIR /workspace

# CMD
CMD /usr/bin/bash