 set path=%path%;C:\Program Files\Java\jdk1.8.0_171\bin
 cd bin
 start rmiregistry 8081
cd ..
 java -cp "lib/hibernate/*;bin" com.ssn.ssijs.workflow.server.Server