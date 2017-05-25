package edu.mum.rideshare.controller.common;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.mum.core.dao.Page;
import edu.mum.core.exception.AppException;
import edu.mum.core.web.PageReq;
import edu.mum.core.web.QueryReq;
import edu.mum.rideshare.controller.LcfBaseController;
import edu.mum.rideshare.data.QueryFormData;
import edu.mum.rideshare.model.SysUser;
import edu.mum.rideshare.service.UserService;

@Controller
@RequestMapping(value="/user")
public class UserController extends LcfBaseController{

	@Autowired
	private UserService userService;

//	@ModelAttribute("queryFormData")
//	public QueryFormData initQueryFormData(QueryFormData queryFormData) {
//		return new QueryFormData();
//	}
	
	@RequestMapping(value="/preAddUser.do")
	public String preAdd(Model model){
		model.addAttribute("sysUser", new SysUser());
		return "common/addUser";

	}


	@RequestMapping(value="/addUser.do")
	public String addUser(Model model, @ModelAttribute @Valid SysUser sysUser,BindingResult errorResult){
		if(errorResult.hasErrors()){
			return "common/addUser";
		}
		//
//		String password=UUID.randomUUID().toString().substring(0, 6);
		String password="123";
		sysUser.setCreateTime(new Date());
//		sysUser.setUpdateTime(new Date());
		sysUser.setPassword(password);
		try {
			userService.save(sysUser);
			String msg="Succeeded to add a User";
			model.addAttribute("msg", msg);
			log.debug("Succeeded to add "+sysUser.getLoginName());
		} catch (AppException e) {
			if (e instanceof AppException) {
				super.handleException(e, errorResult);
			} else{//to use a pre-defined error code regarding current business to report current error for end user, ex: 99999
				super.handleException(new AppException(99999), errorResult);
			}
		}


		return listUser(model, new QueryFormData(), errorResult, null, new PageReq());
	}
	
	@RequestMapping(value="/preListUser.do")
	public String preListUser(Model model,@ModelAttribute QueryFormData queryFormData){
		model.addAttribute("queryFormData", queryFormData);
		return "common/listUser";
	}
	
	@RequestMapping(value="/listUser.do")
	public String listUser(Model modelMap,@ModelAttribute QueryFormData queryFormData,BindingResult errorResult, QueryReq queryReq, PageReq pageReq){
		try {
			Page page=userService.findUser(queryFormData, queryReq, pageReq);
			modelMap.addAttribute("pageData", page);
		} catch (AppException e) {
			if (e instanceof AppException) {
				super.handleException(e, errorResult);
			} else{//to use a pre-defined error code regarding current business to report current error for end user, ex: 99999
				super.handleException(new AppException(99999), errorResult);
			}
		}
		return "common/listUser";
	}


	@RequestMapping(value="/viewUser.do")
	public String view(Model model,long rid){
		try {
			//long realId=2;
			SysUser sysUser=userService.get(rid);
			model.addAttribute("sysUser", sysUser);
		} catch (AppException e) {
//			if (e instanceof AppException) {
//				super.handleException(e, errorResult);
//			} else{//to use a pre-defined error code regarding current business to report current error for end user, ex: 99999
//				super.handleException(new AppException(99999), errorResult);
//			}
		}


		return "common/viewUser";
	}


	@RequestMapping(value="/preEditUser.do")
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String preUpdate(Model model,long rid){
		try {
			//long realId=2;
			SysUser sysUser=userService.get(rid);
			model.addAttribute("sysUser", sysUser);
		} catch (AppException e) {
//			if (e instanceof AppException) {
//				super.handleException(e, errorResult);
//			} else{//to use a pre-defined error code regarding current business to report current error for end user, ex: 99999
//				super.handleException(new AppException(99999), errorResult);
//			}
		}


		return "common/editUser";
	}

//	@RequestMapping(value="/editUser.do")
//	public String update(Model model,@ModelAttribute @Valid SysUser sysUser,BindingResult errorResult){
//		if(errorResult.hasErrors()){
//			return "common/editUser";
//		}
//		String msg=null;
//		QueryFormData queryFormData =new QueryFormData();
//		try {
//			SysUser oriSysUser=userService.get(sysUser.getUserId());
//			oriSysUser.setLoginName(sysUser.getLoginName());
//			oriSysUser.setFirstName(sysUser.getFirstName());
//			oriSysUser.setLastName(sysUser.getLastName());
//			oriSysUser.setCellPhone(sysUser.getCellPhone());
//			oriSysUser.setPersonalEmail(sysUser.getPersonalEmail());
//			oriSysUser.setGender(sysUser.getGender());
//
//			oriSysUser.setStatus(sysUser.getStatus());
//			userService.update(oriSysUser);
//			msg="Succeeded to update the user";
//			model.addAttribute("msg", msg);
//			model.addAttribute(queryFormData);
//			log.debug("Succeeded to update the user:" + sysUser.getLoginName());
//		} catch (AppException e) {
//			if (e instanceof AppException) {
//				super.handleException(e, errorResult);
//			} else{//to use a pre-defined error code regarding current business to report current error for end user, ex: 99999
//				super.handleException(new AppException(99999), errorResult);
//			}
//		}
//
//		return listUser(model, queryFormData, errorResult, null, new PageReq());
//	}
}
