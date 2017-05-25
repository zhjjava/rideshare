package edu.mum.rideshare.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;

import edu.mum.core.dao.HibernateGenericDao;
import edu.mum.core.dao.Page;
import edu.mum.core.exception.AppException;
import edu.mum.core.web.PageReq;
import edu.mum.core.web.QueryReq;
import edu.mum.rideshare.util.ErrorCodeConstants;

public interface IBaseManager extends ErrorCodeConstants {

	public abstract void save(Object obj) throws AppException;

	public abstract <T> T update(T obj) throws AppException;

	public abstract void remove(Object o) throws AppException;

	public void removeById(Serializable id) throws AppException;

	public long getNextSeqValue(String seqName) throws AppException;

	/**
	 * 
	 * 
	 * @see edu.mum.core.dao.Page
	 * @see edu.mum.core.web.QueryReq
	 * @see edu.mum.core.web.PageReq
	 * @param req
	 * @param pageReq
	 * @return
	 * @throws AppException
	 */
	public Page executeQuery(QueryReq req, PageReq pageReq) throws AppException;

	/**
	 * list
	 * 
	 * @see edu.mum.core.web.QueryReq
	 * @param req
	 * @return
	 * @throws AppException
	 */
	public List executeQuery(QueryReq req) throws AppException;

	public int executeCount(QueryReq req) throws AppException;

	public Object executeSingleResult(QueryReq req) throws AppException;

	/**
	 * sequence
	 * 
	 * @param seqName
	 * @throws AppException
	 */
	public void createSequence(String seqName) throws AppException;

	public void createSequence(String seqName, int increment, int start, int maxValue, boolean cycle, int cache)
			throws AppException;
	public <T> T get(Class<T> entityClass, Serializable id) throws AppException;	
}
