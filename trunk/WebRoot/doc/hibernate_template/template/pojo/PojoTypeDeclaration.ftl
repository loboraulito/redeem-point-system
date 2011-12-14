/**
${pojo.getClassJavaDoc(clazz.table.comment + "<br>", 0)}
${pojo.getClassJavaDoc("±íÃû£º" + clazz.table.name + "<br>", 0)}
 * @author Hibernate Tools ${version}
 * @version 1.0
 * @since ${date}
 */
<#include "Ejb3TypeDeclaration.ftl"/>
${pojo.getClassModifiers()} ${pojo.getDeclarationType()} ${pojo.getDeclarationName()} extends BaseEntity <#--${pojo.getExtendsDeclaration()} ${pojo.getImplementsDeclaration()}/>