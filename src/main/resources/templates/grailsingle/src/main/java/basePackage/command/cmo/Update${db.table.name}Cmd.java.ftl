package ${project.basePackage}.command.cmo;

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

/**
 * @author xujin
 *
 */
@Data
public class Update${db.table.name}Cmd {

<#list db.table.columns as column>
    /**
    * ${column.comment!""}
    */
    private ${column.javaType} ${column.fieldName};

</#list>

}
