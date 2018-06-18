<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>fish.payara.micro</groupId>
    <artifactId>payara-micro-sample</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>war</packaging>

    <properties>
        <version.payara.micro>${payaraMicroVersion}</version.payara.micro>
    </properties>

    <build>
        <plugins>
            <plugin>
                <groupId>fish.payara.maven.plugins</groupId>
                <artifactId>payara-micro-maven-plugin</artifactId>
                <version>1.0.1</version>
                <configuration>
                    <payaraVersion>${r"${version.payara.micro}"}</payaraVersion>
                    <deployWar>true</deployWar>
<#if autoBindHttp == "true">
                    <commandLineOptions>
                        <option>
                            <key>--autoBindHttp</key>
                        </option>
                    </commandLineOptions>
</#if>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
