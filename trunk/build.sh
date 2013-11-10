#!/bin/sh
# Rebuild webapp
service tomcat7 stop
cd /usr/share/spdtools
svn status
svn up
mvn clean package -Dmaven.test.skip=true
rm -rf $CATALINA_HOME/work/Catalina/localhost/spdtools
service tomcat7 start
