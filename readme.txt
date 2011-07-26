nlstools by Viaboxx GmbH
=======================================
The project nlstools is a Java project, built with maven2 or maven2, that contains some utilities used for:
- managing i18n resource bundles
- xml files for resource bundles
- excel spreadsheets for resource bundles
- code generation for various target programming languages (java, sql, flex, properties)

How to compile the project from source
=======================================
Requirements:
0. Sources require java1.5 or higher. (Tested with JDK 1.6.0)
1. Maven2 or Maven3 required
   Download and install maven from: http://maven.apache.org/
2. Invoke maven from within one of the directories that contain a pom.xml file

Use the binaries
================
1.
a) WITH MAVEN (recommended)

   <dependency>
        <groupId>de.viaboxx</groupId>
        <artifactId>nlstools</artifactId>
        <version>2.5.0</version> <!-- or a later version... --->
   </dependency>

b) WITHOUT MAVEN
 Place nlstools-*.jar into the classpath

2. Use the Tasks in your maven (pom.xml) or ant (build.xml)
   or with whatever build system you are using (gradle, grails, ...)
   
   Refer to the Tests and Examples provided with nlstools.

compile project:
----------------
mvn install
mvn clean install

(artifacts are generated into the target directories)

deploy to maven central:
========================
mvn clean deploy

Note: this also creates javadoc and sources jar and deploys to maven snapshot repository.

Refer to https://docs.sonatype.org/display/Repository/Sonatype+OSS+Maven+Repository+Usage+Guide

Stage a Release
---------------
mvn release:clean release:prepare release:perform

(optional) generate jar-with-dependencies:
-------------------------------------------
mvn assembly:assembly
(or activate <phase>package</phase> so that it happens automatically during mvn install)

(optional) generate site, javadoc:
-----------------------
mvn site
mvn javadoc:javadoc

(optional) generate an IntelliJ project:
-----------------------------
mvn idea:idea

Getting started
---------------
Refer to the project page and WIKI at:

https://code.google.com/p/nlstools/

You can checkout latest sources and releases from there.
You can also refer to the test cases, examples and templates.

Feedback, questions, contribution
=================================
** Your feedback is welcome! **

https://code.google.com/p/nlstools/
http://www.viaboxx.de/
http://www.viaboxxsystems.de/blog

Roman Stumm, Viaboxx GmbH, 2010, 2011
email: roman.stumm@viaboxx.de