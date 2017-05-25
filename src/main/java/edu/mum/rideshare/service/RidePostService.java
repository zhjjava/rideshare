package edu.mum.rideshare.service;

import java.io.Serializable;

import edu.mum.core.dao.Page;
import edu.mum.core.exception.AppException;
import edu.mum.core.web.PageReq;
import edu.mum.core.web.QueryReq;
import edu.mum.rideshare.data.QueryFormData;
import edu.mum.rideshare.model.RidePost;

public interface RidePostService extends IBaseServiceManager {
	
	public Page findRidePosts(QueryFormData queryFormData, QueryReq queryReq, PageReq pageReq) throws AppException;

	public RidePost get(Serializable id) throws AppException;
	
}
