package edu.mum.core.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mum.core.exception.AppException;
import edu.mum.core.util.GenericsUtils;

/**
 * CRUD
 * 
 * <pre>
 * public class UserManager extends HibernateEntityDao&lt;User&gt; {
 * }
 * </pre>
 * 
 * @author mz
 * @see HibernateGenericDao
 */
@SuppressWarnings("unchecked")
public class HibernateEntityDao<T> extends HibernateGenericDao {

	protected Class<T> entityClass;// DAO所管理的Entity类型.
//	protected Log log = LogFactory.getLog(getClass());
	protected Logger log = LoggerFactory.getLogger(getClass());

	public HibernateEntityDao() {
		entityClass = GenericsUtils.getSuperClassGenricType(getClass());
	}

	/**
	 * 取得entityClass.JDK1.4不支持泛型的子类可以抛开Class<T> entityClass,重载此函数达到相同效果。
	 */
	protected Class<T> getEntityClass() {
		return entityClass;
	}

	/**
	 * 根据ID获取对象.
	 * 
	 * @see HibernateGenericDao#getId(Class,Object)
	 */
	public T get(Serializable id) throws AppException {
		return get(getEntityClass(), id);
	}
	
	/**
	 * 获取全部对象
	 * 
	 * @throws AppException
	 * 
	 * @see HibernateGenericDao#getAll(Class)
	 */
	public List<T> getAll() throws AppException {
		return getAll(getEntityClass());
	}

//	/**
//	 * 获取全部对象,带排序参数.
//	 * 
//	 * @throws AppException
//	 * 
//	 * @see HibernateGenericDao#getAll(Class,String,boolean)
//	 */
//	public List<T> getAll(String orderBy, boolean isAsc) throws AppException {
//		return getAll(getEntityClass(), orderBy, isAsc);
//	}

	/**
	 * 根据ID移除对象.
	 * 
	 * @throws AppException
	 * 
	 * @see HibernateGenericDao#removeById(Class,Serializable)
	 */
	public void removeById(Serializable id) throws AppException {
		removeById(getEntityClass(), id);
	}
	
	public <T> T merge(T entity) throws AppException {
		T t = null;	
		try {
			t = (T)entityManager.merge(entity);
			flush();
			log.debug("merge successful");
			// return t;
		}
		catch (Exception e) {
			log.error("merge failed", e);
			handleException(e);
		}
		return t;
	}

	public <T> T update(T obj) throws AppException {
		try {
			return entityManager.merge(obj);
		}
		catch (Exception e) {
			handleException(e);
		}
		return null;
	}
	
	/**
	 * 根据ID删除对象.
	 */
	public <T> void removeById(Class<T> entityClass, Serializable id) throws AppException {
		try {
			super.remove(this.get(entityClass, id));
		}
		catch (Exception e) {
			handleException(e);
		}
	}

//	/**
//	 * 判断对象某些属性的值在数据库中唯一.
//	 * 
//	 * @param uniquePropertyNames
//	 *            在POJO里不能重复的属性列表,以逗号分割 如"name,loginid,password"
//	 * @throws AppException
//	 * @see HibernateGenericDao#isUnique(Class,Object,String)
//	 */
//	public boolean isUnique(Object entity, String uniquePropertyNames)
//			throws AppException {
//		return isUnique(getEntityClass(), entity, uniquePropertyNames);
//	}
}
