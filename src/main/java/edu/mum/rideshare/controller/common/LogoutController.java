package edu.mum.rideshare.controller.common;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import edu.mum.rideshare.controller.LcfBaseController;

@Controller
public class LogoutController extends LcfBaseController {
	
	@RequestMapping("/logout.do")
	public String logout(HttpSession session, SessionStatus status){
		session.invalidate();
		status.setComplete();
		
		return "redirect:/login.do";
	}
}
