# OCTAVA
==============
This is the demo project in Java of real project "OCTAVA" for proccessing observation GNSS data.

#1) Get Started

## Technologies
- Java 8
- Spring MVC, Spring AOP 5.0.1
- Amazon S3
- Hibernate
- JMockito
- JUnit 5
- Lombok
- JSP

#2) Run demo

1. Setup tomcat or any another application server
####1.1. Tomcat changes 
#####1.1.1. Open and add resources in <tomcat_home>/conf/server.xml file

````xml
<Resource name="jdbc/OctavaDatabase"
  auth="Container"
  type="javax.sql.DataSource"
  driverClassName="org.postgresql.Driver"
  url="jdbc:postgresql://localhost:5432/postgres"
  username="root"
  password="1"
  maxActive="20"
  maxIdle="10"
  maxWait="-1"/>
 ````
  
#####1.1.2. We’ve also had to specify its type and database driver’s class name. 
For it to work, you must also place the corresponding jar in
 
 <tomcat_home>/lib/ (in this case, PostgreSQL’s JDBC jar).

go to 
https://jdbc.postgresql.org/download.html or check jars project folder

#####1.1.3. Define a ResourceLink inside the <Context> element in 
<tomcat_home>/conf/context.xml

````xml
<ResourceLink
  name="jdbc/OctavaDatabase"
  global="jdbc/OctavaDatabase"
  type="javax.sql.DataSource"/>
````

#####1.1.4. Specify jndi lookup url property in resources\rdbmsDev.properties file

````properties
jdbc.url=java:comp/env/jdbc/OctavaDatabase
````

2. Run tomcat server