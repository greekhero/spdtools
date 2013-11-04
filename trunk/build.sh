#!/bin/sh
# Rebuild webapp
service tomcat7 stop
cd /usr/share/spdtools
svn up
mvn clean package -Dmaven.test.skip=true
service tomcat7 start
