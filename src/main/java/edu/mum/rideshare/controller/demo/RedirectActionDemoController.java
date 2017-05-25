package edu.mum.rideshare.controller.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.mum.rideshare.controller.LcfBaseController;

/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，
 * 
 * 
 * @author mz
 */
@Controller
@RequestMapping(value = "/demo")
public class RedirectActionDemoController extends LcfBaseController{

	
	
	@RequestMapping(value = "/redirectAction.do")
	public String redirectToSomeAction(Model model, RedirectAttributes redirectAttributes) {
			redirectAttributes.addFlashAttribute("someMsg", "!!!!测试重定向消息的传递");
		    return "redirect:/config/listSysCode/querySysCode.do";		
	}

}
