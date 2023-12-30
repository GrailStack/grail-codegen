<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>${project.groupId}</groupId>
        <artifactId>${project.artifactId}</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </parent>

    <artifactId>${project.artifactId}-core</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <name>${project.artifactId}-core</name>
    <packaging>${(project.subModules.start.packaging)!'war'}</packaging>

    <properties>
        <java.version>1.8</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <#if project.subModules.client??>
            <dependency>
                 <groupId>${project.groupId}</groupId>
                 <artifactId>${project.artifactId}-client</artifactId>
            </dependency>
        </#if>
 <#list (project.dependencies) as dep>
       <#if dep.modulekey =='core'>
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
</project>
