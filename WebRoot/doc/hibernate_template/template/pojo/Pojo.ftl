/*
 * XXXX系统
 * Copyright (c) 2009 HZH All Rights Reserved.
 */
${pojo.getPackageDeclaration()}

<#assign classbody>
import com.hzh.common.base.entity.BaseEntity;

<#include "PojoTypeDeclaration.ftl"/> {
	/**
	 * UID
	 */
	private static final long serialVersionUID = 1L;
<#if !pojo.isInterface()>
<#include "PojoFields.ftl"/>

<#include "PojoConstructors.ftl"/>
   
<#include "PojoPropertyAccessors.ftl"/>

<#include "PojoToString.ftl"/>

<#include "PojoEqualsHashcode.ftl"/>

<#else>
<#include "PojoInterfacePropertyAccessors.ftl"/>

</#if>
<#include "PojoExtraClassCode.ftl"/>

}
</#assign>

${pojo.generateImports()}
${classbody}

