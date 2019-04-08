<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>fish.payara.micro</groupId>
    <artifactId>payara-micro-sample</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>war</packaging>

    <properties>
        <version.payara>${payaraMicroVersion}</version.payara>
    </properties>

    <build>
        <plugins>
            <plugin>
                <groupId>fish.payara.maven.plugins</groupId>
                <artifactId>payara-micro-maven-plugin</artifactId>
                <version>1.0.4</version>
                <configuration>
                    <payaraVersion>${r"${version.payara}"}</payaraVersion>
                    <deployWar>false</deployWar>
                    <commandLineOptions>
<#if autoBindHttp == "true">
                        <option>
                            <key>--autoBindHttp</key>
                        </option>
</#if>
                        <option>
                            <key>--deploy</key>
                            <value>${r"${project.build.directory}"}/${r"${project.build.finalName}"}</value>
                        </option>
                    </commandLineOptions>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
