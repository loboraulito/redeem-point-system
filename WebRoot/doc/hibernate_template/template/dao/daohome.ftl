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
public class ${declarationName}Home extends ${pojo.importType("org.springframework.orm.hibernate3.support.HibernateDaoSupport")} {

    private static final ${pojo.importType("org.apache.commons.logging.Log")} log = ${pojo.importType("org.apache.commons.logging.LogFactory")}.getLog(${pojo.getDeclarationName()}Home.class);

<#if ejb3>
    @${pojo.importType("javax.persistence.PersistenceContext")} private ${pojo.importType("javax.persistence.EntityManager")} entityManager;
    
    public void persist(${declarationName} transientInstance) {
        log.info("persisting ${declarationName} instance");
        try {
            entityManager.persist(transientInstance);
            log.info("persist successful");
        }
        catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }
    
    public void remove(${declarationName} persistentInstance) {
        log.info("removing ${declarationName} instance");
        try {
            entityManager.remove(persistentInstance);
            log.info("remove successful");
        }
        catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }
    
    public ${declarationName} merge(${declarationName} detachedInstance) {
        log.info("merging ${declarationName} instance");
        try {
            ${declarationName} result = entityManager.merge(detachedInstance);
            log.info("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }
    
<#if clazz.identifierProperty?has_content>    
    public ${declarationName} findById( ${pojo.getJavaTypeName(clazz.identifierProperty, jdk5)} id) {
        log.info("getting ${declarationName} instance with id: " + id);
        try {
            ${declarationName} instance = entityManager.find(${pojo.getDeclarationName()}.class, id);
            log.info("get successful");
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
</#if>
<#else>    
	protected void initDao() {
        //do nothing
    }    
        
    public void saveOrUpdate(${declarationName} instance) {
        log.info("saving or updating dirty ${declarationName} instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.info("save or update successful");
        }
        catch (RuntimeException re) {
            log.error("save or update failed", re);
            throw re;
        }
    }
    
    public void save(${declarationName} instance) {
        log.info("saving dirty ${declarationName} instance");
        try {
            getHibernateTemplate().save(instance);
            log.info("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
<#if jdk5>
	public void saveOrUpdateAll(${pojo.importType("java.util.List")}<${declarationName}> persistentInstances) {
<#else>
	public void saveOrUpdateAll(${pojo.importType("java.util.List")} persistentInstances) {
</#if>
        log.info("saveOrUpdateAll role");
        try {
        	getHibernateTemplate().saveOrUpdateAll(persistentInstances);
        } catch (RuntimeException re) {
            log.error("saveOrUpdateAll failed", re);
            throw re;
        }
    }
    
    
    public void delete(${declarationName} persistentInstance) {
        log.info("deleting ${declarationName} instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.info("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
<#if jdk5>
	public void deleteAll(${pojo.importType("java.util.List")}<${declarationName}> persistentInstances) {
<#else>
	public void deleteAll(${pojo.importType("java.util.List")} persistentInstances) {
</#if>
        log.info("deleting ${declarationName} instance");
        try {
            getHibernateTemplate().deleteAll(persistentInstances);
            log.info("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public ${declarationName} merge(${declarationName} detachedInstance) {
        log.info("merging ${declarationName} instance");
        try {
            ${declarationName} result = (${declarationName}) getHibernateTemplate().merge(detachedInstance);
            log.info("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }
    
<#if jdk5>
	@SuppressWarnings("unchecked")
	public ${pojo.importType("java.util.List")}<${declarationName}> findByProperty(String propertyName, Object value) {
<#else>
	public ${pojo.importType("java.util.List")} findByProperty(String propertyName, Object value) {
</#if>
    	log.info("finding ${declarationName} instance with property: " + propertyName + ", value: " + value);
        try {
        	String queryString = "from ${declarationName} as model where model." + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }
    
    
<#if clazz.identifierProperty?has_content>
    public ${declarationName} findById( ${c2j.getJavaTypeName(clazz.identifierProperty, jdk5)} id) {
        log.info("getting ${declarationName} instance with id: " + id);
        try {
            ${declarationName} instance = (${declarationName}) getHibernateTemplate().get("${clazz.entityName}", id);
            if (instance==null) {
                log.info("get successful, no instance found");
            } else {
                log.info("get successful, instance found");
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
    public ${declarationName} findByNaturalId(${c2j.asNaturalIdParameterList(clazz)}) {
        log.info("getting ${declarationName} instance by natural id");
        try {
            ${declarationName} instance = (${declarationName}) sessionFactory.getCurrentSession()
                    .createCriteria("${clazz.entityName}")
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
                log.info("get successful, no instance found");
            }
            else {
                log.info("get successful, instance found");
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
	@SuppressWarnings("unchecked")
    public ${pojo.importType("java.util.List")}<${declarationName}> findByExample(${declarationName} instance) {
<#else>
    public ${pojo.importType("java.util.List")} findByExample(${declarationName} instance) {
</#if>
        log.info("finding ${declarationName} instance by example");
        try {
<#if jdk5>
            ${pojo.importType("java.util.List")}<${declarationName}> results = getHibernateTemplate().findByExample(instance);
<#else>
            ${pojo.importType("java.util.List")} results = getHibernateTemplate().findByExample(instance);
</#if>
            log.info("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

<#if jdk5>
	@SuppressWarnings("unchecked")
	public ${pojo.importType("java.util.List")}<${declarationName}> findByParams(final String sql, final boolean isHql,final int start, final int limit, final ${pojo.importType("java.util.Map")}<String, Object> params) {
<#else>
	public ${pojo.importType("java.util.List")} findByParams(final String sql, final boolean isHql,final int start, final int limit, final ${pojo.importType("java.util.Map")}<String, Object> params) {
</#if>
    	log.info("finding by ${declarationName} instance by sql : " + sql);
    	<#if jdk5>
    	return getHibernateTemplate().executeFind(new ${pojo.importType("org.springframework.orm.hibernate3.HibernateCallback")}<Object>(){
    	<#else>
    	return getHibernateTemplate().executeFind(new ${pojo.importType("org.springframework.orm.hibernate3.HibernateCallback")}(){
    	</#if>
    		public Object doInHibernate(${pojo.importType("org.hibernate.Session")} session)
    			throws ${pojo.importType("org.hibernate.HibernateException")}, ${pojo.importType("java.sql.SQLException")}{
    			${pojo.importType("org.hibernate.Query")} query = null;
    			if("".equals(sql) || sql == null){
    				query = session.createQuery("from ${declarationName}");
    			}else{
    				if(isHql){
    					query = session.createQuery(sql);
    				}else{
    					query = session.createSQLQuery(sql);
    				}
    			}
    			if(start > -1){
    				query.setFirstResult(start);
    			}
    			if(limit > -1){
    				query.setMaxResults(limit);
    			}
    			if(params != null){
	    			<#if jdk5>
	    			for(String key : params.keySet()){
	            		query.setParameter(key, params.get(key));
	        		}
	    			<#else>
	    			for (${pojo.importType("java.util.Iterator")} i = params.iterator(); i.hasNext(); ) {
					    String key = i.next();
					    query.setParameter(key, params.get(key));
					}
	    			</#if>
    			}
	            return query.list();
    		}
    	});
    }
    
<#if jdk5>
	public int findCountByParams(String sql, boolean isHql,int start, int limit, ${pojo.importType("java.util.Map")}<String, Object> params) {
<#else>
	public int findByParams(String sql, boolean isHql,int start, int limit, ${pojo.importType("java.util.Map")}<String, Object> params) {
</#if>
    	log.info("finding count by sql : " + sql);
    	${pojo.importType("org.hibernate.Session")} session = getHibernateTemplate().getSessionFactory().openSession();
    	
    	${pojo.importType("org.hibernate.Query")} query = null;
		if("".equals(sql) || sql == null){
			query = session.createQuery("from ${declarationName}");
		}else{
			if(isHql){
				query = session.createQuery(sql);
			}else{
				query = session.createSQLQuery(sql);
			}
		}
		if(start > -1){
			query.setFirstResult(start);
		}
		if(limit > -1){
			query.setMaxResults(limit);
		}
		if(params != null){
			<#if jdk5>
			for(String key : params.keySet()){
        		query.setParameter(key, params.get(key));
    		}
			<#else>
			for (${pojo.importType("java.util.Iterator")} i = params.iterator(); i.hasNext(); ) {
			    String key = i.next();
			    query.setParameter(key, params.get(key));
			}
			</#if>
		}
        return query.list().size();
    }
    
<#foreach queryName in cfg.namedQueries.keySet()>
<#if queryName.startsWith(clazz.entityName + ".")>
<#assign methname = c2j.unqualify(queryName)>
<#assign params = cfg.namedQueries.get(queryName).parameterTypes><#assign argList = c2j.asFinderArgumentList(params, pojo)>
<#if jdk5 && methname.startsWith("find")>
    public ${pojo.importType("java.util.List")}<${declarationName}> ${methname}(${argList}) {
<#elseif methname.startsWith("count")>
    public int ${methname}(${argList}) {
<#else>
    public ${pojo.importType("java.util.List")} ${methname}(${argList}) {
</#if>
        ${pojo.importType("org.hibernate.Query")} query = sessionFactory.getCurrentSession()
                .getNamedQuery("${queryName}");
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
}
</#assign>

${pojo.generateImports()}
${classbody}
