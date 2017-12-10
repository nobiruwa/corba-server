#!/usr/bin/env bash

docker run \
       --sysctl net.ipv6.conf.all.disable_ipv6=1 \
       -e "_JAVA_OPTIONS=\"-Djava.net.preferIPv4Stack=true\"" \
       -it \
       -p 3000:3000 \
       -p 18090:18090 \
       -p 23001:23001 \
       -u 1000 \
       -v /sys/fs/cgroup:/sys/fs/cgroup:ro \
       -v `pwd`:/home/user \
       -v `pwd`:/workspace \
       docker-sid-jdk8 $@
