# Release notes: nlstools #
## 2.6.2-SNAPSHOT (current source) ##

## 2.6.1 (27.11.2014) ##
  * fixed potential NullPointerExceptions when some bundles
> in the source file have no interfaceName or sqlDomain

## 2.6 (6.11.2014) ##
  * feature to write angularJS json files with writeJson="angular" or "angular\_pretty"
  * changed readme.txt, mentioned 2014 and jdk1.7

## 2.5.9 (4.4.2013) ##
  * new API: MBPersistencer.forURL(string), load(InputStream)
  * using poi 3.9 (optional)
  * maven-plugins upgrade
  * poi, jettision upgrade

## 2.5.8 (16.11.2012) ##
  * [Issue 1](https://code.google.com/p/nlstools/issues/detail?id=1) fixed
  * [Issue 2](https://code.google.com/p/nlstools/issues/detail?id=2) fixed
  * upgrade junit 4.8.2 -> 4.11, jettison 1.3.1 -> 1.3.2, commons-io 2.3 -> 2.4, xstream 1.4.2 -> 1.4.3

## 2.5.7 (05.09.2012) ##
  * empty text-entries in xml bundle file will be stored as excel cell with grey-background color for indication. this helps to distinguish between empty values and missing values.

## 2.5.6 ##
> this release does not exist

## 2.5.5 (17.08.2012) ##
  * new attribute "exampleLocale" in MessageBundleTask, used to select a locale as example in the generated java- or flex-interface

## 2.5.4 (16.08.2012) ##
  * fixed MessageBundleTask: parent-locale-lookup did not work for locales with Country/Variant.
  * new attribute "merged" in MessageBundleTask

## 2.5.3 (16.05.2012) ##
  * upgraded 3rdparty dependencies

## 2.5.2 (10.08.2011) ##
  * flex and java interface contains a non-empty example value in comment

## 2.5.1 (27.07.2011) ##
  * "fromCharset" attribute in Property2XMLConverterTask to support UTF-8 properties files used in Grails projects
  * "toCharset" attribute in MessageBundleTask to support UTF-8 properties files used in Grails projects
  * dependency to ant with scope=provided to avoid version conflicts (for grails projects using nlstools)

## 2.5.0 (21.07.2011) ##
  * Changed package names from com.google to de.viaboxx
  * Published artifacts to maven central.

You can create a dependency in your maven pom to use nlstools:

```
<dependency>
  <groupId>de.viaboxx</groupId>
  <artifactId>nlstools</artifactId>
  <version>2.5.0</version>
</dependency>
```

## 2.4.0 (29.12.2010) ##
  * Nls Tools separated from annomark.jar
  * Excel-File support as an alternative for XML-Files in all ant-tasks
  * code separated, packages reorganized, google-code site created from http://code.google.com/p/agimatec-tools/wiki/NLSTools
  * ConvertBundlesTask = convert between different formats (XML/Excel/JSON)


Features:
  * MessageBundleTask = generate Java or Flex Interface, properties for Java or Flex from a resource bundle (xml or excel file)
  * AddLocaleTask = modify resource bundle by adding a new language
  * CompareBundlesTask = find missing keys, compare two bundles
  * ListChangesTask = show differences in translations, compare two bundles
  * CopyBundlesTask = copy bundles into zip file or directory
  * FillLocaleTask = Fill missing keys in a locale with values from another locale.
  * LocaleSanityCheckerTask = Checks files for obvious errors like missing translations
  * MergeLocaleTask = takes two bundles and merges new locales in one file into another
  * OptimizeBundlesTask = analyze texts remove all that have an equal translation in a "common" bundle
  * Property2XMLConverterTask = create xml bundle from properties file (reverse generation)
  * UpdateBundlesTask = update xml files from bundles in a zip file