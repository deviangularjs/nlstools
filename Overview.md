Tools (ant tasks) for I18n management of different locales and their translations.

## What's new? ##

  * ReleaseNotes

# API documentation #
  * [JavaDoc](http://nlstools.googlecode.com/svn/trunk/javadoc/index.html)

# Ant-Tasks manage resource bundles #

Some [ant](http://ant.apache.org/)-tasks. You can use them with ant or maven2 (with maven-antrun-plugin).
  * [Ant-Tasks](http://nlstools.googlecode.com/svn/trunk/javadoc/de/viaboxx/nlstools/tasks/package-summary.html)

## Reference nlstools in maven pom.xml ##
```
  <dependency>
    <groupId>de.viaboxx</groupId>
    <artifactId>nlstools</artifactId>
    <version>2.5.9</version>
  </dependency>
```

### transitive dependencies ###

current info, see http://code.google.com/p/nlstools/source/browse/trunk/pom.xml

```

        <dependency>
            <groupId>com.thoughtworks.xstream</groupId>
            <artifactId>xstream</artifactId>
            <version>1.4.3</version>
        </dependency>

        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.1</version>
        </dependency>

        <dependency>
            <groupId>commons-io</groupId>
            <artifactId>commons-io</artifactId>
            <version>2.4</version>
        </dependency>

        <dependency>
            <groupId>org.apache.ant</groupId>
            <artifactId>ant</artifactId>
            <version>1.8.4</version>
            <scope>provided</scope>
        </dependency>

        <!-- required for JSON support with XStream -->
        <dependency>
            <groupId>org.codehaus.jettison</groupId>
            <artifactId>jettison</artifactId>
            <version>1.3.2</version>
            <optional>true</optional>
        </dependency>

        <!-- required to read/write excel spreadsheets -->
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi</artifactId>
            <version>3.9</version>
            <optional>true</optional>
        </dependency>
```

## Examples ##

### Resource Bundle as XML ###

```
<bundles>
  <bundle baseName="Example" interfaceName="com.viaboxx.I_Example">
    <entry key="key1">
      <description>a description for the entry</description>
      <text locale="de">Beispieltext mit ÄÖÜ</text>
      <text locale="en">An example</text>
    </entry>
    <entry key="key2">
      <text locale="de" review="true">anderer Wert</text>
    </entry>
  </bundle>
</bundles>
```


#### XML Attributes and Elements ####
```
  * Element: bundles 
    Root tag of XML resource bundles file
    * Attributes: -
    * Elements: bundle (0..n) - resource bundles in the file

  * Element: bundle
    A single resource bundle in the file
    * Attributes: 
        baseName   - name of bundle
        interfaceName - (optional) name of interface to generate
        sqldomain - (optional) name of sql file to generate
    * Elements: entry - (0..n) entries in the bundle

  * Element: entry
    * Attributes: 
        key - unique key of the resource entry
    * Elements:
       description - (optional) textual description of the entry
       texts - (0..n) the translations for different locales

  * Element: text
    A translation of an entry for a locale.
    * Value: the translation (\n = carriage return)
    * Attributes: 
        locale - locale key (en_EN, ...), unique under its entry
        review - (boolean, optional) true to mark the entry as "to be reviewed"
        useDefault - (boolean, optional) true to mark the entry as "use translation from common bundle"
```

### Resource Bundle as Excel spreadsheet ###
For each bundle, create a new page in the excel file, like this:

![http://nlstools.googlecode.com/svn/wiki/excelExample.png](http://nlstools.googlecode.com/svn/wiki/excelExample.png)

  * You find an example for an excel sheet at [excelExample.xls](http://nlstools.googlecode.com/svn/trunk/example/excelExample.xls)

  * You can use the .xls file instead of the .xml file in all ant-tasks. You can let translator users work on the excel files.
  * Red colored texts are marked as "to be reviewed", when converting between excel and xml format.
  * Cells with grey background indicate missing translations.
  * Each spreadsheet in the excel file represents a single resource bundle.

# Usage #

### ExampleProject ###

Refer to the [example project](ExampleProject.md) for a runnable demonstration.
Refer to the [best practises](BestPractise.md) for a usage description.

### Usage of 'nlstools' with maven2 ###

MavenIntegration

### Using nlstools for a Grails project ###
GrailsIntegration

### Using nlstools for a Flex project with maven flex mojos ###
FlexIntegration