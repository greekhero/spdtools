NET STOP Tomcat7
cd c:\spdtools\
svn up
call mvn clean package -Dmaven.test.skip=true
NET START Tomcat7
@pause