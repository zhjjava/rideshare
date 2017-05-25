package edu.mum.rideshare.dao;

import java.io.Serializable;
import java.util.List;

import edu.mum.core.dao.Page;
import edu.mum.core.exception.AppException;
import edu.mum.core.web.PageReq;
import edu.mum.core.web.QueryReq;
import edu.mum.rideshare.data.QueryFormData;
import edu.mum.rideshare.data.QueryFormDataUser;
import edu.mum.rideshare.model.SysUser;

public interface UserDao extends IBaseManager{
	public Page findUser(QueryFormData queryFormData, QueryReq queryReq, PageReq pageReq) throws AppException;
	public SysUser get(Serializable id) throws AppException;

	public SysUser getLoginUser(String loginName)	throws AppException ;
	public SysUser getByQuery(QueryFormDataUser qryFormData) throws AppException;
	public List<SysUser> findUsers(QueryFormDataUser qryFormData) throws AppException;
	public SysUser getSysUserByEmail(String email) throws AppException;		
	public List<SysUser> findUsers() throws AppException;
}
