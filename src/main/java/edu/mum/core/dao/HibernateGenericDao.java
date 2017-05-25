package edu.mum.core.dao;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.metadata.ClassMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
//import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

import edu.mum.core.exception.AppException;
import edu.mum.core.web.PageReq;
import edu.mum.core.web.QueryReq;

/**
 * Hibernate Dao的泛型基类.
 * 
 * @author mz
 * @see HibernateDaoSupport
 * @see HibernateEntityDao
 */
@SuppressWarnings("unchecked")
public class HibernateGenericDao {
	
	@PersistenceContext
    protected EntityManager entityManager;
	
//	private static Log log = LogFactory.getLog(HibernateGenericDao.class);

	private static Logger log = LoggerFactory.getLogger(HibernateGenericDao.class);
	
	protected static int DEFAULT_FETCH_SIZE = 1000;

	private static final int SYSTEM_ERROR = 1000;

	private static final Pattern PATTERN_SELECT = Pattern.compile("SELECT.*?\\sFROM\\s", Pattern.CASE_INSENSITIVE);
	private static final Pattern PATTERN_GROUPBY = Pattern.compile("\\sgroup\\s+?by\\s", Pattern.CASE_INSENSITIVE);
	private static final Pattern PATTERN_DISTINCT = Pattern.compile("\\sDISTINCT\\s", Pattern.CASE_INSENSITIVE);
	private static final String SELEC_COUNT_FROM = "SELECT COUNT(*) FROM ";

	/**
	 * 根据ID获取对象. 如果对象不存在，抛出异常.
	 */
	public <T> T get(Class<T> entityClass, Serializable id) throws AppException {
		T t = null;
		try {
			t = (T)entityManager.find(entityClass, id);
		}
		catch (Exception e) {
			handleException(e);
		}
		return t;
	}

	/**
	 * 获取全部对象.
	 */
	public <T> List<T> getAll(Class<T> entityClass) throws AppException {
		List<T> list = null;
		try {
			list = entityManager.createQuery( "from " + entityClass.getName() )
				       .getResultList();
		}
		catch (Exception e) {
			handleException(e);
		}
		return list;
	}


	/**
	 * 保存对象.
	 */
	public void save(Object obj) throws AppException {
		try {
			this.save(obj,true);
		}
		catch (Exception e) {
			handleException(e);
		}
	}
	
	public void save(Object obj, boolean isFlush) throws AppException {
		try {
			entityManager.persist(obj);
			flush(isFlush);
		}
		catch (Exception e) {
			handleException(e);
		}
	}
	
	/**
	 * 删除对象.
	 */
	public void remove(Object o) throws AppException {
		try {
			entityManager.remove(o);
		}
		catch (Exception e) {
			handleException(e);
		}
	}
	
	public void flush() throws AppException {
		try {
			flush(true);
		}
		catch (Exception e) {
			handleException(e);
		}
	}
	
	public void flush(boolean isFlush) throws AppException {
		try {
			if(isFlush){
				entityManager.flush();
			}
		}
		catch (Exception e) {
			handleException(e);
		}
	}
	
	public void clear() throws AppException {
		try {
			entityManager.clear();
		}
		catch (Exception e) {
			handleException(e);
		}
	}

	/**
	 * 创建Query对象. 对于需要first,max,fetchsize,cache,cacheRegion等诸多设置的函数,可以在返回Query后自行设置. 留意可以连续设置,如下：
	 * 
	 * <pre>
	 * dao.getQuery(hql).setMaxResult(100).setCacheable(true).list();
	 * </pre>
	 * 
	 * 调用方式如下：
	 * 
	 * <pre>
	 *          dao.createQuery(hql)
	 *          dao.createQuery(hql,arg0);
	 *          dao.createQuery(hql,arg0,arg1);
	 *          dao.createQuery(hql,new Object[arg0,arg1,arg2])
	 * </pre>
	 * 
	 * @param values 可变参数.
	 */
	protected Query createQuery(String hql, Object... values) throws AppException {
		Query query = null;
		try {
			Assert.hasText(hql);
			query = entityManager.createQuery(hql);
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					query.setParameter(i+1, values[i]);
				}
			}
		}
		catch (Exception e) {
			handleException(e);
		}
		return query;
	}

	protected Query createNativeQuery(String hql, Object... values) throws AppException {
		return this.createNativeQuery(hql, null, values);
	}

	protected Query createNativeQuery(String hql, Class classz, Object... values) throws AppException {
		Query query = null;
		try {
			Assert.hasText(hql);
			if (classz == null) {
				query = entityManager.createQuery(hql);
			}
			else {
				query = entityManager.createNativeQuery(hql, classz);
			}
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					query.setParameter(i+1, values[i]);
				}
			}
		}
		catch (Exception e) {
			handleException(e);
		}
		return query;
	}

	/**
	 * @deprecated 根据hql查询,直接使用HibernateTemplate的find函数.
	 * 
	 * @param values 可变参数,见{@link #createQuery(String,Object...)}
	 */
	protected List find(String hql, Object... values) throws AppException {
		List list = null;
		try {
			Assert.hasText(hql);
			// list = getHibernateTemplate().find(hql, values);
			Query query = createQuery(hql, values);
			query.setMaxResults(DEFAULT_FETCH_SIZE);
			list = query.getResultList();
		}
		catch (Exception e) {
			handleException(e);
		}
		return list;
	}

	/**
	 * 用native sql来检索
	 * 
	 * @deprecated
	 * @param hql
	 * @param values
	 * @return
	 * @throws AppException
	 */
	protected List findByNativSql(String hql, Class classz, Object... values) throws AppException {
		List list = null;
		try {
			Assert.hasText(hql);
			Query query = createNativeQuery(hql, classz, values);
			query.setMaxResults(DEFAULT_FETCH_SIZE);
			list = query.getResultList();
		}
		catch (Exception e) {
			handleException(e);
		}
		return list;
	}


	private int checkPageSize(int pageSize) {
		pageSize = pageSize > DEFAULT_FETCH_SIZE ? DEFAULT_FETCH_SIZE : pageSize;
		return pageSize;
	}

	private String getCountSql(String hql) {
		if (hql.indexOf("UNION") > 0 || hql.indexOf("union") > 0) {
			return "select count(*) from (" + hql + ")";
		}
		String countQueryString = " select count (*) " + removeSelect(removeOrders(hql));
		return countQueryString;
	}


//	/**
//	 * 判断对象某些属性的值在数据库中是否唯一.
//	 * 
//	 * @param uniquePropertyNames 在POJO里不能重复的属性列表,以逗号分割 如"name,loginid,password"
//	 */
//	protected <T> boolean isUnique(Class<T> entityClass, Object entity, String uniquePropertyNames) throws AppException {
//		boolean rtnResult = false;
//		try {
//			Assert.hasText(uniquePropertyNames);
//			Criteria criteria = entityManager.getCriteriaBuilder().createQuery(entityClass).setProjection(Projections.rowCount());
//			String[] nameList = uniquePropertyNames.split(",");
//			try {
//				// 循环加入唯一列
//				for (String name : nameList) {
//					criteria.add(Restrictions.eq(name, PropertyUtils.getProperty(entity, name)));
//				}
//				// 以下代码为了如果是update的情况,排除entity自身.
//				String idName = getIdName(entityClass);
//				// 取得entity的主键值
//				Serializable id = getId(entityClass, entity);
//				// 如果id!=null,说明对象已存在,该操作为update,加入排除自身的判断
//				if (id != null) {
//					criteria.add(Restrictions.not(Restrictions.eq(idName, id)));
//				}
//			}
//			catch (Exception e) {
//				ReflectionUtils.handleReflectionException(e);
//			}
//			rtnResult = (Integer)criteria.uniqueResult() == 0;
//		}
//		catch (Exception e) {
//			handleException(e);
//		}
//		return rtnResult;
//	}

	/**
	 * 取得对象的主键值,辅助函数.
	 */
	protected Serializable getId(Class entityClass, Object entity) throws AppException {
		Serializable id = null;
		try {
			Assert.notNull(entity);
			Assert.notNull(entityClass);
			id = (Serializable)PropertyUtils.getProperty(entity, getIdName(entityClass));
		}
		catch (Exception e) {
			handleException(e);
		}
		return id;
	}

	/**
	 * 取得对象的主键名,辅助函数.
	 */
	protected String getIdName(Class clazz) throws AppException {
		String idName = null;
		try {
			Assert.notNull(clazz);
//			ClassMetadata meta = entityManager.getMetamodel().getEntities()(clazz);
		    Session session = entityManager.unwrap(Session.class);
		    ClassMetadata meta = session.getSessionFactory().getClassMetadata(clazz);

			Assert.notNull(meta, "Class " + clazz + " not define in hibernate session factory.");
			idName = meta.getIdentifierPropertyName();
			Assert.hasText(idName, clazz.getSimpleName() + " has no identifier property define.");
		}
		catch (Exception e) {
			handleException(e);
		}
		return idName;
	}

	protected void handleException(Exception e) throws AppException {
		log.error("error:", e);
		if (e instanceof AppException) {
			throw (AppException)e;
		}
		else {
			throw new AppException(SYSTEM_ERROR, e);
		}
	}

	/**
	 * 去除hql的select 子句，未考虑union的情况,用于pagedQuery.
	 * 
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private static String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql + " must has a keyword 'from'");
		return hql.substring(beginPos);
	}

	/**
	 * 去除hql的orderby 子句，用于pagedQuery.
	 * 
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private static String removeOrders(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	/**
	 * compose sql
	 * 
	 * @param originalWhere
	 * @param newPart
	 * @return
	 */
	protected static String appendSqlWhere(String originalWhere, String newPart) {
		return appendSqlWhere(originalWhere, newPart, "and");
	}

	/**
	 * 拼sql
	 * 
	 * @param originalWhere
	 * @param newPart
	 * @param conditionRelationShip
	 * @return
	 */
	protected static String appendSqlWhere(String originalWhere, String newPart, String conditionRelationShip) {
		log.debug("originalWhere:" + originalWhere + ";newPart:" + newPart + ";conditionRelationShip"
					+ conditionRelationShip);
		String rtnStr = originalWhere;
		if (StringUtils.isBlank(newPart)) {
			log.error("newPart is blank, please check it.");
			return rtnStr;
		}
		if (StringUtils.isBlank(rtnStr)) {
			return newPart;
		}
		rtnStr = rtnStr + " " + conditionRelationShip + " " + newPart;
		return rtnStr;
	}

	/**
	 * Checks if a String is whitespace, empty ("") or null. shortcut
	 * 
	 * @param value
	 */
	protected static boolean isBlank(String str) {
		return StringUtils.isBlank(str);
	}

	/**
	 * Checks if a String is not empty (""), not null and not whitespace only. Just shortcut
	 * 
	 * @param str
	 * @return
	 */
	protected static boolean isNotBlank(String str) {
		return StringUtils.isNotBlank(str);
	}

	/**
	 * Checks if a String is empty ("") or null. shortcut
	 * 
	 * @param value
	 */
	protected static boolean isEmpty(String str) {
		return StringUtils.isEmpty(str);
	}

	/**
	 * Checks if a String is not empty ("") and not null. Just shortcut
	 * 
	 * @param str
	 * @return
	 */
	protected static boolean isNotEmpty(String str) {
		return StringUtils.isNotEmpty(str);
	}

	public List executeQuery(QueryReq req) throws AppException {
		try {
			return _executeQuery(req, null);
		}
		catch (Exception e) {
			handleException(e);
		}
		return null;
	}

	protected int executeUpdate(String stmt, Object[] params) throws AppException {
		log.debug("executeUpdate begin: stmt:" +stmt);
		try {
			Query query = createQuery(stmt,params);
			int num = query.executeUpdate();
			log.debug("executeUpdate " + num + " records");
			return num;
		}
		catch (HibernateException e) {
			handleException(e);
		}
		return -1;
	}
	public int executeNativeUpdate(String stmt, Object[] params) throws AppException {
		log.debug("executeUpdate begin: stmt:" +stmt);
		try {
			Query query = createNativeQuery(stmt, params);
			int num = query.executeUpdate();
			log.debug("executeUpdate " + num + " records");
			return num;
		}
		catch (HibernateException e) {
			handleException(e);
		}
		return -1;
	}
	public int executeCount(QueryReq req) throws AppException {
		log.debug("executeCount req=" + req);
		try {
			String whereClause = req.getWhere();
			String selectClause = req.getSelect();
			//if JPQL contains the special keyword, such as group by/distinct, and just use the specified way to handle them
			if(isNotBlank(whereClause)&&(PATTERN_GROUPBY.matcher(whereClause).find()) 
					||
					(isNotBlank(selectClause)&&(PATTERN_GROUPBY.matcher(selectClause).find()||PATTERN_DISTINCT.matcher(selectClause).find()))){
			    return getCountFromReqWithSpecialKeyword(req);
			}
			Number count = (Number)executeSingleResult(req);
			if (count == null) {
				return 0;
			}
			else {
				return count.intValue();
			}
		}
		catch (Exception e) {
			handleException(e);
		}
		return -1;
	}
	/**
	 * return the count of the queryReq that contains the block 'group by'
	 * @param req
	 * @return
	 * @throws AppException
	 */
	public int getCountFromReqWithSpecialKeyword(QueryReq req) throws AppException {
		log.debug("getCountFromReqWithGroupBy req=" + req);
		try {	
			List list = getCountQuery(req).getResultList();
			if(list!=null){
				return list.size();
			}
		}
		catch (Exception e) {
			handleException(e);
		}
		return 0;
	}
	public Object executeSingleResult(QueryReq req) throws AppException {
		log.debug("executeSingleResult req=" + req);
		try {
			return getCountQuery(req).getSingleResult();
		}
		catch (Exception e) {
			handleException(e);
		}
		return null;
	}
	private Query getCountQuery(QueryReq req) throws AppException {
		log.debug("getCountQuery req=" + req);
		try {
			int queryType = req.getType();
			Query query = null;
			String where = req.getWhere();
			String queryStr = null;
			switch (queryType) {
			case QueryReq.JPQL_QUERY:
				queryStr = req.getSelect();
				if (!StringUtils.isEmpty(where)) {
					queryStr += " WHERE " + where;
				}
				log.debug("queryStr=" + queryStr);
				query = createQuery(queryStr);
				break;
			case QueryReq.NATIVE_QUERY:
				queryStr = req.getSelect();
				if (!StringUtils.isEmpty(where)) {
					queryStr += " where " + where;
				}
				log.debug("queryStr=" + queryStr);
				query = createNativeQuery(queryStr);
				break;
			default:
				throw new Exception("Should not get here");
			}
			Object[] params = req.getParams();
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					query.setParameter(i+1, params[i]);
				}
			}
			return query;
		}
		catch (Exception e) {
			handleException(e);
		}
		return null;
	}
	
	public Page executeQuery(QueryReq req, PageReq pageReq) throws AppException {
		Page page = new Page();
		try {
			page.setPageNumber(pageReq.getPageNumber());
			page.setPageSize(pageReq.getPageSize());
			page.setSortCriterion(req.getSortCriterion());
			page.setSortDirection(req.getSortDirection());
			log.debug("pageReq.getCountRec(): " + pageReq.getCountRec());

			if (pageReq.getCountRec()) {
				QueryReq countReq = getCountQueryReq(req);
				if (log.isDebugEnabled()) {
					log.debug("countReq:" + countReq + ", pageReq:" + pageReq + ", page:" + page);
				}
				if (countReq != null) {
					int totalRecs = executeCount(countReq);
					page.setTotalRecs(totalRecs);
					int totalPages = 0;
					int pageSize = pageReq.getPageSize();
					log.debug("pageSize: " + pageSize);
					if (pageSize > 0) {
						totalPages = totalRecs / pageSize;
						if (totalRecs % pageSize > 0) {
							totalPages++;
						}
					}
					page.setTotalPages(totalPages);
					log.debug("page-new:"+page);
				}
				else {
					log.warn("Cannot get count query string!! Records are not counted!");
				}
			}

			List list = null;
			if (!pageReq.getCountRec() || !pageReq.getAutoLast() || page.getTotalPages() == 0
				|| (pageReq.getPageNumber() <= page.getTotalPages())) {
				list = _executeQuery(req, pageReq);
			}
			else {
				int lastPage = page.getTotalPages() ;
				log.info("There's no data in selected page. Jump to the last page!" + lastPage);
				pageReq.setPageNumber(lastPage);
				list = _executeQuery(req, pageReq);
				page.setPageNumber(lastPage);
			}
			page.setList(list);
		}
		catch (Exception e) {
			handleException(e);
		}
		return page;
	}

	private static QueryReq getCountQueryReq(QueryReq req) {
		QueryReq countReq = null;
		switch (req.getType()) {
		case QueryReq.NATIVE_QUERY:
			countReq = getNativeCountQueryReq(req);
			break;
		case QueryReq.JPQL_QUERY:
		default:
			countReq = getJpaCountQueryReq(req);
		}
		if (countReq != null) {
			countReq.setWhere(req.getWhere());
			countReq.setParams(req.getParams());
			countReq.setOrderBy(req.getOrderBy());
		}
		return countReq;
	}

	private static QueryReq getNativeCountQueryReq(QueryReq req) {
		String select = req.getSelect();
		int index = select.toUpperCase().indexOf("FROM");
		select = "SELECT COUNT(*) " + select.substring(index);
		QueryReq countReq = new QueryReq();
		countReq.setSelect(select);
		countReq.setType(QueryReq.NATIVE_QUERY);
		// countReq.setQueryName(req.getQueryName());
		return countReq;
	}

	private static QueryReq getJpaCountQueryReq(QueryReq req) {
		String selectCnt = "";
		//if selectClause contains DISTINCT, then not replace "select ..." with "select count(e)..."
		if(isNotBlank(req.getSelect())&&PATTERN_DISTINCT.matcher(req.getSelect()).find()){
		    selectCnt = req.getSelect();
		} else {
			selectCnt = PATTERN_SELECT.matcher(req.getSelect()).replaceFirst(SELEC_COUNT_FROM);
		}
		log.debug("selectCnt: " + selectCnt);
		QueryReq countReq = new QueryReq();
		countReq.setType(QueryReq.JPQL_QUERY);
		// countReq.setQueryName(req.getQueryName());
		countReq.setSelect(selectCnt);
		return countReq;
	}

	private List _executeQuery(QueryReq req, PageReq pageReq) throws Exception {
		log.debug("executeQuery req=" + req);
		List list = null;
		int queryType = req.getType();
		Query query = null;
		String where = req.getWhere();
		String orderBy = req.getOrderBy();
		String groupBy = req.getGroupBy();
		String queryStr = null;
		switch (queryType) {
		case QueryReq.JPQL_QUERY:
			queryStr = req.getSelect();
			if (!StringUtils.isEmpty(where)) {
				queryStr += " WHERE " + where;
			}
			if (!StringUtils.isEmpty(groupBy)) {
				queryStr += " GROUP BY " + groupBy;
			}
			if (!StringUtils.isEmpty(orderBy)) {
				queryStr += " ORDER BY " + orderBy;
			}

			log.debug("queryStr=" + queryStr);
			query = createQuery(queryStr);
			break;
		case QueryReq.NATIVE_QUERY:
			queryStr = req.getSelect();
			if (!StringUtils.isEmpty(where)) {
				queryStr += " WHERE " + where;
			}
			if (!StringUtils.isEmpty(groupBy)) {
				queryStr += " GROUP BY " + groupBy;
			}
			
			if (!StringUtils.isEmpty(orderBy)) {
				queryStr += " ORDER BY " + orderBy;
			}

			log.debug("queryStr=" + queryStr);
			Class cls = req.getCls();
			if (cls != null) {
				query = createNativeQuery(queryStr, cls);
			}
			else {
				query = createNativeQuery(queryStr);
			}
			break;
		default:
			throw new Exception("Should not get here");
		}
		Object[] params = req.getParams();
		String finalSql = queryStr;
		String paramV = "";
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i+1, params[i]);
				if(params[i] instanceof String){
					paramV = "'" + params[i] +"'";
				} else{
					paramV = params[i].toString();	
				}
				 
				finalSql = finalSql.replaceFirst("\\?", paramV);
			}
			log.debug("final sqllllllllllllllllll:" + finalSql);
		}
		if (pageReq != null) {
			int pageSize = pageReq.getPageSize();
			log.debug("pageSize: " + pageReq.getPageSize() + " ,pageNumber: " + pageReq.getPageNumber());
			if (pageReq.getPageSize() != 0) {
				int startIndex = (pageReq.getPageNumber() - 1) * pageSize;
				query.setFirstResult(startIndex);
				query.setMaxResults(pageSize);
			}
		}

		list = query.getResultList();

		return list;
	}
	
	public long getNextSeqValue(String seqName) throws AppException {
		log.debug("getNextSeqValue seqName=" + seqName);
		long rtnValue = 0;
		try {
			String hql ="SELECT (NEXTVAL FOR "+seqName +") FROM SYSIBM.SYSDUMMY1";
			QueryReq req = new QueryReq(QueryReq.NATIVE_QUERY);
			req.setSelect(hql);
			Number seq = (Number)executeSingleResult(req);
			if (seq == null) {
				return 0;
			}
			else {
				rtnValue = seq.longValue();
			}
		}
		catch (Exception e) {
			handleException(e);
		}
		return rtnValue;
	}

	public void resetSequence(String seqName) throws AppException {
		log.debug("resetSequence seqName=" + seqName);
		try {
			String hql ="drop sequence "+seqName;
			Query query = this.createNativeQuery(hql);
			query.executeUpdate();
			createSequence(seqName, 1,1);
		}
		catch (Exception e) {
  			handleException(e);
		}
	}	
	public void createSequence(String seqName) throws AppException {
		log.debug("createSequence seqName=" + seqName);
		try {
			createSequence(seqName, 1,1);
		}
		catch (Exception e) {
			handleException(e);
		}
	}	
	public void createSequence(String seqName,int increment, int start) throws AppException {
		log.debug("createSequence seqName=" + seqName);
		try {
//			String hql ="create sequence "+seqName +" AS BIGINT INCREMENT BY " + increment + " START WITH " + start +" NO MAXVALUE NO CYCLE NO CACHE";
//			Query query = this.createNativeQuery(hql);
//			query.executeUpdate();
			createSequence(seqName,increment,start,-1,false,-1);
		}
		catch (Exception e) {
			handleException(e);
		}
	}		
	
	public void createSequenceByCycle(String seqName)throws AppException{
		log.debug("createSequence seqName=" + seqName);
		try {
//			String hql ="CREATE SEQUENCE "+seqName
//			+" as integer cache 20 maxvalue 99999 cycle order";
//			Query query = this.createNativeQuery(hql);
//			query.executeUpdate();
			createSequence(seqName,1,1,99999,true,20);
		}
		catch (Exception e) {
			handleException(e);
		}
	}
/**
 * 
 * @param seqName
 * @param increment
 * @param start
 * @param maxValue
 * @param cycle
 * @param cache
 * @throws AppException
 */
	public void createSequence(String seqName, int increment, int start, int maxValue, boolean cycle, int cache)
			throws AppException {
		log.debug("createSequenceByCycle seqName=" + seqName);
		try {
			if (increment <= 0) {
				increment = 1;
			}
			if (start <= 0) {
				start = 1;
			}
			String maxValueStr = (maxValue > 0) ? (" MAXVALUE " + maxValue + " ") : (" NO MAXVALUE ");
			String cacheStr = cache > 0 ? (" CACHE " + cache + " ") : " NO CACHE ";
			String cycleStr = cycle ? " CYCLE ORDER " : " NO CYCLE ";
			String hql = "CREATE SEQUENCE " + seqName + " AS BIGINT INCREMENT BY " + increment + " START WITH "
							+ start + " " + maxValueStr + cacheStr + cycleStr;
			Query query = this.createNativeQuery(hql);
			query.executeUpdate();
		}
		catch (Exception e) {
			handleException(e);
		}
	}
//	/**                                                                                     
//	 * callProcedure                                                              
//	 * **/                                                                                  
//	public void callProcedure(String procString,List<Object> params) throws Exception {     
//		ProcedureCall stmt = null;                                                      
//	    try {                                                                               
//	        stmt = this.currentSession().createStoredProcedureCall(procString);                  
//	        if (params != null){                                                            
//	            int idx = 1;                                                                
//	            for (Object obj : params) {                                                 
//	                if (obj != null) {                                                      
//	                    stmt.setObject(idx, obj);                                           
//	                } else {                                                                
//	                    stmt.setNull(idx, Types.NULL);                                      
//	                }                                                                       
//	                idx++;                                                                  
//	            }                                                                           
//	        }                                                                               
//	        stmt.execute();                                                                 
//	    } catch (Exception e) {                                                          
//	    	handleException(e);                                                                          
//	    }                                                                                   
//	}                                                                                       

}