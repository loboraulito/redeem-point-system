<#assign value = property.value>
<#assign keyValue = value.getKey()>
<#assign elementValue = value.getElement()>
<#assign indexValue = value.getIndex()>
<#assign elementTag = c2h.getCollectionElementTag(property)>

	<list name="${property.name}" inverse="${value.inverse?string}" table="${value.collectionTable.quotedName}"
	lazy="${c2h.getCollectionLazy(value)}"
	<#if property.cascade != "none">
        cascade="${property.cascade}"
	</#if>
	<#if c2h.hasFetchMode(property)> fetch="${c2h.getFetchMode(property)}"</#if>>
		<#assign metaattributable=property>
		<#include "meta.hbm.ftl">
		<#include "key.hbm.ftl">		
    		<index>
    			<#foreach column in indexValue.columnIterator>
	    			<#foreach keyColumn in keyValue.columnIterator>
	   					<#if keyColumn.equals(column)>
	   						<#assign isKey = true>
	   						<#break> 
	   					</#if>
	   				</#foreach>
	   				<#if !isKey>
    					<#include "column.hbm.ftl">
    					<#assign isKey = false>
    				</#if>
			</#foreach>
			</index>
    		<#include "${elementTag}-element.hbm.ftl">
	</list>


