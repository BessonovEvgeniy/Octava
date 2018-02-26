# OCTAVA
==============
This open source project is a demo of original SDK "OCTAVA" for GNSS observation processing (see details http://www.kharkovgnssgroup.net/en/projects/sdk-octava.html)
Original project written on the MatLab (Matrix Laboratory) programming support environment snd still under development.
Those project is being developed in spare time.

## Used Technologies
- Java 8
- Spring MVC, Spring AOP
- Amazon S3
- Hibernate
- Jama (Matrix tool)
- XChart
- JMockito
- JUnit 5
- log4j
- Lombok
- JSP

##Whole project is splitted on several packages (modules):
### - config - Spring / Spring MVC, Hibernate etc settings;
### - business - module for user interaction purposes;
### - ppa - module for Pre Proccesing Algorithms implementation;
### - utils - common tool set for modules.

#1) Get Started

###1.1 Start Tomcat
#####If Tomcat encountered an error for JDBC driver leaking. It should be ignored by Fine server settings

###1.2 Chose create Project -> Enter project Name (at least 4 symbols)

###1.3 Pick up Rinex file from testResourse folder (example 38541890.16o) of current project -> press upload file
###Rinex format can be found by link ftp://igs.org/pub/data/format/rinex211.txt

###1.4 After the file has been uploaded it will be parsed to the Object representation.


