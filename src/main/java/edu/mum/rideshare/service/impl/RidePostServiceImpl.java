package edu.mum.rideshare.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mum.core.dao.Page;
import edu.mum.core.exception.AppException;
import edu.mum.core.web.PageReq;
import edu.mum.core.web.QueryReq;
import edu.mum.rideshare.dao.RidePostDao;
import edu.mum.rideshare.data.QueryFormData;
import edu.mum.rideshare.model.RidePost;
import edu.mum.rideshare.service.RidePostService;

@Service(value = "ridePostService")
public class RidePostServiceImpl implements RidePostService {

	@Autowired
	private RidePostDao ridePostDao;
	
	@Override
	public RidePost get(Serializable id) throws AppException {
		return ridePostDao.get(id);
	}

	@Override
	public Page findRidePosts(QueryFormData queryFormData, QueryReq queryReq, PageReq pageReq) throws AppException {
		return ridePostDao.findRidePosts(queryFormData, queryReq, pageReq);
	}
	
	public void update(Object obj) throws AppException{
		ridePostDao.update(obj);
	}
	public void save(Object obj) throws AppException{
		ridePostDao.save(obj);
	}

	public void remove(Object o) throws AppException{
		ridePostDao.remove(o);
	}

	public void removeById(Serializable id) throws AppException{
		ridePostDao.removeById(id);
	}
	
	
}
