${pojo.getPackageDeclaration()}
// Generated ${date} by Hibernate Tools ${version}

<#assign classbody>
<#assign declarationName = pojo.importType(pojo.getDeclarationName())>/**
 * Home object for domain model class ${declarationName}.
 * @see ${pojo.getQualifiedDeclarationName()}
 * @author Hibernate Tools
 */
<#if ejb3>
@${pojo.importType("javax.ejb.Stateless")}
</#if>
public class ${declarationName}Home extends ${declarationName} {

    private static final ${pojo.importType("org.apache.log4j.Logger")} log = ${pojo.importType("org.apache.log4j.Logger")}.getLogger(${pojo.getDeclarationName()}.class.getName());
////////////// FINDERS CODE STARTS /////////////////
<#include "PojoFinderMethods.ftl"/>
////////////// FINDERS CODE ENDS /////////////////

////////////// DAO CODE STARTS /////////////////
<#if !pojo.isComponent()>

<#if ejb3>
    @${pojo.importType("javax.persistence.PersistenceContext")} private ${pojo.importType("javax.persistence.EntityManager")} entityManager;
    
    public void persist(${declarationName} transientInstance) {
        log.debug("persisting ${declarationName} instance");
        try {
            entityManager.persist(transientInstance);
            log.debug("persist successful");
        }
        catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }
    
    public void remove(${declarationName} persistentInstance) {
        log.debug("removing ${declarationName} instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        }
        catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }
    
    public ${declarationName} merge(${declarationName} detachedInstance) {
        log.debug("merging ${declarationName} instance");
        try {
            ${declarationName} result = entityManager.merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }
    
<#if clazz.identifierProperty?has_content>    
    public ${declarationName} findById( ${pojo.getJavaTypeName(clazz.identifierProperty, jdk5)} id) {
        log.debug("getting ${declarationName} instance with id: " + id);
        try {
            ${declarationName} instance = entityManager.find(${pojo.getDeclarationName()}.class, id);
            log.debug("get successful");
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
</#if>
<#else>    
    public static final void persist(${pojo.importType("org.hibernate.Session")} _session, ${declarationName} transientInstance) {
        log.debug("persisting ${declarationName} instance");
        try {
            _session.persist(transientInstance);
            log.debug("persist successful");
        }
        catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }
    
    public static final void save(${pojo.importType("org.hibernate.Session")} _session, ${declarationName} transientInstance) {
        log.debug("saving ${declarationName} instance");
        try {
            _session.save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
        
    public static final void update(${pojo.importType("org.hibernate.Session")} _session, ${declarationName} transientInstance) {
        log.debug("updating ${declarationName} instance");
        try {
            _session.update(transientInstance);
            log.debug("updating successful");
        }
        catch (RuntimeException re) {
            log.error("updating failed", re);
            throw re;
        }
    }
    
    public static final void saveOrUpdate(${pojo.importType("org.hibernate.Session")} _session, ${declarationName} instance) {
    	attachDirty(_session, instance);
    }
    
    public static final void attachDirty(${pojo.importType("org.hibernate.Session")} _session, ${declarationName} instance) {
        log.debug("attaching dirty ${declarationName} instance");
        try {
            _session.saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public static final void attachClean(${pojo.importType("org.hibernate.Session")} _session, ${declarationName} instance) {
        log.debug("attaching clean ${declarationName} instance");
        try {
            _session.lock(instance, ${pojo.importType("org.hibernate.LockMode")}.NONE);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public static final void delete(${pojo.importType("org.hibernate.Session")} _session, ${declarationName} persistentInstance) {
        log.debug("deleting ${declarationName} instance");
        try {
            _session.delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public static final ${declarationName} merge(${pojo.importType("org.hibernate.Session")} _session, ${declarationName} detachedInstance) {
        log.debug("merging ${declarationName} instance");
        try {
            ${declarationName} result = (${declarationName}) _session.merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }
    
<#if clazz.identifierProperty?has_content>
    public static final ${declarationName} findById(${pojo.importType("org.hibernate.Session")} _session, ${c2j.getJavaTypeName(clazz.identifierProperty, jdk5)} id) {
        log.debug("getting ${declarationName} instance with id: " + id);
        try {
            ${declarationName} instance = (${declarationName}) _session.get("${clazz.entityName}", id);
            if (instance==null) {
                log.debug("get successful, no instance found");
            }
            else {
                log.debug("get successful, instance found");
            }
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
</#if>
    
<#if clazz.hasNaturalId()>
    public static final ${declarationName} findByNaturalId(${pojo.importType("org.hibernate.Session")} _session, ${c2j.asNaturalIdParameterList(clazz)}) {
        log.debug("getting ${declarationName} instance by natural id");
        try {
            ${declarationName} instance = (${declarationName}) _session.createCriteria("${clazz.entityName}")
<#if jdk5>
                    .add( ${pojo.staticImport("org.hibernate.criterion.Restrictions", "naturalId")}()
<#else>
                   .add( ${pojo.importType("org.hibernate.criterion.Restrictions")}.naturalId()
</#if>                    
<#foreach property in pojo.getAllPropertiesIterator()>
<#if property.isNaturalIdentifier()>
                            .set("${property.name}", ${property.name})
</#if>
</#foreach>
                        )
                    .uniqueResult();
            if (instance==null) {
                log.debug("get successful, no instance found");
            }
            else {
                log.debug("get successful, instance found");
            }
            return instance;
        }
        catch (RuntimeException re) {
            log.error("query failed", re);
            throw re;
        }
    }
</#if>    
<#if jdk5>
    public static final ${pojo.importType("java.util.List")}<${declarationName}> findByExample(${pojo.importType("org.hibernate.Session")} _session, ${declarationName} instance) {
<#else>
    public static final ${pojo.importType("java.util.List")} findByExample(${pojo.importType("org.hibernate.Session")} _session, ${declarationName} instance) {
</#if>
        log.debug("finding ${declarationName} instance by example");
        try {
<#if jdk5>
            ${pojo.importType("java.util.List")}<${declarationName}> results = (List<${declarationName}>) _session
<#else>
            ${pojo.importType("java.util.List")} results = _session
</#if>
                    .createCriteria("${clazz.entityName}")
<#if jdk5>
                    .add( ${pojo.staticImport("org.hibernate.criterion.Example", "create")}(instance) )
<#else>
                    .add(${pojo.importType("org.hibernate.criterion.Example")}.create(instance))
</#if>
            .list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    } 
<#foreach queryName in cfg.namedQueries.keySet()>
<#if queryName.startsWith(clazz.entityName + ".")>
<#assign methname = c2j.unqualify(queryName)>
<#assign params = cfg.namedQueries.get(queryName).parameterTypes><#assign argList = c2j.asFinderArgumentList(params, pojo)>
<#if jdk5 && methname.startsWith("find")>
    public static final ${pojo.importType("java.util.List")}<${declarationName}> ${methname}(${pojo.importType("org.hibernate.Session")} _session, ${argList}) {
<#elseif methname.startsWith("count")>
    public static final int ${methname}(${pojo.importType("org.hibernate.Session")} _session, ${argList}) {
<#else>
    public static final ${pojo.importType("java.util.List")} ${methname}(${pojo.importType("org.hibernate.Session")} _session, ${argList}) {
</#if>
        ${pojo.importType("org.hibernate.Query")} query = _session.getNamedQuery("${queryName}");
<#foreach param in params.keySet()>
<#if param.equals("maxResults")>
		query.setMaxResults(maxResults);
<#elseif param.equals("firstResult")>
        query.setFirstResult(firstResult);
<#else>
        query.setParameter("${param}", ${param});
</#if>
</#foreach>
<#if jdk5 && methname.startsWith("find")>
        return (List<${declarationName}>) query.list();
<#elseif methname.startsWith("count")>
        return ( (Integer) query.uniqueResult() ).intValue();
<#else>
        return query.list();
</#if>
    }
</#if>
</#foreach></#if>
////////////// DAO CODE ENDS /////////////////
</#if>

}
</#assign>

${pojo.generateImports()}
${classbody}
