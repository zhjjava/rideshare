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
import edu.mum.rideshare.dao.RidePostDao;
import edu.mum.rideshare.data.QueryFormData;
import edu.mum.rideshare.model.RidePost;

@Repository(value = "ridePostDao")
public class RidePostDaoImpl extends HibernateEntityDao< RidePost> implements RidePostDao {

	public final static int NORMAL_STATUS_0=0;

	@Override
	public Page findRidePosts(QueryFormData queryFormData, QueryReq queryReq, PageReq pageReq) throws AppException {
		if (queryReq == null) {
			queryReq = new QueryReq();
		}
		String select = "select s from RidePost s ";
		String where = "";
		String orderBy = "";
		List<Object> paramList = new ArrayList<Object>();
		
		int offerOrAsk=0;
		if (queryFormData != null && isNotBlank(queryFormData.getField1())) {
			offerOrAsk = Integer.parseInt(queryFormData.getField1());
		}	
		where = appendSqlWhere(where, " s.offerOrAsk = ? ");
		paramList.add(offerOrAsk);
		
		where = appendSqlWhere(where, " s.status = ?");
		paramList.add(NORMAL_STATUS_0);
		
		queryReq.setSelect(select);
		queryReq.setWhere(where);
		queryReq.setParams(paramList.toArray());
		if (StringUtils.isBlank(queryReq.getOrderBy())) {
			orderBy = "s.createTime DESC";
			queryReq.setOrderBy(orderBy);
		}
		Page page = super.executeQuery(queryReq, pageReq);
		return page;
	}

}
