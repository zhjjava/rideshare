package edu.mum.rideshare.controller.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.mum.core.exception.AppException;
import edu.mum.core.util.KeyValueBean;
import edu.mum.rideshare.controller.LcfBaseController;
import edu.mum.rideshare.data.DemoModelData;
import edu.mum.rideshare.validator.DemoModelDataValidator;

/**
 * ListSysCodeController
 * 
 * 
 * @author mz
 */
@Controller
@RequestMapping(value = "/demo/validation")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ValidationErrorDemoController extends LcfBaseController{

	//will be invoked automatically when user invokes every method in this controller
	//so it acts a initialization method
	@ModelAttribute("demoModelData")  
	public DemoModelData initModel() {  
		DemoModelData demoModelData = new DemoModelData();
		List<KeyValueBean> radioBoxEntries = new ArrayList<KeyValueBean>();
		radioBoxEntries.add(new KeyValueBean("Enabled","1"));
		radioBoxEntries.add(new KeyValueBean("Disabled","0"));
		demoModelData.setRadioBoxEntries(radioBoxEntries);

		List<KeyValueBean> selectBoxEntries = new ArrayList<KeyValueBean>();
		selectBoxEntries.add(new KeyValueBean("China","CN"));
		selectBoxEntries.add(new KeyValueBean("USA","US"));
		demoModelData.setSelectBoxEntries(selectBoxEntries);

		List<KeyValueBean> checkBoxEntries1 = new ArrayList<KeyValueBean>();
		checkBoxEntries1.add(new KeyValueBean("Basketball","1"));
		checkBoxEntries1.add(new KeyValueBean("Soccer","2"));
		checkBoxEntries1.add(new KeyValueBean("Traveling","3"));
		demoModelData.setCheckBoxEntries1(checkBoxEntries1);

		Map checkBoxEntries2 = new HashMap();
		checkBoxEntries2.put("ZH", "Chinese");
		checkBoxEntries2.put("EN", "English");
		checkBoxEntries2.put("L1", "Korean");
		checkBoxEntries2.put("L2", "Japanese");
		checkBoxEntries2.put("L3", "Other1");
		checkBoxEntries2.put("L4", "Other2");
		
		demoModelData.setCheckBoxEntries2(checkBoxEntries2);
		
		return demoModelData;  
	}  
	
    @InitBinder
    protected void initBinder(DataBinder  binder) {
        binder.setValidator(new DemoModelDataValidator());
    }
    
	@RequestMapping(value = "/preEditData.do")
	public String preEdit( Map<String, Object> modelMap) {
		return "demo/validation";
	}
	
	@RequestMapping(value = "/editData.do")
	public String edit(Map modelMap, @Valid DemoModelData demoModelData,Errors errorResult) {
		try {
	
			if (errorResult.hasErrors()){
	    	   return "demo/validation";
	        }

			
			return "demo/validation";	
		} catch (Exception e) {
			if (e instanceof AppException) {
				super.handleException(e, errorResult);
			} else{//to use a pre-defined error code regarding current business to report current error for end user, ex: 99999
				super.handleException(new AppException(ERROR_SYSTEM_ERROR), errorResult);
			}
			return "demo/validation";
		}
	}

}
