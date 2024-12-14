<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.itgrail.grail</groupId>
        <artifactId>grail-starter-parent</artifactId>
        <version>${grailFrameworkVersion}</version>
    </parent>

    <groupId>${project.groupId}</groupId>
    <artifactId>${project.artifactId}</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <name>${project.artifactId}</name>
    <description>${project.description!project.artifactId}</description>
    <packaging>pom</packaging>

    <properties>
        <java.version>${javaVersion}</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <modules>
        <#if project.subModules.api??>
        <module>${project.artifactId}-api</module>
        </#if>
        <#if project.subModules.service??>
        <module>${project.artifactId}-service</module>
        </#if>
    </modules>

    <dependencyManagement>
        <dependencies>
            <#if project.subModules.api??>
            <dependency>
                <groupId>${project.groupId}</groupId>
                <artifactId>${project.artifactId}-api</artifactId>
                <version>1.0.0-SNAPSHOT</version>
            </dependency>
            </#if>
            <#if project.subModules.service??>
            <dependency>
                <groupId>${project.groupId}</groupId>
                <artifactId>${project.artifactId}-service</artifactId>
                <version>1.0.0-SNAPSHOT</version>
            </dependency>
            </#if>
            <dependency>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <version>1.18.8</version>
                <scope>provided</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <compilerArgs>
                        <arg>-parameters</arg>
                    </compilerArgs>
                    <encoding>${r"${project.build.sourceEncoding}"}</encoding>
                    <source>${r"${java.version}"}</source>
                    <target>${r"${java.version}"}</target>
                </configuration>
            </plugin>

            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-dependency-plugin</artifactId>
                <version>2.10</version>
            </plugin>
        </plugins>
    </build>

</project>