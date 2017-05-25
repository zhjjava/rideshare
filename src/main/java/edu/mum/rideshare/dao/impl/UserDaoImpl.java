package edu.mum.rideshare.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import edu.mum.core.dao.HibernateEntityDao;
import edu.mum.core.dao.Page;
import edu.mum.core.exception.AppException;
import edu.mum.core.web.PageReq;
import edu.mum.core.web.QueryReq;
import edu.mum.rideshare.dao.UserDao;
import edu.mum.rideshare.data.QueryFormData;
import edu.mum.rideshare.data.QueryFormDataUser;
import edu.mum.rideshare.model.SysUser;

@Repository(value = "userDao")
public class UserDaoImpl extends HibernateEntityDao<SysUser> implements UserDao {


	public List<SysUser> findUsers(QueryFormDataUser qryFormData) throws AppException {
		QueryReq queryReq = new QueryReq();
		String select = "select e from SysUser e  ";
		String where = " ";
		List<Object> paramList = new ArrayList<Object>();
		if (qryFormData.getFpStatus() != null) {
			where = appendSqlWhere(where, " e.fpStatus = ? ");
			paramList.add(Integer.parseInt(qryFormData.getFpStatus()));
		}
		queryReq.setSelect(select);
		queryReq.setWhere(where);
		queryReq.setParams(paramList.toArray());
		List<SysUser> list = super.executeQuery(queryReq);
		return list;
	}
	
	public List<SysUser> findUsers() throws AppException{
		QueryReq queryReq = new QueryReq();
		String select = "select e from SysUser e";
		queryReq.setSelect(select);

		List<SysUser> list = null;
		list = super.executeQuery(queryReq);

		return list;
	}

	public SysUser getByQuery(QueryFormDataUser qryFormData) throws AppException {
		QueryReq queryReq = new QueryReq();
		String select = "select e from SysUser e  ";
		String where = " ";
		List<Object> paramList = new ArrayList<Object>();
		if (qryFormData.getLoginName() != null) {
			where = appendSqlWhere(where, " e.loginName = ? ");
			paramList.add(qryFormData.getLoginName());
		}
		queryReq.setSelect(select);
		queryReq.setWhere(where);
		queryReq.setParams(paramList.toArray());
		List<SysUser> list = super.executeQuery(queryReq);
		if (list != null && list.size() > 0)
			return (SysUser) list.get(0);
		else
			return null;
	}

	
	public SysUser getSysUserByEmail(String email) throws AppException {
		QueryReq queryReq = new QueryReq();
		String select = "select e from SysUser e ";
		String where = "";
		List<Object> paramList = new ArrayList<Object>();
		where = appendSqlWhere(where, " e.personalEmail = ? ");
		paramList.add(email);
		queryReq.setSelect(select);
		queryReq.setWhere(where);
		queryReq.setParams(paramList.toArray());
		List<SysUser> list = super.executeQuery(queryReq);
		if (list != null && list.size() > 0)
			return (SysUser) list.get(0);
		else
			return null;
	}
	
	@SuppressWarnings("unchecked")
	public SysUser getLoginUser(String loginName) throws AppException {
		QueryReq queryReq = new QueryReq();
		String select = "select e from SysUser e ";
		String where = "";
		List<Object> paramList = new ArrayList<Object>();
		where = appendSqlWhere(where, " e.loginName = ? ");
		paramList.add(loginName);
		queryReq.setSelect(select);
		queryReq.setWhere(where);
		queryReq.setParams(paramList.toArray());
		List<SysUser> list = super.executeQuery(queryReq);
		if (list != null && list.size() > 0)
			return (SysUser) list.get(0);
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	public SysUser isValidate(QueryFormDataUser qryFormData) throws AppException {

		QueryReq queryReq = new QueryReq();
		String select = "select e from SysUser e ";
		String where = "";
		List<Object> paramList = new ArrayList<Object>();
		// 用户名
		// if (isNotBlank(qryFormData.getLoginName())) {
		where = appendSqlWhere(where, " e.loginName = ? ");
		paramList.add(qryFormData.getLoginName());
		// }
		// if (isNotBlank(qryFormData.getPassword())) {
		where = appendSqlWhere(where, " e.password = ? ");
		paramList.add(qryFormData.getPassword());
		// }
		queryReq.setSelect(select);
		queryReq.setWhere(where);
		queryReq.setParams(paramList.toArray());
		
//		String loginUserName = qryFormData.getLoginName();
//		String query = "select e from SysUser e where e.loginName = " + loginUserName;
		
		List<SysUser> list = super.executeQuery(queryReq);

		if (list != null && list.size() > 0)
			return (SysUser) list.get(0);
		else
			return null;
	}

	@Override
	public Page findUser(QueryFormData queryFormData, QueryReq queryReq, PageReq pageReq) throws AppException {
		if (queryReq == null) {
			queryReq = new QueryReq();
		}
		String select = "select s from SysUser s ";
		String where = "";
		String orderBy = "";
		List<Object> paramList = new ArrayList<Object>();
		if (queryFormData != null && isNotBlank(queryFormData.getField1())) {
			where = appendSqlWhere(where, " upper(s.loginName) = ? ");
			paramList.add(StringUtils.upperCase(queryFormData.getField1()));
		}
		queryReq.setSelect(select);
		queryReq.setWhere(where);
		queryReq.setParams(paramList.toArray());
		if (StringUtils.isBlank(queryReq.getOrderBy())) {
			orderBy = "s.userId";
			queryReq.setOrderBy(orderBy);
		}
		Page page = super.executeQuery(queryReq, pageReq);
		return page;
	}

}
