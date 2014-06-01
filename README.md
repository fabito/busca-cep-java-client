[![Build Status](https://travis-ci.org/fabito/busca-cep-java-client.png?branch=master)](https://travis-ci.org/fabito/busca-cep-java-client)
[![Coverage Status](https://coveralls.io/repos/fabito/busca-cep-java-client/badge.png?branch=master)](https://coveralls.io/r/fabito/busca-cep-java-client?branch=master)


busca-cep-java-client
=====================

API Java para invocar a busca de CEPs dos Correios


### Setting up build tools 


#### Gradle

```groovy
repositories {
    mavenCentral()
    mavenRepo url: 'https://github.com/fabito/mvn-repo/raw/master/releases'
}
```
```groovy
dependencies {
    compile "com.github.fabito:busca-cep-java-client:1.3"
}
```

#### Maven

```xml
<repository>
        <id>com.github.fabito</id>
        <url>https://github.com/fabito/mvn-repo/raw/master/releases</url>
        <snapshots>
                <enabled>false</enabled>
        </snapshots>
        <releases>
                <enabled>true</enabled>
        </releases>
</repository>
```

```xml
<dependency>
  <groupId>com.github.fabito</groupId>
  <artifactId>busca-cep-java-client</artifactId>
  <version>1.3</version>
</dependency>
```
