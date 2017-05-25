//package edu.mum.rideshare.controller.common;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import edu.mum.core.exception.AppException;
//import edu.mum.rideshare.controller.LcfBaseController;
//import edu.mum.rideshare.model.SysUser;
//import edu.mum.rideshare.service.CredentialsService;
//import edu.mum.rideshare.service.UserService;
//
//@Controller
//public class SignUpController extends LcfBaseController {
//	@Autowired
//	private CredentialsService credentialsService;
//	
//	@Autowired
//	private UserService userService;
//	
//	@ModelAttribute("sysUser")
//	public SysUser init() {
//		return new SysUser();
//	}
//	
//	@RequestMapping(value = "/signup.do", method = RequestMethod.GET)
//	public String signUpForm(){
//		return "signup";
//	}
//	
//	@RequestMapping(value = "/signup.do", method = RequestMethod.POST)
//	public String signUp(Model model,@RequestParam String verifyPassword, @ModelAttribute @Valid SysUser sysUser, BindingResult bindingResult){
//		try {
//			if(!(sysUser.getPassword()).equals(verifyPassword)){
//				super.addFieldError("verifyPassword", "Password you input does not match each other.", bindingResult);
//			}
//			SysUser user = userService.getLoginUser(sysUser.getLoginName());
//			if(user!=null){
//				super.addFieldError("loginName", "login name exists.", bindingResult);
//			}
//			
//			if(bindingResult.hasErrors()){
//				System.out.println("There is someting wrong in the input.");
//				return "signup";
//			}
//			
//			credentialsService.save(sysUser);
//		} catch (AppException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return "signup";
//		}
//		
//		return "login";
//	}
//}
