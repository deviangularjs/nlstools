# Introduction #

Use nlstools to generate the properties files under grails-app/i18n/messages**.properties for your Grails project. (http://grails.org/)**

Caution: The properties files for a grails project are stored in UTF-8 charset (in contrast to the java default for properties files).

This page show how to prepare the grails project for nlstools.

## Migration to nlstools ##

This describes how to generate the messages.xml file from the properties files. This happens only once when you a an existing grails project that you want to migrate to nlstools.

1. copy maven-ant-tasks-2.1.3.jar into grails app
This lets you use the artifacts for nlstools with ant from maven central. Alternatively, you could also create a maven pom (see maven integration).

Download the file from: http://maven.apache.org/ant-tasks/download.html

2. Create an ant build.xml file in grails app directory

```
<project name="nlstools-integrator" xmlns:artifact="antlib:maven-artifact-ant">
    <path id="maven-ant-tasks.classpath" path="maven-ant-tasks-2.1.3.jar"/>
    <typedef resource="org/apache/maven/artifact/ant/antlib.xml" uri="antlib:maven-artifact-ant"
             classpathref="maven-ant-tasks.classpath"/>

    <target name="init">
        <artifact:dependencies pathid="toolspath">
            <dependency groupId="de.viaboxx" artifactId="nlstools" version="2.5.9"/>
        </artifact:dependencies>
    </target>

    <target name="prop2xml" depends="init">
        <taskdef name="prop2xml"
                 classname="de.viaboxx.nlstools.tasks.Property2XMLConverterTask"
                 classpathref="toolspath"/>

        <prop2xml locales="en;de" fromProperty="grails-app/i18n/messages"
                  to="i18n/messages.xml" fromCharset="UTF-8"
                  interfaceName="Messages"/>
    </target>
</project>

```

  * Adapt the list of locales for your needs (en;de)
  * Change the interfaceName (optional)

3. Call the script

```
ant prop2xml
```

This creates a file i18n/messages.xml
You can now delete the properties files from the vcs.
Next steps show how to integrate the generation of the files into the grails build lifecycle.

## Integration in grails build lifecycle ##

Whenever you call grails compile, we want to generate/update the properties files under grails-app/i18n from the XML file i18n/messages.xml.

1. Modify grails-app/conf/BuildConfig.groovy

Enable mavenLocal and mavenCentral repositories.
Add the nlstools dependency.

```
  repositories {
    grailsPlugins()
    grailsHome()
    grailsCentral()

    mavenLocal()
    mavenCentral()
    }
  dependencies {
    // for _Events.groovy to build the project
    build('de.viaboxx:nlstools:2.5.9') {
      transitive = true
    }
  }
```

2. Create the file scripts/`_`Events.groovy

This is the file content:
```
eventCompileStart = { msg ->      

  ant.taskdef(name: "msgbundle", classname: "de.viaboxx.nlstools.tasks.MessageBundleTask")

  ant.msgbundle(writeProperties: "true",
          bundles: "i18n/messages.xml",
          propertyPath: "grails-app/i18n", toCharset: "UTF-8"//, overwrite: "true", debugMode: "true"
  )
}
```


3. That's it!
Whenever you call
```
grails compile
```

You get the properties files whenever the xml file has been changed.