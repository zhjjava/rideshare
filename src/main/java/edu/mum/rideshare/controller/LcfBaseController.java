package edu.mum.rideshare.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;

import edu.mum.core.exception.AppException;
import edu.mum.rideshare.model.SysUser;
import edu.mum.rideshare.util.AppConstants;
import edu.mum.rideshare.util.ErrorCodeConstants;


//@Controller
/**
*
* 类<strong>LcfBaseAjaxController.java</strong>{应用系统常量定义}
*
* @author: mz
* @version: 1.0	Date: 2015-7-5 下午03:21:42
*/
public class LcfBaseController implements ErrorCodeConstants{
	
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	public static String DEFAULT_SYSTEM_INTERNAL_ERROR = "System Internal Error!";
	protected static final String MSG_CREATE_SUCCESS = "create.success";
	protected static final String MSG_UPDATE_SUCCESS = "update.success";
	protected static final String MSG_DELETE_SUCCESS = "delete.success";

	protected static final String MSG_CREATE1_SUCCESS = "create1.success"; //with parameters
	protected static final String MSG_UPDATE1_SUCCESS = "update1.success";
	protected static final String MSG_DELETE1_SUCCESS = "delete1.success";
	
//	//for ajax call begin
//	public static final String IS_SUCCESS = "isSuccess";
//	public static final String OP_RESULT_TRUE = "true";
//	public static final String OP_RESULT_FALSE = "false";	
//	public static final String OP_RETURN_MSG = "opReturnMsg";
//	
//	public static final String ACTION_ERROR_MSG_LIST = "actionErrorMsgList";
//	public static final String FIELDS_ERROR_MSG_MAP = "fieldsErrorMsgMap";
//	
//	protected static final String EDIT_DATA_ROOT = "editDataR";
//	protected static final String DATA_LIST_ROOT = "dataListR";
//	protected static final String RECORDS_COUNT = "recordsCount";	
//	//for ajax call end
	
	//return the i18n key/code of the message to front end, meanwhile, maybe also return back the parameters of return message
	@Deprecated
	protected static final String RTN_MSG_CODE = "rtnMsgI18NCode";
	@Deprecated
	protected static final String RTN_MSG_PARAMS = "rtnMsgParams";
	
	public static final String ACTION_MSG_LIST = "actionMsgList";//for showing some message coming from controller for front end user
	protected static final String RTN_ACTION_MSG = "rtnActionMsg";
	
	private static String SYSTEM_ERROR_MSG = null;
	
	public LcfBaseController() {
		super();
		SYSTEM_ERROR_MSG = DEFAULT_SYSTEM_INTERNAL_ERROR;
	}
    
	/**
	 * If exception's type is AppException, it will add error with e.getMessage(getLocale()) in request. If exception's
	 * type isn't AppException, It will add system error in request.
	 * 
	 * @param e
	 */
	protected void handleException(Exception e,Errors errorResult) {
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

			if(ex.getErrorCode()!=0){//errorCode's priority is higher than errorMsg
				   this.addActionI18NError(String.valueOf(ex.getErrorCode()), ex.getParams(), SYSTEM_ERROR_MSG,errorResult);
			} else if(StringUtils.isNotBlank(ex.getErrorMsg())){//it means there is a direct error message defined, so just add it into the context.
					this.addActionError(ex.getErrorMsg(), errorResult);
			} else{
					this.addActionError(SYSTEM_ERROR_MSG, errorResult);
			}			
			
			log.error("ErrorCode: " + ex.getErrorCode() + "\nMessage: " + errorResult.getGlobalError().toString());

		}
		else {
//			errorResult.reject(null,SYSTEM_ERROR_MSG);
//			errorResult.reject(null,e.getMessage());
			this.addActionError(SYSTEM_ERROR_MSG, errorResult);
			this.addActionError(e.getMessage(), errorResult);
		}
	}

	protected SysUser getSessionUser(HttpSession session){
		if(session==null){
			return null;
		}
		return (SysUser)session.getAttribute(AppConstants.SYS_USER_KEY_IN_SESSION);
	}
	
	protected Long getSessionUserId(HttpSession session){
		SysUser user = getSessionUser(session);
		if(user==null){
			return null;
		}
		return user.getUserId();
	}

	protected SysUser getSessionUser(HttpServletRequest req){
		if(req==null){
			return null;
		}
		return getSessionUser(req.getSession());
	}
	protected Long getSessionUserId(HttpServletRequest req){
		if(req==null){
			return null;
		}
		return getSessionUserId(req.getSession());
	}
	
	@SuppressWarnings({ "rawtypes",  "unchecked" })
	private List getActionMsgList(Map map){
		if(map.get(ACTION_MSG_LIST)==null){
			map.put(ACTION_MSG_LIST, new ArrayList<Object>());
		}		
		List<Object> actionMsgList =  (List <Object>)map.get(ACTION_MSG_LIST);	
		return actionMsgList;
	}	
	@SuppressWarnings({ "rawtypes",  "unchecked" })
	private List getActionMsgList(Model springMvcModel){
		if(!springMvcModel.containsAttribute(ACTION_MSG_LIST)){
			springMvcModel.addAttribute(ACTION_MSG_LIST, new ArrayList<Object>());
		}		
		List<Object> actionMsgList =  (List <Object>)springMvcModel.asMap().get(ACTION_MSG_LIST);	
		return actionMsgList;
	}	
	@SuppressWarnings({ "rawtypes",  "unchecked" })
	private List getActionMsgList(ModelMap modelMap){
		if(!modelMap.containsAttribute(ACTION_MSG_LIST)){
			modelMap.addAttribute(ACTION_MSG_LIST, new ArrayList<Object>());
		}		
		List<Object> actionMsgList =  (List <Object>)modelMap.get(ACTION_MSG_LIST);	
		return actionMsgList;
	}

	/**
	 * 
	 * @param errorMsg
	 * @param map
	 */
	protected void addActionMsg(String errorMsg,Map map){
		getActionMsgList(map).add(errorMsg);
	}
	/**
	 * 
	 * @param errorMsg
	 * @param modelMap
	 */
	protected void addActionMsg(String errorMsg,ModelMap modelMap){
		getActionMsgList(modelMap).add(errorMsg);
	}
	
	/**
	 * 
	 * @param errorMsg
	 * @param model
	 */
	protected void addActionMsg(String errorMsg,Model model){
		getActionMsgList(model).add(errorMsg);
	}
	
	
	protected void addActionError(String errorMsg,Errors errorResult){
		errorResult.reject(null, errorMsg);
	}
	protected void addActionI18NError(String errorMsgI18NKey,Errors errorResult){
		errorResult.reject(errorMsgI18NKey,"");
	}
	protected void addActionI18NError(String errorMsgI18NKey,String defaultMsg, Errors errorResult){
		errorResult.reject(errorMsgI18NKey,defaultMsg);
	}
	protected void addActionI18NError(String errorMsgI18NKey,Object[] errorMsgParams, String defaultMsg, Errors errorResult){
		errorResult.reject(errorMsgI18NKey, errorMsgParams,defaultMsg);
	}
	
	protected void addActionI18NError(String errorMsgI18NKey,List<Object> errorMsgParamList, String defaultMsg, Errors errorResult){
		if(errorMsgParamList!=null){
//		   errorResult.reject(errorMsgI18NKey, errorMsgParamList.toArray(),defaultMsg);
		   this.addActionI18NError(errorMsgI18NKey, errorMsgParamList.toArray(), defaultMsg, errorResult);
		} else{
//			errorResult.reject(errorMsgI18NKey, null,defaultMsg);
		   this.addActionI18NError(errorMsgI18NKey, (Object[])null, defaultMsg, errorResult);
		}
	}
	protected void addActionI18NError(String errorMsgI18NKey,List<Object> errorMsgParamList, Errors errorResult){
        this.addActionI18NError(errorMsgI18NKey, errorMsgParamList, SYSTEM_ERROR_MSG, errorResult);
	}	
	protected void addActionI18NError(String errorMsgI18NKey,Object[] errorMsgParams, Errors errorResult){
		this.addActionI18NError(errorMsgI18NKey, errorMsgParams, SYSTEM_ERROR_MSG, errorResult);
	}


	//direct message, not i18n message
	protected void addFieldError(String fieldName, String errorMsg,Errors errorResult){
		errorResult.rejectValue(fieldName, null, errorMsg);
	}
	protected void addFieldI18NError(String fieldName,String errorMsgI18NKey,Errors errorResult){
		errorResult.rejectValue(fieldName, errorMsgI18NKey,"");
	}
	protected void addFieldI18NError(String fieldName,String errorMsgI18NKey,String defaultMsg, Errors errorResult){
		errorResult.rejectValue(fieldName, errorMsgI18NKey, defaultMsg);
	}
	protected void addFieldI18NError(String fieldName,String errorMsgI18NKey,Object[] errorMsgParams, String defaultMsg, Errors errorResult){
		errorResult.rejectValue(fieldName, errorMsgI18NKey, errorMsgParams,defaultMsg);
	}
	protected void addFieldI18NError(String fieldName,String errorMsgI18NKey,List<Object> errorMsgParamList,String defaultMsg, Errors errorResult){
		if(errorMsgParamList!=null){
		   this.addFieldI18NError(fieldName, errorMsgI18NKey, errorMsgParamList.toArray(), defaultMsg, errorResult);
		} else{
			this.addFieldI18NError(fieldName, errorMsgI18NKey, (Object[])null, defaultMsg, errorResult);
		}
	}	
	protected void addFieldI18NError(String fieldName,String errorMsgI18NKey,List<Object> errorMsgParamList, Errors errorResult){
		this.addFieldI18NError(fieldName, errorMsgI18NKey, errorMsgParamList, SYSTEM_ERROR_MSG, errorResult);
	}
	
	protected void addFieldI18NError(String fieldName,String errorMsgI18NKey,Object[] errorMsgParams, Errors errorResult){		
		this.addFieldI18NError(fieldName, errorMsgI18NKey, errorMsgParams, SYSTEM_ERROR_MSG, errorResult);
	}
}
