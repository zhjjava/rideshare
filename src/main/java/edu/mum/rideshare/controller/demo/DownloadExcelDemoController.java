//package edu.mum.rideshare.controller.demo;
//
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//import edu.mum.rideshare.controller.LcfBaseController;
//import edu.mum.rideshare.model.SysCode;
//import edu.mum.rideshare.service.SysCodeService;
//import edu.mum.rideshare.util.AppConstants;
//
///**
// * ListSysCodeController
// * 
// * 
// * @author mz
// */
//@Controller
//@RequestMapping(value = "/demo")
//@SuppressWarnings({ "unchecked", "rawtypes" })
//public class DownloadExcelDemoController extends LcfBaseController{
//
//	@Autowired 
//	private SysCodeService sysCodeService;
//	
//	@RequestMapping(value="/downloadSysCodeListExcel.do")
//	    public ModelAndView downloadExcelFile(Map<String, Object> model, HttpServletRequest request,HttpServletResponse response){
//        try {
//		    String fileName="sysCodeList File";	        
//			List<SysCode> dataList=sysCodeService.listSysCode(1000, "en_US", 1);
//	        String columnHeaderNames[]={"sysCodeId","Category Name","Sum"};//column name
//	        String propertyNames[]   =    {"sysCodeId","sysCodeCategory.categoryName","theValue"};
//
//	        model.put(AppConstants.EXCEL_ROW_DATA_LIST, dataList);
//	        model.put(AppConstants.EXCEL_PROPERTY_NAMES, propertyNames);
//	        model.put(AppConstants.EXCEL_COLUMN_HEADER_NAMES, columnHeaderNames);
//	        response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
//	        
//		} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//	    }
//	   return new ModelAndView("sysCodeListExcelView", model);
//	}
//}
//
