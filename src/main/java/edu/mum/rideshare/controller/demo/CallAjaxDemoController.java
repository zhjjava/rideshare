//package edu.mum.rideshare.controller.demo;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import edu.mum.core.exception.AppException;
//import edu.mum.rideshare.controller.LcfBaseAjaxController;
//import edu.mum.rideshare.data.QueryFormData;
//import edu.mum.rideshare.model.SysCode;
//import edu.mum.rideshare.service.SysCodeService;
//
///**
// * ListSysCodeController
// * 
// * 
// * @author mz
// */
//@Controller
//@RequestMapping(value = "/ajax/callAjaxDemo")
//@SuppressWarnings({ "unchecked", "rawtypes" })
//public class CallAjaxDemoController extends LcfBaseAjaxController{
//
//	//will be invoked automatically when user invokes every method in this controller
//	//so it acts a initialization method
//	@ModelAttribute("queryFormData")  
//	public QueryFormData initQueryFormData() {  
//	    return new QueryFormData();  
//	}  
//
//	@Autowired 
//	private SysCodeService sysCodeService;
//
//	@RequestMapping(value="/preCallAjax.do")
//	public String preCallAjax( Map modelMap) {
//		return "demo/callAjaxDemo";
//	}
//
//	
//	@RequestMapping(value="/callAjax.do")
//    @ResponseBody
//	public Map callAjax(@RequestParam("data1") String data1,  @RequestParam("data2")String data2,  @RequestParam("data3") String[] data3,QueryFormData queryFormData,HttpServletRequest req) {
//		Map rtnDatalMap = new HashMap();
//		try {
////			Page page = sysCodeService.find(queryFormData, queryReq, pageReq);
////			modelMap.put("pageData", page);
//			
//			rtnDatalMap.put(IS_SUCCESS, OP_RESULT_TRUE);
//			rtnDatalMap.put(OP_RETURN_MSG, "show more details for front end user.");
//			rtnDatalMap.put("data1", data1+"ddd");
//			rtnDatalMap.put("data2", data2+"ddd");
//			rtnDatalMap.put("data3", data3);
//		} catch (Exception e) {
//			if (e instanceof AppException) {
//				super.handleException(e, rtnDatalMap,req);
//			} else{//to use a pre-defined error code regarding current business to report current error for end user, ex: 99999
//				super.handleException(new AppException(99999), rtnDatalMap,req);
//			}
//			rtnDatalMap.put(IS_SUCCESS, OP_RESULT_FALSE);
//		}
//
//		return rtnDatalMap;
//	}	
//}
