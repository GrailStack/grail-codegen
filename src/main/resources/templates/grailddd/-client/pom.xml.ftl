<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.itgrail.grail</groupId>
        <artifactId>grail-starter-parent</artifactId>
        <version>${grailFrameworkVersion}</version>
    </parent>

    <groupId>${project.groupId}</groupId>
    <artifactId>${project.artifactId}-client</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <name>${project.artifactId}-client</name>

    <properties>
        <java.version>1.8</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
 <#list (project.dependencies) as dep>
      <#if dep.modulekey =='client'>
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
