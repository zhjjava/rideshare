//package edu.mum.rideshare.controller.demo;
//
//import java.util.HashMap;
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
//public class DownloadExcelDemoWithTmplController extends LcfBaseController{
//
//	@Autowired 
//	private SysCodeService sysCodeService;
//	
//	@RequestMapping(value="/downloadExcelWithTmplDemo.do")
//	    public String  downloadExcelFile(Map<String, Object> modelMap, HttpServletRequest request,HttpServletResponse response){
//        try {
//		    String fileName="sysCodeListWithTmpl文件";	        
//			List<SysCode> dataList=sysCodeService.listSysCode(1000, "en_US", 1);
//	       // String columnHeaderNames[]={"sysCodeId","Category Name","数值"};//列名
//	        String propertyNames[]   =    {"sysCodeId","sysCodeCategory.categoryName","theValue"};
//	        
//	        Map otherCellDataMap = new HashMap();
//	        otherCellDataMap.put("6,2", "2015年05月01日");
//	        otherCellDataMap.put("8,2", "MZ");
//	        
//	        modelMap.put(AppConstants.EXCEL_ROW_DATA_LIST, dataList);
//	        modelMap.put(AppConstants.EXCEL_PROPERTY_NAMES, propertyNames);
//	        
//	        modelMap.put(AppConstants.EXCEL_OTHER_CELL_DATA_MAP, otherCellDataMap);
//	        modelMap.put(AppConstants.EXCEL_DATA_AREA_START_CELL_NO, "0,6");
//	        
////	        response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
//	        
//		} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//	    }
//        return "sysCodeListExcelWithTmplDemoView";
////	   return new ModelAndView("sysCodeListExcelWithTmplDemoView", modelMap);
//	}
//}
//
