## Glimpse syntax for usage of 'nlstools' with maven2 ##

This demonstrates the use of the NLS Tasks from within a maven2 (http://maven.apache.org) pom.xml:

```
  <build>
    <plugins>
      <plugin>
        <artifactId>maven-antrun-plugin</artifactId>
        <executions>
          <execution>
            <id>generate bundles</id>
            <phase>generate-sources</phase>
            <configuration>
              <tasks>
               <!-- <taskdef name="addLocale"
                         classname="de.viaboxx.nlstools.tasks.AddLocaleTask"/>

                <addLocale
                    from="src/main/bundles/CustomerForm.xml"
                    locales="de_DE"
                    to="src/main/bundles/CustomerForm_de_DE.xml"/>

                <taskdef name="mergeLocale"
                         classname="de.viaboxx.nlstools.tasks.MergeLocaleTask"/>

                <mergeLocale
                    from="src/main/bundles/CustomerForm.xml"
                    with="src/main/bundles/CustomerForm_de_DE_new.xml"
                    locales="da_DK"
                    to="src/main/bundles/CustomerForm_de_DE.xml"/> -->

                <taskdef name="msgbundle"
                         classname="de.viaboxx.nlstools.tasks.MessageBundleTask"/>

                <msgbundle overwrite="true" bundles="src/main/bundles/CustomerForm.xml;../base/src/main/bundles/Common.xml"
                           writeProperties="true"
                           writeJson="true"
                           jsonPath="src\main\webapp\js"
                           jsonFile="i18n"
                           propertyPath="src\main\webapp\WEB-INF\classes"/>
              </tasks>
            </configuration>
            <goals>
              <goal>run</goal>
            </goals>
          </execution>
        </executions>
        <dependencies>
                    <dependency>
                        <groupId>de.viaboxx</groupId>
                        <artifactId>nlstools</artifactId>
                        <version>2.5.9</version>
                    </dependency>
       </dependencies>
      </plugin>
    </plugins>
  </build>
```

Another example:
This time, a java interface and .properties files are generated. It makes use of the build-helper-maven-plugin to automatically add the folders containing the generated files to the maven source/resource directories. So you can clean all generated files automatically with a "mvn clean" and you do not need to set deleteOldFiles="true".

```
             <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>build-helper-maven-plugin</artifactId>
                <version>1.7</version>
                <executions>
                    <execution>
                        <id>add-source</id>
                        <phase>generate-sources</phase>
                        <goals>
                            <goal>add-source</goal>
                        </goals>
                        <configuration>
                            <sources>
                                <source>target/generated-sources/java</source>
                            </sources>
                        </configuration>
                    </execution>
                    <execution>
                        <id>add-resource</id>
                        <phase>generate-resources</phase>
                        <goals>
                            <goal>add-resource</goal>
                        </goals>
                        <configuration>
                            <resources>
                                <resource>
                                    <directory>target/generated-sources/resources</directory>
                                </resource>
                            </resources>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <artifactId>maven-antrun-plugin</artifactId>
                <executions>
                    <execution>
                        <id>generate-bundles</id>
                        <phase>generate-sources</phase>
                        <configuration>
                            <tasks>
                                <taskdef name="msgbundle" classpathref="maven.plugin.classpath"
                                         classname="de.viaboxx.nlstools.tasks.MessageBundleTask"/>

                                <msgbundle writeProperties="true" writeInterface="true" merged="false"
                                           sourcePath="target/generated-sources/java" overwrite="true"
                                           deleteOldFiles="false" debugMode="false" flexLayout="false"
                                           preserveNewlines="true" bundles="i18n/main.xml"
                                           propertyPath="target/generated-sources/resources"/>
                            </tasks>
                        </configuration>
                        <goals>
                            <goal>run</goal>
                        </goals>
                    </execution>
                </executions>
                <dependencies>
                    <dependency>
                        <groupId>de.viaboxx</groupId>
                        <artifactId>nlstools</artifactId>
                        <version>2.5.9</version>
                    </dependency>
                </dependencies>
            </plugin>
```