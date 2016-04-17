[![Build Status](https://travis-ci.org/fabito/busca-cep-java-client.png?branch=master)](https://travis-ci.org/fabito/busca-cep-java-client)
[![Coverage Status](https://coveralls.io/repos/fabito/busca-cep-java-client/badge.png?branch=master)](https://coveralls.io/r/fabito/busca-cep-java-client?branch=master)
[![Download](https://api.bintray.com/packages/fabito/maven/busca-cep-java-client/images/download.svg) ](https://bintray.com/fabito/maven/busca-cep-java-client/_latestVersion)


busca-cep-java-client
=====================

API Java para invocar a busca de CEPs dos Correios

```groovy
#!/usr/bin/env groovy

@Grapes([
	@Grab('com.github.fabito:busca-cep-java-client:1.7')
])

import org.talesolutions.cep.CEPService
import org.talesolutions.cep.CEPServiceFactory

def buscaCEP = CEPServiceFactory.getCEPService()
def cep = buscaCEP.obtemPorNumeroCEP("13084440")

println cep
```



### Setting up build tools 


#### Gradle

```groovy
repositories {
    maven {
        url  "http://dl.bintray.com/fabito/maven" 
    }
}
```
```groovy
dependencies {
	compile 'com.github.fabito:busca-cep-java-client:1.7'
}
```

#### Maven

```xml
<?xml version='1.0' encoding='UTF-8'?>
<settings xsi:schemaLocation='http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd' xmlns='http://maven.apache.org/SETTINGS/1.0.0' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>
<profiles>
	<profile>
		<repositories>
			<repository>
				<snapshots>
					<enabled>false</enabled>
				</snapshots>
				<id>bintray-fabito-maven</id>
				<name>bintray</name>
				<url>http://dl.bintray.com/fabito/maven</url>
			</repository>
		</repositories>
		<pluginRepositories>
			<pluginRepository>
				<snapshots>
					<enabled>false</enabled>
				</snapshots>
				<id>bintray-fabito-maven</id>
				<name>bintray-plugins</name>
				<url>http://dl.bintray.com/fabito/maven</url>
			</pluginRepository>
		</pluginRepositories>
		<id>bintray</id>
	</profile>
</profiles>
<activeProfiles>
	<activeProfile>bintray</activeProfile>
</activeProfiles>
</settings>
```

```xml
<dependency>
  <groupId>com.github.fabito</groupId>
  <artifactId>busca-cep-java-client</artifactId>
  <version>1.7</version>
</dependency>
```
