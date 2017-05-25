package edu.mum.rideshare.controller.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.mum.rideshare.controller.LcfBaseController;
import edu.mum.rideshare.model.SysUser;
import edu.mum.rideshare.service.UserService;

/**
 * 
 * LoginController is for managing login and logout.
 *
 * @author Lin
 */
@Controller
@RequestMapping(value = "/resetpassword")
public class ResetPasswordController extends LcfBaseController {

	@Autowired
	private UserService userService;


	// will be invoked automatically when user invokes every method in this
	// controller
	// so it acts a initialization method

	@ModelAttribute("sysUser")
	public SysUser init() {
		return new SysUser();
	}

	@RequestMapping(value = "/forgetPassword.do", method = RequestMethod.GET)
	public String preResetPassword() {
		return "preResetPassword";
	}

//	@RequestMapping(value = "/sendRequestOfResetPassword.do", method = RequestMethod.POST)
//	public String sendRequestOfResetPassword(@ModelAttribute SysUser sysUser, BindingResult bindingResult) {
//
//		String receiptEmail = sysUser.getPersonalEmail();
//		
//		try {
//			SysUser dbUser = userService.getSysUserByEmail(receiptEmail);
//			if(dbUser==null){
//				super.addActionError("Please Check Your Email", bindingResult);
//				return "preResetPassword";
//			}
//			MailInfoObject message = new MailInfoObject("Reset Password", dbUser, receiptEmail,
//					"admin@flipperband.com");
//			emailGateway.process(message);
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("Bad message");
//		}
//
//		return "redirect:/login.do";
//	}
//
//	@RequestMapping("/{id}.do")
//	public String resetPasswordPage(Model model, @PathVariable("id") Long id) {
//		try {
//			SysUser sysUser = userService.get(id);
//
//			sysUser.setPassword("");
//			model.addAttribute("sysUser", sysUser);
//		} catch (AppException e) {
//			e.printStackTrace();
//		}
//
//		return "showResetPasswordPage";
//	}
//
//	@RequestMapping(value = "/resetPassword.do")
//	public String resetPassword(@ModelAttribute SysUser sysUser, String password, String verifyPassword,
//			BindingResult bindingResult) {
//		//LcfValidationUtils.validateStringMinAndMaxLength(bindingResult, password, 3, 8);
//	//	LcfValidationUtils.validateStringMinAndMaxLength(bindingResult, verifyPassword, 3, 8);
////				
//		if(password.length() == 0){
//			super.addActionError("password should not be empty", bindingResult);
//		}
//		
//		if(password!=null&& !password.equals(verifyPassword)){
//			super.addActionError("password should equal verifyPassword", bindingResult);
//		}
//		
//		if (bindingResult.hasErrors()) {
//			return "showResetPasswordPage";
//		}
//
//		try {
//
//			userService.updatePassword(sysUser, password);
//		} catch (AppException e) {
//			e.printStackTrace();
//			return "showResetPasswordPage";
//		}
//
//		return "redirect:/login.do";
//	}
}
