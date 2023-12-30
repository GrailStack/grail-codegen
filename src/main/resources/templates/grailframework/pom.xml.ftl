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
    </properties>

    <modules>
        <#if project.subModules.app??>
        <module>${project.artifactId}-app</module>
        </#if>
        <#if project.subModules.client??>
        <module>${project.artifactId}-client</module>
        </#if>
        <#if project.subModules.domain??>
        <module>${project.artifactId}-domain</module>
        </#if>
        <#if project.subModules.infrastructure??>
        <module>${project.artifactId}-infrastructure</module>
        </#if>
        <#if project.subModules.start??>
        <module>${project.artifactId}-start</module>
        </#if>
    </modules>

    <dependencyManagement>
        <dependencies>
            <#if project.subModules.app??>
            <dependency>
                <groupId>${project.groupId}</groupId>
                <artifactId>${project.artifactId}-app</artifactId>
                <version>1.0.0-SNAPSHOT</version>
            </dependency>
            </#if>
            <#if project.subModules.client??>
            <dependency>
                <groupId>${project.groupId}</groupId>
                <artifactId>${project.artifactId}-client</artifactId>
                <version>1.0.0-SNAPSHOT</version>
            </dependency>
            </#if>
            <#if project.subModules.domain??>
            <dependency>
                <groupId>${project.groupId}</groupId>
                <artifactId>${project.artifactId}-domain</artifactId>
                <version>1.0.0-SNAPSHOT</version>
            </dependency>
            </#if>
            <#if project.subModules.infrastructure??>
            <dependency>
                <groupId>${project.groupId}</groupId>
                <artifactId>${project.artifactId}-infrastructure</artifactId>
                <version>1.0.0-SNAPSHOT</version>
            </dependency>
            </#if>
            <#if project.subModules.start??>
            <dependency>
                <groupId>${project.groupId}</groupId>
                <artifactId>${project.artifactId}-start</artifactId>
                <version>1.0.0-SNAPSHOT</version>
            </dependency>
            </#if>

  <#list (project.dependencies) as dep>
         <#if dep.modulekey =='parent'>
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
    </dependencyManagement>

</project>