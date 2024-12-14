<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>${project.groupId}</groupId>
        <artifactId>${project.artifactId}</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </parent>

    <groupId>${project.groupId}</groupId>
    <artifactId>${project.artifactId}-service</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <name>${project.artifactId}-service</name>
    <description>${project.description!project.artifactId}</description>
    <packaging>${(project.subModules.service.packaging)!'jar'}</packaging>

    <properties>
        <java.version>${javaVersion}</java.version>
    </properties>

    <dependencies>

        <#if project.subModules.api??>
         <dependency>
             <groupId>${project.groupId}</groupId>
             <artifactId>${project.artifactId}-api</artifactId>
             <version>1.0.0-SNAPSHOT</version>
         </dependency>
        </#if>

<#list (project.dependencies) as dep>
       <#if dep.modulekey =='service'>
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
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <mainClass>${project.basePackage}.Application</mainClass>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>