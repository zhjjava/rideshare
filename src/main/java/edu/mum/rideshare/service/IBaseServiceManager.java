package edu.mum.rideshare.service;

import java.io.Serializable;
import java.util.List;

import edu.mum.core.dao.HibernateGenericDao;
import edu.mum.core.dao.Page;
import edu.mum.core.exception.AppException;
import edu.mum.core.web.PageReq;
import edu.mum.core.web.QueryReq;
import edu.mum.rideshare.util.ErrorCodeConstants;

public interface IBaseServiceManager {
	public void update(Object obj) throws AppException;
	public void save(Object obj) throws AppException;

	public void remove(Object o) throws AppException;

	public void removeById(Serializable id) throws AppException;
}
