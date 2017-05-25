package edu.mum.rideshare.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;

import edu.mum.core.exception.AppException;
import edu.mum.rideshare.util.ErrorCodeConstants;
import edu.mum.rideshare.util.LcFrameworkUtil;



/**
*
*
* @author: mz
* @version: 1.0	Date: 2015-7-5 下午03:21:42
*/
//@Controller
@SuppressWarnings({ "unchecked", "rawtypes" })
public class LcfBaseAjaxController extends LcfBaseController implements ErrorCodeConstants{
	
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	//for ajax call begin
	public static final String IS_SUCCESS = "isSuccess";
	public static final String OP_RESULT_TRUE = "true";
	public static final String OP_RESULT_FALSE = "false";	
	public static final String OP_RETURN_MSG = "opReturnMsg";
	
	public static final String ACTION_ERROR_MSG_LIST = "actionErrorMsgList";
	public static final String FIELDS_ERROR_MSG_MAP = "fieldsErrorMsgMap";
	
	protected static final String EDIT_DATA_ROOT = "editDataR";
	protected static final String DATA_LIST_ROOT = "dataListR";
	protected static final String RECORDS_COUNT = "recordsCount";	
	//for ajax call end
	
	private static String SYSTEM_ERROR_MSG = null;
	
	public LcfBaseAjaxController() {
		super();
		SYSTEM_ERROR_MSG = DEFAULT_SYSTEM_INTERNAL_ERROR;
	}
    
	/**
	 * If exception's type is AppException, it will add error with e.getMessage(getLocale()) in request. If exception's
	 * type isn't AppException, It will add system error in request.
	 * 
	 * for ajax call
	 * @param e
	 */
	
	protected void handleException(Exception e,Map rtnDataMap,HttpServletRequest req) {
		log.error("error", e);
		if (e instanceof AppException) {
			AppException ex = (AppException)e;
			if(ex.getCause()!=null) {
			      log.error("Cause error:",ex.getCause());
			      if(ex.getCause().getCause()!=null){
			    	  log.error("Parent cause errror:",ex.getCause().getCause());
			      }
			}
//			String message = ex.getErrorCode()+", " + getText(String.valueOf(ex.getErrorCode()), ex.getParams());
			String message = "";

			log.error("ErrorCode: " + ex.getErrorCode() + "\nMessage: " + ex.getErrorMsg());
//			if (StringUtils.isBlank(message)) {
//				log.error("handleException() - Can not get error message for the code ["+ex.getErrorCode()+"] from AppException!");
//				//message = ex.getErrorCode()+",Unknown Business Exception!";
//				getActionErrorMsgList(rtnDataMap).add(ex.getErrorCode()+", Unknown Business Exception!");	
//			} else{
//				getActionErrorMsgList(rtnDataMap).add(ex.getErrorCode()+", "+message);	
//			}
			
			if(ex.getErrorCode()!=0){//errorCode's priority is higher than errorMsg
				   this.addActionI18NError(String.valueOf(ex.getErrorCode()), ex.getParams(), SYSTEM_ERROR_MSG,rtnDataMap, req);
			} else if(StringUtils.isNotBlank(ex.getErrorMsg())){//it means there is a direct error message defined, so just add it into the context.
					this.addActionError(ex.getErrorMsg(), rtnDataMap);
			} else{
					this.addActionError(SYSTEM_ERROR_MSG, rtnDataMap);
			}	
		}
		else {
//			getActionErrorMsgList(rtnDataMap).add(SYSTEM_ERROR_MSG);
//			getActionErrorMsgList(rtnDataMap).add(e.getMessage());
			this.addActionError(SYSTEM_ERROR_MSG, rtnDataMap);
			this.addActionError(e.getMessage(), rtnDataMap);
		}
	}
   
	//for wrapping the returning data, in agjax
	
	protected void handleFieldsErrorMsgMap(List<FieldError> fieldErrors, Map rtnDataMap){
		if(CollectionUtils.isEmpty(fieldErrors)){
			return;
		}		
		for(FieldError field : fieldErrors){
			addFieldErrorMsg(field.getField(),field.getDefaultMessage(),rtnDataMap);
		}
	}
	
	private List getActionErrorMsgList(Map rtnDataMap){
		if(rtnDataMap.get(ACTION_ERROR_MSG_LIST)==null){
			rtnDataMap.put(ACTION_ERROR_MSG_LIST, new ArrayList<Object>());
		}		
		List<Object> actionErrorMsgList =  (List <Object>)rtnDataMap.get(ACTION_ERROR_MSG_LIST);	
		return actionErrorMsgList;
	}
	
	private void addFieldErrorMsg(String fieldName, String errorMsg,Map rtnDataMap){
		Map fieldsErrorMsgMap = getFieldsErrorMsgMap(rtnDataMap);		
		if(fieldsErrorMsgMap.get(fieldName)==null){
			fieldsErrorMsgMap.put(fieldName, new ArrayList());
		}		
		List fieldErrorList = (List)fieldsErrorMsgMap.get(fieldName);
		fieldErrorList.add(errorMsg);
	}
	
	private Map getFieldsErrorMsgMap(Map rtnDataMap){
		if(rtnDataMap.get(FIELDS_ERROR_MSG_MAP)==null){
			rtnDataMap.put(FIELDS_ERROR_MSG_MAP, new HashMap());
		}		
		Map fieldsErrorMsgMap = (Map)rtnDataMap.get(FIELDS_ERROR_MSG_MAP);	
		return fieldsErrorMsgMap;
	}
	
//	//not judge the rtnDataMap is null or not, controller must pass in a non-empty rtnDataMap object
//	protected boolean hasFieldErrors(Map rtnDataMap){
//		if(rtnDataMap.get(FIELDS_ERROR_MSG_MAP)==null){
//			return false;
//		}
////		if(((Map)rtnDataMap.get(FIELDS_ERROR_MSG_MAP)).size()>0){
////			return true;
////		}
//		return true;
//	}
	protected boolean hasActionErrors(Map rtnDataMap){
		if(rtnDataMap.get(ACTION_ERROR_MSG_LIST)==null){
			return false;
		}
//		if(((Map)rtnDataMap.get(FIELDS_ERROR_MSG_MAP)).size()>0){
//			return true;
//		}
		return true;
	}
	//for ajax
	
	protected void addActionError(String errorMsg,Map rtnDataMap){
		getActionErrorMsgList(rtnDataMap).add(errorMsg);	
	}
	
	protected void addActionI18NError(String errorMsgI18NKey,Map rtnDataMap,HttpServletRequest req){
		getActionErrorMsgList(rtnDataMap).add(LcFrameworkUtil.getSpringAppContext().getMessage(errorMsgI18NKey, null, "",req.getLocale()));
	}
	
	protected void addActionI18NError(String errorMsgI18NKey,String defaultMsg, Map rtnDataMap,HttpServletRequest req){
		getActionErrorMsgList(rtnDataMap).add(LcFrameworkUtil.getSpringAppContext().getMessage(errorMsgI18NKey, null, defaultMsg,req.getLocale()));
	}

	protected void addActionI18NError(String errorMsgI18NKey,Object[] errorMsgParams, String defaultMsg, Map rtnDataMap,HttpServletRequest req){
		getActionErrorMsgList(rtnDataMap).add(LcFrameworkUtil.getSpringAppContext().getMessage(errorMsgI18NKey, errorMsgParams, defaultMsg,req.getLocale()));
	}
	protected void addActionI18NError(String errorMsgI18NKey,Object[] errorMsgParams, Map rtnDataMap,HttpServletRequest req){
//		getActionErrorMsgList(rtnDataMap).add(LcFrameworkUtil.getSpringAppContext().getMessage(errorMsgI18NKey, errorMsgParams, "",req.getLocale()));
		this.addActionI18NError(errorMsgI18NKey, errorMsgParams, SYSTEM_ERROR_MSG, rtnDataMap, req);
	}
	
	protected void addActionI18NError(String errorMsgI18NKey,List<Object> errorMsgParamList,String defaultMsg,  Map rtnDataMap,HttpServletRequest req){
		if(errorMsgParamList!=null){
//		   getActionErrorMsgList(rtnDataMap).add(LcFrameworkUtil.getSpringAppContext().getMessage(errorMsgI18NKey, errorMsgParamList.toArray(), defaultMsg,req.getLocale()));
		   this.addActionI18NError(errorMsgI18NKey, errorMsgParamList.toArray(), defaultMsg, rtnDataMap, req);
		} else{
//			getActionErrorMsgList(rtnDataMap).add(LcFrameworkUtil.getSpringAppContext().getMessage(errorMsgI18NKey, null, defaultMsg,req.getLocale()));
		   this.addActionI18NError(errorMsgI18NKey, (Object[])null, defaultMsg, rtnDataMap, req);
		}
	}
	protected void addActionI18NError(String errorMsgI18NKey,List<Object> errorMsgParamList, Map rtnDataMap,HttpServletRequest req){
		addActionI18NError(errorMsgI18NKey, errorMsgParamList, SYSTEM_ERROR_MSG,rtnDataMap,req);
	}
	
	//direct message, not i18n message	
	protected void addFieldError(String fieldName, String errorMsg,Map rtnDataMap){
		addFieldErrorMsg(fieldName, errorMsg,rtnDataMap);
	}
	
	protected void addFieldI18NError(String fieldName,String errorMsgI18NKey,Map rtnDataMap,HttpServletRequest req){
		String errorMsg = LcFrameworkUtil.getI18nMsg(errorMsgI18NKey,req.getLocale());
		addFieldErrorMsg(fieldName, errorMsg,rtnDataMap);
	}
	
	protected void addFieldI18NError(String fieldName,String errorMsgI18NKey,String defaultMsg, Map rtnDataMap,HttpServletRequest req){
		String errorMsg = LcFrameworkUtil.getI18nMsg(errorMsgI18NKey, null, defaultMsg,req.getLocale());
		addFieldErrorMsg(fieldName, errorMsg,rtnDataMap);
	}

	protected void addFieldI18NError(String fieldName,String errorMsgI18NKey,Object[] errorMsgParams, String defaultMsg, Map rtnDataMap,HttpServletRequest req){
//		getFieldsErrorMsgMap(rtnDataMap).put(fieldName,  LcFrameworkUtil.getI18nMsg(errorMsgI18NKey, errorMsgParams, defaultMsg,req.getLocale()));
		String errorMsg = LcFrameworkUtil.getI18nMsg(errorMsgI18NKey, errorMsgParams, defaultMsg,req.getLocale());
		addFieldErrorMsg(fieldName, errorMsg,rtnDataMap);
	}
	
	protected void addFieldI18NError(String fieldName,String errorMsgI18NKey,List<Object> errorMsgParamList, String defaultMsg, Map rtnDataMap,HttpServletRequest req){
		if(errorMsgParamList!=null){
//		   getFieldsErrorMsgMap(rtnDataMap).put(fieldName, LcFrameworkUtil.getSpringAppContext().getMessage(errorMsgI18NKey, errorMsgParamList.toArray(), defaultMsg,req.getLocale()));
		   this.addFieldI18NError(fieldName, errorMsgI18NKey, errorMsgParamList.toArray(), defaultMsg,rtnDataMap,req);
		} else{
//		   getFieldsErrorMsgMap(rtnDataMap).put(fieldName, LcFrameworkUtil.getSpringAppContext().getMessage(errorMsgI18NKey, null, defaultMsg,req.getLocale()));
		   this.addFieldI18NError(fieldName, errorMsgI18NKey,  (Object[])null, defaultMsg,rtnDataMap,req);
		}
	}
	
	protected void addFieldI18NError(String fieldName,String errorMsgI18NKey,List<Object> errorMsgParamList, Map rtnDataMap,HttpServletRequest req){
		addFieldI18NError(fieldName, errorMsgI18NKey, errorMsgParamList, SYSTEM_ERROR_MSG,rtnDataMap,req);
	}
	
	protected void addFieldI18NError(String fieldName,String errorMsgI18NKey,Object[] errorMsgParams, Map rtnDataMap,HttpServletRequest req){
//		getFieldsErrorMsgMap(rtnDataMap).put(fieldName,  LcFrameworkUtil.getSpringAppContext().getMessage(errorMsgI18NKey, errorMsgParams, "",req.getLocale()));
		this.addFieldI18NError(fieldName, errorMsgI18NKey, errorMsgParams, SYSTEM_ERROR_MSG, rtnDataMap, req);
	}
}
