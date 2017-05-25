package edu.mum.rideshare.controller.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import edu.mum.rideshare.controller.view.LcfBaseExcelView;

/**
 * SysCodeListExcelDemoView
 * 
 * 
 * @author mz
 */

@SuppressWarnings({ "unchecked", "rawtypes" })
public class SysCodeListExcelDemoView extends LcfBaseExcelView{

	
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
//        response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
		super.buildExcelDocument(model, workbook, request, response);
	}

	}
