<#-- // Property accessors -->
<#foreach property in pojo.getAllPropertiesIterator()>
<#if pojo.getMetaAttribAsBool(property, "gen-property", true)>
    /**  
<#if pojo.hasFieldJavaDoc(property)>    
     * ${pojo.getFieldJavaDoc(property, 4)}
</#if>
<#foreach column in property.columnIterator><#if column.comment?exists && column.comment?trim?length!=0>     * »°µ√ ${column.comment}.
     * @return ${column.comment}
</#if>
</#foreach>
     */
    <#include "GetPropertyAnnotation.ftl"/>
    ${pojo.getPropertyGetModifiers(property)} ${pojo.getJavaTypeName(property, jdk5)} ${pojo.getGetterSignature(property)}() {
        return this.${property.name};
    }

    /**
<#if pojo.hasFieldJavaDoc(property)>    
     * ${pojo.getFieldJavaDoc(property, 4)}
</#if>
<#foreach column in property.columnIterator><#if column.comment?exists && column.comment?trim?length!=0>     * …Ë÷√ ${column.comment}.
     * @param ${property.name} ${column.comment}
</#if>
</#foreach>
     */   
    ${pojo.getPropertySetModifiers(property)} void set${pojo.getPropertyName(property)}(${pojo.getJavaTypeName(property, jdk5)} ${property.name}) {
        this.${property.name} = ${property.name};
    }
</#if>
</#foreach>
