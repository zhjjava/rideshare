package edu.mum.rideshare.controller.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import edu.mum.core.exception.AppException;
import edu.mum.rideshare.model.SysUser;
import edu.mum.rideshare.util.AppConstants;
import edu.mum.rideshare.util.ErrorCodeConstants;
import edu.mum.rideshare.util.ExcelUtil;


//@Controller
/**
*
* 类<strong>LcfBaseExcelView.java</strong>{应用系统常量定义}
*
* @author: mz
* @version: 1.0	Date: 2015-7-5 下午03:21:42
*/
public class LcfBaseExcelView extends AbstractExcelView implements ErrorCodeConstants{
	
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	public static String DEFAULT_SYSTEM_INTERNAL_ERROR = "System Internal Error!";
	
	private static String SYSTEM_ERROR_MSG = null;
	private String usingTemplate ="false";
	
	public LcfBaseExcelView() {
		super();
		SYSTEM_ERROR_MSG = DEFAULT_SYSTEM_INTERNAL_ERROR;
	}
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void buildExcelDocument(Map<String, Object> modelMap,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(AppConstants.EXCEL_TMPL_APPLIED_TRUE.equalsIgnoreCase(usingTemplate)){
			Map otherCellDataMap = (Map)modelMap.get(AppConstants.EXCEL_OTHER_CELL_DATA_MAP);
			List<Object> rowDataList =(List<Object>) modelMap.get(AppConstants.EXCEL_ROW_DATA_LIST);
			String[] propertyNames = (String[]) modelMap.get(AppConstants.EXCEL_PROPERTY_NAMES);
			String dataAreaStartCellNo = (String)modelMap.get(AppConstants.EXCEL_DATA_AREA_START_CELL_NO);//looks like 0,2 OR 1,10 OR 列号,行号
			ExcelUtil.buildWorkBook(workbook, "", rowDataList, propertyNames, otherCellDataMap, dataAreaStartCellNo);
		} else{			
			List<Object> rowDataList =(List<Object>) modelMap.get(AppConstants.EXCEL_ROW_DATA_LIST);
			String[] propertyNames = (String[]) modelMap.get(AppConstants.EXCEL_PROPERTY_NAMES);
			String[] columnHeaderNames = (String[]) modelMap.get(AppConstants.EXCEL_COLUMN_HEADER_NAMES);
			
			ExcelUtil.buildWorkBook(workbook, "", rowDataList, propertyNames, columnHeaderNames);
			
		}
	}
	

	public String getUsingTemplate() {
		return usingTemplate;
	}

	public void setUsingTemplate(String usingTemplate) {
		this.usingTemplate = usingTemplate;
	}

	/**
	 * If exception's type is AppException, it will add error with e.getMessage(getLocale()) in request. If exception's
	 * type isn't AppException, It will add system error in request.
	 * 
	 * @param e
	 */
	protected void handleException(Exception e,BindingResult errorResult) {
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
	
	protected void addActionError(String errorMsg,BindingResult errorResult){
		errorResult.reject(null, errorMsg);
	}
	protected void addActionI18NError(String errorMsgI18NKey,BindingResult errorResult){
		errorResult.reject(errorMsgI18NKey,"");
	}
	protected void addActionI18NError(String errorMsgI18NKey,String defaultMsg, BindingResult errorResult){
		errorResult.reject(errorMsgI18NKey,defaultMsg);
	}
	protected void addActionI18NError(String errorMsgI18NKey,Object[] errorMsgParams, String defaultMsg, BindingResult errorResult){
		errorResult.reject(errorMsgI18NKey, errorMsgParams,defaultMsg);
	}
	
	protected void addActionI18NError(String errorMsgI18NKey,List<Object> errorMsgParamList, String defaultMsg, BindingResult errorResult){
		if(errorMsgParamList!=null){
//		   errorResult.reject(errorMsgI18NKey, errorMsgParamList.toArray(),defaultMsg);
		   this.addActionI18NError(errorMsgI18NKey, errorMsgParamList.toArray(), defaultMsg, errorResult);
		} else{
//			errorResult.reject(errorMsgI18NKey, null,defaultMsg);
		   this.addActionI18NError(errorMsgI18NKey, (Object[])null, defaultMsg, errorResult);
		}
	}
	protected void addActionI18NError(String errorMsgI18NKey,List<Object> errorMsgParamList, BindingResult errorResult){
        this.addActionI18NError(errorMsgI18NKey, errorMsgParamList, SYSTEM_ERROR_MSG, errorResult);
	}	
	protected void addActionI18NError(String errorMsgI18NKey,Object[] errorMsgParams, BindingResult errorResult){
		this.addActionI18NError(errorMsgI18NKey, errorMsgParams, SYSTEM_ERROR_MSG, errorResult);
	}


	//direct message, not i18n message
	protected void addFieldError(String fieldName, String errorMsg,BindingResult errorResult){
		errorResult.rejectValue(fieldName, null, errorMsg);
	}
	protected void addFieldI18NError(String fieldName,String errorMsgI18NKey,BindingResult errorResult){
		errorResult.rejectValue(fieldName, errorMsgI18NKey,"");
	}
	protected void addFieldI18NError(String fieldName,String errorMsgI18NKey,String defaultMsg, BindingResult errorResult){
		errorResult.rejectValue(fieldName, errorMsgI18NKey, defaultMsg);
	}
	protected void addFieldI18NError(String fieldName,String errorMsgI18NKey,Object[] errorMsgParams, String defaultMsg, BindingResult errorResult){
		errorResult.rejectValue(fieldName, errorMsgI18NKey, errorMsgParams,defaultMsg);
	}
	protected void addFieldI18NError(String fieldName,String errorMsgI18NKey,List<Object> errorMsgParamList,String defaultMsg, BindingResult errorResult){
		if(errorMsgParamList!=null){
		   this.addFieldI18NError(fieldName, errorMsgI18NKey, errorMsgParamList.toArray(), defaultMsg, errorResult);
		} else{
			this.addFieldI18NError(fieldName, errorMsgI18NKey, (Object[])null, defaultMsg, errorResult);
		}
	}	
	protected void addFieldI18NError(String fieldName,String errorMsgI18NKey,List<Object> errorMsgParamList, BindingResult errorResult){
		this.addFieldI18NError(fieldName, errorMsgI18NKey, errorMsgParamList, SYSTEM_ERROR_MSG, errorResult);
	}
	
	protected void addFieldI18NError(String fieldName,String errorMsgI18NKey,Object[] errorMsgParams, BindingResult errorResult){		
		this.addFieldI18NError(fieldName, errorMsgI18NKey, errorMsgParams, SYSTEM_ERROR_MSG, errorResult);
	}
}
