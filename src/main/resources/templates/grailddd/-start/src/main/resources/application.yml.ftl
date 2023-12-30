<#if project.dbConfigure>
spring:
  <#if (db.dbName)??>
  datasource:
    url: ${db.dbUrl}
    username: ${db.dbUserName}
    password: ${db.dbPassword}
    type: com.zaxxer.hikari.HikariDataSource
    hikari:
      maximum-pool-size: 20
      minimum-idle: 10
   <#else>
  datasource:
    url: jdbc:mysql://localhost:3306/forge?useUnicode=true&autoReconnect=true&rewriteBatchedStatements=TRUE&characterEncoding=utf8
    username: root
    password: root
    type: com.zaxxer.hikari.HikariDataSource
    hikari:
      maximum-pool-size: 20
      minimum-idle: 10
</#if>
</#if>

server:
  port: 8080

<#if project.hasDependencyForModule("start", "ngfs-starter-swagger")>
grail:
  swagger:
    enabled: true
    title: ${project.artifactId}
    name: ${project.artifactId}
    description: ${project.description!project.artifactId}
    version: 1.0.0
    contactName: xx.xx
    contactEmail: xx.xx@ityst.com
</#if>


