#!/usr/bin/env bash

docker run \
       -it \
       -u 1000 \
       -v /sys/fs/cgroup:/sys/fs/cgroup:ro \
       -v `pwd`:/home/user \
       -v `pwd`:/workspace \
       -p 1050:1050 \
       -p 8090:8090 \
       docker-sid-jdk8 $@
