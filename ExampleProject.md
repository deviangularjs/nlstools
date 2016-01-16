# Introduction #
The project is checked in SVN in the example folder (or in the Downloads section). Check out the sources and refer to the build.xml file.

You need to have apache ant installed to execute the scripts.

  * Open a command window in directory nlstools/example/exampleProject
  * Invoke the ant-build target "generate-sources-all" (which is the default target)
```
   ant
```

This creates a 'src' directory with generated sources for various target languages.
You can use this principle (the build.xml) as a starting point for your projects.

You can run the tasks separately:
  * ant generate-sources-java
  * ant generate-sources-flex
  * ant generate-sources-json
  * ant generate-sources-sql

You can use the excel file instead of the xml file:
  * Invoke ant with 'ant -DbundlesType=.xls' instead of the default '.xml'


### Want to know more? Read BestPractise ###