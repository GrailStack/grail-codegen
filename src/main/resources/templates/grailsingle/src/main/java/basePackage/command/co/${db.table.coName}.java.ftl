package ${project.basePackage}.command.co;

<#assign _importList=[
"lombok.Data"
]>
<#list db.table.columns as column>
    <#if !column.javaFullType?starts_with("java.lang")>
        <#if !_importList?seq_contains(column.javaFullType)>
            <#assign _importList= _importList + [column.javaFullType]/>
        </#if>
    </#if>
</#list>
<#assign _importList = _importList?sort>
<#list _importList as importEle>
import ${importEle};
</#list>

@Data
public class ${db.table.coName} {
<#list db.table.columns as column>
    /**
    * ${column.comment!""}
    */
    private ${column.javaType} ${column.fieldName};

</#list>
}
