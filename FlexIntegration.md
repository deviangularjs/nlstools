# Introduction #

You can build your flex project (http://www.adobe.com/de/products/flex) with the maven (http://maven.apache.org) flex mojos (http://flexmojos.sonatype.org).

## pom.xml to generate a flex.as file ##

under the build section of the pom place something like this to generate a flex source file with resource keys:

```
         <plugin>
                <artifactId>maven-antrun-plugin</artifactId>
                <executions>
                    <execution>
                        <id>clean</id>
                        <phase>clean</phase>
                        <configuration>
                            <tasks>
                                <delete>
                                    <fileset dir="src/main/flex/de/viaboxx/flexClient/">
                                        <include name="I18nKeys.as"/>
                                    </fileset>
                                </delete>
                            </tasks>
                        </configuration>
                        <goals>
                            <goal>run</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>generate-bundles</id>
                        <phase>generate-sources</phase>
                        <configuration>
                            <tasks>
                                <taskdef name="msgbundle"
                                         classname="de.viaboxx.nlstools.tasks.MessageBundleTask"/>

                                <msgbundle writeProperties="false"
                                           writeJson="false"
                                           writeInterface="Flex"
                                           overwrite="true"
                                           deleteOldFiles="false"
                                           debugMode="false"
                                           bundles="../i18n/main-default.xml"
                                           sourcePath="src/main/flex"
                                        />

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
                        <version>2.5.7</version>
                    </dependency>
                </dependencies>
            </plugin>
```

### Flex I18nKeys.as ###

This generates:

1. The file I18nKeys.as in src/main/flex

```
package de.viaboxx.flexClient{

/**
 * contains keys of resource bundle main.
 * THIS FILE HAS BEEN GENERATED AUTOMATICALLY - DO NOT ALTER!
 */
public class I18nKeys {
  public static const _BUNDLE_NAME:String = "main";

  /** {ar_AE} {de_DE} {en_US} {fr_FR} {it_IT} =>  */
  public static const askForCustomsDuty_question_text:String = "askForCustomsDuty.question.text";
  /** {ar_AE} {de_DE} {en_US} {fr_FR} {it_IT} => إدخال باستخدام لوحة المفاتيح */
  public static const barcode_button_inputSwitchKeyboard:String = "barcode.button.inputSwitchKeyboard";
  /** {ar_AE} {de_DE} {en_US} {fr_FR} {it_IT} => إدخال باستخدام الماسح الضوئي */
  public static const barcode_button_inputSwitchScanner:String = "barcode.button.inputSwitchScanner";

...
```

2. You can create a subclass of the generated file for convenience:

file I18n.as in src/main/flex/<your package here>
```
package de.viaboxx.flexClient {
import flash.events.Event;
import flash.events.EventDispatcher;
import flash.events.IEventDispatcher;

import mx.logging.ILogger;
import mx.logging.Log;
import mx.resources.IResourceManager;
import mx.resources.ResourceManager;

public class I18n extends I18nKeys implements IEventDispatcher, I18nFactory {

    private static var resourceManager:IResourceManager = ResourceManager.getInstance();
    private static var _instance:I18n;

    private var eventDispatcher:EventDispatcher;

    [Logger]
    public var log:ILogger = Log.getLogger("de.viaboxx.flexClient.I18n");

    public static var factory:I18nFactory = new I18n();


    function I18n() {
        eventDispatcher = new EventDispatcher(this);
        resourceManager.addEventListener("change", function(event:Event):void {
            dispatchEvent(event);
        });
    }

    public static function get bundle():I18n {
        if (_instance == null) {
            _instance = factory.newI18nInstance();
        }
        return _instance;
    }

    public static function forceNewBundle():void {
        _instance = null;
    }

    [Bindable("change")]
    public function string(key:String, parameters:Array = null, locale:String = null):String {
        var translation:String = resourceManager.getString(_BUNDLE_NAME, key, parameters, locale);
        if (translation == null) {
            if(log) log.warn("Cannot find translation. key=" + key);
            translation = key;
        }
        return translation;
    }

    /**
     * Fetches an integer from the bundle.
     *
     * @param key The key to look up the value for.
     * @param locale The locale to use for lookup. Defaults to the currently chosen locale.
     * @return The integer found in the resource bundle or "0" if the key is not found.
     */
    public function integer(key:String, locale:String = null):int {
        // TODO add logging when key is not found?!
        return resourceManager.getInt(_BUNDLE_NAME, key, locale);
    }

    public function dispatchEvent(event:Event):Boolean {
        return eventDispatcher.dispatchEvent(event);
    }

    public function hasEventListener(type:String):Boolean {
        return eventDispatcher.hasEventListener(type);
    }

    public function willTrigger(type:String):Boolean {
        return eventDispatcher.willTrigger(type);
    }

    public function removeEventListener(type:String, listener:Function, useCapture:Boolean = false):void {
        eventDispatcher.removeEventListener(type, listener, useCapture);
    }

    public function addEventListener(type:String, listener:Function, useCapture:Boolean = false, priority:int = 0, useWeakReference:Boolean = false):void {
        eventDispatcher.addEventListener(type, listener, useCapture, priority, useWeakReference);
    }

    public function newI18nInstance():I18n {
        return new I18n();
    }
}
}

```

and this interface (in case you want to mock the implementation for testing)

```
package de.viaboxx.flexClient {
public interface I18nFactory {
    function newI18nInstance():I18n;
}
}
```
### Usage in Flex Skins and Views ###

```
  <s:Label text="{I18n.bundle.string(I18nKeys.askForCustomsDuty_question_text)}"
```

## pom.xml to generate properties files in flex layout ##

3. Generated properties files in flex project layout

To generate the properties files, the pom.xml can be like this:
```
<plugin>
                <artifactId>maven-antrun-plugin</artifactId>
                <executions>
                    <execution>
                        <id>clean</id>
                        <phase>clean</phase>
                        <configuration>
                            <tasks>
                                <delete>
                                    <fileset dir="src/main/locales">
                                        <include name="**/*.properties"/>
                                    </fileset>
                                </delete>
                            </tasks>
                        </configuration>
                        <goals>
                            <goal>run</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>generate-bundles</id>
                        <phase>generate-sources</phase>
                        <configuration>
                            <tasks>
                                <taskdef name="msgbundle"
                                         classname="de.viaboxx.nlstools.tasks.MessageBundleTask"/>
                                <msgbundle writeProperties="true" writeJson="false" writeInterface="false"
                                           overwrite="true" deleteOldFiles="false" debugMode="false"
                                           flexLayout="true"
                                           preserveNewlines="true"
                                           allowedLocales="en_US;ar_AE"
                                           bundles="../i18n/main-default.xml"
                                           propertyPath="src/main/locales"/>
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
                        <version>2.5.5</version>
                    </dependency>
                </dependencies>
            </plugin>
```

You find the generated properties files under src/main/locales/<the locale here>/main.properties


### More? ###

Refer to ExampleProject and BestPractise