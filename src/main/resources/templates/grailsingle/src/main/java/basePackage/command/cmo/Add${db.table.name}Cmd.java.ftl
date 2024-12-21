package ${project.basePackage}.command.cmo;

<#assign _importList=[
"lombok.Data",
"com.baomidou.mybatisplus.annotation.TableName",
"com.baomidou.mybatisplus.annotation.TableField"
]>
<#list db.table.columns as column>
    <#if !column.javaFullType?starts_with("java.lang")>
        <#if !_importList?seq_contains(column.javaFullType)>
            <#assign _importList= _importList + [column.javaFullType]/>
        </#if>
    </#if>
    <#if column.primaryKey>
        <#if !_importList?seq_contains("com.baomidou.mybatisplus.annotation.TableId")>
            <#assign _importList= _importList + ["com.baomidou.mybatisplus.annotation.TableId"]/>
        </#if>
        <#if column.autoincrement>
            <#if !_importList?seq_contains("static com.baomidou.mybatisplus.annotation.IdType.AUTO")>
                <#assign _importList= _importList + ["static com.baomidou.mybatisplus.annotation.IdType.AUTO"]/>
            </#if>
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
public class Add${db.table.name}Cmd {

<#list db.table.columns as column>
    /**
    * ${column.comment!""}
    */
    private ${column.javaType} ${column.fieldName};

</#list>

}
