package edu.mum.rideshare.controller.common;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.mum.core.exception.AppException;
import edu.mum.rideshare.controller.LcfBaseController;
import edu.mum.rideshare.model.SysUser;
import edu.mum.rideshare.service.UserService;
import edu.mum.rideshare.util.AppConstants;


/**
 * 
 * LoginController is for managing login and logout.
 *
 * @author mz
 */
@Controller
@SessionAttributes(edu.mum.rideshare.util.AppConstants.SYS_USER_KEY_IN_SESSION)
@RequestMapping(value = "/login.do")
public class LoginController extends LcfBaseController{

	@Autowired
	private UserService userService;
	
//	@Autowired
//	private CredentialsService credentialsService;

	@ModelAttribute("sysUser")
	public SysUser init() {
		return new SysUser();
	}

//	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
//	public String loginPage() {
//		return "login";
//	}

//	@RequestMapping(value="/logout.do", method = RequestMethod.GET)
//	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		if (auth != null){    
//			new SecurityContextLogoutHandler().logout(request, response, auth);
//			request.getSession().invalidate();
//		}
//		return "redirect:/login.do?logout";
//	}

	
	@RequestMapping(method = RequestMethod.GET)
	public String login( Map<String, Object> modelMap, HttpSession session) {
		if(super.getSessionUser(session)!=null){
			return "index";
		}
		return "login";
	}

	//This part can be done in the table.
	private void validateLogin(String loginName,String password,BindingResult errorResult){
		//field error
		if(StringUtils.isBlank(loginName) || loginName.length()<2){
			super.addFieldError("loginName", "Expected valid loginName.", errorResult);
		}

		if(loginName.length()>8){
			super.addFieldError("loginName", "Max length for login in name is 8.", errorResult);
		}

		if(StringUtils.isBlank(password)){
			super.addFieldError("password", "Expected valid password.", errorResult);
			//			errorResult.rejectValue("password",null,"Expected valid password.");
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public String login(Model model, @RequestParam("loginName") String loginName, String password, @ModelAttribute SysUser user, BindingResult errorResult) {
		try {
			validateLogin(loginName, password, errorResult);

			if (errorResult.hasErrors()){
				return "login";
			}
			

			SysUser currentUser = userService.getLoginUser(loginName);
			if(currentUser==null){				
				log.debug("@@initLogin user is null!!!!!!!!!!");
				super.addFieldError("loginName", "Expected valid loginName.", errorResult);
				return "login";
			}			
//			if(currentUser.getStatus()==AppConstants.STATUS_INVALID_0){
//				super.addActionError("User status is invalid.", errorResult);
//				return "login";
//			}
			if(!currentUser.getPassword().equals(password)){
				super.addActionError("The loginName or password is invalid.", errorResult);
				return "login";
			}
			
			model.addAttribute(AppConstants.SYS_USER_KEY_IN_SESSION, currentUser);
		
			return "index";
		} catch (AppException e) {
			if (e instanceof AppException) {
				super.handleException(e, errorResult);
			} else{
				super.handleException(new AppException(ERROR_SYSTEM_ERROR), errorResult);
			}
			return "login";
		}
	}
}
