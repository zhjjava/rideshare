package edu.mum.rideshare.controller.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.mum.core.dao.Page;
import edu.mum.core.exception.AppException;
import edu.mum.core.web.PageReq;
import edu.mum.core.web.QueryReq;
import edu.mum.rideshare.controller.LcfBaseAjaxController;
import edu.mum.rideshare.data.QueryFormData;
import edu.mum.rideshare.model.RidePost;
import edu.mum.rideshare.model.SysUser;
import edu.mum.rideshare.service.RidePostService;

@Controller
@RequestMapping(value="/ridepost")
public class RidePostController extends LcfBaseAjaxController{

	@Autowired
	private RidePostService ridePostService;

//	@ModelAttribute("queryFormData")
//	public QueryFormData initQueryFormData(QueryFormData queryFormData) {
//		return new QueryFormData();
//	}
	

	@RequestMapping(value = "/addRidePost.do", method = RequestMethod.POST)
	@ResponseBody
	public Map addRidePost(Model model, HttpSession session, RidePost ridePost,HttpServletRequest req) {
		Map rtnDatalMap = new HashMap();
		try {
			SysUser user = super.getSessionUser(session);
			ridePost.setCreateTime(new Date());
			ridePost.setSysUser(user);
			
			ridePostService.save(ridePost);
			String msg = "Submitting a ride share post successfully.";

			rtnDatalMap.put(IS_SUCCESS, OP_RESULT_TRUE);
			rtnDatalMap.put(OP_RETURN_MSG, msg);
			//if succeeded, need to retrieve the latest data again.
			QueryFormData form = new QueryFormData();
			form.setField1(ridePost.getOfferOrAsk()+"");
			return listRidePosts(model, form, null, new PageReq(), req);
		} catch (AppException e) {
			if (e instanceof AppException) {
				super.handleException(e, rtnDatalMap, req);
			} else {// to use a pre-defined error code regarding current
					// business to report current error for end user, ex: 99999
				super.handleException(new AppException(99999), rtnDatalMap, req);
			}
			rtnDatalMap.put(IS_SUCCESS, OP_RESULT_FALSE);
			return rtnDatalMap;
		}		
	}
	
	@RequestMapping(value="/listRidePost.do")
	@ResponseBody
	public Map listRidePosts(Model modelMap, QueryFormData queryFormData, QueryReq queryReq, PageReq pageReq,HttpServletRequest req){
		Map rtnDatalMap = new HashMap();
		try {
			Page page=ridePostService.findRidePosts(queryFormData, queryReq, pageReq);
			rtnDatalMap.put("pageData", page);
			rtnDatalMap.put(IS_SUCCESS, OP_RESULT_TRUE);
			rtnDatalMap.put(OP_RETURN_MSG, "Successfully.");
		} catch (AppException e) {
			if (e instanceof AppException) {
				super.handleException(e, rtnDatalMap, req);
			} else {// to use a pre-defined error code regarding current
					// business to report current error for end user, ex: 99999
				super.handleException(new AppException(99999), rtnDatalMap, req);
			}
			rtnDatalMap.put(IS_SUCCESS, OP_RESULT_FALSE);
			
		}
		return rtnDatalMap;
	}
}
