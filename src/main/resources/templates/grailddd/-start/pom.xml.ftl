<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>${project.groupId}</groupId>
        <artifactId>${project.artifactId}</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </parent>

    <artifactId>${project.artifactId}-start</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <name>${project.artifactId}-start</name>
    <packaging>${(project.subModules.start.packaging)!'war'}</packaging>

    <properties>
        <java.version>1.8</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <#if project.subModules.core??>
        <dependency>
            <groupId>${project.groupId}</groupId>
            <artifactId>${project.artifactId}-core</artifactId>
        </dependency>
        </#if>

<#list (project.dependencies) as dep>
      <#if dep.modulekey =='start'>
        <#list (dep.dependencyModels) as depModel>
         <dependency>
             <groupId>${depModel.groupId}</groupId>
             <artifactId>${depModel.artifactId}</artifactId>
             <#if (depModel.version)??>
             <version>${depModel.version}</version>
             </#if>
             <#if (depModel.scope)??>
              <scope>${depModel.scope}</scope>
             </#if>
         </dependency>
        </#list>
      </#if>
</#list>
    </dependencies>

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

            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <mainClass>${project.basePackage}.Application</mainClass>
                    <executable>true</executable>
                    <layout>JAR</layout>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                        <configuration>
                            <attach>false</attach>
                        </configuration>
                    </execution>
                </executions>
            </plugin>

            <plugin>
                <groupId>pl.project13.maven</groupId>
                <artifactId>git-commit-id-plugin</artifactId>
                <executions>
                    <execution>
                        <goals>
                            <goal>revision</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <generateGitPropertiesFile>true</generateGitPropertiesFile>
                    <dateFormat>yyyyMMdd</dateFormat>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
