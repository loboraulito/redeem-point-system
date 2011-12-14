<#-- // Property accessors -->
<#foreach property in pojo.getAllPropertiesIterator()>
<#if pojo.getMetaAttribAsBool(property, "gen-property", true)>
	<#if pojo.hasMetaAttribute(property, "finder")>
		<#if pojo.hasFieldJavaDoc(property)>    
		    /**       
		     * ${pojo.getFieldJavaDoc(property, 4)}
		     */
		</#if>
		public static final List<${declarationName}> ${c2j.getMetaAsString(property, "finder","")} (${pojo.importType("org.hibernate.Session")} _session, ${pojo.getJavaTypeName(property, jdk5)} ${property.name}) {
			${pojo.importType("org.hibernate.Query")} query = _session.createQuery("from ${declarationName} as ${declarationName} where ${declarationName}.${property.name} = :${property.name}");
	        query.setParameter("${property.name}", ${property.name});
	        return (List<${declarationName}>) query.list();
		}
	</#if> 
</#if>
</#foreach>
