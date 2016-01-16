# Best practises #

Alle labels of GUI elements and messages in the applications are maintained in resource bundle files. During development, these files are kept in the version control systems together with the sources, in XML-files. We generate some artifacts based on these files as required.

This side describes, how to maintain the files and exchange them with employees, that are responsible for translation of new languages. Usually, these employees are freelancers that need a tool, that is easy to use for their translation tasks.


## Add a new locale ##

Sometimes a new locale should be added and we need to prepare the resource bundles files beforehand.


### How-to ###

  * Open a command window in directory nlstools/example/exampleProject
  * Invoke the ant-build target "add-locale" with the "newLocale" property

Example to add the Spanisch language to the main-default.xml file:


```
 ant -DnewLocale=es_ES add-locale
```

  * Check the xml-file, rename/replace as required and do the same for other xml-files of the project (refer to build.xml how to adapt the ant target)

## File exchange with translator ##

Our experience shows, that freelancer translators find it difficult to deal with XML files or that the XML files they return are usually corrupted. Attention is also required when they are working with systems with a different charset.

We suggest to exchange Excel-Spreadsheets instead of XML, because
  * Usability is better for the translator
  * Excel-Spreadsheets can be enhanced, so that some parts of the sheets are read-only
  * The tools we are using to develop the software can also handle excel sheets, not only XML

### How-to send a file to the translator ###

  * Open a command window in directory nlstools/example/exampleProject
  * Invoke the ant-build target "convert-xml-to-excel"

```
ant convert-xml-to-excel
```


  * Send the Excel files (those ending with .xls) to the translator. You can set some cells to read-only before you send the files or you can delete the locales from the excel files, that you do not want to include.

### How-to work with a file received from the translator ###

  * Check the excel files whether the translator has provided a correct format
  * Copy the files to their positions (just besides their XML-counterpart)
  * Open a command window in directory nlstools/example/exampleProject
  * Compare the changes and check the bundle. (The property 'newLocale' can be omitted.)

```
ant -DnewLocale=es_ES compare-bundles
```

  * Check for missing translations

This task creates a new bundle (XML or Excel) that contains only missing/empty translations or translations marked as "review". This helps to export a file with those translations, that need work to be done by the translator.

```
ant -DnewLocale=es_ES sanity-check
```

#### Option 1 ####

  * IF you want to convert the whole Excel-file back to XML (and replace the XML files), invoke the ant-build target "convert-excel-to-xml"


```
ant convert-excel-to-xml
```

#### Option 2 ####

  * IF you want to merge specific locales from theExcel-file back into XML, invoke the ant-build target "merge-bundles"

```
 ant -DnewLocale=es_ES merge-bundles
```